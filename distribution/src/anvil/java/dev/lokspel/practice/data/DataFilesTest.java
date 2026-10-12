package dev.lokspel.practice.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.lokspel.practice.config.PracticeJourney;
import dev.lokspel.practice.config.PracticeScenarios;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import me.whereareiam.anvil.api.model.scenario.AnvilScenario;
import me.whereareiam.anvil.api.player.SimulatedPlayer;
import me.whereareiam.anvil.api.scenario.AnvilScenarioDefinition;
import me.whereareiam.anvil.api.scenario.ScenarioContext;
import me.whereareiam.anvil.integration.junit.AnvilTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

/**
 * Starts the plugin on the data files 7.9 wrote (arenas, ladders, events, a profile and
 * backend.yml, all written by the plugin before this change) and checks that they load, that saving
 * them keeps every value, and that the saved files load again after a restart.
 */
class DataFilesTest {
  private static final String FIXTURE = "data-from-7.9";

  @Test
  @AnvilTest(DataFrom79.class)
  void dataFrom79LoadsAndSurvivesSaving(ScenarioContext anvil) throws Exception {
    PracticeJourney.assertEnabled(anvil);
    assertNoReadFailures(anvil);

    SimulatedPlayer alice = PracticeJourney.join(anvil, "Alice");
    PracticeJourney.command(alice, "party disband", "You are not a member of a party.");
    alice.destroy();

    Path data = PracticeJourney.dataFolder(anvil);
    anvil.processes().stop(PracticeScenarios.SERVER);

    Path fixture = PracticeScenarios.fixture(FIXTURE);
    List<String> differences = new ArrayList<>();
    List<String> untouched = new ArrayList<>();
    try (Stream<Path> files = Files.walk(fixture)) {
      for (Path original : (Iterable<Path>) files.filter(Files::isRegularFile)::iterator) {
        Path relative = fixture.relativize(original);
        Path saved = data.resolve(relative.toString());
        assertTrue(Files.exists(saved), relative + " is gone");
        if (Files.readString(saved).equals(Files.readString(original))) {
          untouched.add(relative.toString());
        }
        compare("", read(original), read(saved), relative.toString(), differences);
      }
    }
    assertEquals(List.of(), untouched, "files the new code did not write");
    assertEquals(List.of(), differences, "values changed by loading and saving");
    try (Stream<Path> files = Files.list(data.resolve("arenas"))) {
      assertFalse(files.anyMatch(file -> file.toString().contains(".broken-")), "broken copies");
    }

    anvil.processes().start(PracticeScenarios.SERVER);
    PracticeJourney.assertEnabled(anvil);
    assertNoReadFailures(anvil);
  }

  private static void assertNoReadFailures(ScenarioContext anvil) {
    String log =
        String.join("\n", anvil.processes().server(PracticeScenarios.SERVER).console().tail(3000));
    assertFalse(log.contains("Couldn't read"), log);
    assertFalse(log.contains("Exception"), log);
  }

  private static Object read(Path file) throws IOException {
    try (Reader reader = Files.newBufferedReader(file)) {
      return normalize(new Yaml().load(reader));
    }
  }

  /** Numbers compare by value; empty sections count as absent, as they did for the old reader. */
  private static Object normalize(Object value) {
    if (value instanceof Map<?, ?> map) {
      Map<String, Object> normalized = new LinkedHashMap<>();
      map.forEach(
          (key, entry) -> {
            Object inner = normalize(entry);
            if (!(inner instanceof Map<?, ?> section && section.isEmpty())) {
              normalized.put(String.valueOf(key), inner);
            }
          });
      return normalized;
    }
    if (value instanceof List<?> list) {
      return list.stream().map(DataFilesTest::normalize).toList();
    }
    if (value instanceof Number number) {
      return number.doubleValue();
    }

    return value;
  }

  private static void compare(
      String path, Object original, Object saved, String file, List<String> differences) {
    if (original instanceof Map<?, ?> before && saved instanceof Map<?, ?> after) {
      for (Object key : before.keySet()) {
        if (!after.containsKey(key)) {
          differences.add(file + ": " + path + "/" + key + " removed");
          continue;
        }
        compare(path + "/" + key, before.get(key), after.get(key), file, differences);
      }
      for (Object key : after.keySet()) {
        if (!before.containsKey(key) && !isUpdatedOnJoin(file, key)) {
          differences.add(file + ": " + path + "/" + key + " added = " + after.get(key));
        }
      }
      return;
    }
    if (!String.valueOf(original).equals(String.valueOf(saved)) && !isUpdatedOnJoin(file, path)) {
      differences.add(file + ": " + path + " " + original + " -> " + saved);
    }
  }

  /** A profile records its latest join. */
  private static boolean isUpdatedOnJoin(String file, Object path) {
    return file.replace('\\', '/').startsWith("profiles/")
        && String.valueOf(path).endsWith("join/latest");
  }

  /** The plugin folder 7.9 left after a player joined and an owner created a few things. */
  public static final class DataFrom79 implements AnvilScenarioDefinition {
    @Override
    public @NotNull AnvilScenario define() {
      return PracticeScenarios.paper(
          "astralpractice-data-from-7.9", PracticeScenarios.fixture(FIXTURE));
    }
  }
}

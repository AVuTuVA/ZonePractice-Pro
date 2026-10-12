package dev.lokspel.practice.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import me.whereareiam.anvil.api.player.SimulatedPlayer;
import me.whereareiam.anvil.api.scenario.ScenarioContext;
import me.whereareiam.anvil.integration.junit.AnvilTest;
import org.junit.jupiter.api.Test;

/**
 * Starts the plugin on Paper and checks the configuration files it writes and reloads.
 *
 * <p>Runs on Paper 26.1.2 build 74, the newest version Anvil supports; the plugin targets 26.2.
 */
class PracticeConfigTest {
  private static final String[] FILES = {
    "config.yml",
    "language.yml",
    "guis.yml",
    "sidebar.yml",
    "groups.yml",
    "divisions.yml",
    "inventories.yml",
    "playerkit.yml"
  };
  private static final String NO_PARTY = "You are not a member of a party.";
  private static final String RELOADED = "Practice config files have been reloaded.";

  @Test
  @AnvilTest(PracticeScenarios.FreshInstall.class)
  void freshInstall(ScenarioContext anvil) throws Exception {
    PracticeJourney.assertEnabled(anvil);
    Path data = PracticeJourney.dataFolder(anvil);
    for (String file : FILES) {
      assertTrue(Files.isRegularFile(data.resolve(file)), file + " was not written");
    }
    String config = Files.readString(data.resolve("config.yml"));
    assertTrue(config.startsWith("_version: 1\n"), config.lines().findFirst().orElse(""));
    assertTrue(config.lines().anyMatch(line -> line.strip().startsWith("#")), "no comments");

    String guis = Files.readString(data.resolve("guis.yml"));
    assertTrue(guis.contains("ELYTRA:"), "guis.yml does not contain ELYTRA cosmetic");
    assertTrue(guis.contains("firework"), "guis.yml does not contain firework description");

    SimulatedPlayer alice = PracticeJourney.join(anvil, "Alice");
    PracticeJourney.assertLobbyItems(alice);
    PracticeJourney.command(alice, "party disband", NO_PARTY);
    PracticeJourney.console(anvil, "practice reload", RELOADED);
    alice.destroy();
  }

  @Test
  @AnvilTest(PracticeScenarios.UpgradeFrom79.class)
  void upgradeFrom79(ScenarioContext anvil) throws Exception {
    PracticeJourney.assertEnabled(anvil);
    Path data = PracticeJourney.dataFolder(anvil);
    Path language = data.resolve("language.yml");
    for (String file : FILES) {
      assertFalse(
          Files.readString(data.resolve(file))
              .lines()
              .anyMatch(line -> line.startsWith("VERSION:")),
          file + " still has its VERSION key");
    }
    assertTrue(Files.readString(data.resolve("config.yml")).startsWith("_version: 1\n"));
    assertTrue(Files.readString(language).contains("<red>Custom no party"), "custom message lost");
    assertTrue(
        Files.readString(language).lines().anyMatch(line -> line.startsWith("MATCH:")),
        "missing settings were not added");
    assertTrue(Files.isDirectory(data.resolve(".strata/backup")), "no backup of the old files");

    SimulatedPlayer alice = PracticeJourney.join(anvil, "Alice");
    PracticeJourney.command(alice, "party disband", "Custom no party");
    PracticeJourney.command(alice, "party chat", "Party chat is disabled on this server.");

    Files.writeString(
        language, Files.readString(language).replace("Custom no party", "Reloaded no party"));
    PracticeJourney.console(anvil, "practice reload", RELOADED);
    PracticeJourney.command(alice, "party disband", "Reloaded no party");

    String broken = Files.readString(language) + "\nCOMMAND: [this is not a section\n";
    Files.writeString(language, broken);
    PracticeJourney.console(anvil, "practice reload", "Failed to reload practice files");
    assertEquals(broken, Files.readString(language), "a broken file is left as it is");
    PracticeJourney.command(alice, "party disband", "Reloaded no party");
    alice.destroy();
  }
}

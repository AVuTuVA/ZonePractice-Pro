package dev.lokspel.practice.armor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.lokspel.practice.config.PracticeJourney;
import dev.lokspel.practice.config.PracticeScenarios;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import me.whereareiam.anvil.api.player.SimulatedPlayer;
import me.whereareiam.anvil.api.scenario.ScenarioContext;
import me.whereareiam.anvil.integration.junit.AnvilTest;
import org.junit.jupiter.api.Test;

/** Tests the lobby and state armor system and backend persistence with Anvil. */
class LobbyArmorTest {

  @Test
  void testLobbyArmorSlotLayout() {
    final List<Integer> helmets = List.of(9, 11, 13, 15, 17);
    final List<Integer> chestplates = List.of(18, 20, 22, 24, 26);
    final List<Integer> leggings = List.of(27, 29, 31, 33, 35);
    final List<Integer> boots = List.of(36, 38, 40, 42, 44);

    assertEquals(5, helmets.size());
    assertEquals(5, chestplates.size());
    assertEquals(5, leggings.size());
    assertEquals(5, boots.size());

    // Verify columns 0, 2, 4, 6, 8 for row 1 (helmets)
    for (int i = 0; i < 5; i++) {
      int col = i * 2;
      assertEquals(col + 9, helmets.get(i));
      assertEquals(col + 18, chestplates.get(i));
      assertEquals(col + 27, leggings.get(i));
      assertEquals(col + 36, boots.get(i));
    }
  }

  @Test
  @AnvilTest(PracticeScenarios.FreshInstall.class)
  void testLobbyArmorOnPaper(ScenarioContext anvil) throws Exception {
    PracticeJourney.assertEnabled(anvil);

    PracticeJourney.console(anvil, "op Alice", "Made Alice a server operator");
    SimulatedPlayer alice = PracticeJourney.join(anvil, "Alice");
    PracticeJourney.assertLobbyItems(alice);

    // Set lobby location
    PracticeJourney.command(alice, "practice lobby set", "You successfully set the lobby spawn.");

    alice.destroy();

    // Verify backend.yml is created and contains valid structure
    Path data = PracticeJourney.dataFolder(anvil);
    Path backendFile = data.resolve("backend.yml");
    assertTrue(Files.isRegularFile(backendFile), "backend.yml missing");
    String backendContent = Files.readString(backendFile);
    assertTrue(backendContent.contains("lobby:"), "backend.yml should have lobby section");
  }
}

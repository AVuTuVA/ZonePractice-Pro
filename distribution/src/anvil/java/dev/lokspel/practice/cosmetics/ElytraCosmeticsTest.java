package dev.lokspel.practice.cosmetics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.lokspel.practice.config.PracticeJourney;
import dev.lokspel.practice.config.PracticeScenarios;
import dev.lokspel.practice.manager.profile.cosmetics.CosmeticsData;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import me.whereareiam.anvil.api.player.SimulatedPlayer;
import me.whereareiam.anvil.api.scenario.ScenarioContext;
import me.whereareiam.anvil.capability.inventory.Inventory;
import me.whereareiam.anvil.integration.junit.AnvilTest;
import org.junit.jupiter.api.Test;

/** Tests Elytra and Fireworks cosmetic integration on Paper using Anvil. */
class ElytraCosmeticsTest {

  @Test
  void testEnumValuesAndParsing() {
    assertEquals(
        CosmeticsData.LobbyItemType.ELYTRA, CosmeticsData.LobbyItemType.fromString("ELYTRA"));
    assertEquals(
        CosmeticsData.LobbyItemType.ELYTRA, CosmeticsData.LobbyItemType.fromString("elytra"));
    assertEquals(
        CosmeticsData.LobbyItemType.ELYTRA, CosmeticsData.LobbyItemType.fromString("FIREWORK"));
    assertEquals(
        CosmeticsData.LobbyItemType.ELYTRA, CosmeticsData.LobbyItemType.fromString("fireworks"));
    assertEquals(
        CosmeticsData.LobbyItemType.WIND_CHARGE,
        CosmeticsData.LobbyItemType.fromString("WIND_CHARGE"));
    assertEquals(
        CosmeticsData.LobbyItemType.TRIDENT, CosmeticsData.LobbyItemType.fromString("TRIDENT"));
    assertEquals(
        CosmeticsData.LobbyItemType.SPEAR, CosmeticsData.LobbyItemType.fromString("SPEAR"));
    assertEquals(CosmeticsData.LobbyItemType.NONE, CosmeticsData.LobbyItemType.fromString("NONE"));
    assertEquals(CosmeticsData.LobbyItemType.NONE, CosmeticsData.LobbyItemType.fromString(null));
    assertEquals(
        CosmeticsData.LobbyItemType.NONE, CosmeticsData.LobbyItemType.fromString("unknown"));
    assertEquals(
        "ap.cosmetics.lobby.elytra", CosmeticsData.LobbyItemType.ELYTRA.getPermissionNode());
  }

  @Test
  @AnvilTest(PracticeScenarios.ElytraCosmetic.class)
  void testElytraCosmeticAppliedOnJoin(ScenarioContext anvil) throws Exception {
    PracticeJourney.assertEnabled(anvil);

    // Operator Alice so she has permission for the lobby cosmetic
    PracticeJourney.console(anvil, "op Alice", "Made Alice a server operator");

    SimulatedPlayer alice = PracticeJourney.join(anvil, "Alice");

    // Alice joins with profile set to ELYTRA. She should receive lobby items,
    // plus the offhand fireworks (stack of 64) and chestplate elytra.
    alice
        .capability(Inventory.class)
        .matching(
            state -> state.getItems().stream().anyMatch(item -> item.getAmount() == 64),
            Duration.ofSeconds(20));

    alice.destroy();

    // Verify profile file preserves lobby-item: ELYTRA when saved
    Path data = PracticeJourney.dataFolder(anvil);
    Path profileFile = data.resolve("profiles/10920508-d5d8-3eed-93d2-92f193afe7d7.yml");
    assertTrue(Files.isRegularFile(profileFile), "profile file missing");
    String profileContent = Files.readString(profileFile);
    assertTrue(
        profileContent.contains("lobby-item: \"ELYTRA\"")
            || profileContent.contains("lobby-item: ELYTRA"),
        "Saved profile should contain lobby-item: ELYTRA; was:\n" + profileContent);
  }
}

package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class PartyPublicArg {

  private PartyPublicArg() {}

  public static void publicCommand(Player player) {
    if (!player.hasPermission("ap.party.view")) {
      Common.sendMmMessage(player, LanguageManager.getString("PARTY.NO-PERMISSION"));
      return;
    }

    if (!PartyManager.getInstance().hasJoinablePublicParty()) {
      Common.sendMmMessage(player, LanguageManager.getString("PARTY.NO-JOINABLE-PARTY"));
      return;
    }

    GuiManager.getInstance().searchGui(GuiType.Party_PublicParties).open(player);
  }
}

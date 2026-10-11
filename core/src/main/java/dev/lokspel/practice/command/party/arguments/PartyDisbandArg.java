package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class PartyDisbandArg {

  private PartyDisbandArg() {}

  public static void disbandCommand(Player player, String label, String[] args) {
    if (args.length != 1) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.DISBAND.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    Party party = PartyManager.getInstance().getParty(player);
    if (party == null) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.DISBAND.NO-PARTY"));
      return;
    }

    if (!party.getLeader().equals(player)) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.DISBAND.NOT-LEADER"));
      return;
    }

    party.disband();
  }
}

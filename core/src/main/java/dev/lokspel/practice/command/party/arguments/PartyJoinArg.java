package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class PartyJoinArg {

  private PartyJoinArg() {}

  public static void joinCommand(Player player, String label, String[] args) {
    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.ACCEPT.COMMAND-HELP2")
              .replace("%label%", label));
      return;
    }

    PartyAcceptArg.acceptCommand(player, label, args);
  }
}

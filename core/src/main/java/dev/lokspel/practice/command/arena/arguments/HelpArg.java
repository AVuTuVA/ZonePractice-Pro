package dev.lokspel.practice.command.arena.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class HelpArg {

  private HelpArg() {}

  public static void run(Player player, String label) {
    if (player.hasPermission("ap.setup")) {
      for (String line : LanguageManager.getList("COMMAND.ARENA.ARGUMENTS.HELP.ADMIN")) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else if (player.hasPermission("ap.arena.freeze") || player.hasPermission("ap.arena.stop")) {
      for (String line : LanguageManager.getList("COMMAND.ARENA.ARGUMENTS.HELP.STAFF")) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else {
      Common.sendMmMessage(player, LanguageManager.getString("command.arena.no-permission"));
    }
  }

  public static void runSetCommand(Player player, String label) {
    if (player.hasPermission("ap.setup")) {
      for (String line : LanguageManager.getList("COMMAND.ARENA.ARGUMENTS.HELP.SET-COMMAND")) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else {
      Common.sendMmMessage(player, LanguageManager.getString("command.arena.no-permission"));
    }
  }
}

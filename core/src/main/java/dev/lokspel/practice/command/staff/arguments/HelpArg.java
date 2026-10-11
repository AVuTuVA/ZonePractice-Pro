package dev.lokspel.practice.command.staff.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class HelpArg {

  private HelpArg() {}

  public static void run(Player player, String label) {
    if (!player.hasPermission("ap.staffmode")) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.STAFF.NO-PERMISSION"));
      return;
    }

    for (String line : LanguageManager.getList("COMMAND.STAFF.ARGUMENTS.HELP")) {
      Common.sendMmMessage(player, line.replace("%label%", label));
    }
  }

  public static void run(String label) {
    for (String line : LanguageManager.getList("COMMAND.STAFF.ARGUMENTS.HELP")) {
      Common.sendConsoleMmMessage(line.replace("%label%", label));
    }
  }
}

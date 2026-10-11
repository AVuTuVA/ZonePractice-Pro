package dev.lokspel.practice.command.staff.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class HelpArg {

  private HelpArg() {}

  public static void run(Player player, String label) {
    if (!player.hasPermission("ap.staffmode")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getStaff().getNoPermission());
      return;
    }

    for (String line : PracticeConfig.language().getCommand().getStaff().getArguments().getHelp()) {
      Common.sendMmMessage(player, line.replace("%label%", label));
    }
  }

  public static void run(String label) {
    for (String line : PracticeConfig.language().getCommand().getStaff().getArguments().getHelp()) {
      Common.sendConsoleMmMessage(line.replace("%label%", label));
    }
  }
}

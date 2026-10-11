package dev.lokspel.practice.command.practice.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class HelpArg {

  private HelpArg() {}

  public static void run(Player player, String label) {
    if (player.hasPermission("ap.admin")) {
      for (String line :
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getHelp()
              .getAdmin()) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else if (player.hasPermission("ap.staff")) {
      for (String line :
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getHelp()
              .getStaff()) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getPractice().getNoPermission());
    }
  }

  public static void run(String label) {
    for (String line :
        PracticeConfig.language().getCommand().getPractice().getArguments().getHelp().getAdmin()) {
      Common.sendConsoleMmMessage(line.replace("%label%", label));
    }
  }
}

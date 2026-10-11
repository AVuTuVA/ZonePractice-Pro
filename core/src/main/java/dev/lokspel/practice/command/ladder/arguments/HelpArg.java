package dev.lokspel.practice.command.ladder.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class HelpArg {

  private HelpArg() {}

  public static void run(Player player, String label) {
    if (player.hasPermission("ap.setup")) {
      for (String line :
          PracticeConfig.language().getCommand().getLadder().getArguments().getHelp().getAdmin()) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else if (player.hasPermission("ap.ladder.freeze") || player.hasPermission("ap.ladder.stop")) {
      for (String line :
          PracticeConfig.language().getCommand().getLadder().getArguments().getHelp().getStaff()) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getLadder().getNoPermission());
    }
  }

  public static void runSetCommand(Player player, String label) {
    if (player.hasPermission("ap.setup")) {
      for (String line :
          PracticeConfig.language()
              .getCommand()
              .getLadder()
              .getArguments()
              .getHelp()
              .getSetCommand()) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getLadder().getNoPermission());
    }
  }
}

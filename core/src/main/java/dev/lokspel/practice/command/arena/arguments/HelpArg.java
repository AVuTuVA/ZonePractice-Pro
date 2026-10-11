package dev.lokspel.practice.command.arena.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class HelpArg {

  private HelpArg() {}

  public static void run(Player player, String label) {
    if (player.hasPermission("ap.setup")) {
      for (String line :
          PracticeConfig.language().getCommand().getArena().getArguments().getHelp().getAdmin()) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else if (player.hasPermission("ap.arena.freeze") || player.hasPermission("ap.arena.stop")) {
      for (String line :
          PracticeConfig.language().getCommand().getArena().getArguments().getHelp().getStaff()) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getArena().getNoPermission());
    }
  }

  public static void runSetCommand(Player player, String label) {
    if (player.hasPermission("ap.setup")) {
      for (String line :
          PracticeConfig.language()
              .getCommand()
              .getArena()
              .getArguments()
              .getHelp()
              .getSetCommand()) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    } else {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getArena().getNoPermission());
    }
  }
}

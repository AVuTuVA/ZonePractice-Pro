package dev.lokspel.practice.command.ffa.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class HelpArg {

  private HelpArg() {}

  public static void run(Player player, String label) {
    for (String line : PracticeConfig.language().getFfa().getCommand().getHelp()) {
      Common.sendMmMessage(player, line.replace("%label%", label));
    }
  }
}

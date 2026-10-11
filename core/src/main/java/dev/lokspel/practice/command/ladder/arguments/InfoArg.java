package dev.lokspel.practice.command.ladder.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public final class InfoArg {

  private InfoArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getLadder().getNoPermission());
      return;
    }

    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getLadder()
              .getArguments()
              .getInfo()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    Ladder ladder = LadderManager.getInstance().getLadder(args[1]);
    if (ladder == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getLadder()
              .getArguments()
              .getInfo()
              .getNotExists()
              .replace("%ladder%", args[1]));
      return;
    }

    for (String line :
        PracticeConfig.language()
            .getCommand()
            .getLadder()
            .getArguments()
            .getInfo()
            .getLadderInfo()) {
      Common.sendMmMessage(
          player,
          line.replace("%ladder%", ladder.getName())
              .replace("%type%", ladder.getType().getName())
              .replace(
                  "%icon%",
                  ladder.getIcon() != null
                      ? PracticeConfig.language()
                          .getCommand()
                          .getLadder()
                          .getArguments()
                          .getInfo()
                          .getStatusNames()
                          .getSet()
                      : PracticeConfig.language()
                          .getCommand()
                          .getLadder()
                          .getArguments()
                          .getInfo()
                          .getStatusNames()
                          .getNotSet())
              .replace("%displayName%", ladder.getDisplayName())
              .replace(
                  "%inventory%",
                  ladder.getKitData().isSet()
                      ? PracticeConfig.language()
                          .getCommand()
                          .getLadder()
                          .getArguments()
                          .getInfo()
                          .getStatusNames()
                          .getSet()
                      : PracticeConfig.language()
                          .getCommand()
                          .getLadder()
                          .getArguments()
                          .getInfo()
                          .getStatusNames()
                          .getNotSet())
              .replace("%effects%", String.valueOf(ladder.getKitData().getEffects().size()))
              .replace(
                  "%status%",
                  ladder.isEnabled()
                      ? PracticeConfig.language()
                          .getCommand()
                          .getLadder()
                          .getArguments()
                          .getInfo()
                          .getStatusNames()
                          .getEnabled()
                      : PracticeConfig.language()
                          .getCommand()
                          .getLadder()
                          .getArguments()
                          .getInfo()
                          .getStatusNames()
                          .getDisabled()));
    }
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();
    if (!player.hasPermission("ap.setup")) {
      return arguments;
    }

    if (args.length == 2) {
      for (Ladder ladder : LadderManager.getInstance().getLadders()) {
        arguments.add(ladder.getName());
      }

      return org.bukkit.util.StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

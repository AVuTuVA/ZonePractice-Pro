package dev.lokspel.practice.command.ladder.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class StopArg {

  private StopArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.ladder.stop")) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.LADDER.NO-PERMISSION"));
      return;
    }

    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    Ladder ladder = LadderManager.getInstance().getLadder(args[1]);
    if (ladder == null) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.NOT-EXISTS")
              .replace("%ladder%", args[1]));
      return;
    }

    if (!ladder.isEnabled()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.LADDER-DISABLED")
              .replace("%ladder%", ladder.getDisplayName()));
      return;
    }

    List<Match> matches = MatchManager.getInstance().getLiveMatchesByLadder(ladder);
    if (matches.isEmpty()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.NO-MATCHES")
              .replace("%ladder%", ladder.getDisplayName()));
      return;
    }

    for (Match match : matches) {
      match.sendMessage(
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.MATCH-END-MESSAGE")
              .replace("%player%", player.getName()),
          true);
      match.endMatch();
    }
    Common.sendMmMessage(
        player,
        LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.STOP.PLAYER-ENDED")
            .replace("%ladder%", ladder.getDisplayName()));
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();
    if (!player.hasPermission("ap.setup")) {
      return arguments;
    }

    if (args.length == 2) {
      for (Ladder ladder : LadderManager.getInstance().getLadders()) {
        if (!ladder.isEnabled()) {
          continue;
        }

        arguments.add(ladder.getName());
      }

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

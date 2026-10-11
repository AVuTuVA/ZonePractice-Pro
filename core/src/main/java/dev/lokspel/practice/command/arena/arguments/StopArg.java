package dev.lokspel.practice.command.arena.arguments;

import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.arena.arenas.interfaces.DisplayArena;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class StopArg {

  private StopArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.arena.stop")) {
      Common.sendMmMessage(player, LanguageManager.getString("command.arena.no-permission"));
      return;
    }

    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.STOP.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    DisplayArena arena = ArenaManager.getInstance().getArena(args[1]);
    if (arena == null) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.STOP.ARENA-NOT-EXISTS")
              .replace("%arena%", args[1]));
      return;
    }

    if (!arena.isEnabled()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.STOP.ARENA-ENABLED")
              .replace("%arena%", arena.getName()));
      return;
    }

    List<Match> matches = MatchManager.getInstance().getLiveMatchesByArena(arena);
    if (matches.isEmpty()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.STOP.NO-MATCHES")
              .replace("%arena%", arena.getName()));
      return;
    }

    for (Match match : matches) {
      match.sendMessage(
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.STOP.MATCH-END-MESSAGE")
              .replace("%player%", player.getName()),
          true);
      match.endMatch();
    }
    Common.sendMmMessage(
        player, LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.STOP.PLAYER-ENDED"));
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();
    if (!player.hasPermission("ap.setup")) {
      return arguments;
    }

    if (args.length == 2) {
      for (DisplayArena arena : ArenaManager.getInstance().getArenaList()) {
        arguments.add(arena.getName());
      }

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

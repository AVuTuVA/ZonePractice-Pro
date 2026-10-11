package dev.lokspel.practice.command.arena.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.arena.arenas.interfaces.DisplayArena;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class TeleportArg {

  private TeleportArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getArena().getNoPermission());
      return;
    }

    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getArena()
              .getArguments()
              .getTeleport()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    DisplayArena arena = ArenaManager.getInstance().getArena(args[1]);
    if (arena == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getArena()
              .getArguments()
              .getTeleport()
              .getArenaNotExists()
              .replace("%arena%", args[1]));
      return;
    }

    arena.teleport(player);
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

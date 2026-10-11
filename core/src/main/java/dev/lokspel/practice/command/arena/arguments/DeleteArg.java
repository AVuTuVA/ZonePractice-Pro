package dev.lokspel.practice.command.arena.arguments;

import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.arena.arenas.interfaces.DisplayArena;
import dev.lokspel.practice.manager.arena.setup.SpawnMarkerManager;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.setup.arena.ArenaGuiSetupManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class DeleteArg {

  private DeleteArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(player, LanguageManager.getString("command.arena.no-permission"));
      return;
    }

    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("command.arena.arguments.delete.command-help")
              .replace("%label%", label));
      return;
    }

    DisplayArena arena = ArenaManager.getInstance().getArena(args[1]);
    if (arena == null) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("command.arena.arguments.delete.not-exists")
              .replace("%arena%", args[1]));
      return;
    }

    if (arena.isEnabled()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("command.arena.arguments.delete.arena-enabled")
              .replace("%arena%", arena.getName()));
      return;
    }

    // Clear spawn markers before deleting the arena
    SpawnMarkerManager.getInstance().clearMarkers(arena);

    if (arena.deleteData()) {
      ArenaManager.getInstance().getArenaList().remove(arena);
      ArenaGuiSetupManager.getInstance().removeArenaGuis(arena);

      Common.sendMmMessage(
          player,
          LanguageManager.getString("command.arena.arguments.delete.delete-success")
              .replace("%arena%", arena.getName()));
    }
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();
    if (!player.hasPermission("ap.setup")) {
      return arguments;
    }

    if (args.length == 2) {
      for (DisplayArena arena : ArenaManager.getInstance().getArenaList()) {
        if (!arena.isEnabled()) {
          arguments.add(arena.getName());
        }
      }

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

package dev.lokspel.practice.command.arena.arguments.set;

import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.arena.arenas.Arena;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.arena.ArenaGuiSetupManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class PartyFfaCenterArg {

  private PartyFfaCenterArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.ARENA.NO-PERMISSION"));
      return;
    }

    if (args.length != 3) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.PARTYFFACENTER.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    Arena arena = ArenaManager.getInstance().getNormalArena(args[2]);
    if (arena == null) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.PARTYFFACENTER.ARENA-NOT-EXISTS")
              .replace("%arena%", args[2]));
      return;
    }

    if (arena.isEnabled()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.PARTYFFACENTER.ARENA-ENABLED")
              .replace("%arena%", arena.getName()));
      return;
    }

    if (arena.isBuild() && !arena.getCopies().isEmpty()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.PARTYFFACENTER.CANT-EDIT")
              .replace("%arena%", arena.getName()));
      return;
    }

    if (arena.getCuboid() == null) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.PARTYFFACENTER.NO-REGION"));
      return;
    }

    Location center = player.getLocation().clone();
    if (!arena.getCuboid().contains(center)) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.PARTYFFACENTER.POS-OUTSIDE-REGION"));
      return;
    }

    arena.setPartyFfaCenter(center);
    ArenaGuiSetupManager.getInstance()
        .getArenaSetupGuis()
        .get(arena)
        .get(GuiType.Arena_Main)
        .update();
    Common.sendMmMessage(
        player,
        LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.PARTYFFACENTER.SET")
            .replace("%arena%", arena.getName()));
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();
    if (!player.hasPermission("ap.setup")) {
      return arguments;
    }

    if (args.length == 3) {
      for (Arena arena : ArenaManager.getInstance().getNormalArenas()) {
        arguments.add(arena.getName());
      }

      return StringUtil.copyPartialMatches(args[2], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

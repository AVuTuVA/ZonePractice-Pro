package dev.lokspel.practice.command.practice.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class TeleportArg {

  private TeleportArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getPractice().getNoPermission());
      return;
    }

    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getTeleport()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    World world = Bukkit.getWorld(args[1]);
    if (world == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getTeleport()
              .getWorldDontExists()
              .replace("%world%", args[1]));
      return;
    }

    player.teleport(world.getSpawnLocation());
    Common.sendMmMessage(
        player,
        PracticeConfig.language()
            .getCommand()
            .getPractice()
            .getArguments()
            .getTeleport()
            .getTeleported()
            .replace("%world%", world.getName()));
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();

    if (args.length == 2) {
      if (player.hasPermission("ap.setup")) {
        for (World world : Bukkit.getWorlds()) {
          arguments.add(world.getName());
        }
      }

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

package dev.lokspel.practice.command.practice.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.language.Command;
import dev.lokspel.practice.manager.server.ServerManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class ReloadArg {

  private ReloadArg() {}

  private static Command.Practice.Arguments.Reload messages() {
    return PracticeConfig.language().getCommand().getPractice().getArguments().getReload();
  }

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.practice.reload")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getPractice().getNoPermission());
      return;
    }

    if (args.length != 1) {
      Common.sendMmMessage(player, messages().getCommandHelp().replace("%label%", label));
      return;
    }

    if (ServerManager.getInstance().reloadFiles()) {
      Common.sendMmMessage(player, messages().getReloaded());
      return;
    }

    Common.sendMmMessage(player, messages().getFailed());
  }

  public static void run(String label, String[] args) {
    if (args.length != 1) {
      Common.sendConsoleMmMessage(messages().getCommandHelp().replace("%label%", label));
      return;
    }

    if (ServerManager.getInstance().reloadFiles()) {
      Common.sendConsoleMmMessage(messages().getReloaded());
      return;
    }

    Common.sendConsoleMmMessage(messages().getFailed());
  }
}

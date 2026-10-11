package dev.lokspel.practice.command.practice.arguments;

import dev.lokspel.practice.manager.backend.ConfigManager;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.server.ServerManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class ResetArg {

  private ResetArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.practice.reset")) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.PRACTICE.NO-PERMISSION"));
      return;
    }

    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PRACTICE.ARGUMENTS.RESET.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    Profile target =
        ProfileManager.getInstance().getProfile(ServerManager.getInstance().resolvePlayer(args[1]));
    if (target == null) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PRACTICE.ARGUMENTS.RESET.TARGET-OFFLINE")
              .replace("%target%", args[1]));
      return;
    }

    if (player != target.getPlayer()
        && target.getPlayer().isOp()
        && ConfigManager.getBoolean("ADMIN-SETTINGS.OP-BYPASS-FULL-RESET")) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PRACTICE.ARGUMENTS.RESET.TARGET-OP")
              .replace("%target%", target.getPlayer().getName()));
      return;
    }

    for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
      target.getStats().loadDefaultStats(ladder);

      if (target.getUnrankedCustomKits().containsKey(ladder)) {
        target.getUnrankedCustomKits().get(ladder).clear();
      }

      if (target.getRankedCustomKits().containsKey(ladder)) {
        target.getRankedCustomKits().get(ladder).clear();
      }
    }
    target.getStats().setExperience(0);

    Common.sendMmMessage(
        player,
        LanguageManager.getString("COMMAND.PRACTICE.ARGUMENTS.RESET.RESET-PLAYER")
            .replace("%target%", target.getPlayer().getName()));
  }

  public static void run(String label, String[] args) {
    if (args.length != 2) {
      Common.sendConsoleMmMessage(
          LanguageManager.getString("COMMAND.PRACTICE.ARGUMENTS.RESET.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    Profile target =
        ProfileManager.getInstance().getProfile(ServerManager.getInstance().resolvePlayer(args[1]));
    if (target == null) {
      Common.sendConsoleMmMessage(
          LanguageManager.getString("COMMAND.PRACTICE.ARGUMENTS.RESET.TARGET-OFFLINE")
              .replace("%target%", args[1]));
      return;
    }

    for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
      target.getStats().loadDefaultStats(ladder);
      if (target.getUnrankedCustomKits().containsKey(ladder)) {
        target.getUnrankedCustomKits().get(ladder).clear();
      }
      if (target.getRankedCustomKits().containsKey(ladder)) {
        target.getRankedCustomKits().get(ladder).clear();
      }
    }
    target.getStats().setExperience(0);

    Common.sendConsoleMmMessage(
        LanguageManager.getString("COMMAND.PRACTICE.ARGUMENTS.RESET.RESET-PLAYER")
            .replace("%target%", target.getPlayer().getName()));
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();

    if (!player.hasPermission("ap.practice.reset")) {
      return arguments;
    }

    if (args.length == 2) {
      for (Player online : Bukkit.getOnlinePlayers()) {
        arguments.add(online.getName());
      }

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

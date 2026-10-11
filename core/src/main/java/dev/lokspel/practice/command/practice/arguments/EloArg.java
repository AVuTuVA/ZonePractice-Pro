package dev.lokspel.practice.command.practice.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
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

public final class EloArg {

  private EloArg() {}

  public static void run(Player player, String label, String[] args) {
    if (args.length < 2) {
      sendHelp(player, label);
      return;
    }

    if (args[1].equalsIgnoreCase("reset")) {
      if (args.length != 4) {
        sendHelp(player, label);
        return;
      }

      if (!player.hasPermission("ap.practice.elo.default")) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getCommand().getPractice().getNoPermission());
        return;
      }

      Profile target = ProfileManager.getInstance().getProfile(Bukkit.getPlayer(args[2]));

      if (target == null) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getTargetOffline()
                .replace("%target%", args[2]));
        return;
      }

      if (player != target.getPlayer().getPlayer()
          && target.getPlayer().isOp()
          && PracticeConfig.settings().getAdminSettings().isOpBypassEloChange()) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getTargetOp()
                .replace("%target%", target.getPlayer().getName()));
        return;
      }

      int defaultElo = LadderManager.getDEFAULT_ELO();
      if (!args[3].equalsIgnoreCase("*")) {
        NormalLadder ladder = LadderManager.getInstance().getLadder(args[3]);
        if (ladder == null) {
          Common.sendMmMessage(
              player,
              PracticeConfig.language()
                  .getCommand()
                  .getPractice()
                  .getArguments()
                  .getElo()
                  .getLadderNotExists()
                  .replace("%ladder%", args[3]));
          return;
        }

        target.getStats().setElo(ladder, defaultElo);
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getSpecificLadderEloReset()
                .replace("%target%", target.getPlayer().getName())
                .replace("%ladder%", ladder.getDisplayName())
                .replace("%defaultElo%", String.valueOf(defaultElo)));
      } else {
        for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
          if (ladder.isRanked()) {
            target.getStats().setElo(ladder, defaultElo);
          }
        }

        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getAllLadderEloReset()
                .replace("%target%", target.getPlayer().getName())
                .replace("%defaultElo%", String.valueOf(defaultElo)));
      }
    } else if (args[1].equalsIgnoreCase("set")) {
      if (args.length != 5) {
        sendHelp(player, label);
        return;
      }

      if (!player.hasPermission("ap.practice.elo.specific")) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getCommand().getPractice().getNoPermission());
        return;
      }

      Profile target = ProfileManager.getInstance().getProfile(Bukkit.getPlayer(args[2]));

      if (target == null) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getTargetOffline()
                .replace("%target%", args[2]));
        return;
      }

      if (player != target.getPlayer().getPlayer()
          && target.getPlayer().isOp()
          && PracticeConfig.settings().getAdminSettings().isOpBypassEloChange()) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getTargetOp()
                .replace("%target%", target.getPlayer().getName()));
        return;
      }

      int newElo;
      try {
        newElo = Integer.parseInt(args[4]);
      } catch (NumberFormatException exception) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getInvalidNumber());
        return;
      }

      if (newElo < 0) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getInvalidNumber());
        return;
      }

      if (!args[3].equalsIgnoreCase("*")) {
        NormalLadder ladder = LadderManager.getInstance().getLadder(args[3]);
        if (ladder == null) {
          Common.sendMmMessage(
              player,
              PracticeConfig.language()
                  .getCommand()
                  .getPractice()
                  .getArguments()
                  .getElo()
                  .getLadderNotExists()
                  .replace("%ladder%", args[3]));
          return;
        }

        target.getStats().setElo(ladder, newElo);
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getSpecificLadderEloSet()
                .replace("%target%", target.getPlayer().getName())
                .replace("%ladder%", ladder.getDisplayName())
                .replace("%newElo%", String.valueOf(newElo)));
      } else {
        for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
          if (ladder.isRanked()) {
            target.getStats().setElo(ladder, newElo);
          }
        }

        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getAllLadderEloSet()
                .replace("%target%", target.getPlayer().getName())
                .replace("%newElo%", String.valueOf(newElo)));
      }
    } else {
      sendHelp(player, label);
    }
  }

  public static void run(String label, String[] args) {
    if (args.length < 2) {
      sendHelp(label);
      return;
    }

    if (args[1].equalsIgnoreCase("reset")) {
      if (args.length != 4) {
        sendHelp(label);
        return;
      }

      Profile target =
          ProfileManager.getInstance()
              .getProfile(ServerManager.getInstance().resolvePlayer(args[2]));
      if (target == null) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getTargetOffline()
                .replace("%target%", args[2]));
        return;
      }

      int defaultElo = LadderManager.getDEFAULT_ELO();

      if (!args[3].equalsIgnoreCase("*")) {
        NormalLadder ladder = LadderManager.getInstance().getLadder(args[3]);
        if (ladder == null) {
          Common.sendConsoleMmMessage(
              PracticeConfig.language()
                  .getCommand()
                  .getPractice()
                  .getArguments()
                  .getElo()
                  .getLadderNotExists()
                  .replace("%ladder%", args[3]));
          return;
        }

        target.getStats().setElo(ladder, defaultElo);
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getSpecificLadderEloReset()
                .replace("%target%", target.getPlayer().getName())
                .replace("%ladder%", ladder.getDisplayName())
                .replace("%defaultElo%", String.valueOf(defaultElo)));
      } else {
        for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
          if (ladder.isRanked()) {
            target.getStats().setElo(ladder, defaultElo);
          }
        }

        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getAllLadderEloReset()
                .replace("%target%", target.getPlayer().getName())
                .replace("%defaultElo%", String.valueOf(defaultElo)));
      }
    } else if (args[1].equalsIgnoreCase("set")) {
      if (args.length != 5) {
        sendHelp(label);
        return;
      }

      Profile target =
          ProfileManager.getInstance()
              .getProfile(ServerManager.getInstance().resolvePlayer(args[2]));
      if (target == null) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getTargetOffline()
                .replace("%target%", args[2]));
        return;
      }

      int newElo;
      try {
        newElo = Integer.parseInt(args[4]);
      } catch (NumberFormatException exception) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getInvalidNumber());
        return;
      }

      if (newElo < 0) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getInvalidNumber());
        return;
      }

      if (!args[3].equalsIgnoreCase("*")) {
        NormalLadder ladder = LadderManager.getInstance().getLadder(args[3]);
        if (ladder == null) {
          Common.sendConsoleMmMessage(
              PracticeConfig.language()
                  .getCommand()
                  .getPractice()
                  .getArguments()
                  .getElo()
                  .getLadderNotExists()
                  .replace("%ladder%", args[3]));
          return;
        }

        target.getStats().setElo(ladder, newElo);
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getSpecificLadderEloSet()
                .replace("%target%", target.getPlayer().getName())
                .replace("%ladder%", ladder.getDisplayName())
                .replace("%newElo%", String.valueOf(newElo)));
      } else {
        for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
          if (ladder.isRanked()) {
            target.getStats().setElo(ladder, newElo);
          }
        }

        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getElo()
                .getAllLadderEloSet()
                .replace("%target%", target.getPlayer().getName())
                .replace("%newElo%", String.valueOf(newElo)));
      }
    } else {
      sendHelp(label);
    }
  }

  private static void sendHelp(Player player, String label) {
    for (String line :
        PracticeConfig.language()
            .getCommand()
            .getPractice()
            .getArguments()
            .getElo()
            .getCommandHelp()) {
      Common.sendMmMessage(player, line.replace("%label%", label));
    }
  }

  private static void sendHelp(String label) {
    for (String line :
        PracticeConfig.language()
            .getCommand()
            .getPractice()
            .getArguments()
            .getElo()
            .getCommandHelp()) {
      Common.sendConsoleMmMessage(line.replace("%label%", label));
    }
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();

    if (args.length == 2) {
      if (player.hasPermission("ap.practice.elo.default")) {
        arguments.add("reset");
      }
      if (player.hasPermission("ap.practice.elo.specific")) {
        arguments.add("set");
      }

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    } else if (args.length == 3) {
      if (player.hasPermission("ap.practice.elo.default") && args[1].equalsIgnoreCase("reset")) {
        for (Player online : Bukkit.getOnlinePlayers()) {
          arguments.add(online.getName());
        }
      } else if (player.hasPermission("ap.practice.elo.specific")
          && args[1].equalsIgnoreCase("set")) {
        for (Player online : Bukkit.getOnlinePlayers()) {
          arguments.add(online.getName());
        }
      }

      return StringUtil.copyPartialMatches(args[2], arguments, new ArrayList<>());
    } else if (args.length == 4) {
      if (player.hasPermission("ap.practice.elo.default") && args[1].equalsIgnoreCase("reset")) {
        for (Ladder ladder : LadderManager.getInstance().getLadders()) {
          arguments.add(ladder.getName());
        }

        arguments.add("*");
      } else if (player.hasPermission("ap.practice.elo.specific")
          && args[1].equalsIgnoreCase("set")) {
        for (Ladder ladder : LadderManager.getInstance().getLadders()) {
          arguments.add(ladder.getName());
        }

        arguments.add("*");
      }

      return StringUtil.copyPartialMatches(args[3], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

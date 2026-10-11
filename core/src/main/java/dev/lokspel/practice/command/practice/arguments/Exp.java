package dev.lokspel.practice.command.practice.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.division.DivisionManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class Exp {

  private Exp() {}

  public static void run(Player player, String label, String[] args) {
    if (args.length < 2) {
      sendHelp(player, label);
      return;
    }

    if (args[1].equalsIgnoreCase("reset")) {

      // /prac exp reset <player>
      if (args.length != 3) {
        sendHelp(player, label);
        return;
      }

      if (!player.hasPermission("ap.practice.exp.reset")) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getCommand().getPractice().getNoPermission());
        return;
      }

      Profile target = ProfileManager.getInstance().getProfile(Bukkit.getPlayer(args[2]));

      if (get(player, args, target)) {
        return;
      }

      target.getStats().setExperience(0);
      target.getStats().setDivision(DivisionManager.getInstance().getDivision(target));

      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getExperience()
              .getTargetReset()
              .replace("%target%", target.getPlayer().getName()));
    } else if (args[1].equalsIgnoreCase("add")) {
      // /prac exp add <player> <number>
      if (args.length != 4) {
        sendHelp(player, label);
        return;
      }

      if (!player.hasPermission("ap.practice.exp.add")) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getCommand().getPractice().getNoPermission());
        return;
      }

      Profile target = ProfileManager.getInstance().getProfile(Bukkit.getPlayer(args[2]));

      if (get(player, args, target)) {
        return;
      }

      final int extraExp;
      try {
        extraExp = Integer.parseInt(args[3]);
      } catch (NumberFormatException exception) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getInvalidNumber());
        return;
      }

      if (extraExp <= 0) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getInvalidNumber());
        return;
      }

      target.getStats().setExperience(target.getStats().getExperience() + extraExp);
      target.getStats().setDivision(DivisionManager.getInstance().getDivision(target));

      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getExperience()
              .getAddExtraExp()
              .replace("%extraExp%", String.valueOf(extraExp))
              .replace("%target%", target.getPlayer().getName())
              .replace("%newExp%", String.valueOf(target.getStats().getExperience())));
    } else if (args[1].equalsIgnoreCase("set")) {
      // /prac exp set <player> <number>
      if (args.length != 4) {
        sendHelp(player, label);
        return;
      }

      if (!player.hasPermission("ap.practice.exp.set")) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getCommand().getPractice().getNoPermission());
        return;
      }

      Profile target = ProfileManager.getInstance().getProfile(Bukkit.getPlayer(args[2]));

      if (get(player, args, target)) {
        return;
      }

      final int setExp;
      try {
        setExp = Integer.parseInt(args[3]);
      } catch (NumberFormatException exception) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getInvalidNumber());
        return;
      }

      if (setExp <= 0) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getInvalidNumber());
        return;
      }

      target.getStats().setExperience(setExp);
      target.getStats().setDivision(DivisionManager.getInstance().getDivision(target));

      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getExperience()
              .getSetExp()
              .replace("%newExp%", String.valueOf(setExp))
              .replace("%target%", target.getPlayer().getName()));
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

      // /prac exp reset <player>
      if (args.length != 3) {
        sendHelp(label);
        return;
      }

      Profile target = ProfileManager.getInstance().getProfile(Bukkit.getPlayer(args[2]));

      if (target == null) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getTargetNotFound()
                .replace("%target%", args[2]));
        return;
      }

      target.getStats().setExperience(0);
      target.getStats().setDivision(DivisionManager.getInstance().getDivision(target));

      Common.sendConsoleMmMessage(
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getExperience()
              .getTargetReset()
              .replace("%target%", target.getPlayer().getName()));
    } else if (args[1].equalsIgnoreCase("add")) {
      // /prac exp add <player> <number>
      if (args.length != 4) {
        sendHelp(label);
        return;
      }

      Profile target = ProfileManager.getInstance().getProfile(Bukkit.getPlayer(args[2]));

      if (target == null) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getTargetNotFound()
                .replace("%target%", args[2]));
        return;
      }

      final int extraExp;
      try {
        extraExp = Integer.parseInt(args[3]);
      } catch (NumberFormatException exception) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getInvalidNumber());
        return;
      }

      if (extraExp <= 0) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getInvalidNumber());
        return;
      }

      target.getStats().setExperience(target.getStats().getExperience() + extraExp);
      target.getStats().setDivision(DivisionManager.getInstance().getDivision(target));

      Common.sendConsoleMmMessage(
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getExperience()
              .getAddExtraExp()
              .replace("%extraExp%", String.valueOf(extraExp))
              .replace("%target%", target.getPlayer().getName())
              .replace("%newExp%", String.valueOf(target.getStats().getExperience())));
    } else if (args[1].equalsIgnoreCase("set")) {
      // /prac exp set <player> <number>
      if (args.length != 4) {
        sendHelp(label);
        return;
      }

      Profile target = ProfileManager.getInstance().getProfile(Bukkit.getPlayer(args[2]));

      if (target == null) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getTargetNotFound()
                .replace("%target%", args[2]));
        return;
      }

      final int setExp;
      try {
        setExp = Integer.parseInt(args[3]);
      } catch (NumberFormatException exception) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getInvalidNumber());
        return;
      }

      if (setExp <= 0) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getPractice()
                .getArguments()
                .getExperience()
                .getInvalidNumber());
        return;
      }

      target.getStats().setExperience(setExp);
      target.getStats().setDivision(DivisionManager.getInstance().getDivision(target));

      Common.sendConsoleMmMessage(
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getExperience()
              .getSetExp()
              .replace("%newExp%", String.valueOf(setExp))
              .replace("%target%", target.getPlayer().getName()));
    } else {
      sendHelp(label);
    }
  }

  private static boolean get(Player player, String[] args, Profile target) {
    if (target == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getExperience()
              .getTargetNotFound()
              .replace("%target%", args[2]));
      return true;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile != target
        && target.getPlayer().isOp()
        && PracticeConfig.settings().getAdminSettings().isOpBypassExpChange()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getExperience()
              .getTargetOp()
              .replace("%target%", args[2]));
      return true;
    }
    return false;
  }

  private static void sendHelp(Player player, String label) {
    if (player.hasPermission("ap.practice.exp.reset")
        || player.hasPermission("ap.practice.exp.set")
        || player.hasPermission("ap.practice.exp.add")) {
      for (String line :
          PracticeConfig.language()
              .getCommand()
              .getPractice()
              .getArguments()
              .getExperience()
              .getCommandHelp()) {
        Common.sendMmMessage(player, line.replace("%label%", label));
      }
    }
  }

  private static void sendHelp(String label) {
    for (String line :
        PracticeConfig.language()
            .getCommand()
            .getPractice()
            .getArguments()
            .getExperience()
            .getCommandHelp()) {
      Common.sendConsoleMmMessage(line.replace("%label%", label));
    }
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();

    if (args.length == 2) {
      if (player.hasPermission("ap.practice.exp.reset")) {
        arguments.add("reset");
      }
      if (player.hasPermission("ap.practice.exp.add")) {
        arguments.add("add");
      }
      if (player.hasPermission("ap.practice.exp.set")) {
        arguments.add("set");
      }

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    } else if (args.length == 3) {
      if (player.hasPermission("ap.practice.exp.reset")
          || player.hasPermission("ap.practice.exp.add")
          || player.hasPermission("ap.practice.exp.set")) {
        for (Player online : Bukkit.getOnlinePlayers()) {
          arguments.add(online.getName());
        }
      }

      return StringUtil.copyPartialMatches(args[2], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

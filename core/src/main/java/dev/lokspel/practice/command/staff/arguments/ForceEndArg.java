package dev.lokspel.practice.command.staff.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.interfaces.Event;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import dev.lokspel.practice.manager.fight.match.runnable.round.RoundEndRunnable;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class ForceEndArg {
  private ForceEndArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.staffmode.forceend")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getStaff().getNoPermission());
      return;
    }

    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (!profile.isStaffMode()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getOnlyInStaffmode());
      return;
    }

    Player target = Bukkit.getPlayer(args[1]);
    if (target == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getTargetOffline()
              .replace("%target%", args[1]));
      return;
    }

    Profile targetProfile = ProfileManager.getInstance().getProfile(target);
    if (targetProfile.getStatus().equals(ProfileStatus.MATCH)) {
      Match match = MatchManager.getInstance().getLiveMatchByPlayer(target);
      RoundEndRunnable roundEndRunnable = match.getCurrentRound().getRoundEndRunnable();

      if (roundEndRunnable != null && roundEndRunnable.isRunning() && roundEndRunnable.isEnded()) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getStaff()
                .getArguments()
                .getForceEnd()
                .getMatch()
                .getEnded());
        return;
      }

      for (Player matchPlayer : match.getPlayers()) {
        if (player != matchPlayer && matchPlayer.hasPermission("ap.bypass.forceend")) {
          Common.sendMmMessage(
              player,
              PracticeConfig.language()
                  .getCommand()
                  .getStaff()
                  .getArguments()
                  .getForceEnd()
                  .getMatch()
                  .getCantEnd());
          return;
        }
      }

      match.endMatch();
      match.sendMessage(
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getMatch()
              .getMatchEndMsg()
              .replace("%player%", player.getName()),
          false);
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getMatch()
              .getPlayerEndMsg()
              .replace("%target%", target.getName()));
    } else if (targetProfile.getStatus().equals(ProfileStatus.EVENT)) {
      Event event = EventManager.getInstance().getEventByPlayer(target);

      if (event.getEndRunnable() != null && event.getEndRunnable().isRunning()) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getStaff()
                .getArguments()
                .getForceEnd()
                .getEvent()
                .getEnded());
        return;
      }

      for (Player matchPlayer : event.getPlayers()) {
        if (player != matchPlayer && matchPlayer.hasPermission("ap.bypass.forceend")) {
          Common.sendMmMessage(
              player,
              PracticeConfig.language()
                  .getCommand()
                  .getStaff()
                  .getArguments()
                  .getForceEnd()
                  .getEvent()
                  .getCantEnd());
          return;
        }
      }

      event.forceEnd(player);
    } else {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getPlayerInactive());
    }
  }

  public static void run(String label, String[] args) {
    if (args.length != 2) {
      Common.sendConsoleMmMessage(
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    Player target = Bukkit.getPlayer(args[1]);
    if (target == null) {
      Common.sendConsoleMmMessage(
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getTargetOffline()
              .replace("%target%", args[1]));
      return;
    }

    Profile targetProfile = ProfileManager.getInstance().getProfile(target);
    if (targetProfile.getStatus().equals(ProfileStatus.MATCH)) {
      Match match = MatchManager.getInstance().getLiveMatchByPlayer(target);
      RoundEndRunnable roundEndRunnable = match.getCurrentRound().getRoundEndRunnable();

      if (roundEndRunnable != null && roundEndRunnable.isRunning() && roundEndRunnable.isEnded()) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getStaff()
                .getArguments()
                .getForceEnd()
                .getMatch()
                .getEnded());
        return;
      }

      match.sendMessage(
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getMatch()
              .getMatchEndMsg()
              .replace("%player%", PracticeConfig.language().getConsoleName()),
          false);
      Common.sendConsoleMmMessage(
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getMatch()
              .getPlayerEndMsg()
              .replace("%target%", target.getName()));

      match.endMatch();
    } else if (targetProfile.getStatus().equals(ProfileStatus.EVENT)) {
      Event event = EventManager.getInstance().getEventByPlayer(target);

      if (event.getEndRunnable() != null && event.getEndRunnable().isRunning()) {
        Common.sendConsoleMmMessage(
            PracticeConfig.language()
                .getCommand()
                .getStaff()
                .getArguments()
                .getForceEnd()
                .getEvent()
                .getEnded());
        return;
      }

      Common.sendConsoleMmMessage(
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getEvent()
              .getPlayerEndMsg());
      event.sendMessage(
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getEvent()
              .getMatchEndMsg()
              .replace("%player%", PracticeConfig.language().getConsoleName()),
          true);

      event.forceEnd(null);
    } else {
      Common.sendConsoleMmMessage(
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getForceEnd()
              .getPlayerInactive());
    }
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();

    if (!player.hasPermission("ap.staffmode.forceend")) {
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

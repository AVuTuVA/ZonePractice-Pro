package dev.lokspel.practice.command.singlecommands;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.interfaces.Event;
import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import dev.lokspel.practice.manager.fight.ffa.game.Ffa;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class SpectateCommand implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player player)) {
      Common.sendConsoleMmMessage(PracticeConfig.language().getCantUseConsole());
      return false;
    }

    if (!player.hasPermission("ap.spectate")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getSpectate().getNoSpectate());
      return false;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if ((!profile.getStatus().equals(ProfileStatus.LOBBY)
            && !profile.getStatus().equals(ProfileStatus.SPECTATE))
        || profile.isParty()
        || profile.isStaffMode()) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getSpectate().getCantSpectate());
      return false;
    }

    if (args.length != 1) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getSpectate()
              .getCommandHelp()
              .replace("%label%", label));
      return false;
    }

    Player target = Bukkit.getPlayer(args[0]);
    if (target == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getSpectate()
              .getTargetOffline()
              .replace("%target%", args[0]));
      return false;
    }

    if (target == player) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getSpectate().getCantSpectate2());
      return false;
    }

    Profile targetProfile = ProfileManager.getInstance().getProfile(target);
    if (targetProfile.getStatus().equals(ProfileStatus.MATCH)) {
      Match match = MatchManager.getInstance().getLiveMatchByPlayer(target);

      if (match != null) {
        match.addSpectator(player, target, true, true);
      }
    } else if (targetProfile.getStatus().equals(ProfileStatus.EVENT)) {
      Event event = EventManager.getInstance().getEventByPlayer(target);

      if (event != null) {
        event.addSpectator(player, target, true, true);
      }
    } else if (targetProfile.getStatus().equals(ProfileStatus.FFA)) {
      Ffa ffa = FfaManager.getInstance().getFfaByPlayer(target);

      if (ffa != null) {
        ffa.addSpectator(player, target, true, true);
      }
    } else {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getSpectate().getPlayerInactive());
    }

    return true;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command command, String alias, String[] args) {
    List<String> arguments = new ArrayList<>();
    List<String> completion = new ArrayList<>();
    if (!(sender instanceof Player player)) {
      return arguments;
    }
    if (!player.hasPermission("ap.spectate")) {
      return arguments;
    }

    if (args.length == 1) {
      for (Player online : Bukkit.getOnlinePlayers()) {
        if (player != online) {
          arguments.add(online.getName());
        }
      }

      StringUtil.copyPartialMatches(args[0], arguments, completion);
    }

    Collections.sort(completion);
    return completion;
  }
}

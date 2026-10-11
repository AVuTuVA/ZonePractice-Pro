package dev.lokspel.practice.command.singlecommands;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class IgnoreQueueCommand implements CommandExecutor, TabExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player player)) {
      Common.sendConsoleMmMessage(PracticeConfig.language().getCantUseConsole());
      return false;
    }

    if (!player.hasPermission("ap.ignorequeue")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getIgnoreQueue().getNoPerm());
      return false;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);

    if (args.length != 1) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getIgnoreQueue().getUse());
      return false;
    }

    Profile target = ProfileManager.getInstance().getProfile(Bukkit.getPlayer(args[0]));
    if (target == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getIgnoreQueue()
              .getPlayerOffline()
              .replace("%target%", args[0]));
      return false;
    }

    if (profile == target) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getIgnoreQueue().getCantYourself());
      return false;
    }

    if (!profile.getIgnoredPlayers().contains(target)) {
      profile.getIgnoredPlayers().add(target);
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getIgnoreQueue()
              .getIgnoreOn()
              .replace("%target%", args[0]));
    } else {
      profile.getIgnoredPlayers().remove(target);
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getIgnoreQueue()
              .getIgnoreOff()
              .replace("%target%", args[0]));
    }

    return true;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command cmd, String label, String[] args) {
    List<String> arguments = new ArrayList<>();
    List<String> completion = new ArrayList<>();

    if (!(sender instanceof Player player)) {
      return arguments;
    }

    if (!player.hasPermission("ap.ignorequeue")) {
      return arguments;
    }

    if (args.length == 1) {
      for (Player online : Bukkit.getOnlinePlayers()) {
        arguments.add(online.getName());
      }

      StringUtil.copyPartialMatches(args[0], arguments, completion);
    }

    Collections.sort(completion);
    return completion;
  }
}

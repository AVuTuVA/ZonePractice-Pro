package dev.lokspel.practice.command.privatemessage;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand extends PrivateMessageCommand {

  @Getter public static HashMap<Player, Player> latestMessage = new HashMap<>();

  public MessageCommand() {
    super("message", new String[] {"m", "msg", "tell", "whisper", "w"});
  }

  @Override
  public void executeCommand(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player player)) {
      Common.sendConsoleMmMessage(PracticeConfig.language().getCantUseConsole());
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);

    if (args.length < 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPrivateMessage()
              .getMessage()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    if (!profile.isPrivateMessages()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language().getCommand().getPrivateMessage().getMessage().getCantSend());
      return;
    }

    Player target = Bukkit.getPlayer(args[0]);
    if (target == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPrivateMessage()
              .getMessage()
              .getTargetOffline()
              .replace("%target%", args[0]));
      return;
    }

    Profile targetProfile = ProfileManager.getInstance().getProfile(target);
    if (player != target
        && targetProfile.isHideFromPlayers()
        && !player.hasPermission("ap.staffmode.see")) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPrivateMessage()
              .getMessage()
              .getTargetHide()
              .replace("%target%", target.getName()));
      return;
    }

    if (!targetProfile.isPrivateMessages() && !player.hasPermission("ap.bypass.privatemessage")) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getPrivateMessage()
              .getMessage()
              .getCantSend2()
              .replace("%target%", target.getName()));
      return;
    }

    StringBuilder message = new StringBuilder();
    for (int i = 1; i < args.length; i++) {
      message.append(args[i]).append(" ");
    }

    Common.sendMmMessage(
        player,
        PracticeConfig.language()
            .getCommand()
            .getPrivateMessage()
            .getMessage()
            .getSenderMessage()
            .replace("%sender%", player.getName())
            .replace("%receiver%", target.getName())
            .replace("%message%", message.toString()));
    Common.sendMmMessage(
        target,
        PracticeConfig.language()
            .getCommand()
            .getPrivateMessage()
            .getMessage()
            .getReceiverMessage()
            .replace("%sender%", player.getName())
            .replace("%receiver%", target.getName())
            .replace("%message%", message.toString()));

    latestMessage.put(target, player);
    latestMessage.put(player, target);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    List<String> results = new ArrayList<>();
    if (!(sender instanceof Player player)) {
      return results;
    }

    if (args.length == 1) {
      List<String> onlineNames = new ArrayList<>();

      for (Player online : Bukkit.getOnlinePlayers()) {
        Profile onlineProfile = ProfileManager.getInstance().getProfile(online);
        if (player != online
            && (!onlineProfile.isHideFromPlayers() || player.hasPermission("ap.staffmode.see"))
            && (onlineProfile.isPrivateMessages()
                || player.hasPermission("ap.bypass.privatemessage"))) {
          onlineNames.add(online.getName());
        }
      }

      return org.bukkit.util.StringUtil.copyPartialMatches(args[0], onlineNames, new ArrayList<>());
    }

    return results;
  }
}

package dev.lokspel.practice.command.privatemessage;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand extends PrivateMessageCommand {

  public ReplyCommand() {
    super("reply", new String[] {"r", "re"});
  }

  @Override
  public void executeCommand(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player player)) {
      Common.sendConsoleMmMessage(LanguageManager.getString("CANT-USE-CONSOLE"));
      return;
    }

    if (args.length < 1) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PRIVATE-MESSAGE.REPLY.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    if (!MessageCommand.getLatestMessage().containsKey(player)) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.PRIVATE-MESSAGE.REPLY.NO-MESSAGE"));
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (!profile.isPrivateMessages()) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.PRIVATE-MESSAGE.REPLY.CANT-REPLY"));
      return;
    }

    Player target = MessageCommand.getLatestMessage().get(player);
    if (!target.isOnline()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PRIVATE-MESSAGE.REPLY.PLAYER-LEFT")
              .replace("%target%", target.getName()));
      return;
    }

    Profile targetProfile = ProfileManager.getInstance().getProfile(target);
    if (!targetProfile.isPrivateMessages() && !player.hasPermission("ap.bypass.privatemessage")) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PRIVATE-MESSAGE.REPLY.CANT-REPLY2")
              .replace("%target%", target.getName()));
      return;
    }

    StringBuilder message = new StringBuilder();
    for (String arg : args) {
      message.append(arg).append(" ");
    }

    Common.sendMmMessage(
        player,
        LanguageManager.getString("COMMAND.PRIVATE-MESSAGE.REPLY.SENDER-MESSAGE")
            .replace("%sender%", player.getName())
            .replace("%receiver%", target.getName())
            .replace("%message%", message.toString()));
    Common.sendMmMessage(
        target,
        LanguageManager.getString("COMMAND.PRIVATE-MESSAGE.REPLY.RECEIVER-MESSAGE")
            .replace("%sender%", player.getName())
            .replace("%receiver%", target.getName())
            .replace("%message%", message.toString()));

    MessageCommand.getLatestMessage().put(target, player);
    MessageCommand.getLatestMessage().put(player, target);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    return null;
  }
}

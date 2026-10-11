package dev.lokspel.practice.command.singlecommands;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player player)) {
      Common.sendConsoleMmMessage(PracticeConfig.language().getCantUseConsole());
      return false;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);

    if (!profile.getStatus().equals(ProfileStatus.LOBBY)
        && !profile.getStatus().equals(ProfileStatus.STAFF_MODE)) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getSettings().getCantUse());
      return false;
    }

    if (!player.hasPermission("ap.settings.open")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getSettings().getNoPermission());
      return false;
    }

    profile.getSettingsGui().open(player);

    return true;
  }
}

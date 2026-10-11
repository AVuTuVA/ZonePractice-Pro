package dev.lokspel.practice.command.singlecommands;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditorCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player player)) {
      return false;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);

    if (!profile.getStatus().equals(ProfileStatus.LOBBY) || profile.isStaffMode()) {
      Common.sendMmMessage(player, PracticeConfig.language().getCantUseCommand());
      return false;
    }

    if (profile.getGroup() == null || profile.getGroup().getCustomKitLimit() <= 0) {
      GuiManager.getInstance().searchGui(GuiType.CustomLadder_Selector).open(player);
    } else {
      GuiManager.getInstance().searchGui(GuiType.CustomLadder_EditorMenu).open(player);
    }

    return true;
  }
}

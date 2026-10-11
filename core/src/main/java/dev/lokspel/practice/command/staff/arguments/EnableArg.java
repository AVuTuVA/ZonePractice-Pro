package dev.lokspel.practice.command.staff.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.InventoryManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class EnableArg {

  private EnableArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.staff")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getStaff().getNoPermission());
      return;
    }

    if (args.length != 1) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getEnable()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (!profile.getStatus().equals(ProfileStatus.LOBBY)) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getEnable()
              .getOnlyInLobby());
      return;
    }

    profile.setStaffMode(true);
    InventoryManager.getInstance().setLobbyInventory(player, false);
  }
}

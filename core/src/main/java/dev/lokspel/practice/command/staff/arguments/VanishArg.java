package dev.lokspel.practice.command.staff.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.InventoryManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.entityhider.PlayerHider;
import org.bukkit.entity.Player;

public final class VanishArg {

  private VanishArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.staffmode")) {
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
              .getVanish()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile.getStatus().equals(ProfileStatus.MATCH)
        || profile.getStatus().equals(ProfileStatus.EVENT)) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getVanish()
              .getCantUse());
      return;
    }

    profile.setHideFromPlayers(!profile.isHideFromPlayers());
    PlayerHider.getInstance().toggleStaffVisibility(player);

    if (profile.isStaffMode()) {
      InventoryManager.getInstance().setStaffModeInventory(player);
    }

    if (profile.isHideFromPlayers()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getVanish()
              .getInvisible());
    } else {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getStaff()
              .getArguments()
              .getVanish()
              .getVisible());
    }
  }
}

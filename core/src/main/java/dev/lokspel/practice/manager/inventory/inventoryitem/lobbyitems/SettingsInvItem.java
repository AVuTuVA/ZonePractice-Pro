package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public class SettingsInvItem extends InvItem {

  public SettingsInvItem() {
    super(PracticeConfig.inventories().getLobbyBasic().getNormal().get("SETTINGS"));
  }

  @Override
  public void handleClickEvent(Player player) {
    if (player.hasPermission("ap.settings.open")) {
      ProfileManager.getInstance().getProfile(player).getSettingsGui().open(player);
    } else {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getSettings().getNoPermission());
    }
  }
}

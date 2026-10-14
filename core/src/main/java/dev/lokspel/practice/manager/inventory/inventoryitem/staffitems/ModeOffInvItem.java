package dev.lokspel.practice.manager.inventory.inventoryitem.staffitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.InventoryManager;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.spectator.SpectatorManager;
import dev.lokspel.practice.util.entityhider.PlayerHider;
import org.bukkit.entity.Player;

public class ModeOffInvItem extends InvItem {

  public ModeOffInvItem() {
    super(PracticeConfig.inventories().getStaffMode().getNormal().get("TURN-OFF"));
  }

  @Override
  public void handleClickEvent(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    profile.setStaffMode(false);
    profile.setHideFromPlayers(false);
    PlayerHider.getInstance().toggleStaffVisibility(player);

    if (SpectatorManager.getInstance().getSpectators().containsKey(player)) {
      SpectatorManager.getInstance().getSpectators().get(player).removeSpectator(player);
    } else {
      InventoryManager.getInstance().setLobbyInventory(player, false);
    }
  }
}

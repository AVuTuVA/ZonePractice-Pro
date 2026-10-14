package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.InventoryManager;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.util.entityhider.PlayerHider;
import org.bukkit.entity.Player;

public class StaffMode extends InvItem {

  public StaffMode() {
    super(PracticeConfig.inventories().getLobbyBasic().getNormal().get("STAFF-MODE"));
  }

  @Override
  public void handleClickEvent(Player player) {
    InventoryManager.getInstance().setStaffModeInventory(player);
    PlayerHider.getInstance().toggleStaffVisibility(player);
  }
}

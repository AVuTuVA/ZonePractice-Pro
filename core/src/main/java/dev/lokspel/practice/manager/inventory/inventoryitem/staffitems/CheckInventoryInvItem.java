package dev.lokspel.practice.manager.inventory.inventoryitem.staffitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.gui.guis.PlayerInvGui;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class CheckInventoryInvItem extends InvItem {

  public CheckInventoryInvItem() {
    super(PracticeConfig.inventories().getStaffMode().getNormal().get("PLAYER-INVENTORY"));
  }

  public void handleClickEvent(Player player, Player target) {
    if (target != null) {
      new PlayerInvGui(target).open(player);
    }
  }

  @Override
  public void handleClickEvent(Player player) {}
}

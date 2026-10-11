package dev.lokspel.practice.manager.inventory.inventories;

import dev.lokspel.practice.manager.inventory.Inventory;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.inventory.inventoryitem.partyitems.KitEditorInvItem;
import dev.lokspel.practice.manager.inventory.inventoryitem.partyitems.LeavePartyInvItem;
import dev.lokspel.practice.manager.inventory.inventoryitem.partyitems.OtherPartiesInvItem;
import dev.lokspel.practice.manager.inventory.inventoryitem.partyitems.PartyGameInvItem;
import dev.lokspel.practice.manager.inventory.inventoryitem.partyitems.PartyInfoInvItem;
import dev.lokspel.practice.manager.inventory.inventoryitem.partyitems.PartySettingsInvItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class PartyInventory extends Inventory {

  public PartyInventory() {
    super(InventoryType.PARTY);

    this.invItems.add(new KitEditorInvItem());
    this.invItems.add(new LeavePartyInvItem());
    this.invItems.add(new OtherPartiesInvItem());
    this.invItems.add(new PartyGameInvItem());
    this.invItems.add(new PartyInfoInvItem());
    this.invItems.add(new PartySettingsInvItem());
  }

  @Override
  protected void set(Player player) {
    PlayerInventory playerInventory = player.getInventory();
    /*
     * TODO
     * Party party = PartyManager.getInstance().getParty(player);
     */

    for (InvItem invItem : invItems) {
      int slot = invItem.getSlot();
      if (slot == -1) {
        continue;
      }

      playerInventory.setItem(slot, invItem.getItem());
    }
  }
}

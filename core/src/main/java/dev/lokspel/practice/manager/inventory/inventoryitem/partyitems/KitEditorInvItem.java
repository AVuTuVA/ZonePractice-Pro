package dev.lokspel.practice.manager.inventory.inventoryitem.partyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class KitEditorInvItem extends InvItem {

  public KitEditorInvItem() {
    super(PracticeConfig.inventories().getParty().getNormal().get("PARTY-KIT-EDITOR"));
  }

  @Override
  public void handleClickEvent(Player player) {
    player.performCommand("editor");
  }
}

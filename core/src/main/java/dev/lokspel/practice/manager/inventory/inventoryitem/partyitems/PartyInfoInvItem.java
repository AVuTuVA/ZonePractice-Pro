package dev.lokspel.practice.manager.inventory.inventoryitem.partyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class PartyInfoInvItem extends InvItem {

  public PartyInfoInvItem() {
    super(PracticeConfig.inventories().getParty().getNormal().get("PARTY-INFO"));
  }

  @Override
  public void handleClickEvent(Player player) {
    player.performCommand("party info");
  }
}

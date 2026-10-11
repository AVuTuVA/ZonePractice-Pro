package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.party.PartyManager;
import org.bukkit.entity.Player;

public class PartyCreateInvItem extends InvItem {

  public PartyCreateInvItem() {
    super(PracticeConfig.inventories().getLobbyBasic().getNormal().get("PARTY-CREATE"));
  }

  @Override
  public void handleClickEvent(Player player) {
    PartyManager.getInstance().createParty(player);
  }
}

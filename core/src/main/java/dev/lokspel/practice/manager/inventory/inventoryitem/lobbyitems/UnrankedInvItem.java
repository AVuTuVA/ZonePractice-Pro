package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class UnrankedInvItem extends InvItem {

  public UnrankedInvItem() {
    super(PracticeConfig.inventories().getLobbyBasic().getNormal().get("UNRANKED"));
  }

  @Override
  public void handleClickEvent(Player player) {
    player.performCommand("unranked");
  }
}

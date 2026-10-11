package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class RankedInvItem extends InvItem {

  public RankedInvItem() {
    super(PracticeConfig.inventories().getLobbyBasic().getNormal().get("RANKED"));
  }

  @Override
  public void handleClickEvent(Player player) {
    player.performCommand("ranked");
  }
}

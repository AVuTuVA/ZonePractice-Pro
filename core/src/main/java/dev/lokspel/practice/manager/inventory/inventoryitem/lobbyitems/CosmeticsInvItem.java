package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class CosmeticsInvItem extends InvItem {

  public CosmeticsInvItem() {
    super(PracticeConfig.inventories().getLobbyBasic().getNormal().get("COSMETICS"));
  }

  @Override
  public void handleClickEvent(Player player) {
    player.performCommand("cosmetics");
  }
}

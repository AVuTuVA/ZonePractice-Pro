package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class SetupInvItem extends InvItem {

  public SetupInvItem() {
    super(PracticeConfig.inventories().getLobbyBasic().getNormal().get("SETUP"));
  }

  @Override
  public void handleClickEvent(Player player) {
    player.performCommand("setup");
  }
}

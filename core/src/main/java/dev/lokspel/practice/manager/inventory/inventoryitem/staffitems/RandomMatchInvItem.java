package dev.lokspel.practice.manager.inventory.inventoryitem.staffitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.spectator.SpectatorManager;
import org.bukkit.entity.Player;

public class RandomMatchInvItem extends InvItem {

  public RandomMatchInvItem() {
    super(PracticeConfig.inventories().getStaffMode().getNormal().get("RANDOM-GAME-SPECTATE"));
  }

  @Override
  public void handleClickEvent(Player player) {
    SpectatorManager.spectateRandomMatch(player);
  }
}

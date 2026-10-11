package dev.lokspel.practice.manager.inventory.inventoryitem.spectatoritems.spectatormodeitems.match;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.spectator.SpectatorManager;
import org.bukkit.entity.Player;

public class RandomMatchInvItem extends InvItem {

  public RandomMatchInvItem() {
    super(PracticeConfig.inventories().getSpectator().getMatch().getNormal().get("RANDOM"));
  }

  @Override
  public void handleClickEvent(Player player) {
    SpectatorManager.spectateRandomMatchItemUse(player);
  }
}

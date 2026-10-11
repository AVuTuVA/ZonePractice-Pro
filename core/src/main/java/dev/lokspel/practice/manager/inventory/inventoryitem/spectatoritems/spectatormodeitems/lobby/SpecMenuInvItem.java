package dev.lokspel.practice.manager.inventory.inventoryitem.spectatoritems.spectatormodeitems.lobby;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.spectator.SpectatorManager;
import org.bukkit.entity.Player;

public class SpecMenuInvItem extends InvItem {

  public SpecMenuInvItem() {
    super(PracticeConfig.inventories().getSpectator().getLobby().getNormal().get("MENU"));
  }

  @Override
  public void handleClickEvent(Player player) {
    SpectatorManager.getInstance().spectateMenuUse(player);
  }
}

package dev.lokspel.practice.manager.inventory.inventoryitem.spectatoritems.spectatormodeitems.lobby;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.InventoryManager;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.profile.ProfileManager;
import org.bukkit.entity.Player;

public class DisableSpecMode extends InvItem {

  public DisableSpecMode() {
    super(PracticeConfig.inventories().getSpectator().getLobby().getNormal().get("DISABLE"));
  }

  @Override
  public void handleClickEvent(Player player) {
    ProfileManager.getInstance().getProfile(player).setSpectatorMode(false);
    InventoryManager.getInstance().setLobbyInventory(player, false);
  }
}

package dev.lokspel.practice.manager.inventory.inventoryitem.spectatoritems.spectatormodeitems.match;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.Inventory;
import dev.lokspel.practice.manager.inventory.InventoryManager;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.StringUtil;
import dev.lokspel.practice.util.cooldown.CooldownObject;
import dev.lokspel.practice.util.cooldown.PlayerCooldown;
import dev.lokspel.practice.util.entityhider.PlayerHider;
import org.bukkit.entity.Player;

public class HideSpectatorsInvItem extends InvItem {

  public HideSpectatorsInvItem() {
    super(
        PracticeConfig.inventories().getSpectator().getMatch().getNormal().get("HIDE-SPECTATORS"));
  }

  @Override
  public void handleClickEvent(Player player) {
    if (!player.hasPermission("ap.spectate.vanish")) {
      Common.sendMmMessage(player, PracticeConfig.language().getSpectate().getNoPermissions());
      return;
    }

    if (!player.hasPermission("ap.bypass.cooldown")
        && PlayerCooldown.isActive(player, CooldownObject.SPECTATOR_VANISH)) {
      Common.sendMmMessage(
          player,
          StringUtil.replaceSecondString(
              PracticeConfig.language().getSpectate().getVanishCooldown(),
              PlayerCooldown.getLeftInDouble(player, CooldownObject.SPECTATOR_VANISH)));
      return;
    } else {
      PlayerCooldown.addCooldown(
          player,
          CooldownObject.SPECTATOR_VANISH,
          PracticeConfig.settings().getSpectatorSettings().getVanishCooldown());
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);

    profile.setHideSpectators(!profile.isHideSpectators());
    InventoryManager.getInstance().setInventory(player, Inventory.InventoryType.SPECTATE_MATCH);
    PlayerHider.getInstance().toggleSpectatorVisibility(player);
  }
}

package dev.lokspel.practice.manager.inventory.inventoryitem.spectatoritems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import dev.lokspel.practice.manager.fight.ffa.game.Ffa;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class LeaveFfaSpecInvItem extends InvItem {

  public LeaveFfaSpecInvItem() {
    super(PracticeConfig.inventories().getSpectator().getFfa().getNormal().get("LEAVE"));
  }

  @Override
  public void handleClickEvent(Player player) {
    Ffa ffa = FfaManager.getInstance().getFfaBySpectator(player);
    if (ffa != null) {
      ffa.removeSpectator(player);
    }
  }
}

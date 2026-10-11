package dev.lokspel.practice.manager.inventory.inventoryitem.spectatoritems;

import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import dev.lokspel.practice.manager.fight.ffa.game.Ffa;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class LeaveFfaSpecInvItem extends InvItem {

  public LeaveFfaSpecInvItem() {
    super(
        getItemStack("SPECTATOR.FFA.NORMAL.LEAVE.ITEM"), getInt("SPECTATOR.FFA.NORMAL.LEAVE.SLOT"));
  }

  @Override
  public void handleClickEvent(Player player) {
    Ffa ffa = FfaManager.getInstance().getFfaBySpectator(player);
    if (ffa != null) {
      ffa.removeSpectator(player);
    }
  }
}

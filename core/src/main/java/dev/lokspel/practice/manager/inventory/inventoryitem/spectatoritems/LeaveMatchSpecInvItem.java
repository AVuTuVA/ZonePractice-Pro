package dev.lokspel.practice.manager.inventory.inventoryitem.spectatoritems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class LeaveMatchSpecInvItem extends InvItem {

  public LeaveMatchSpecInvItem() {
    super(PracticeConfig.inventories().getSpectator().getMatch().getNormal().get("LEAVE"));
  }

  @Override
  public void handleClickEvent(Player player) {
    Match match = MatchManager.getInstance().getLiveMatchBySpectator(player);
    if (match != null) {
      match.removeSpectator(player);
    }
  }
}

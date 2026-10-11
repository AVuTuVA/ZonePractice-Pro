package dev.lokspel.practice.manager.inventory.inventoryitem.spectatoritems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.interfaces.Event;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class LeaveEventSpecInvItem extends InvItem {

  public LeaveEventSpecInvItem() {
    super(PracticeConfig.inventories().getSpectator().getEvent().getNormal().get("LEAVE"));
  }

  @Override
  public void handleClickEvent(Player player) {
    Event event = EventManager.getInstance().getEventBySpectator(player);
    if (event != null) {
      event.removeSpectator(player);
    }
  }
}

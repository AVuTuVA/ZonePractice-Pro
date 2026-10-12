package dev.lokspel.practice.api;

import dev.lokspel.api.event.EventApi;
import dev.lokspel.api.interfaces.Event;
import dev.lokspel.practice.manager.fight.event.EventManager;
import org.bukkit.entity.Player;

public class EventApiImpl implements EventApi {

  @Override
  public Event getEvent(Player player) {
    return EventManager.getInstance().getEventByPlayer(player);
  }
}

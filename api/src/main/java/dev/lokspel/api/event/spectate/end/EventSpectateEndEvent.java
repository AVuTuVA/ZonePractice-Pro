package dev.lokspel.api.event.spectate.end;

import dev.lokspel.api.interfaces.Event;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class EventSpectateEndEvent extends SpectateEndEvent {

  private static final HandlerList handlers = new HandlerList();

  private final Event event;

  public EventSpectateEndEvent(Player player, Event event) {
    super(player, event);
    this.event = event;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}

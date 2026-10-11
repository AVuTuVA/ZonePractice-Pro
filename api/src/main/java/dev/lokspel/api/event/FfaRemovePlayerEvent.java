package dev.lokspel.api.event;

import dev.lokspel.api.interfaces.Ffa;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class FfaRemovePlayerEvent extends Event {

  private static final HandlerList handlers = new HandlerList();

  private final Ffa ffa;
  private final Player player;

  public FfaRemovePlayerEvent(Ffa ffa, Player player) {
    this.ffa = ffa;
    this.player = player;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}

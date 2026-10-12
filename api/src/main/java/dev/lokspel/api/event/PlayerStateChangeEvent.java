package dev.lokspel.api.event;

import dev.lokspel.api.enums.PlayerState;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Event called when a player's {@link PlayerState} changes. */
@Getter
public class PlayerStateChangeEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  private final Player player;
  private final PlayerState oldState;
  private final PlayerState newState;

  public PlayerStateChangeEvent(Player player, PlayerState oldState, PlayerState newState) {
    this.player = player;
    this.oldState = oldState;
    this.newState = newState;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}

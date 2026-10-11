package dev.lokspel.api.event.spectate.start;

import dev.lokspel.api.interfaces.Match;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class MatchSpectateStartEvent extends SpectateStartEvent {

  private static final HandlerList handlers = new HandlerList();

  private final Match match;

  public MatchSpectateStartEvent(Player player, Match match) {
    super(player, match);
    this.match = match;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}

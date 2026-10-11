package dev.lokspel.api.event.spectate.start;

import dev.lokspel.api.interfaces.Ffa;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class FfaSpectateStartEvent extends SpectateStartEvent {

  private static final HandlerList handlers = new HandlerList();

  private final Ffa ffa;

  public FfaSpectateStartEvent(Player player, Ffa ffa) {
    super(player, ffa);
    this.ffa = ffa;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}

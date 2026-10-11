package dev.lokspel.api.event.spectate.end;

import dev.lokspel.api.interfaces.Ffa;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class FfaSpectateEndEvent extends SpectateEndEvent {

  private static final HandlerList handlers = new HandlerList();

  private final Ffa ffa;

  public FfaSpectateEndEvent(Player player, Ffa ffa) {
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

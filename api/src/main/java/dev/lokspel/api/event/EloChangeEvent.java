package dev.lokspel.api.event;

import dev.lokspel.api.interfaces.Match;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class EloChangeEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  private final Player player;
  private final String ladderName;
  private final int oldElo;
  private final int newElo;
  private final Match match;

  public EloChangeEvent(Player player, String ladderName, int oldElo, int newElo, Match match) {
    this.player = player;
    this.ladderName = ladderName;
    this.oldElo = oldElo;
    this.newElo = newElo;
    this.match = match;
  }

  public int getDelta() {
    return this.newElo - this.oldElo;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}

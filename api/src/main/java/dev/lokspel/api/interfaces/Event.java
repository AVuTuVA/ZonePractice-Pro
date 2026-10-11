package dev.lokspel.api.interfaces;

import java.util.List;
import org.bukkit.entity.Player;

public interface Event extends Spectatable {

  /**
   * Get the starter of the event.
   *
   * @return The starter of the event
   */
  Object getStarter();

  /**
   * Get the players of the match.
   *
   * @return The players of the match
   */
  List<Player> getPlayers();

  /**
   * Get the spectators of the match.
   *
   * @return The spectators of the match
   */
  List<Player> getSpectators();

  /**
   * Get the amount of players that the event has started with.
   *
   * @return The amount of players that the event has started with
   */
  int getStartPlayerCount();

  /**
   * Get the winner of the event.
   *
   * @return The winner of the event
   */
  Player getWinner();
}

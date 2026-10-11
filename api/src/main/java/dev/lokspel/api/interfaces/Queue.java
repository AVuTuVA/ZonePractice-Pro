package dev.lokspel.api.interfaces;

import org.bukkit.entity.Player;

public interface Queue {

  /**
   * Get the player.
   *
   * @return the player
   */
  Player getPlayer();

  /**
   * Get the duration.
   *
   * @return the duration
   */
  int getDuration();

  /**
   * Get the type.
   *
   * @return the type
   */
  boolean isRanked();
}

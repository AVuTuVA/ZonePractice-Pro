package dev.lokspel.api.event;

import dev.lokspel.api.interfaces.Event;
import org.bukkit.entity.Player;

/** API section for tournament events (e.g. Brackets, Sumo, Juggernaut). */
public interface EventApi {

  /**
   * Get the tournament event the player is participating in.
   *
   * @param player The player
   * @return The event instance, or null if not in an event
   */
  Event getEvent(Player player);
}

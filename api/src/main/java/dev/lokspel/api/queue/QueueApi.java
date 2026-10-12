package dev.lokspel.api.queue;

import dev.lokspel.api.interfaces.Queue;
import org.bukkit.entity.Player;

/** API section for matchmaking queues. */
public interface QueueApi {

  /**
   * Get the matchmaking queue the player is waiting in.
   *
   * @param player The player
   * @return The queue instance, or null if not queuing
   */
  Queue getQueue(Player player);
}

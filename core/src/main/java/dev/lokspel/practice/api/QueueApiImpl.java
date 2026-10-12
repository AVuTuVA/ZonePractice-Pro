package dev.lokspel.practice.api;

import dev.lokspel.api.interfaces.Queue;
import dev.lokspel.api.queue.QueueApi;
import dev.lokspel.practice.manager.queue.QueueManager;
import org.bukkit.entity.Player;

public class QueueApiImpl implements QueueApi {

  @Override
  public Queue getQueue(Player player) {
    return QueueManager.getInstance().getQueue(player);
  }
}

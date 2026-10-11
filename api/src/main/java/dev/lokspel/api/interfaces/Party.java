package dev.lokspel.api.interfaces;

import java.util.List;
import org.bukkit.entity.Player;

public interface Party {

  /**
   * Get the leader.
   *
   * @return the leader
   */
  Player getLeader();

  /**
   * Get the members.
   *
   * @return the members
   */
  List<Player> getMembers();
}

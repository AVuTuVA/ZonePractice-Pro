package dev.lokspel.api.ffa;

import dev.lokspel.api.interfaces.Ffa;
import org.bukkit.entity.Player;

/** API section for Free-For-All (FFA) arenas. */
public interface FfaApi {

  /**
   * Get the FFA arena game the player is participating in.
   *
   * @param player The player
   * @return The FFA instance, or null if not in FFA
   */
  Ffa getFfa(Player player);
}

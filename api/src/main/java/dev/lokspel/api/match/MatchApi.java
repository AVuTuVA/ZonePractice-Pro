package dev.lokspel.api.match;

import dev.lokspel.api.interfaces.Match;
import org.bukkit.entity.Player;

/** API section for managing live matches and spectators. */
public interface MatchApi {

  /**
   * Get the live match the player is participating in.
   *
   * @param player The player
   * @return The match instance, or null if not in a match
   */
  Match getMatch(Player player);

  /**
   * Get the match the player is spectating.
   *
   * @param player The player
   * @return The spectated match, or null if not spectating
   */
  Match getSpectatingMatch(Player player);

  /**
   * End the match for the player with a custom end message.
   *
   * @param player The player
   * @param endMessage The MiniMessage-formatted message to send to the match
   */
  void endMatch(Player player, String endMessage);
}

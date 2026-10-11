package dev.lokspel.api.interfaces;

import java.util.List;
import org.bukkit.entity.Player;

public interface Match extends Spectatable {

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
   * Get the amount of wins needed to win the match.
   *
   * @return The amount of wins needed to win the match
   */
  int getWinsNeeded();

  /**
   * Get the ladder name of the match.
   *
   * @return The ladder name of the match, or null if not set
   */
  String getLadderName();

  /**
   * Get the ladder display name of the match.
   *
   * @return The ladder display name of the match, or null if not set
   */
  String getLadderDisplayName();

  /**
   * Get the unique match identifier.
   *
   * @return The match ID
   */
  String getMatchId();

  /**
   * Get the name of the arena.
   *
   * @return The arena name
   */
  String getArenaName();

  /**
   * Check if the match is ranked.
   *
   * @return True if ranked
   */
  boolean isRanked();

  /**
   * Get the duration of the match in seconds.
   *
   * @return Duration in seconds
   */
  int getDurationSeconds();

  /**
   * Get the current round number of the match.
   *
   * @return Current round number
   */
  int getCurrentRoundNumber();
}

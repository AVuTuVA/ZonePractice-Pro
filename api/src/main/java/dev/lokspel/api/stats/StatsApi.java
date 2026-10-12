package dev.lokspel.api.stats;

import dev.lokspel.api.enums.WeightClass;
import org.bukkit.entity.Player;

/** API section for querying player statistics, Elo, and ladder records. */
public interface StatsApi {

  /**
   * Get the player's Elo rating for a specific ladder.
   *
   * @param player The player
   * @param ladder The ladder identifier
   * @return The player's Elo rating
   */
  int getElo(Player player, String ladder);

  /**
   * Get the player's total experience points.
   *
   * @param player The player
   * @return The player's experience
   */
  int getExperience(Player player);

  /**
   * Get the player's wins for a specific ladder and weight class.
   *
   * @param player The player
   * @param ladder The ladder identifier
   * @param weightClass The weight class (RANKED or UNRANKED)
   * @return The player's wins
   */
  int getLadderWins(Player player, String ladder, WeightClass weightClass);

  /**
   * Get the player's losses for a specific ladder and weight class.
   *
   * @param player The player
   * @param ladder The ladder identifier
   * @param weightClass The weight class (RANKED or UNRANKED)
   * @return The player's losses
   */
  int getLadderLosses(Player player, String ladder, WeightClass weightClass);

  /**
   * Get the player's global wins across all ladders.
   *
   * @param player The player
   * @return The player's global wins
   */
  int getGlobalWins(Player player);

  /**
   * Get the player's kill count for a specific ladder.
   *
   * @param player The player
   * @param ladderName The ladder identifier
   * @return The kills count, or -1 if unavailable
   */
  int getLadderKills(Player player, String ladderName);

  /**
   * Get the player's death count for a specific ladder.
   *
   * @param player The player
   * @param ladderName The ladder identifier
   * @return The death count, or -1 if unavailable
   */
  int getLadderDeaths(Player player, String ladderName);
}

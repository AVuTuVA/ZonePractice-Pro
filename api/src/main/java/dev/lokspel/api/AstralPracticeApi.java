package dev.lokspel.api;

import dev.lokspel.api.enums.DivisionName;
import dev.lokspel.api.enums.WeightClass;
import dev.lokspel.api.interfaces.Event;
import dev.lokspel.api.interfaces.Ffa;
import dev.lokspel.api.interfaces.Match;
import dev.lokspel.api.interfaces.Party;
import dev.lokspel.api.interfaces.Queue;
import dev.lokspel.api.utilities.PlayerNametag;
import java.util.List;
import org.bukkit.entity.Player;

public abstract class AstralPracticeApi {

  public static final int API_VERSION = 3;

  public static AstralPracticeApi instance = null;

  public static AstralPracticeApi getInstance() {
    return instance;
  }

  public static boolean isAvailable() {
    return instance != null;
  }

  /**
   * Get the player's division.
   *
   * @param player The player
   * @param divisionName Short or Full name of the division
   * @return The player's division
   */
  public abstract String getPlayerDivision(Player player, DivisionName divisionName);

  /**
   * Get the amount of unranked matches left for the player.
   *
   * @param player The player
   * @return The amount of unranked matches left
   */
  public abstract int getPlayerUnRankedLeft(Player player);

  /**
   * Get the amount of ranked matches left for the player.
   *
   * @param player The player
   * @return The amount of ranked matches left
   */
  public abstract int getPlayerRankedLeft(Player player);

  /**
   * Reset the player's unranked matches.
   *
   * @param player The player
   */
  public abstract void resetPlayerUnRanked(Player player);

  /**
   * Reset the player's ranked matches.
   *
   * @param player The player
   */
  public abstract void resetPlayerRanked(Player player);

  /**
   * Add unranked matches to the player.
   *
   * @param player The player
   * @param amount The amount of unranked matches to add
   */
  public abstract void addPlayerUnRanked(Player player, int amount);

  /**
   * Add ranked matches to the player.
   *
   * @param player The player
   * @param amount The amount of ranked matches to add
   */
  public abstract void addPlayerRanked(Player player, int amount);

  /**
   * End the match for the player.
   *
   * @param player The player
   * @param endMessage The message to send to the match
   */
  public abstract void endMatch(Player player, String endMessage);

  /**
   * Get the player's elo for a specific ladder.
   *
   * @param player The player
   * @param ladder The ladder
   * @return The player's elo
   */
  public abstract int getElo(Player player, String ladder);

  /**
   * Get the player's experience points.
   *
   * @param player The player
   * @return The player's experience
   */
  public abstract int getExperience(Player player);

  /**
   * Get the player's wins for a specific weight class.
   *
   * @param player The player
   * @param ladder The ladder
   * @param weightClass The weight class
   * @return The player's wins
   */
  public abstract int getLadderWins(Player player, String ladder, WeightClass weightClass);

  /**
   * Get the player's losses for a specific weight class.
   *
   * @param player The player
   * @param ladder The ladder
   * @param weightClass The weight class
   * @return The player's losses
   */
  public abstract int getLadderLosses(Player player, String ladder, WeightClass weightClass);

  /**
   * Get the player's global wins.
   *
   * @param player The player
   * @return The player's global wins (unranked + ranked)
   */
  public abstract int getGlobalWins(Player player);

  /**
   * Retrieves the nametag information of a given player.
   *
   * @param player The player whose nametag information is to be retrieved.
   * @return The {@link PlayerNametag} associated with the specified player, containing prefix, name
   *     color, and suffix components.
   */
  public abstract PlayerNametag getPlayerNametag(Player player);

  /**
   * Get the match the player is participating in.
   *
   * @param player The player
   * @return The match, or null if none
   */
  public abstract Match getMatch(Player player);

  /**
   * Get the match the player is spectating.
   *
   * @param player The player
   * @return The spectated match, or null if none
   */
  public abstract Match getSpectatingMatch(Player player);

  /**
   * Get the FFA game the player is participating in.
   *
   * @param player The player
   * @return The FFA instance, or null if none
   */
  public abstract Ffa getFfa(Player player);

  /**
   * Get the event the player is participating in.
   *
   * @param player The player
   * @return The event instance, or null if none
   */
  public abstract Event getEvent(Player player);

  /**
   * Get the party the player belongs to.
   *
   * @param player The player
   * @return The party, or null if none
   */
  public abstract Party getParty(Player player);

  /**
   * Get the queue the player is waiting in.
   *
   * @param player The player
   * @return The queue, or null if none
   */
  public abstract Queue getQueue(Player player);

  /**
   * Get the names of all available ladders.
   *
   * @return List of ladder names
   */
  public abstract List<String> getLadderNames();

  /**
   * Get the player's kill count for a specific ladder.
   *
   * @param player The player
   * @param ladderName The ladder name
   * @return The kills count, or -1 if unavailable
   */
  public abstract int getLadderKills(Player player, String ladderName);

  /**
   * Get the player's death count for a specific ladder.
   *
   * @param player The player
   * @param ladderName The ladder name
   * @return The death count, or -1 if unavailable
   */
  public abstract int getLadderDeaths(Player player, String ladderName);
}

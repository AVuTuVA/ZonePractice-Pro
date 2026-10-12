package dev.lokspel.api;

import dev.lokspel.api.cosmetics.CosmeticsApi;
import dev.lokspel.api.event.EventApi;
import dev.lokspel.api.ffa.FfaApi;
import dev.lokspel.api.ladder.LadderApi;
import dev.lokspel.api.match.MatchApi;
import dev.lokspel.api.party.PartyApi;
import dev.lokspel.api.player.PlayerApi;
import dev.lokspel.api.queue.QueueApi;
import dev.lokspel.api.stats.StatsApi;

/**
 * Main entry point for the AstralPractice developer API. Provides modular access to specific
 * sub-API sections.
 */
public interface AstralPracticeApi {

  /**
   * Get the singleton instance of the AstralPractice API.
   *
   * @return The API instance, or null if AstralPractice is not enabled
   */
  static AstralPracticeApi getInstance() {
    return AstralPracticeApiImpl.getInstance();
  }

  /**
   * Check whether the AstralPractice API is available.
   *
   * @return true if available, false otherwise
   */
  static boolean isAvailable() {
    return AstralPracticeApiImpl.getInstance() != null;
  }

  /**
   * Player management API section (states, nametags, divisions, daily match limits).
   *
   * @return The player API
   */
  PlayerApi getPlayers();

  /**
   * Statistics API section (Elo, experience, wins, losses, kills, deaths).
   *
   * @return The statistics API
   */
  StatsApi getStats();

  /**
   * Match API section (live matches, spectating, match termination).
   *
   * @return The match API
   */
  MatchApi getMatches();

  /**
   * Party API section (player parties).
   *
   * @return The party API
   */
  PartyApi getParties();

  /**
   * Matchmaking queue API section.
   *
   * @return The queue API
   */
  QueueApi getQueues();

  /**
   * Practice ladder API section.
   *
   * @return The ladder API
   */
  LadderApi getLadders();

  /**
   * Free-for-all (FFA) arena API section.
   *
   * @return The FFA API
   */
  FfaApi getFfa();

  /**
   * Tournament event API section.
   *
   * @return The event API
   */
  EventApi getEvents();

  /**
   * Cosmetics API section (categories, menu).
   *
   * @return The cosmetics API
   */
  CosmeticsApi getCosmetics();
}

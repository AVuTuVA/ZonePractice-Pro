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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AstralPracticeApiImpl implements AstralPracticeApi {

  @Getter @Setter private static AstralPracticeApi instance;

  private final PlayerApi players;
  private final StatsApi stats;
  private final MatchApi matches;
  private final PartyApi parties;
  private final QueueApi queues;
  private final LadderApi ladders;
  private final FfaApi ffa;
  private final EventApi events;
  private final CosmeticsApi cosmetics;
}

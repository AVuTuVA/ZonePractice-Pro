package dev.lokspel.practice;

import dev.lokspel.api.AstralPracticeApi;
import dev.lokspel.api.cosmetics.CosmeticsApi;
import dev.lokspel.api.event.EventApi;
import dev.lokspel.api.ffa.FfaApi;
import dev.lokspel.api.ladder.LadderApi;
import dev.lokspel.api.match.MatchApi;
import dev.lokspel.api.party.PartyApi;
import dev.lokspel.api.player.PlayerApi;
import dev.lokspel.api.queue.QueueApi;
import dev.lokspel.api.stats.StatsApi;
import dev.lokspel.practice.api.EventApiImpl;
import dev.lokspel.practice.api.FfaApiImpl;
import dev.lokspel.practice.api.LadderApiImpl;
import dev.lokspel.practice.api.MatchApiImpl;
import dev.lokspel.practice.api.PartyApiImpl;
import dev.lokspel.practice.api.PlayerApiImpl;
import dev.lokspel.practice.api.QueueApiImpl;
import dev.lokspel.practice.api.StatsApiImpl;
import dev.lokspel.practice.manager.profile.cosmetics.CosmeticsCategoryManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class AstralPracticeApiImpl extends AstralPracticeApi {

  private final PlayerApi playerApi = new PlayerApiImpl();
  private final StatsApi statsApi = new StatsApiImpl();
  private final MatchApi matchApi = new MatchApiImpl();
  private final PartyApi partyApi = new PartyApiImpl();
  private final QueueApi queueApi = new QueueApiImpl();
  private final LadderApi ladderApi = new LadderApiImpl();
  private final FfaApi ffaApi = new FfaApiImpl();
  private final EventApi eventApi = new EventApiImpl();

  public static void setup() {
    AstralPracticeApiImpl impl = new AstralPracticeApiImpl();
    AstralPracticeApi.instance = impl;
    Bukkit.getServicesManager()
        .register(
            AstralPracticeApi.class, impl, AstralPractice.getInstance(), ServicePriority.Normal);
  }

  @Override
  public PlayerApi getPlayers() {
    return playerApi;
  }

  @Override
  public StatsApi getStats() {
    return statsApi;
  }

  @Override
  public MatchApi getMatches() {
    return matchApi;
  }

  @Override
  public PartyApi getParties() {
    return partyApi;
  }

  @Override
  public QueueApi getQueues() {
    return queueApi;
  }

  @Override
  public LadderApi getLadders() {
    return ladderApi;
  }

  @Override
  public FfaApi getFfa() {
    return ffaApi;
  }

  @Override
  public EventApi getEvents() {
    return eventApi;
  }

  @Override
  public CosmeticsApi getCosmetics() {
    return CosmeticsCategoryManager.getInstance();
  }
}

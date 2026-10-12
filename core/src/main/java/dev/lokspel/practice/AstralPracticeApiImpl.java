package dev.lokspel.practice;

import dev.lokspel.api.AstralPracticeApi;
import dev.lokspel.api.enums.DivisionName;
import dev.lokspel.api.enums.PlayerState;
import dev.lokspel.api.enums.WeightClass;
import dev.lokspel.api.interfaces.Event;
import dev.lokspel.api.interfaces.Ffa;
import dev.lokspel.api.interfaces.Match;
import dev.lokspel.api.interfaces.Party;
import dev.lokspel.api.interfaces.Queue;
import dev.lokspel.api.utilities.PlayerNametag;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import dev.lokspel.practice.manager.inventory.InventoryUtil;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.group.Group;
import dev.lokspel.practice.manager.queue.QueueManager;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;

public class AstralPracticeApiImpl extends AstralPracticeApi {

  public static void setup() {
    AstralPracticeApiImpl impl = new AstralPracticeApiImpl();
    AstralPracticeApi.instance = impl;
    Bukkit.getServicesManager()
        .register(
            AstralPracticeApi.class, impl, AstralPractice.getInstance(), ServicePriority.Normal);
  }

  @Override
  public String getPlayerDivision(Player player, DivisionName divisionName) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return null;
    }
    if (profile.getStats().getDivision() == null) {
      return null;
    }

    return switch (divisionName) {
      case FULL -> profile.getStats().getDivision().getFullName();
      case SHORT -> profile.getStats().getDivision().getShortName();
    };
  }

  @Override
  public int getPlayerUnRankedLeft(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }

    return profile.getUnrankedLeft();
  }

  @Override
  public int getPlayerRankedLeft(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }

    return profile.getRankedLeft();
  }

  @Override
  public void resetPlayerUnRanked(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return;
    }

    Group group = profile.getGroup();
    if (group == null) {
      return;
    }

    profile.setUnrankedLeft(group.getUnrankedLimit());
  }

  @Override
  public void resetPlayerRanked(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return;
    }

    Group group = profile.getGroup();
    if (group == null) {
      return;
    }

    profile.setRankedLeft(group.getRankedLimit());
  }

  @Override
  public void addPlayerUnRanked(Player player, int i) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return;
    }

    profile.setUnrankedLeft(profile.getUnrankedLeft() + i);
  }

  @Override
  public void addPlayerRanked(Player player, int i) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return;
    }

    profile.setRankedLeft(profile.getRankedLeft() + i);
  }

  @Override
  public void endMatch(Player player, String s) {
    var match = MatchManager.getInstance().getLiveMatchByPlayer(player);
    if (match == null) {
      return;
    }

    if (s != null) {
      match.sendMessage(s, true);
    }

    match.endMatch();
  }

  @Override
  public int getElo(Player player, String s) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }

    NormalLadder ladder = LadderManager.getInstance().getLadder(s);
    if (ladder == null) {
      return -1;
    }
    if (ladder.isRanked()) {
      return -1;
    }

    return profile.getStats().getLadderStat(ladder).getElo();
  }

  @Override
  public int getExperience(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }

    return profile.getStats().getExperience();
  }

  @Override
  public int getLadderWins(Player player, String s, WeightClass weightClass) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }

    NormalLadder ladder = LadderManager.getInstance().getLadder(s);
    if (ladder == null) {
      return -1;
    }

    switch (weightClass) {
      case RANKED:
        if (ladder.isRanked()) {
          return profile.getStats().getLadderStat(ladder).getRankedWins();
        }
      // fall through
      case UNRANKED:
        return profile.getStats().getLadderStat(ladder).getUnRankedWins();
      default:
        return -1;
    }
  }

  @Override
  public int getLadderLosses(Player player, String s, WeightClass weightClass) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }

    NormalLadder ladder = LadderManager.getInstance().getLadder(s);
    if (ladder == null) {
      return -1;
    }

    switch (weightClass) {
      case RANKED:
        if (ladder.isRanked()) {
          return profile.getStats().getLadderStat(ladder).getRankedLosses();
        }
      // fall through
      case UNRANKED:
        return profile.getStats().getLadderStat(ladder).getUnRankedLosses();
      default:
        return -1;
    }
  }

  @Override
  public int getGlobalWins(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }

    return profile.getStats().getGlobalWins();
  }

  @Override
  public PlayerNametag getPlayerNametag(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return null;
    }

    return InventoryUtil.getLobbyNametag(profile);
  }

  @Override
  public Match getMatch(Player player) {
    return MatchManager.getInstance().getLiveMatchByPlayer(player);
  }

  @Override
  public Match getSpectatingMatch(Player player) {
    return MatchManager.getInstance().getLiveMatchBySpectator(player);
  }

  @Override
  public Ffa getFfa(Player player) {
    return FfaManager.getInstance().getFfaByPlayer(player);
  }

  @Override
  public Event getEvent(Player player) {
    return EventManager.getInstance().getEventByPlayer(player);
  }

  @Override
  public Party getParty(Player player) {
    return PartyManager.getInstance().getParty(player);
  }

  @Override
  public Queue getQueue(Player player) {
    return QueueManager.getInstance().getQueue(player);
  }

  @Override
  public List<String> getLadderNames() {
    return LadderManager.getInstance().getLadders().stream().map(Ladder::getName).toList();
  }

  @Override
  public int getLadderKills(Player player, String ladderName) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }

    NormalLadder ladder = LadderManager.getInstance().getLadder(ladderName);
    if (ladder == null) {
      return -1;
    }

    return profile.getStats().getLadderStat(ladder).getKills();
  }

  @Override
  public int getLadderDeaths(Player player, String ladderName) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }

    NormalLadder ladder = LadderManager.getInstance().getLadder(ladderName);
    if (ladder == null) {
      return -1;
    }

    return profile.getStats().getLadderStat(ladder).getDeaths();
  }

  @Override
  public PlayerState getPlayerState(Player player) {
    if (player == null || !player.isOnline()) {
      return PlayerState.OFFLINE;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null || profile.getStatus() == null) {
      return PlayerState.OFFLINE;
    }

    return profile.getStatus().toPlayerState();
  }

  @Override
  public PlayerState getPlayerState(UUID uuid) {
    if (uuid == null) {
      return PlayerState.OFFLINE;
    }

    Player player = Bukkit.getPlayer(uuid);
    if (player == null || !player.isOnline()) {
      return PlayerState.OFFLINE;
    }

    return getPlayerState(player);
  }
}

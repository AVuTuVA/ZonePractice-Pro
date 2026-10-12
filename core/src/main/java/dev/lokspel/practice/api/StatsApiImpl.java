package dev.lokspel.practice.api;

import dev.lokspel.api.enums.WeightClass;
import dev.lokspel.api.stats.StatsApi;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import org.bukkit.entity.Player;

public class StatsApiImpl implements StatsApi {

  @Override
  public int getElo(Player player, String ladderName) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }
    NormalLadder ladder = LadderManager.getInstance().getLadder(ladderName);
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
  public int getLadderWins(Player player, String ladderName, WeightClass weightClass) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }
    NormalLadder ladder = LadderManager.getInstance().getLadder(ladderName);
    if (ladder == null) {
      return -1;
    }
    return switch (weightClass) {
      case RANKED ->
          ladder.isRanked()
              ? profile.getStats().getLadderStat(ladder).getRankedWins()
              : profile.getStats().getLadderStat(ladder).getUnRankedWins();
      case UNRANKED -> profile.getStats().getLadderStat(ladder).getUnRankedWins();
    };
  }

  @Override
  public int getLadderLosses(Player player, String ladderName, WeightClass weightClass) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return -1;
    }
    NormalLadder ladder = LadderManager.getInstance().getLadder(ladderName);
    if (ladder == null) {
      return -1;
    }
    return switch (weightClass) {
      case RANKED ->
          ladder.isRanked()
              ? profile.getStats().getLadderStat(ladder).getRankedLosses()
              : profile.getStats().getLadderStat(ladder).getUnRankedLosses();
      case UNRANKED -> profile.getStats().getLadderStat(ladder).getUnRankedLosses();
    };
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
}

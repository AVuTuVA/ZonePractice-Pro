package dev.lokspel.practice.manager.profile.statistics;

import dev.lokspel.practice.manager.ladder.LadderManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LadderStats {

  private int unRankedWins;
  private int unRankedLosses;
  private int unRankedWinStreak;
  private int unRankedBestWinStreak;
  private int unRankedLoseStreak;
  private int unRankedBestLoseStreak;

  private int rankedWins;
  private int rankedLosses;
  private int rankedWinStreak;
  private int rankedBestWinStreak;
  private int rankedLoseStreak;
  private int rankedBestLoseStreak;
  private int elo = LadderManager.getDEFAULT_ELO();

  private int kills;
  private int deaths;

  public void increaseWins(boolean ranked) {
    if (ranked) {
      rankedWins++;
    } else {
      unRankedWins++;
    }
  }

  public void increaseLosses(boolean ranked) {
    if (ranked) {
      rankedLosses++;
    } else {
      unRankedLosses++;
    }
  }

  public void increaseWinStreak(boolean ranked) {
    if (ranked) {
      rankedWinStreak++;
      rankedLoseStreak = 0;

      if (rankedWinStreak > rankedBestWinStreak) {
        rankedBestWinStreak = rankedWinStreak;
      }
    } else {
      unRankedWinStreak++;
      unRankedLoseStreak = 0;

      if (unRankedWinStreak > unRankedBestWinStreak) {
        unRankedBestWinStreak = unRankedWinStreak;
      }
    }
  }

  public void increaseLoseStreak(boolean ranked) {
    if (ranked) {
      rankedLoseStreak++;
      rankedWinStreak = 0;

      if (rankedLoseStreak > rankedBestLoseStreak) {
        rankedBestLoseStreak = rankedLoseStreak;
      }
    } else {
      unRankedLoseStreak++;
      unRankedWinStreak = 0;

      if (unRankedLoseStreak > unRankedBestLoseStreak) {
        unRankedBestLoseStreak = unRankedLoseStreak;
      }
    }
  }

  public void increaseElo(int elo) {
    this.elo += elo;
  }

  public void decreaseElo(int elo) {
    if (this.elo < 100) {
      return;
    }

    this.elo -= elo;
  }

  public void increaseKills() {
    kills++;
  }

  public void increaseDeaths() {
    deaths++;
  }

  /**
   * Writes these statistics into the ladder's section of the profile file. The ranked results are
   * only written for ranked ladders; what the section held otherwise is kept.
   *
   * @param data the ladder's section, updated in place
   * @param ranked whether the ladder is ranked
   */
  public void setData(LadderStatsData data, boolean ranked) {
    LadderStatsData.Results unranked =
        data.getUnranked() != null ? data.getUnranked() : new LadderStatsData.Results();
    unranked.setWins(unRankedWins);
    unranked.setLosses(unRankedLosses);
    unranked.setWinStreak(unRankedWinStreak);
    unranked.setBestWinStreak(unRankedBestWinStreak);
    unranked.setLoseStreak(unRankedLoseStreak);
    unranked.setBestLoseStreak(unRankedBestLoseStreak);
    data.setUnranked(unranked);

    if (ranked) {
      LadderStatsData.Results rankedResults =
          data.getRanked() != null ? data.getRanked() : new LadderStatsData.Results();
      rankedResults.setWins(rankedWins);
      rankedResults.setLosses(rankedLosses);
      rankedResults.setWinStreak(rankedWinStreak);
      rankedResults.setBestWinStreak(rankedBestWinStreak);
      rankedResults.setLoseStreak(rankedLoseStreak);
      rankedResults.setBestLoseStreak(rankedBestLoseStreak);
      rankedResults.setElo(elo != 0 ? elo : LadderManager.getDEFAULT_ELO());
      data.setRanked(rankedResults);
    }

    LadderStatsData.Global global =
        data.getGlobal() != null ? data.getGlobal() : new LadderStatsData.Global();
    global.setKills(kills);
    global.setDeaths(deaths);
    data.setGlobal(global);
  }

  /**
   * Reads these statistics from the ladder's section of the profile file; missing values are 0.
   *
   * @param data the ladder's section, or {@code null} if the file has none
   * @param ranked whether the ladder is ranked
   */
  public void getData(LadderStatsData data, boolean ranked) {
    LadderStatsData.Results unranked = data != null ? data.getUnranked() : null;
    if (unranked == null) {
      unranked = new LadderStatsData.Results();
    }
    unRankedWins = valueOf(unranked.getWins());
    unRankedLosses = valueOf(unranked.getLosses());
    unRankedWinStreak = valueOf(unranked.getWinStreak());
    unRankedBestWinStreak = valueOf(unranked.getBestWinStreak());
    unRankedLoseStreak = valueOf(unranked.getLoseStreak());
    unRankedBestLoseStreak = valueOf(unranked.getBestLoseStreak());

    if (ranked) {
      LadderStatsData.Results rankedResults = data != null ? data.getRanked() : null;
      if (rankedResults == null) {
        rankedResults = new LadderStatsData.Results();
      }
      rankedWins = valueOf(rankedResults.getWins());
      rankedLosses = valueOf(rankedResults.getLosses());
      rankedWinStreak = valueOf(rankedResults.getWinStreak());
      rankedBestWinStreak = valueOf(rankedResults.getBestWinStreak());
      rankedLoseStreak = valueOf(rankedResults.getLoseStreak());
      rankedBestLoseStreak = valueOf(rankedResults.getBestLoseStreak());
      elo = valueOf(rankedResults.getElo());

      if (elo == 0) {
        elo = LadderManager.getDEFAULT_ELO();
      }
    }

    LadderStatsData.Global global = data != null ? data.getGlobal() : null;
    kills = global != null ? valueOf(global.getKills()) : 0;
    deaths = global != null ? valueOf(global.getDeaths()) : 0;
  }

  private static int valueOf(Integer value) {
    return value != null ? value : 0;
  }

  public void reset() {
    unRankedWins = 0;
    unRankedLosses = 0;
    unRankedWinStreak = 0;
    unRankedBestWinStreak = 0;
    unRankedLoseStreak = 0;
    unRankedBestLoseStreak = 0;

    rankedWins = 0;
    rankedLosses = 0;
    rankedWinStreak = 0;
    rankedBestWinStreak = 0;
    rankedLoseStreak = 0;
    rankedBestLoseStreak = 0;
    elo = LadderManager.getDEFAULT_ELO();

    kills = 0;
    deaths = 0;
  }
}

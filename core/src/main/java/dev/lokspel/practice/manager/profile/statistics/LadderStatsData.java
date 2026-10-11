package dev.lokspel.practice.manager.profile.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The statistics of one ladder in a profile file, stored under {@code stats.ladder-stats.<ladder>}.
 */
@Getter
@Setter
public class LadderStatsData {

  @JsonProperty("unranked")
  private Results unranked;

  @JsonProperty("ranked")
  private Results ranked;

  @JsonProperty("global")
  private Global global;

  /** Wins, losses and streaks of one weight class; {@code elo} is only kept for ranked. */
  @Getter
  @Setter
  public static class Results {

    @JsonProperty("wins")
    private Integer wins;

    @JsonProperty("losses")
    private Integer losses;

    @JsonProperty("win-streak")
    private Integer winStreak;

    @JsonProperty("best-win-streak")
    private Integer bestWinStreak;

    @JsonProperty("lose-streak")
    private Integer loseStreak;

    @JsonProperty("best-lose-streak")
    private Integer bestLoseStreak;

    @JsonProperty("elo")
    private Integer elo;
  }

  /** Kills and deaths regardless of weight class. */
  @Getter
  @Setter
  public static class Global {

    @JsonProperty("kills")
    private Integer kills;

    @JsonProperty("deaths")
    private Integer deaths;
  }
}

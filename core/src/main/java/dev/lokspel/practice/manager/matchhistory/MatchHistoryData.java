package dev.lokspel.practice.manager.matchhistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * The file {@code match-history/<uuid>.yml}: a player's recent matches under {@code matches}, keyed
 * by match id.
 */
@Getter
@Setter
public class MatchHistoryData {

  @JsonProperty("matches")
  private Map<String, Match> matches = new LinkedHashMap<>();

  /** One recorded match, seen from the file owner's side. */
  @Getter
  @Setter
  public static class Match {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("opponentUuid")
    private String opponentUuid;

    @JsonProperty("username")
    private String username;

    @JsonProperty("opponentName")
    private String opponentName;

    @JsonProperty("kitName")
    private String kitName;

    @JsonProperty("arenaName")
    private String arenaName;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("opponentScore")
    private Integer opponentScore;

    @JsonProperty("finalHealth")
    private Double finalHealth;

    @JsonProperty("opponentFinalHealth")
    private Double opponentFinalHealth;

    /** The winner's UUID, or an empty string for a draw. */
    @JsonProperty("winnerUuid")
    private String winnerUuid;

    @JsonProperty("matchDuration")
    private Integer matchDuration;

    @JsonProperty("playedAt")
    private Long playedAt;
  }
}

package dev.lokspel.practice.config.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
public class Match {
  @JsonProperty("START-TITLES")
  private StartTitles startTitles = new StartTitles();

  @JsonProperty("END-TITLES")
  private EndTitles endTitles = new EndTitles();

  @JsonProperty("COOLDOWN")
  private Cooldown cooldown = new Cooldown();

  @JsonProperty("ACTIONBAR")
  private Actionbar actionbar = new Actionbar();

  @JsonProperty("CANT-BUILD-OUTSIDE-ARENA")
  private String cantBuildOutsideArena = "<red>You can't build outside the arena.";

  @JsonProperty("CANT-BUILD-OVER-LIMIT")
  private String cantBuildOverLimit = "<red>You can't build there.";

  @JsonProperty("CANT-CRAFT")
  private String cantCraft = "<red>You can't craft with this ladder.";

  @JsonProperty("CANT-BREAK-OWN-BED")
  private String cantBreakOwnBed = "<red>You can't break your own bed.";

  @JsonProperty("ARROW-HIT-PLAYER")
  private String arrowHitPlayer = "<red>%player% is now at %health% ♥";

  @JsonProperty("EXP-RECEIVED")
  private String expReceived =
      "<yellow><bold>(EXP) <reset><white>You received <yellow>%exp% <white>experience!";

  @JsonProperty("ONLY-THROW-EXP-BOTTLES")
  private String onlyThrowExpBottles = "<red>You can only throw EXP bottles in build ladders.";

  @JsonProperty("ONLY-CHARGE-WIND")
  private String onlyChargeWind = "<red>You can only use wind charge in build ladders.";

  @Comment("%oldDivision_fullName% & %newDivision_fullName%")
  @JsonProperty("REACHED-NEW-DIVISION")
  private String reachedNewDivision =
      "<green>Congratulations! <yellow>You have reached %newDivision_fullName% <yellow>division.";

  @JsonProperty("MATCH-OVER-IN-30")
  private String matchOverIn30 = "<yellow>Match is over in <red>30 <yellow>seconds.";

  @JsonProperty("ARENA-ROLLING-BACK")
  private String arenaRollingBack =
      "<yellow>Rolling back arena... <gray>Waiting for next round to start.";

  @JsonProperty("REMATCH-REQUEST")
  private RematchRequest rematchRequest = new RematchRequest();

  @JsonProperty("DUEL")
  private MatchTypeMessages duel = MatchTypeMessages.duel();

  @JsonProperty("PARTY-FFA")
  private MatchTypeMessages partyFfa = MatchTypeMessages.partyFfa();

  @JsonProperty("PARTY-SPLIT")
  private MatchTypeMessages partySplit = MatchTypeMessages.partySplit();

  @JsonProperty("PARTY-VS-PARTY")
  private MatchTypeMessages partyVsParty = MatchTypeMessages.partyVsParty();

  @Getter
  public static class StartTitles {
    @JsonProperty("COUNTDOWN")
    private String countdown = "<red><bold>%remaining%";

    @JsonProperty("FIGHT")
    private String fight = "<gold><bold>Fight!";
  }

  @Getter
  public static class EndTitles {
    @JsonProperty("MATCH")
    private MatchSection match = new MatchSection();

    @JsonProperty("ROUND")
    private Round round = new Round();

    @Getter
    public static class MatchSection {
      @JsonProperty("VICTORY")
      private String victory = "<green><bold>Victory!";

      @JsonProperty("DEFEAT")
      private String defeat = "<red><bold>Defeat!";

      @JsonProperty("SUBTITLE-VICTORY")
      private String subtitleVictory =
          "<gray>Final score: <green>%winnerScore% <dark_gray>- <red>%loserScore%";

      @JsonProperty("SUBTITLE-DEFEAT")
      private String subtitleDefeat =
          "<gray>Final score: <green>%winnerScore% <dark_gray>- <red>%loserScore%";
    }

    @Getter
    public static class Round {
      @JsonProperty("VICTORY")
      private String victory = "<green><bold>Victory!";

      @JsonProperty("DEFEAT")
      private String defeat = "<red><bold>Defeat!";

      @JsonProperty("SUBTITLE-PLAYER")
      private String subtitlePlayer =
          "<gray><yellow>%winner% <dark_gray>(<green>%winnerScore%<dark_gray>) <gray>-"
              + " <dark_gray>(<red>%loserScore%<dark_gray>) <yellow>%loser%";

      @JsonProperty("SUBTITLE-TEAM")
      private String subtitleTeam =
          "<gray><yellow>%scoredTeam% <gray>scored <dark_gray>| <gray>%team1Name%"
              + " <white>%team1Score% <dark_gray>- <white>%team2Score% <gray>%team2Name%";
    }
  }

  @Getter
  public static class Cooldown {
    @JsonProperty("ENDER-PEARL")
    private String enderPearl =
        "<red>You cannot use this for another <yellow>%seconds% <red>%secondName%.";

    @JsonProperty("GOLDEN-APPLE")
    private String goldenApple =
        "<red>You cannot eat another <gold>golden apple</gold> for another <yellow>%seconds%"
            + " <red>%secondName%.";

    @JsonProperty("FIREBALL")
    private String fireball =
        "<red>You cannot throw another fireball for another <yellow>%seconds% <red>%secondName%.";

    @JsonProperty("FIREWORK-ROCKET-COOLDOWN")
    private String fireworkRocketCooldown =
        "<red>You cannot use firework rockets for another <yellow>%seconds% <red>%secondName%.";
  }

  @Getter
  public static class Actionbar {
    @JsonProperty("TNT-SUMO-TNT-EARN")
    private String tntSumoTntEarn = "<yellow><bold>+1 TNT <gray>(Attack)";
  }

  @Getter
  public static class RematchRequest {
    @JsonProperty("TARGET-OFFLINE")
    private String targetOffline = "<red>%target% has left the server.";

    @JsonProperty("ALREADY-SENT")
    private String alreadySent = "<red>The rematch request has already been sent out.";

    @JsonProperty("TARGET-DONT-ACCEPT")
    private String targetDontAccept = "<red>%target% doesn't accept rematch requests.";

    @JsonProperty("CANT-SEND-ANYMORE")
    private String cantSendAnymore = "<red>You can't send rematch request for %target% anymore.";
  }
}

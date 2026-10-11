package dev.lokspel.practice.config.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
public class MatchTypeMessages {
  @JsonProperty("PLAYER-LEFT")
  private String playerLeft;

  @JsonProperty("RANKED-REQUIREMENT-REACHED")
  private String rankedRequirementReached;

  @JsonProperty("START")
  private Start start;

  @JsonProperty("LADDER-SPECIFIC")
  private LadderSpecific ladderSpecific;

  @JsonProperty("MATCH-START-UNRANKED")
  private List<String> matchStartUnranked;

  @Comment("You can also use %player1win% & %player2win% - Shows how many ranked the players won.")
  @JsonProperty("MATCH-START-RANKED")
  private List<String> matchStartRanked;

  @JsonProperty("MATCH-END-ROUND-DRAW")
  private List<String> matchEndRoundDraw;

  @JsonProperty("MATCH-END-ROUND")
  private List<String> matchEndRound;

  @JsonProperty("MATCH-END-DRAW")
  private List<String> matchEndDraw;

  @JsonProperty("MATCH-END")
  private MatchEnd matchEnd;

  @JsonProperty("PLAYER-DIE")
  private String playerDie;

  @JsonProperty("MATCH-START")
  private List<String> matchStart;

  static MatchTypeMessages duel() {
    MatchTypeMessages value = new MatchTypeMessages();
    value.playerLeft = "<red>%player% <yellow>left the match.";
    value.rankedRequirementReached =
        "<green>Congratulations! <yellow>You have reached the required conditions for ranked"
            + " matches.";
    value.start = Start.duel();
    value.ladderSpecific = LadderSpecific.duel();
    value.matchStartUnranked =
        List.of(
            "<gray><st>----------------------------------------",
            "%matchTypeName% <gray>- %weightClassName%",
            "",
            " <yellow>» Kit: <white>%ladder%",
            " <yellow>» Map: <white>%arena%",
            " <yellow>» Rounds: <white>%rounds%",
            "<gray><st>----------------------------------------");
    value.matchStartRanked =
        List.of(
            "<gray><st>----------------------------------------",
            "%matchTypeName% - %weightClassName%",
            "",
            " <yellow>» Kit: <white>%ladder%",
            " <yellow>» Map: <white>%arena%",
            " <yellow>» Rounds: <white>%rounds%",
            "",
            "<gold>%player1%'s elo: <white>%player1elo%",
            "<gold>%player2%'s elo: <white>%player2elo%",
            "<gray><st>----------------------------------------");
    value.matchEndRoundDraw =
        List.of(
            "<gray><st>----------------------------------------",
            "<red>The round has ended with a tie.",
            "<gray><st>----------------------------------------");
    value.matchEndRound =
        List.of(
            "<gray><st>----------------------------------------",
            "%player% <yellow>has <green>won <yellow>the round. <light_purple>%round% <yellow>more"
                + " to win the match.",
            "<gray><st>----------------------------------------");
    value.matchEndDraw =
        List.of(
            "<gray><st>----------------------------------------",
            "<red>The match is ended with a draw.",
            "<gray><st>----------------------------------------");
    value.matchEnd = MatchEnd.duel();

    return value;
  }

  static MatchTypeMessages partyFfa() {
    MatchTypeMessages value = new MatchTypeMessages();
    value.playerLeft = "<red>%player% <yellow>left the match.";
    value.start = Start.duel();
    value.matchEndRoundDraw =
        List.of(
            "<gray><st>----------------------------------------",
            "<red>No one has won this round.",
            "<gray><st>----------------------------------------");
    value.matchEndRound =
        List.of(
            "<gray><st>----------------------------------------",
            "%player% <yellow>has <green>won <yellow>the round. <light_purple>%round% <yellow>more"
                + " to win the match.",
            "<gray><st>----------------------------------------");
    value.matchEndDraw =
        List.of(
            "<gray><st>----------------------------------------",
            "<red>The match is ended with no winner.",
            "<gray><st>----------------------------------------");
    value.matchEnd = MatchEnd.partyFfa();
    value.playerDie =
        "<red>%player% <yellow>has been eliminated. <gray>(%alivePlayers% players remaining)";
    value.matchStart =
        List.of(
            "<gray><st>----------------------------------------",
            "%matchTypeName%",
            "",
            " <yellow>» Kit: <white>%ladder%",
            " <yellow>» Map: <white>%arena%",
            " <yellow>» Rounds: <white>%rounds%",
            "",
            "<gold>Players: <gray>%players%",
            "<gray><st>----------------------------------------");

    return value;
  }

  static MatchTypeMessages partySplit() {
    MatchTypeMessages value = new MatchTypeMessages();
    value.playerLeft = "<red>%player% <yellow>left the match.";
    value.start = Start.duel();
    value.ladderSpecific = LadderSpecific.duel();
    value.matchEndRoundDraw =
        List.of(
            "<gray><st>----------------------------------------",
            "<red>The round has ended with a tie.",
            "<gray><st>----------------------------------------");
    value.matchEndRound =
        List.of(
            "<gray><st>----------------------------------------",
            "%team% <yellow>has <green>won <yellow>the round. <light_purple>%round% <yellow>more to"
                + " win the match.",
            "<gray><st>----------------------------------------");
    value.matchEndDraw =
        List.of(
            "<gray><st>----------------------------------------",
            "<red>The match is ended with a draw.",
            "<gray><st>----------------------------------------");
    value.matchEnd = MatchEnd.partySplit();
    value.playerDie =
        "<red>%player% died. <gray>(%team%<gray>) - <yellow>%playerTeamLeft% <gray>players left in"
            + " their team.";
    value.matchStart =
        List.of(
            "<gray><st>----------------------------------------",
            "%matchTypeName%",
            "",
            " <yellow>» Kit: <white>%ladder%",
            " <yellow>» Map: <white>%map%",
            " <yellow>» Rounds: <white>%rounds%",
            "",
            "%team1name%: <gray>%team1players%",
            "%team2name%: <gray>%team2players%",
            "<gray><st>----------------------------------------");

    return value;
  }

  static MatchTypeMessages partyVsParty() {
    MatchTypeMessages value = new MatchTypeMessages();
    value.playerLeft = "<red>%player% <yellow>left the match.";
    value.start = Start.duel();
    value.ladderSpecific = LadderSpecific.partyVsParty();
    value.matchEndRoundDraw =
        List.of(
            "<gray><st>----------------------------------------",
            "<red>The round has ended with a tie.",
            "<gray><st>----------------------------------------");
    value.matchEndRound =
        List.of(
            "<gray><st>----------------------------------------",
            "%team% <yellow>has <green>won <yellow>the round. <light_purple>%round% <yellow>more to"
                + " win the match.",
            "<gray><st>----------------------------------------");
    value.matchEndDraw =
        List.of(
            "<gray><st>----------------------------------------",
            "<red>The match is ended with a draw.",
            "<gray><st>----------------------------------------");
    value.matchEnd = MatchEnd.partySplit();
    value.playerDie =
        "<red>%player% died. <gray>(%team%<gray>) - <yellow>%playerTeamLeft% <gray>players left in"
            + " their team.";
    value.matchStart =
        List.of(
            "<gray><st>----------------------------------------",
            "%matchTypeName%",
            "",
            " <yellow>» Kit: <white>%ladder%",
            " <yellow>» Map: <white>%arena%",
            " <yellow>» Rounds: <white>%rounds%",
            "",
            "<gold>%party1leader%<gray>'s party - %team1name%: <white>%team1players%",
            "<gold>%party2leader%<gray>'s party - %team2name%: <white>%team2players%",
            "<gray><st>----------------------------------------");

    return value;
  }

  @Getter
  public static class Start {
    @JsonProperty("MATCH-STARTING")
    private String matchStarting;

    @JsonProperty("MATCH-STARTED")
    private String matchStarted;

    @JsonProperty("ROUND-STARTING")
    private String roundStarting;

    @JsonProperty("ROUND-STARTED")
    private String roundStarted;

    static Start duel() {
      Start value = new Start();
      value.matchStarting = "<yellow>Match is starting in <gold>%seconds% <yellow>%secondName%.";
      value.matchStarted = "<green>Match has been started.";
      value.roundStarting = "<yellow>Round is starting in <gold>%seconds% <yellow>%secondName%.";
      value.roundStarted = "<green>Round has been started.";

      return value;
    }
  }

  @Getter
  public static class LadderSpecific {
    @JsonProperty("BED-WARS")
    private LadderSpecificMessages bedWars;

    @JsonProperty("FIREBALL-FIGHT")
    private LadderSpecificMessages fireballFight;

    @JsonProperty("MLG-RUSH")
    private LadderSpecificMessages mlgRush;

    @JsonProperty("BATTLE-RUSH")
    private LadderSpecificMessages battleRush;

    @JsonProperty("BRIDGES")
    private LadderSpecificMessages bridges;

    static LadderSpecific duel() {
      LadderSpecific value = new LadderSpecific();
      value.bedWars = LadderSpecificMessages.duelBedWars();
      value.fireballFight = LadderSpecificMessages.duelBedWars();
      value.mlgRush = LadderSpecificMessages.duelBedWars();
      value.battleRush = LadderSpecificMessages.duelBattleRush();
      value.bridges = LadderSpecificMessages.duelBattleRush();

      return value;
    }

    static LadderSpecific partyVsParty() {
      LadderSpecific value = new LadderSpecific();
      value.bedWars = LadderSpecificMessages.partyVsPartyBedWars();
      value.fireballFight = LadderSpecificMessages.partyVsPartyBedWars();
      value.mlgRush = LadderSpecificMessages.partyVsPartyBedWars();
      value.battleRush = LadderSpecificMessages.partyVsPartyBattleRush();
      value.bridges = LadderSpecificMessages.duelBattleRush();

      return value;
    }
  }

  @Getter
  public static class MatchEnd {
    @JsonProperty("SPECTATOR-EXTENSION")
    private List<String> spectatorExtension;

    @Comment("Also usable: %winnerOldElo%, %loserOldElo%, %winnerEloChange% and %loserEloChange%")
    @JsonProperty("RANKED-EXTENSION")
    private List<String> rankedExtension;

    @JsonProperty("MESSAGE")
    private List<String> message;

    @JsonProperty("LOSER-PLAYER-FORMAT")
    private String loserPlayerFormat;

    @JsonProperty("LEFT-PLAYER-FORMAT")
    private String leftPlayerFormat;

    @JsonProperty("SEPARATOR-FORMAT")
    private String separatorFormat;

    @JsonProperty("WINNER-PLAYER-FORMAT")
    private String winnerPlayerFormat;

    static MatchEnd duel() {
      MatchEnd value = new MatchEnd();
      value.spectatorExtension = List.of("<aqua>Spectators (%size%): <gray>%spectators%");
      value.rankedExtension =
          List.of(
              "<yellow>Elo Changes: <green>%winner% +%winnerEloChange% (%winnerNewElo%)"
                  + " <red>%loser% -%loserEloChange% (%loserNewElo%)",
              "<gray><st>----------------------------------------");
      value.message =
          List.of(
              "<gray><st>----------------------------------------",
              "<gold>Post-Match Inventories <gray>(click name to view)",
              "<green>Winner: <hover:show_text:'<green>Click to view inventory of"
                  + " <gold>%winner%<green>.'><click:run_command:'/matchinv %matchId% %winner_uuid%"
                  + " 1'><yellow>%winner%</click></hover> <gray>- <red>Loser:"
                  + " <hover:show_text:'<green>Click to view inventory of"
                  + " <gold>%loser%<green>.'><click:run_command:'/matchinv %matchId% %loser_uuid%"
                  + " 1'><yellow>%loser%</click></hover>",
              "%spectatorExtension%",
              "<gray><st>----------------------------------------",
              "%rankedExtension%");

      return value;
    }

    static MatchEnd partyFfa() {
      MatchEnd value = new MatchEnd();
      value.spectatorExtension = List.of("<aqua>Spectators (%size%): <gray>%spectators%");
      value.message =
          List.of(
              "<gray><st>----------------------------------------",
              "<green>Winner: <hover:show_text:'<green>Click to view inventory of"
                  + " <gold>%winner%<green>.'><click:run_command:'/matchinv %matchId% %winner_uuid%"
                  + " 1'><yellow>%winner%</click></hover>",
              "<red>Losers: <yellow>%losers%",
              "%spectatorExtension%",
              "<gray><st>----------------------------------------");
      value.loserPlayerFormat =
          "<hover:show_text:'<green>Click to view inventory of"
              + " <gold>%player%<green>.'><click:run_command:'/matchinv %matchId% %player_uuid%"
              + " 1'><yellow>%player%</click></hover><gray>";
      value.leftPlayerFormat =
          "<hover:show_text:'<yellow>%player% <red>has left the game before it"
              + " ended.'><red>%player%</hover><gray>";
      value.separatorFormat = "<gray>, ";

      return value;
    }

    static MatchEnd partySplit() {
      MatchEnd value = new MatchEnd();
      value.spectatorExtension = List.of("<aqua>Spectators (%size%): <gray>%spectators%");
      value.message =
          List.of(
              "<gray><st>----------------------------------------",
              "<green>Winners: <gray>(%winnerTeam%<gray>) <gray>- <yellow>%winners%",
              "<red>Losers: <gray>(%loserTeam%<gray>) <gray>- <yellow>%losers%",
              "%spectatorExtension%",
              "<gray><st>----------------------------------------");
      value.loserPlayerFormat =
          "<hover:show_text:'<green>Click to view inventory of"
              + " <gold>%player%<green>.'><click:run_command:'/matchinv %matchId% %player_uuid%"
              + " 1'><yellow>%player%</click></hover><gray>";
      value.leftPlayerFormat = "<gray><st>%player%</st></gray>";
      value.winnerPlayerFormat =
          "<hover:show_text:'<green>Click to view inventory of"
              + " <gold>%player%<green>.'><click:run_command:'/matchinv %matchId% %player_uuid%"
              + " 1'><yellow>%player%</click></hover><gray>";

      return value;
    }
  }
}

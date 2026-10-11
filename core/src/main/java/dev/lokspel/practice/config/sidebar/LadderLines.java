package dev.lokspel.practice.config.sidebar;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class LadderLines {
  @JsonProperty("DUEL")
  private List<String> duel;

  @JsonProperty("PARTY_FFA")
  private List<String> partyFfa;

  @JsonProperty("PARTY_SPLIT")
  private List<String> partySplit;

  @JsonProperty("PARTY_VS_PARTY")
  private List<String> partyVsParty;

  static LadderLines matchSumo() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Fighting: <yellow>%enemyNameOnly%",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping%<gray>ms | <red>%enemyPing%<gray>ms",
            "",
            " <green>Won: %playerTeamColor%%rounds%",
            " <red>Lost: %enemyTeamColor%%enemyRounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }

  static LadderLines matchPearlfight() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Fighting: <yellow>%enemyNameOnly%",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping%<gray>ms | <red>%enemyPing%<gray>ms",
            "",
            " <green>Won: %playerTeamColor%%rounds%",
            " <red>Lost: %enemyTeamColor%%enemyRounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyFfa =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Players: <gold>%players%<gray>/<yellow>%alivePlayers%",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " <gray>1. <white>%player1% <yellow>%player1rounds%",
            " <gray>2. <white>%player2% <yellow>%player2rounds%",
            " <gray>3. <white>%player3% <yellow>%player3rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partySplit =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " %team1name%<gray>: %team1color%%team1players%<gray>/<green>%team1alivePlayers%"
                + " %team1color%%team1rounds%",
            " %team2name%<gray>: %team2color%%team2players%<gray>/<green>%team2alivePlayers%"
                + " %team2color%%team2rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyVsParty =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " %partyTeamName%<gray>:"
                + " %partyTeamColor%%partyTeamPlayers%<gray>/<green>%partyTeamAlivePlayers%"
                + " %partyTeamColor%%partyTeamRounds%",
            " %enemyTeamName%<gray>:"
                + " %enemyTeamColor%%enemyTeamPlayers%<gray>/<green>%enemyTeamAlivePlayers%"
                + " %enemyTeamColor%%enemyTeamRounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }

  static LadderLines matchBoxing() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Fighting: <yellow>%enemyNameOnly%",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping%<gray>ms | <red>%enemyPing%<gray>ms",
            "",
            "<white>Hits: %overAllHits%",
            " %playerTeamColor%%player%: <white>%hits%",
            " %enemyTeamColor%%enemyName%: <white>%enemyHits%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyFfa =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Players: <gold>%players%<gray>/<yellow>%alivePlayers%",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " <gray>1. <white>%player1boxing%: <yellow>%player1boxingHits%",
            " <gray>2. <white>%player2boxing%: <yellow>%player2boxingHits%",
            " <gray>3. <white>%player3boxing%: <yellow>%player3boxingHits%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partySplit =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " %team1name%: <white>%team1boxingHits%",
            " %team2name%: <white>%team2boxingHits%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyVsParty =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " %partyTeamName%: <white>%partyTeamBoxingHits%",
            " %enemyTeamName%: <white>%enemyTeamBoxingHits%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }

  static LadderLines matchBedwars() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Fighting: <yellow>%enemyNameOnly%",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping%<gray>ms | <red>%enemyPing%<gray>ms",
            "",
            " %playerTeamName%: %playerBedStatus% <gray>YOU",
            " %enemyTeamName%: %enemyBedStatus%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partySplit =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " %team1name%: %team1BedStatus%",
            " %team2name%: %team2BedStatus%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyVsParty =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " %partyTeamName%: %partyTeamBedStatus%",
            " %enemyTeamName%: %enemyTeamBedStatus%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }

  static LadderLines matchBridges() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Fighting: <yellow>%enemyNameOnly%",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping%<gray>ms | <red>%enemyPing%<gray>ms",
            "",
            " <green>Won: %playerTeamColor%%rounds%",
            " <red>Lost: %enemyTeamColor%%enemyRounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partySplit =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " %team1name%<gray>: %team1color%%team1players%<gray>/<green>%team1alivePlayers%"
                + " %team1color%%team1rounds%",
            " %team2name%<gray>: %team2color%%team2players%<gray>/<green>%team2alivePlayers%"
                + " %team2color%%team2rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyVsParty =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Duration: <yellow>%roundDuration%",
            "  <white>Ping: <green>%ping% ms",
            "",
            " %partyTeamName%<gray>:"
                + " %partyTeamColor%%partyTeamPlayers%<gray>/<green>%partyTeamAlivePlayers%"
                + " %partyTeamColor%%partyTeamRounds%",
            " %enemyTeamName%<gray>:"
                + " %enemyTeamColor%%enemyTeamPlayers%<gray>/<green>%enemyTeamAlivePlayers%"
                + " %enemyTeamColor%%enemyTeamRounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }

  static LadderLines spectateSumo() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1color%%player1rounds% <gray>| %team2color%%player2rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }

  static LadderLines spectatePearlfight() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1color%%player1rounds% <gray>| %team2color%%player2rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyFfa =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " <gray>1. <white>%player1% <yellow>%player1rounds%",
            " <gray>2. <white>%player2% <yellow>%player2rounds%",
            " <gray>3. <white>%player3% <yellow>%player3rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partySplit =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1name%<gray>: %team1color%%team1players%<gray>/<green>%team1alivePlayers%"
                + " %team1color%%team1rounds%",
            " %team2name%<gray>: %team2color%%team2players%<gray>/<green>%team2alivePlayers%"
                + " %team2color%%team2rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyVsParty =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1name%<gray>: %team1color%%team1players%<gray>/<green>%team1alivePlayers%"
                + " %team1color%%team1rounds%",
            " %team2name%<gray>: %team2color%%team2players%<gray>/<green>%team2alivePlayers%"
                + " %team2color%%team2rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }

  static LadderLines spectateBoxing() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1color%%player1%: <white>%player1hits%",
            " %team2color%%player2%: <white>%player2hits%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyFfa =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " <gray>1. <white>%player1boxing%: <yellow>%player1boxingHits%",
            " <gray>2. <white>%player2boxing%: <yellow>%player2boxingHits%",
            " <gray>3. <white>%player3boxing%: <yellow>%player3boxingHits%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partySplit =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1name%: <white>%team1boxingHits%",
            " %team2name%: <white>%team2boxingHits%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyVsParty =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1name%: <white>%team1boxingHits%",
            " %team2name%: <white>%team2boxingHits%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }

  static LadderLines spectateBedwars() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %player1NameOnly%: %player1BedStatus%",
            " %player2NameOnly%: %player2BedStatus%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partySplit =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1name%: %team1BedStatus%",
            " %team2name%: %team2BedStatus%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyVsParty =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1name%: %team1BedStatus%",
            " %team2name%: %team2BedStatus%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }

  static LadderLines spectateBridges() {
    LadderLines value = new LadderLines();
    value.duel =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1color%%player1rounds% <gray>| %team2color%%player2rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partySplit =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1name%<gray>: %team1color%%team1players%<gray>/<green>%team1alivePlayers%"
                + " %team1color%%team1rounds%",
            " %team2name%<gray>: %team2color%%team2players%<gray>/<green>%team2alivePlayers%"
                + " %team2color%%team2rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");
    value.partyVsParty =
        List.of(
            "<gray><st>---------------------",
            "<gold><b>Info",
            "  <white>Kit: <yellow>%ladder%",
            "  <white>Arena: <yellow>%arena%",
            "  <white>Duration: <yellow>%roundDuration%",
            "",
            " %team1name%<gray>: %team1color%%team1players%<gray>/<green>%team1alivePlayers%"
                + " %team1color%%team1rounds%",
            " %team2name%<gray>: %team2color%%team2players%<gray>/<green>%team2alivePlayers%"
                + " %team2color%%team2rounds%",
            "",
            "<gold>play.astralpractice.com",
            "<gray><st>---------------------");

    return value;
  }
}

package dev.lokspel.practice.manager.fight.match.type.playersvsplayers.partysplit;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.enums.TeamEnum;
import dev.lokspel.practice.manager.fight.match.type.playersvsplayers.PlayersVsPlayersRound;
import dev.lokspel.practice.manager.fight.match.util.EndMessageUtil;
import dev.lokspel.practice.manager.fight.match.util.TeamUtil;

public class PartySplitRound extends PlayersVsPlayersRound {

  protected PartySplitRound(Match match, int roundNumber) {
    super(match, roundNumber);
  }

  @Override
  public void sendEndMessage(boolean endMatch) {
    PartySplit partySplit = (PartySplit) match;
    if (endMatch) {
      TeamEnum matchWinner = this.getMatch().getMatchWinner();
      if (matchWinner != null) {
        // Use getOriginalTeamPlayers to include all players who started the match (including those
        // who left)
        for (String message :
            EndMessageUtil.getEndMessage(
                partySplit,
                matchWinner,
                partySplit.getOriginalTeamPlayers(matchWinner),
                partySplit.getOriginalTeamPlayers(TeamUtil.getOppositeTeam(matchWinner)))) {
          this.match.sendMessage(message, true);
        }
      } else {
        for (String line : PracticeConfig.language().getMatch().getPartySplit().getMatchEndDraw()) {
          this.match.sendMessage(line, true);
        }
      }
    } else {
      if (roundWinner != null) {
        for (String line :
            PracticeConfig.language().getMatch().getPartySplit().getMatchEndRound()) {
          this.match.sendMessage(
              line.replace("%team%", roundWinner.getNameMm())
                  .replace(
                      "%round%",
                      String.valueOf(
                          (match.getWinsNeeded() - partySplit.getWonRounds(roundWinner)))),
              true);
        }
      } else {
        for (String line :
            PracticeConfig.language().getMatch().getPartySplit().getMatchEndRoundDraw()) {
          this.match.sendMessage(line, true);
        }
      }
    }
  }
}

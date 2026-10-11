package dev.lokspel.practice.manager.fight.match.type.duel;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.Round;
import dev.lokspel.practice.manager.fight.match.interfaces.PlayerWinner;
import dev.lokspel.practice.manager.fight.match.util.EndMessageUtil;
import dev.lokspel.practice.manager.fight.match.util.MatchUtil;
import dev.lokspel.practice.manager.fight.match.util.RewardCommandManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.statistics.LadderStats;
import dev.lokspel.practice.util.Pair;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class DuelRound extends Round implements PlayerWinner {

  private Player roundWinner;

  public DuelRound(Match match, int round) {
    super(match, round);
  }

  @Override
  public void sendEndMessage(boolean endMatch) {
    Duel duel = (Duel) match;
    Ladder ladder = duel.getLadder();

    if (endMatch) {
      Player matchWinner = this.getMatch().getMatchWinner();
      if (matchWinner != null) {
        Profile winnerProfile = duel.getPlayerProfiles().get(matchWinner);
        Profile loserProfile = duel.getPlayerProfiles().get(duel.getOppositePlayer(matchWinner));

        List<String> rankedExtension = new ArrayList<>();
        if (ladder instanceof NormalLadder normalLadder) {

          LadderStats winnerLadderStats = winnerProfile.getStats().getLadderStat(normalLadder);
          winnerLadderStats.increaseWins(duel.isRanked());
          winnerProfile.getStats().increaseWinStreak(normalLadder, duel.isRanked());

          LadderStats loserLadderStats = loserProfile.getStats().getLadderStat(normalLadder);
          loserLadderStats.increaseLosses(duel.isRanked());
          loserProfile.getStats().increaseLoseStreak(normalLadder, duel.isRanked());

          if (duel.isRanked()) {
            Pair<Integer, Integer> eloChanges =
                MatchUtil.getEloChange(winnerLadderStats.getElo(), loserLadderStats.getElo());
            int winnerEloChange = eloChanges.getFirst();
            int loserEloChange = Math.abs(eloChanges.getSecond());

            int winnerOldElo = winnerLadderStats.getElo();
            winnerLadderStats.increaseElo(winnerEloChange);

            int loserOldElo = loserLadderStats.getElo();
            loserLadderStats.decreaseElo(loserEloChange);

            for (String reLine : LanguageManager.getList("MATCH.DUEL.MATCH-END.RANKED-EXTENSION")) {
              rankedExtension.add(
                  reLine
                      .replace("%winner%", matchWinner.getName())
                      .replace("%loser%", duel.getOppositePlayer(matchWinner).getName())
                      .replace("%eloChange%", String.valueOf(winnerEloChange))
                      .replace("%winnerEloChange%", String.valueOf(winnerEloChange))
                      .replace("%loserEloChange%", String.valueOf(loserEloChange))
                      .replace("%winnerNewElo%", String.valueOf(winnerLadderStats.getElo()))
                      .replace("%loserNewElo%", String.valueOf(loserLadderStats.getElo()))
                      .replace("%winnerOldElo%", String.valueOf(winnerOldElo))
                      .replace("%loserOldElo%", String.valueOf(loserOldElo)));
            }
          }
        }

        for (String message : EndMessageUtil.getEndMessage(duel, rankedExtension)) {
          duel.sendMessage(message, true);
        }

        RewardCommandManager.getInstance().executeCommands(duel, duel.isRanked());
      } else {
        for (String line : LanguageManager.getList("MATCH.DUEL.MATCH-END-DRAW")) {
          duel.sendMessage(line, true);
        }
      }
    } else {
      if (roundWinner != null) {
        for (String line : LanguageManager.getList("MATCH.DUEL.MATCH-END-ROUND")) {
          duel.sendMessage(
              line.replace("%player%", roundWinner.getName())
                  .replace(
                      "%round%",
                      String.valueOf((match.getWinsNeeded() - duel.getWonRounds(roundWinner)))),
              true);
        }
      } else {
        for (String line : LanguageManager.getList("MATCH.DUEL.MATCH-END-ROUND-DRAW")) {
          duel.sendMessage(line, true);
        }
      }
    }
  }

  @Override
  public Duel getMatch() {
    return (Duel) this.match;
  }
}

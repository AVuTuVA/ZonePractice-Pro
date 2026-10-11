package dev.lokspel.practice.manager.fight.match.type.partyffa;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.Round;
import dev.lokspel.practice.manager.fight.match.interfaces.PlayerWinner;
import dev.lokspel.practice.manager.fight.match.util.EndMessageUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Setter
@Getter
public class PartyFfaRound extends Round implements PlayerWinner {

  private Player roundWinner;

  protected PartyFfaRound(Match match, int roundNumber) {
    super(match, roundNumber);
  }

  @Override
  public void sendEndMessage(boolean endMatch) {
    PartyFfa partyFfa = (PartyFfa) match;

    if (endMatch) {
      Player matchWinner = this.getMatch().getMatchWinner();
      if (matchWinner != null) {
        for (String message : EndMessageUtil.getEndMessage(partyFfa, this.getLosers())) {
          this.match.sendMessage(message, true);
        }
      } else {
        for (String line : LanguageManager.getList("MATCH.PARTY-FFA.MATCH-END-DRAW")) {
          this.match.sendMessage(line, true);
        }
      }
    } else {
      if (roundWinner != null) {
        for (String line : LanguageManager.getList("MATCH.PARTY-FFA.MATCH-END-ROUND")) {
          this.match.sendMessage(
              line.replace("%player%", roundWinner.getName())
                  .replace(
                      "%round%",
                      String.valueOf((match.getWinsNeeded() - partyFfa.getWonRounds(roundWinner)))),
              true);
        }
      } else {
        for (String line : LanguageManager.getList("MATCH.PARTY-FFA.MATCH-END-ROUND-DRAW")) {
          this.match.sendMessage(line, true);
        }
      }
    }
  }

  private List<Player> getLosers() {
    List<Player> losers = new ArrayList<>();
    for (Player player : this.match.getMatchPlayers().keySet()) {
      if (!player.equals(roundWinner)) {
        losers.add(player);
      }
    }
    return losers;
  }

  @Override
  public PartyFfa getMatch() {
    return (PartyFfa) this.match;
  }
}

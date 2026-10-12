package dev.lokspel.practice.api;

import dev.lokspel.api.interfaces.Match;
import dev.lokspel.api.match.MatchApi;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import org.bukkit.entity.Player;

public class MatchApiImpl implements MatchApi {

  @Override
  public Match getMatch(Player player) {
    return MatchManager.getInstance().getLiveMatchByPlayer(player);
  }

  @Override
  public Match getSpectatingMatch(Player player) {
    return MatchManager.getInstance().getLiveMatchBySpectator(player);
  }

  @Override
  public void endMatch(Player player, String endMessage) {
    var match = MatchManager.getInstance().getLiveMatchByPlayer(player);
    if (match == null) {
      return;
    }

    if (endMessage != null) {
      match.sendMessage(endMessage, true);
    }

    match.endMatch();
  }
}

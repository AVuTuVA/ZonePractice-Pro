package dev.lokspel.practice.api;

import dev.lokspel.api.ffa.FfaApi;
import dev.lokspel.api.interfaces.Ffa;
import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import org.bukkit.entity.Player;

public class FfaApiImpl implements FfaApi {

  @Override
  public Ffa getFfa(Player player) {
    return FfaManager.getInstance().getFfaByPlayer(player);
  }
}

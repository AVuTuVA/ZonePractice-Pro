package dev.lokspel.practice.api;

import dev.lokspel.api.interfaces.Party;
import dev.lokspel.api.party.PartyApi;
import dev.lokspel.practice.manager.party.PartyManager;
import org.bukkit.entity.Player;

public class PartyApiImpl implements PartyApi {

  @Override
  public Party getParty(Player player) {
    return PartyManager.getInstance().getParty(player);
  }
}

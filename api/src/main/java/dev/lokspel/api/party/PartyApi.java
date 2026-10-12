package dev.lokspel.api.party;

import dev.lokspel.api.interfaces.Party;
import org.bukkit.entity.Player;

/** API section for player parties. */
public interface PartyApi {

  /**
   * Get the party the player belongs to.
   *
   * @param player The player
   * @return The party instance, or null if not in a party
   */
  Party getParty(Player player);
}

package dev.lokspel.practice.manager.inventory.inventoryitem.partyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public class PartySettingsInvItem extends InvItem {

  public PartySettingsInvItem() {
    super(PracticeConfig.inventories().getParty().getNormal().get("PARTY-SETTINGS"));
  }

  @Override
  public void handleClickEvent(Player player) {
    Party party = PartyManager.getInstance().getParty(player);
    if (party == null) {
      return;
    }

    if (!party.getLeader().equals(player)) {
      Common.sendMmMessage(player, PracticeConfig.language().getParty().getNotLeader());
      return;
    }

    party.getPartySettingsGui().open(player);
  }
}

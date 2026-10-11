package dev.lokspel.practice.manager.inventory.inventoryitem.partyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public class PartyGameInvItem extends InvItem {

  public PartyGameInvItem() {
    super(PracticeConfig.inventories().getParty().getNormal().get("HOST-PARTY-GAME"));
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

    if (party.getMembers().size() < 2) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getParty().getNotEnoughPlayerForGame());
      return;
    }

    GuiManager.getInstance().searchGui(GuiType.Party_Events).open(player);
  }
}

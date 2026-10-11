package dev.lokspel.practice.manager.inventory.inventoryitem.partyitems;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public class PartyGameInvItem extends InvItem {

  public PartyGameInvItem() {
    super(
        getItemStack("PARTY.NORMAL.HOST-PARTY-GAME.ITEM"),
        getInt("PARTY.NORMAL.HOST-PARTY-GAME.SLOT"));
  }

  @Override
  public void handleClickEvent(Player player) {
    Party party = PartyManager.getInstance().getParty(player);
    if (party == null) {
      return;
    }

    if (!party.getLeader().equals(player)) {
      Common.sendMmMessage(player, LanguageManager.getString("PARTY.NOT-LEADER"));
      return;
    }

    if (party.getMembers().size() < 2) {
      Common.sendMmMessage(player, LanguageManager.getString("PARTY.NOT-ENOUGH-PLAYER-FOR-GAME"));
      return;
    }

    GuiManager.getInstance().searchGui(GuiType.Party_Events).open(player);
  }
}

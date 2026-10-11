package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public class PartyJoinInvItem extends InvItem {

  public PartyJoinInvItem() {
    super(
        getItemStack("LOBBY-BASIC.NORMAL.PARTY-JOIN.ITEM"),
        getInt("LOBBY-BASIC.NORMAL.PARTY-JOIN.SLOT"));
  }

  @Override
  public void handleClickEvent(Player player) {
    if (!PartyManager.getInstance().hasJoinablePublicParty()) {
      Common.sendMmMessage(player, LanguageManager.getString("PARTY.NO-JOINABLE-PARTY"));
      return;
    }

    GuiManager.getInstance().searchGui(GuiType.Party_PublicParties).open(player);
  }
}

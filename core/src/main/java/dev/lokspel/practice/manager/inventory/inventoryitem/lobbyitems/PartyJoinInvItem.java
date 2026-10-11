package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public class PartyJoinInvItem extends InvItem {

  public PartyJoinInvItem() {
    super(PracticeConfig.inventories().getLobbyBasic().getNormal().get("PARTY-JOIN"));
  }

  @Override
  public void handleClickEvent(Player player) {
    if (!PartyManager.getInstance().hasJoinablePublicParty()) {
      Common.sendMmMessage(player, PracticeConfig.language().getParty().getNoJoinableParty());
      return;
    }

    GuiManager.getInstance().searchGui(GuiType.Party_PublicParties).open(player);
  }
}

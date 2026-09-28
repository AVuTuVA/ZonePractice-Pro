package dev.nandi0813.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.nandi0813.practice.manager.gui.GUIManager;
import dev.nandi0813.practice.manager.gui.GUIType;
import dev.nandi0813.practice.manager.inventory.inventoryitem.InvItem;
import org.bukkit.entity.Player;

public class PartyJoinInvItem extends InvItem {

    public PartyJoinInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.PARTY-JOIN.ITEM"), getInt("LOBBY-BASIC.NORMAL.PARTY-JOIN.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        GUIManager.getInstance().searchGUI(GUIType.Party_PublicParties).open(player);
    }

}

package dev.nandi0813.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.nandi0813.practice.manager.backend.LanguageManager;
import dev.nandi0813.practice.manager.gui.GUIManager;
import dev.nandi0813.practice.manager.gui.GUIType;
import dev.nandi0813.practice.manager.inventory.inventoryitem.InvItem;
import dev.nandi0813.practice.manager.party.PartyManager;
import dev.nandi0813.practice.util.Common;
import org.bukkit.entity.Player;

public class PartyJoinInvItem extends InvItem {

    public PartyJoinInvItem() {
        super(getItemStack("LOBBY-BASIC.NORMAL.PARTY-JOIN.ITEM"), getInt("LOBBY-BASIC.NORMAL.PARTY-JOIN.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        if (!PartyManager.getInstance().hasJoinablePublicParty()) {
            Common.sendMMMessage(player, LanguageManager.getString("PARTY.NO-JOINABLE-PARTY"));
            return;
        }

        GUIManager.getInstance().searchGUI(GUIType.Party_PublicParties).open(player);
    }

}

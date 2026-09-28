package dev.nandi0813.practice.command.party.arguments;

import dev.nandi0813.practice.manager.backend.LanguageManager;
import dev.nandi0813.practice.manager.gui.GUIManager;
import dev.nandi0813.practice.manager.gui.GUIType;
import dev.nandi0813.practice.manager.party.PartyManager;
import dev.nandi0813.practice.util.Common;
import org.bukkit.entity.Player;

public final class PartyPublicArg {

    private PartyPublicArg() {}

    public static void PublicCommand(Player player) {
        if (!player.hasPermission("zpp.party.joinpublic")) {
            Common.sendMMMessage(player, LanguageManager.getString("PARTY.NO-PERMISSION"));
            return;
        }

        if (!PartyManager.getInstance().hasJoinablePublicParty()) {
            Common.sendMMMessage(player, LanguageManager.getString("PARTY.NO-JOINABLE-PARTY"));
            return;
        }

        GUIManager.getInstance().searchGUI(GUIType.Party_PublicParties).open(player);
    }

}

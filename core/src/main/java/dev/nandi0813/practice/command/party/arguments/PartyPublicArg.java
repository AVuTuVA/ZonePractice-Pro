package dev.nandi0813.practice.command.party.arguments;

import dev.nandi0813.practice.manager.gui.GUIManager;
import dev.nandi0813.practice.manager.gui.GUIType;
import org.bukkit.entity.Player;

public final class PartyPublicArg {

    private PartyPublicArg() {}

    public static void PublicCommand(Player player) {
        GUIManager.getInstance().searchGUI(GUIType.Party_PublicParties).open(player);
    }

}

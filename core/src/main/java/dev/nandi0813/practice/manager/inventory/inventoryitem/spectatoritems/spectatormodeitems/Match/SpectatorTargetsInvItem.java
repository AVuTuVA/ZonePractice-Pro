package dev.nandi0813.practice.manager.inventory.inventoryitem.spectatoritems.spectatormodeitems.Match;

import dev.nandi0813.practice.manager.backend.LanguageManager;
import dev.nandi0813.practice.manager.fight.match.Match;
import dev.nandi0813.practice.manager.gui.guis.SpectatorTargetsGui;
import dev.nandi0813.practice.manager.inventory.inventoryitem.InvItem;
import dev.nandi0813.practice.manager.spectator.SpectatorManager;
import dev.nandi0813.practice.util.Common;
import org.bukkit.entity.Player;

public class SpectatorTargetsInvItem extends InvItem {

    public SpectatorTargetsInvItem() {
        super(getItemStack("SPECTATOR.MATCH.NORMAL.TARGETS.ITEM"), getInt("SPECTATOR.MATCH.NORMAL.TARGETS.SLOT"));
    }

    @Override
    public void handleClickEvent(Player player) {
        if (!player.hasPermission("zpp.spectate.targets")) {
            Common.sendMMMessage(player, LanguageManager.getString("SPECTATE.NO-PERMISSIONS"));
            return;
        }

        if (SpectatorManager.getInstance().getSpectators().get(player) instanceof Match match) {
            new SpectatorTargetsGui(match).open(player);
        }
    }

}

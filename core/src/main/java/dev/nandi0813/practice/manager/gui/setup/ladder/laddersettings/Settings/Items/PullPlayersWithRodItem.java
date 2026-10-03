package dev.nandi0813.practice.manager.gui.setup.ladder.laddersettings.Settings.Items;

import dev.nandi0813.practice.manager.backend.GUIFile;
import dev.nandi0813.practice.manager.gui.setup.ladder.laddersettings.Settings.SettingItem;
import dev.nandi0813.practice.manager.gui.setup.ladder.laddersettings.Settings.SettingType;
import dev.nandi0813.practice.manager.gui.setup.ladder.laddersettings.Settings.SettingsGui;
import dev.nandi0813.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PullPlayersWithRodItem extends SettingItem {

    public PullPlayersWithRodItem(SettingsGui settingsGui, NormalLadder ladder) {
        super(settingsGui, SettingType.PULL_PLAYERS_WITH_ROD, ladder);
    }

    @Override
    public void updateItemStack() {
        if (ladder.isPullPlayersWithRod())
            guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.PULL-PLAYERS-WITH-ROD.ENABLED").setGlowing(true);
        else
            guiItem = GUIFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.PULL-PLAYERS-WITH-ROD.DISABLED");
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        ladder.setPullPlayersWithRod(!ladder.isPullPlayersWithRod());

        build(true);
    }

}
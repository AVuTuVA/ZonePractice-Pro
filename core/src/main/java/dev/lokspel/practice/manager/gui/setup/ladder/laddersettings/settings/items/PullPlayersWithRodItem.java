package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PullPlayersWithRodItem extends SettingItem {

  public PullPlayersWithRodItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.PULL_PLAYERS_WITH_ROD, ladder);
  }

  @Override
  public void updateItemStack() {
    if (ladder.isPullPlayersWithRod()) {
      guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.PULL-PLAYERS-WITH-ROD.ENABLED")
              .setGlowing(true);
    } else {
      guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.PULL-PLAYERS-WITH-ROD.DISABLED");
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setPullPlayersWithRod(!ladder.isPullPlayersWithRod());

    build(true);
  }
}

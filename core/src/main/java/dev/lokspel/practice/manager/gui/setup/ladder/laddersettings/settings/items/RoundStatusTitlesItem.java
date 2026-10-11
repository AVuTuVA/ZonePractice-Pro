package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RoundStatusTitlesItem extends SettingItem {

  public RoundStatusTitlesItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.ROUND_STATUS_TITLES, ladder);
  }

  @Override
  public void updateItemStack() {
    if (this.ladder.isRoundStatusTitles()) {
      this.guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.ROUND-STATUS-TITLES.ENABLED");
    } else {
      this.guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.ROUND-STATUS-TITLES.DISABLED");
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    this.ladder.setRoundStatusTitles(!ladder.isRoundStatusTitles());
    this.build(true);
  }
}

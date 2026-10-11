package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
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
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getSettings()
                  .getIcons()
                  .getRoundStatusTitles()
                  .getEnabled());
    } else {
      this.guiItem =
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getSettings()
                  .getIcons()
                  .getRoundStatusTitles()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    this.ladder.setRoundStatusTitles(!ladder.isRoundStatusTitles());
    this.build(true);
  }
}

package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CountdownTitlesItem extends SettingItem {

  public CountdownTitlesItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.COUNTDOWN_TITLES, ladder);
  }

  @Override
  public void updateItemStack() {
    if (this.ladder.isCountdownTitles()) {
      this.guiItem =
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getSettings()
                  .getIcons()
                  .getCountdownTitles()
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
                  .getCountdownTitles()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    this.ladder.setCountdownTitles(!ladder.isCountdownTitles());
    this.build(true);
  }
}

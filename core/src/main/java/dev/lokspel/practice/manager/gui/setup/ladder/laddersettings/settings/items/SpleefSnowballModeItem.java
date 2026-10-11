package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.type.Spleef;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SpleefSnowballModeItem extends SettingItem {

  private final Spleef spleef;

  public SpleefSnowballModeItem(SettingsGui settingsGui, Spleef spleef) {
    super(settingsGui, SettingType.SPLEEF_SNOWBALL_MODE, spleef);
    this.spleef = spleef;
  }

  @Override
  public void updateItemStack() {
    if (spleef.isSnowballMode()) {
      this.guiItem =
          BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getLadder()
                      .getSettings()
                      .getIcons()
                      .getSpleefSnowballMode()
                      .getEnabled())
              .setGlowing(true);
    } else {
      this.guiItem =
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getSettings()
                  .getIcons()
                  .getSpleefSnowballMode()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    spleef.setSnowballMode(!spleef.isSnowballMode());
    this.build(true);
  }
}

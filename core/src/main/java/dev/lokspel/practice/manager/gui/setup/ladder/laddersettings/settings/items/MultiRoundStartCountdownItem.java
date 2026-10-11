package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MultiRoundStartCountdownItem extends SettingItem {

  public MultiRoundStartCountdownItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.MULTI_ROUND_START_COUNTDOWN, ladder);
  }

  @Override
  public void updateItemStack() {
    if (ladder.isMultiRoundStartCountdown()) {
      guiItem =
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getSettings()
                  .getIcons()
                  .getMultiRoundStartCountdown()
                  .getEnabled());
    } else {
      guiItem =
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getSettings()
                  .getIcons()
                  .getMultiRoundStartCountdown()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setMultiRoundStartCountdown(!ladder.isMultiRoundStartCountdown());

    build(true);
  }
}

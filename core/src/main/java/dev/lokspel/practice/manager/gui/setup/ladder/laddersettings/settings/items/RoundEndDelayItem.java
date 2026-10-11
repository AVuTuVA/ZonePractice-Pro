package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RoundEndDelayItem extends SettingItem {

  public RoundEndDelayItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.ROUND_END_DELAY, ladder);
  }

  @Override
  public void updateItemStack() {
    this.guiItem =
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getLadder()
                    .getSettings()
                    .getIcons()
                    .getRoundEndDelay())
            .replace("%roundEndDelay%", String.valueOf(ladder.getRoundEndDelay()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    int delay = ladder.getRoundEndDelay();

    if (e.getClick().isLeftClick() && delay > 0) {
      ladder.setRoundEndDelay(delay - 1);
    } else if (e.getClick().isRightClick() && delay < 30) {
      ladder.setRoundEndDelay(delay + 1);
    }

    this.build(true);
  }
}

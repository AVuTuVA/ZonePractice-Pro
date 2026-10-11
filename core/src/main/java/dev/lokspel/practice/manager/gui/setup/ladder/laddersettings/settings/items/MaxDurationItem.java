package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MaxDurationItem extends SettingItem {

  public MaxDurationItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.MAX_DURATION, ladder);
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
                    .getMaxDuration())
            .replace("%maxDuration%", String.valueOf(ladder.getMaxDuration()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    int duration = ladder.getMaxDuration();

    if (e.getClick().isLeftClick() && duration > 60) {
      ladder.setMaxDuration(duration - 30);
    } else if (e.getClick().isRightClick() && duration < 6000) {
      ladder.setMaxDuration(duration + 30);
    }

    this.settingsGui.build();
  }
}

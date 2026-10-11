package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RoundsItem extends SettingItem {

  public RoundsItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.ROUNDS, ladder);
  }

  @Override
  public void updateItemStack() {
    guiItem =
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getLadder()
                    .getSettings()
                    .getIcons()
                    .getRounds())
            .replace("%rounds%", String.valueOf(ladder.getRounds()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    int round = ladder.getRounds();

    if (e.getClick().isLeftClick() && round > 1) {
      ladder.setRounds(round - 1);
    } else if (e.getClick().isRightClick() && round < 10) {
      ladder.setRounds(round + 1);
    }

    this.settingsGui.build();
  }
}

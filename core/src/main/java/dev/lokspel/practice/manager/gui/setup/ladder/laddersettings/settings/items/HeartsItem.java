package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HeartsItem extends SettingItem {

  public HeartsItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.HEARTS, ladder);
  }

  @Override
  public void updateItemStack() {
    guiItem =
        GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.HEARTS")
            .replace("%hearts%", String.valueOf(ladder.getHearts()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    int hearts = ladder.getHearts();

    if (e.getClick().isLeftClick() && hearts > 1) {
      ladder.setHearts(hearts - 1);
    } else if (e.getClick().isRightClick() && hearts < 20) {
      ladder.setHearts(hearts + 1);
    }

    settingsGui.build();
  }
}

package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StartCountdownItem extends SettingItem {

  public StartCountdownItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.START_COUNTDOWN, ladder);
  }

  @Override
  public void updateItemStack() {
    guiItem =
        GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.START-COUNTDOWN")
            .replace("%startCountdown%", String.valueOf(ladder.getStartCountdown()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    int startCountdown = ladder.getStartCountdown();

    if (e.getClick().isLeftClick() && startCountdown > 2) {
      ladder.setStartCountdown(startCountdown - 1);
    } else if (e.getClick().isRightClick() && startCountdown < 5) {
      ladder.setStartCountdown(startCountdown + 1);
    }

    build(true);
  }
}

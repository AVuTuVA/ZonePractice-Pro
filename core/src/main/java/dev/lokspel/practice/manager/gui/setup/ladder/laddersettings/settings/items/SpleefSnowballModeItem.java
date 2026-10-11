package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
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
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.SPLEEF-SNOWBALL-MODE.ENABLED")
              .setGlowing(true);
    } else {
      this.guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.SPLEEF-SNOWBALL-MODE.DISABLED");
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    spleef.setSnowballMode(!spleef.isSnowballMode());
    this.build(true);
  }
}

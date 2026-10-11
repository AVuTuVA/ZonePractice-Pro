package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RegenerationItem extends SettingItem {

  public RegenerationItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.REGENERATION, ladder);
  }

  @Override
  public void updateItemStack() {
    if (ladder.isRegen()) {
      guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.REGENERATION.ENABLED")
              .setGlowing(true);
    } else {
      guiItem = GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.REGENERATION.DISABLED");
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setRegen(!ladder.isRegen());

    build(true);
  }
}

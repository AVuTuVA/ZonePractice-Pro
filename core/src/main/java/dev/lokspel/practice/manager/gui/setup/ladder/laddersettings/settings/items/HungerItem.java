package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HungerItem extends SettingItem {

  public HungerItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.HUNGER, ladder);
  }

  @Override
  public void updateItemStack() {
    if (ladder.isHunger()) {
      guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.HUNGER.ENABLED").setGlowing(true);
    } else {
      guiItem = GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.HUNGER.DISABLED");
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setHunger(!ladder.isHunger());

    build(true);
  }
}

package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DropInventoryItem extends SettingItem {

  public DropInventoryItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.DROP_INVENTORY, ladder);
  }

  @Override
  public void updateItemStack() {
    if (ladder.isDropInventory()) {
      guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.DROP-INVENTORY-PARTY-GAMES.ENABLED")
              .setGlowing(true);
    } else {
      guiItem =
          GuiFile.getGuiItem(
              "GUIS.SETUP.LADDER.SETTINGS.ICONS.DROP-INVENTORY-PARTY-GAMES.DISABLED");
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setDropInventory(!ladder.isDropInventory());

    build(true);
  }
}

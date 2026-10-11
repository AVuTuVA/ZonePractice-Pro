package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TntAutoIgniteItem extends SettingItem {

  public TntAutoIgniteItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.TNT_AUTO_IGNITE, ladder);
  }

  @Override
  public void updateItemStack() {
    if (ladder.isTntAutoIgnite()) {
      guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.TNT-AUTO-IGNITE.ENABLED")
              .setGlowing(true);
    } else {
      guiItem = GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.TNT-AUTO-IGNITE.DISABLED");
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setTntAutoIgnite(!ladder.isTntAutoIgnite());

    build(true);
  }
}

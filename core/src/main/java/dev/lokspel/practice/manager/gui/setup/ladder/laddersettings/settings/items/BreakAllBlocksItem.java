package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BreakAllBlocksItem extends SettingItem {

  public BreakAllBlocksItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.BREAK_ALL_BLOCKS, ladder);
  }

  @Override
  public void updateItemStack() {
    if (this.ladder.isBreakAllBlocks()) {
      this.guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.BREAK-ALL-BLOCKS.ENABLED")
              .setGlowing(true);
    } else {
      this.guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.BREAK-ALL-BLOCKS.DISABLED");
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    this.ladder.setBreakAllBlocks(!this.ladder.isBreakAllBlocks());
    this.build(true);
  }
}

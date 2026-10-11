package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
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
          BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getLadder()
                      .getSettings()
                      .getIcons()
                      .getBreakAllBlocks()
                      .getEnabled())
              .setGlowing(true);
    } else {
      this.guiItem =
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getSettings()
                  .getIcons()
                  .getBreakAllBlocks()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    this.ladder.setBreakAllBlocks(!this.ladder.isBreakAllBlocks());
    this.build(true);
  }
}

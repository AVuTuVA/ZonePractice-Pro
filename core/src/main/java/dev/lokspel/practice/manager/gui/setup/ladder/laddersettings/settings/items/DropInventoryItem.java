package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
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
          BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getLadder()
                      .getSettings()
                      .getIcons()
                      .getDropInventoryPartyGames()
                      .getEnabled())
              .setGlowing(true);
    } else {
      guiItem =
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getSettings()
                  .getIcons()
                  .getDropInventoryPartyGames()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setDropInventory(!ladder.isDropInventory());

    build(true);
  }
}

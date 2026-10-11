package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
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
          BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getLadder()
                      .getSettings()
                      .getIcons()
                      .getTntAutoIgnite()
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
                  .getTntAutoIgnite()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setTntAutoIgnite(!ladder.isTntAutoIgnite());

    build(true);
  }
}

package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PullPlayersWithRodItem extends SettingItem {

  public PullPlayersWithRodItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.PULL_PLAYERS_WITH_ROD, ladder);
  }

  @Override
  public void updateItemStack() {
    if (ladder.isPullPlayersWithRod()) {
      guiItem =
          BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getLadder()
                      .getSettings()
                      .getIcons()
                      .getPullPlayersWithRod()
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
                  .getPullPlayersWithRod()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setPullPlayersWithRod(!ladder.isPullPlayersWithRod());

    build(true);
  }
}

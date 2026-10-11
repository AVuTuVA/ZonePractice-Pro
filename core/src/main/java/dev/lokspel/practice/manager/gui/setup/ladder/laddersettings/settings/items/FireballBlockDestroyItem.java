package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.ladder.type.FireballFight;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FireballBlockDestroyItem extends SettingItem {

  private final FireballFight fireballFight;

  public FireballBlockDestroyItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.FIREBALL_BLOCK_DESTROY, ladder);
    this.fireballFight = (FireballFight) ladder;
  }

  @Override
  public void updateItemStack() {
    if (fireballFight.isFireballBlockDestroy()) {
      this.guiItem =
          BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getLadder()
                      .getSettings()
                      .getIcons()
                      .getFireballBlockDestroy()
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
                  .getFireballBlockDestroy()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    fireballFight.setFireballBlockDestroy(!fireballFight.isFireballBlockDestroy());
    this.build(true);
  }
}

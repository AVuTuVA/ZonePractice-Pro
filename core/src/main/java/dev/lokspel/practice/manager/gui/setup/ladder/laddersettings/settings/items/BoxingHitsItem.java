package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.type.Boxing;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BoxingHitsItem extends SettingItem {

  private final Boxing boxing;

  public BoxingHitsItem(SettingsGui settingsGui, Boxing boxing) {
    super(settingsGui, SettingType.BOXING_HITS, boxing);
    this.boxing = boxing;
  }

  @Override
  public void updateItemStack() {
    this.guiItem =
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getLadder()
                    .getSettings()
                    .getIcons()
                    .getBoxing())
            .replace("%boxingWinHits%", String.valueOf(boxing.getBoxingWinHit()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ClickType click = e.getClick();

    int boxingWinHit = boxing.getBoxingWinHit();

    if (click.isLeftClick() && boxingWinHit > 40) {
      boxing.setBoxingWinHit(boxingWinHit - 20);
    } else if (click.isRightClick() && boxingWinHit < 600) {
      boxing.setBoxingWinHit(boxingWinHit + 20);
    }

    build(true);
  }
}

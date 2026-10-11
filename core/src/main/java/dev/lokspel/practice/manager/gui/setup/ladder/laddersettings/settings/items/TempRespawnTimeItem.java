package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.interfaces.RespawnableLadder;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TempRespawnTimeItem extends SettingItem {

  private final RespawnableLadder respawnableLadder;

  public TempRespawnTimeItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.RESPAWN_TIME, ladder);
    this.respawnableLadder = (RespawnableLadder) ladder;
  }

  @Override
  public void updateItemStack() {
    guiItem =
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getLadder()
                    .getSettings()
                    .getIcons()
                    .getRespawn())
            .replace("%respawnTime%", String.valueOf(respawnableLadder.getRespawnTime()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ClickType click = e.getClick();

    int respawnTime = respawnableLadder.getRespawnTime();

    if (click.isLeftClick() && respawnTime > 0) {
      respawnableLadder.setRespawnTime(respawnTime - 1);
    } else if (click.isRightClick() && respawnTime < 10) {
      respawnableLadder.setRespawnTime(respawnTime + 1);
    }

    build(true);
  }
}

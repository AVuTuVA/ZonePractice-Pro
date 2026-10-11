package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TntFuseTimeItem extends SettingItem {

  public TntFuseTimeItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.TNT_FUSE_TIME, ladder);
  }

  @Override
  public void updateItemStack() {
    this.guiItem =
        GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.TNT-FUSE-TIME")
            .replace("%tntFuseTime%", String.valueOf(ladder.getTntFuseTime()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    int tntFuseTime = ladder.getTntFuseTime();

    if (e.getClick().isLeftClick() && tntFuseTime > 1) {
      ladder.setTntFuseTime(tntFuseTime - 1);
    } else if (e.getClick().isRightClick() && tntFuseTime < 10) {
      ladder.setTntFuseTime(tntFuseTime + 1);
    }

    build(true);
  }
}

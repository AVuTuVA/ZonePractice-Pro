package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WindChargeCooldownItem extends SettingItem {

  public WindChargeCooldownItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.WIND_CHARGE_COOLDOWN, ladder);
  }

  @Override
  public void updateItemStack() {
    guiItem =
        GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.WIND-CHARGE-COOLDOWN")
            .replace("%windChargeCooldown%", String.valueOf(ladder.getWindChargeCooldown()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    double windChargeCooldown = ladder.getWindChargeCooldown();

    if (e.getClick().isLeftClick() && windChargeCooldown > 0) {
      ladder.setWindChargeCooldown(windChargeCooldown - 0.5);
    } else if (e.getClick().isRightClick() && windChargeCooldown < 30) {
      ladder.setWindChargeCooldown(windChargeCooldown + 0.5);
    }

    build(true);
  }
}

package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GoldenAppleItem extends SettingItem {

  public GoldenAppleItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.GOLDEN_APPLE_COOLDOWN, ladder);
  }

  @Override
  public void updateItemStack() {
    guiItem =
        GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.GOLDENAPPLE-COOLDOWN")
            .replace("%golden_apple_cooldown%", String.valueOf(ladder.getGoldenAppleCooldown()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    double goldenAppleCooldown = ladder.getGoldenAppleCooldown();

    if (e.getClick().isLeftClick() && goldenAppleCooldown > 0) {
      ladder.setGoldenAppleCooldown(goldenAppleCooldown - 0.5);
    } else if (e.getClick().isRightClick() && goldenAppleCooldown < 30) {
      ladder.setGoldenAppleCooldown(goldenAppleCooldown + 0.5);
    }

    build(true);
  }
}

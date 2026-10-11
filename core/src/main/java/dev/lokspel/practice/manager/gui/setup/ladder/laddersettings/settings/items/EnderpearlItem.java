package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EnderpearlItem extends SettingItem {

  public EnderpearlItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.ENDER_PEARL_COOLDOWN, ladder);
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
                    .getEnderpearlCooldown())
            .replace("%epCooldown%", String.valueOf(ladder.getEnderPearlCooldown()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    double epCooldown = ladder.getEnderPearlCooldown();

    if (e.getClick().isLeftClick() && epCooldown > 0) {
      ladder.setEnderPearlCooldown(epCooldown - 0.5);
    } else if (e.getClick().isRightClick() && epCooldown < 60) {
      ladder.setEnderPearlCooldown(epCooldown + 0.5);
    }

    build(true);
  }
}

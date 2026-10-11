package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HitdelayItem extends SettingItem {

  public HitdelayItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.HIT_DELAY, ladder);
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
                    .getAttackcooldown())
            .replace("%attackcooldown%", String.format("%.1f", ladder.getAttackCooldownModifier()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    double hitDelay = ladder.getAttackCooldownModifier();

    if (e.getClick().isLeftClick() && hitDelay > 0) {
      ladder.setAttackCooldownModifier(
          Math.clamp(Math.round((hitDelay - 0.1) * 10) / 10.0, 0, 3.0));
    } else if (e.getClick().isRightClick() && hitDelay < 3.0) {
      ladder.setAttackCooldownModifier(
          Math.clamp(Math.round((hitDelay + 0.1) * 10) / 10.0, 0, 3.0));
    }

    build(true);
  }
}

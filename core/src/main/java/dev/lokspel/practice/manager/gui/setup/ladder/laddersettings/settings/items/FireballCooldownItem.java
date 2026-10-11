package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.ladder.type.FireballFight;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FireballCooldownItem extends SettingItem {

  private final FireballFight fireballFight;

  public FireballCooldownItem(SettingsGui settingsGui, NormalLadder fireballFight) {
    super(settingsGui, SettingType.FIREBALL_COOLDOWN, fireballFight);
    this.fireballFight = (FireballFight) fireballFight;
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
                    .getFireballCooldown())
            .replace("%cooldown%", String.valueOf(fireballFight.getFireballCooldown()));
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ClickType click = e.getClick();

    double fireballCooldown = fireballFight.getFireballCooldown();

    if (click.isLeftClick() && fireballCooldown > 0.5) {
      fireballFight.setFireballCooldown(fireballCooldown - 0.5);
    } else if (click.isRightClick() && fireballCooldown < 15) {
      fireballFight.setFireballCooldown(fireballCooldown + 0.5);
    }

    build(true);
  }
}

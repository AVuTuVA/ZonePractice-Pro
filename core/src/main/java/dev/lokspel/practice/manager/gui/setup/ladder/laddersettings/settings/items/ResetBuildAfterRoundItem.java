package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ResetBuildAfterRoundItem extends SettingItem {

  public ResetBuildAfterRoundItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.RESET_BUILD_AFTER_ROUND, ladder);
  }

  @Override
  public void updateItemStack() {
    if (this.ladder.isResetBuildAfterRound()) {
      this.guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.RESET-BUILD-AFTER-ROUND.ENABLED")
              .setGlowing(true);
    } else {
      this.guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.RESET-BUILD-AFTER-ROUND.DISABLED");
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    this.ladder.setResetBuildAfterRound(!this.ladder.isResetBuildAfterRound());
    this.build(true);
  }
}

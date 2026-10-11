package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.ladder.LadderSetupManager;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditableItem extends SettingItem {

  public EditableItem(SettingsGui settingsGui, NormalLadder ladder) {
    super(settingsGui, SettingType.EDITABLE, ladder);
  }

  @Override
  public void updateItemStack() {
    if (ladder.isEditable()) {
      guiItem =
          BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getLadder()
                      .getSettings()
                      .getIcons()
                      .getEditable()
                      .getEnabled())
              .setGlowing(true);
    } else {
      guiItem =
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getSettings()
                  .getIcons()
                  .getEditable()
                  .getDisabled());
    }
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    ladder.setEditable(!ladder.isEditable());

    LadderSetupManager.getInstance()
        .getLadderSetupGuis()
        .get(ladder)
        .get(GuiType.Ladder_Inventory)
        .update();

    build(true);
  }
}

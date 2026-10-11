package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings;

import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class SettingItem {

  protected final SettingsGui settingsGui;

  protected final SettingType settingType;
  @Getter @Setter protected int slot = -1;

  protected GuiItem guiItem;
  protected final NormalLadder ladder;

  protected SettingItem(
      final SettingsGui settingsGui, SettingType settingType, NormalLadder ladder) {
    this.settingsGui = settingsGui;
    this.settingType = settingType;
    this.ladder = ladder;
  }

  public abstract void updateItemStack();

  public void build(boolean update) {
    updateItemStack();

    if (slot == -1) {
      return;
    }

    if (guiItem != null) {
      settingsGui.getGui().get(1).setItem(slot, guiItem.get());
    }

    if (update) {
      settingsGui.update();
    }
  }

  public abstract void clickEvent(InventoryClickEvent e);
}

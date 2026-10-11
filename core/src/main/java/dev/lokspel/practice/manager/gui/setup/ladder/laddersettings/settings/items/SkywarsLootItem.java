package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.items;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.ladder.LadderSetupManager;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingItem;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.type.SkyWars;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SkywarsLootItem extends SettingItem {

  private final SkyWars skyWars;

  public SkywarsLootItem(SettingsGui settingsGui, SkyWars skyWars) {
    super(settingsGui, SettingType.SKYWARS_LOOT, skyWars);
    this.skyWars = skyWars;
  }

  @Override
  public void updateItemStack() {
    this.guiItem = GuiFile.getGuiItem("GUIS.SETUP.LADDER.SETTINGS.ICONS.SKYWARS");
  }

  @Override
  public void clickEvent(InventoryClickEvent e) {
    LadderSetupManager.getInstance()
        .getLadderSetupGuis()
        .get(skyWars)
        .get(GuiType.Ladder_SkyWarsLoot)
        .open(((Player) e.getWhoClicked()));
  }
}

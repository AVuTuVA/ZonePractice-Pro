package dev.lokspel.practice.manager.gui.setup;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.server.ServerHubGui;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SetupHubGui extends Gui {

  public SetupHubGui() {
    super(GuiType.Setup_Hub);
    this.gui.put(1, InventoryUtil.createInventory(GuiFile.getString("GUIS.SETUP.HUB.TITLE"), 4));

    build();
  }

  @Override
  public void build() {
    Inventory inventory = gui.get(1);

    inventory.setItem(11, GuiFile.getGuiItem("GUIS.SETUP.HUB.ICONS.ARENA-MANAGER").get());
    inventory.setItem(13, GuiFile.getGuiItem("GUIS.SETUP.HUB.ICONS.LADDER-MANAGER").get());
    inventory.setItem(15, GuiFile.getGuiItem("GUIS.SETUP.HUB.ICONS.HOLOGRAM-MANAGER").get());
    inventory.setItem(21, GuiFile.getGuiItem("GUIS.SETUP.HUB.ICONS.EVENT-MANAGER").get());
    inventory.setItem(23, GuiFile.getGuiItem("GUIS.SETUP.HUB.ICONS.SERVER-MANAGER").get());

    for (int i = 0; i < inventory.getSize(); i++) {
      if (inventory.getItem(i) == null) {
        inventory.setItem(i, GuiFile.getGuiItem("GUIS.SETUP.HUB.ICONS.GENERAL-FILLER-ITEM").get());
      }
    }
  }

  @Override
  public void update() {
    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    Inventory inventory = e.getView().getTopInventory();
    int slot = e.getRawSlot();

    e.setCancelled(true);

    if (inventory.getSize() <= slot) {
      return;
    }

    switch (slot) {
      case 11:
        GuiManager.getInstance().searchGui(GuiType.Arena_Summary).open(player);
        break;
      case 13:
        GuiManager.getInstance().searchGui(GuiType.Ladder_Summary).open(player);
        break;
      case 15:
        Gui hologramSummaryGui = GuiManager.getInstance().searchGui(GuiType.Hologram_Summary);
        if (hologramSummaryGui != null) {
          hologramSummaryGui.open(player);
        } else {
          Common.sendMmMessage(player, LanguageManager.getString("COMMAND.SETUP.GUI-LOADING"));
        }
        break;
      case 21:
        GuiManager.getInstance().searchGui(GuiType.Event_Summary).open(player);
        break;
      case 23:
        new ServerHubGui().open(player);
        break;

      default:
        break;
    }
  }
}

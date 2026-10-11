package dev.lokspel.practice.manager.gui.guis.customladder;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EditorMenuGui extends Gui {

  private static final ItemStack FILLER_ITEM =
      GuiFile.getGuiItem("GUIS.KIT-EDITOR.EDITOR-MENU.ICONS.FILLER-ITEM").get();

  public EditorMenuGui() {
    super(GuiType.CustomLadder_EditorMenu);

    this.gui.put(
        1,
        InventoryUtil.createInventory(GuiFile.getString("GUIS.KIT-EDITOR.EDITOR-MENU.TITLE"), 3));
    this.build();
  }

  @Override
  public void build() {
    this.update();
  }

  @Override
  public void update() {
    Inventory inventory = this.gui.get(1);

    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, FILLER_ITEM);
    }

    inventory.setItem(
        11, GuiFile.getGuiItem("GUIS.KIT-EDITOR.EDITOR-MENU.ICONS.PREMADE-LADDER").get());
    inventory.setItem(
        15, GuiFile.getGuiItem("GUIS.KIT-EDITOR.EDITOR-MENU.ICONS.PLAYER-LADDER").get());

    this.updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    Profile profile = ProfileManager.getInstance().getProfile(player);
    int slot = e.getRawSlot();

    e.setCancelled(true);
    switch (slot) {
      case 11:
        GuiManager.getInstance().searchGui(GuiType.CustomLadder_Selector).open(player);
        break;
      case 15:
        if (profile.getGroup() == null || profile.getGroup().getCustomKitLimit() == 0) {
          Common.sendMmMessage(
              player, LanguageManager.getString("CUSTOM-PLAYER-KIT.NO-CUSTOM-KIT"));
          return;
        }

        profile.getPlayerCustomKitSelector().update();
        profile.getPlayerCustomKitSelector().open(player);
        break;

      default:
        break;
    }
  }
}

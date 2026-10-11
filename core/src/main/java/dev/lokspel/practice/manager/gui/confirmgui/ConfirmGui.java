package dev.lokspel.practice.manager.gui.confirmgui;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.util.InventoryUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class ConfirmGui {

  private static final ItemStack CANCEL_ITEM =
      GuiFile.getGuiItem("GUIS.VALIDATION-GUI.ICONS.CANCEL").get();
  private static final ItemStack APPROVE_ITEM =
      GuiFile.getGuiItem("GUIS.VALIDATION-GUI.ICONS.APPROVE").get();

  private final ConfirmGuiType type;
  private final String description;
  private final Gui backToConfirm;
  private final Gui backToCancel;

  private Inventory inventory;

  public ConfirmGui(ConfirmGuiType type, Gui backToConfirm, Gui backToCancel) {
    this.type = type;
    this.description = type.getDescription();
    this.backToConfirm = backToConfirm;
    this.backToCancel = backToCancel;

    this.buildInventory();
  }

  private void buildInventory() {
    inventory = InventoryUtil.createInventory(GuiFile.getString("GUIS.VALIDATION-GUI.TITLE"), 1);

    for (int i = 0; i < 9; i++) {
      inventory.setItem(i, CANCEL_ITEM);
    }
    inventory.setItem(4, APPROVE_ITEM);
  }

  public void openInventory(Player player) {
    player.openInventory(inventory);
  }
}

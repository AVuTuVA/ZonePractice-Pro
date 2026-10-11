package dev.lokspel.practice.manager.gui.confirmgui;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.util.InventoryUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class ConfirmGui {

  private static ItemStack cancelItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis().getGuis().getValidationGui().getIcons().getCancel())
        .get();
  }

  private static ItemStack approveItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis().getGuis().getValidationGui().getIcons().getApprove())
        .get();
  }

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
    inventory =
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getValidationGui().getTitle(), 1);

    for (int i = 0; i < 9; i++) {
      inventory.setItem(i, cancelItem());
    }
    inventory.setItem(4, approveItem());
  }

  public void openInventory(Player player) {
    player.openInventory(inventory);
  }
}

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

  private static final ItemStack CANCEL_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis().getGuis().getValidationGui().getIcons().getCancel())
          .get();
  private static final ItemStack APPROVE_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis().getGuis().getValidationGui().getIcons().getApprove())
          .get();

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
      inventory.setItem(i, CANCEL_ITEM);
    }
    inventory.setItem(4, APPROVE_ITEM);
  }

  public void openInventory(Player player) {
    player.openInventory(inventory);
  }
}

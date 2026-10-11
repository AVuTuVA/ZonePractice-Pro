package dev.lokspel.practice.manager.playerkit;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.ladder.abstraction.playercustom.CustomLadder;
import dev.lokspel.practice.manager.playerkit.guis.ShulkerBoxEditorGui;
import dev.lokspel.practice.manager.playerkit.items.KitItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class PlayerKitEditing {

  private final CustomLadder customLadder;
  @Setter private KitItem kitItem;

  /** Non-null when the player is editing a slot INSIDE a shulker box. */
  @Setter private ShulkerBoxEditorGui shulkerEditor = null;

  /** The shulker content slot index being modified (0-26), or -1 if not editing a shulker slot. */
  @Setter private int shulkerSlot = -1;

  public PlayerKitEditing(CustomLadder customLadder) {
    this.customLadder = customLadder;
  }

  /** True while editing a specific slot inside a shulker box. */
  public boolean isEditingShulker() {
    return shulkerEditor != null && shulkerSlot >= 0;
  }

  /** Returns to the editor, applying the selected item to the active shulker slot if needed. */
  public void returnToEditor(Player player, ItemStack selectedItem) {
    if (isEditingShulker()) {
      // Keep the editor and slot before clearing the context.
      ShulkerBoxEditorGui editor = this.shulkerEditor;
      int slot = this.shulkerSlot;
      clearShulkerContext();
      editor.onItemSelected(slot, selectedItem != null ? selectedItem.clone() : null);
      editor.update(true);
      editor.open(player);
    } else {
      Gui mainGui = this.customLadder.getMainGui();
      mainGui.update();
      mainGui.open(player);
    }
  }

  /** Reopens the main editor after a menu closes unless another GUI has opened. */
  public void scheduleReturnToMainEditor(Player player) {
    Gui mainGui = this.customLadder.getMainGui();
    Bukkit.getScheduler()
        .runTaskLater(
            AstralPractice.getInstance(),
            () -> {
              if (GuiManager.getInstance().getOpenGui().containsKey(player)) {
                return;
              }

              mainGui.open(player);
            },
            5L);
  }

  /** Clear shulker editing context. */
  public void clearShulkerContext() {
    this.shulkerEditor = null;
    this.shulkerSlot = -1;
  }
}

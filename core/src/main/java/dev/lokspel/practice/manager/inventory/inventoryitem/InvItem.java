package dev.lokspel.practice.manager.inventory.inventoryitem;

import dev.lokspel.practice.config.ItemConfig;
import dev.lokspel.practice.config.inventory.InventoryLayout;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.GuiItem;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public abstract class InvItem {

  private final ItemStack item;
  private final int slot;

  public InvItem(final ItemStack item, final int slot) {
    this.item = item;
    this.slot = slot;
  }

  /**
   * Creates the item an inventory layout entry describes. A missing entry gives an empty item in
   * slot 0.
   *
   * @param config the entry, or {@code null} if the layout lacks it
   */
  protected InvItem(final InventoryLayout.InventoryItem config) {
    this(
        getItemStack(config != null ? config.getItem() : null),
        config != null && config.getSlot() != null ? config.getSlot() : 0);
  }

  public abstract void handleClickEvent(Player player);

  private static ItemStack getItemStack(ItemConfig item) {
    return (item != null ? BackendUtil.getGuiItem(item) : new GuiItem()).get();
  }
}

package dev.lokspel.practice.manager.playerkit.guis;

import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.playerkit.PlayerKitEditing;
import dev.lokspel.practice.manager.playerkit.PlayerKitManager;
import dev.lokspel.practice.manager.playerkit.PlayerKitUtil;
import dev.lokspel.practice.manager.playerkit.StaticItems;
import dev.lokspel.practice.manager.playerkit.items.KitItem;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CategoryGui extends Gui {

  private static int itemsPerPage() {
    return (StaticItems.categoryGuiPageSize() - 1) * 9;
  }

  private static int leftNavSlot() {
    return (StaticItems.categoryGuiPageSize() - 1) * 9;
  }

  private static int rightNavSlot() {
    return (StaticItems.categoryGuiPageSize() * 9) - 1;
  }

  private final String title;
  private final List<ItemStack> items = new ArrayList<>();

  public CategoryGui(GuiType type, String title, List<String> items) {
    super(type);
    this.title = title;

    for (String item : items) {
      ItemStack itemStack = PlayerKitUtil.getItem(item);
      this.items.add(Objects.requireNonNullElseGet(itemStack, () -> new ItemStack(Material.AIR)));
    }

    this.build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    int page = 1;
    do {
      Inventory inventory = InventoryUtil.createInventory(title, StaticItems.categoryGuiPageSize());
      this.gui.put(page, inventory);
      List<ItemStack> pageItems = PageUtil.getPageItems(items, page, itemsPerPage());

      if (page == 1) {
        inventory.setItem(leftNavSlot(), StaticItems.categoryGuiPageBackIcon().get());
      } else {
        inventory.setItem(leftNavSlot(), StaticItems.categoryGuiPagePreviousIcon().get());
      }

      if (PageUtil.getMaxPage(items, itemsPerPage()) > 1
          && PageUtil.isPageValid(items, page + 1, itemsPerPage())) {
        inventory.setItem(rightNavSlot(), StaticItems.categoryGuiPageNextIcon().get());
      }

      for (int i = 0; i < pageItems.size(); i++) {
        inventory.setItem(i, pageItems.get(i));
      }

      page++;
    } while (PageUtil.isPageValid(items, page, itemsPerPage()));

    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    Player player = (Player) e.getWhoClicked();
    int page = inGuiPlayers.get(player);

    if (e.getClickedInventory() == null) {
      return;
    }
    int slot = e.getRawSlot();
    if (e.getView().getTopInventory().getSize() <= slot) {
      return;
    }

    if (slot == leftNavSlot() && page == 1) {
      GuiManager.getInstance().searchGui(GuiType.PlayerCustom_Category).open(player);
    } else if (slot == rightNavSlot() && gui.containsKey(page + 1)) {
      open(player, page + 1);
    } else if (slot == leftNavSlot() && gui.containsKey(page - 1)) {
      open(player, page - 1);
    } else {
      ItemStack item = gui.get(page).getItem(slot);
      if (item == null || item.getType() == Material.AIR) {
        return;
      }

      PlayerKitEditing playerKitEditing = PlayerKitManager.getInstance().getEditing().get(player);
      KitItem kitItem = playerKitEditing.getKitItem();
      kitItem.setItemStack(item.clone());

      playerKitEditing.returnToEditor(player, item);
    }
  }

  @Override
  public void handleCloseEvent(InventoryCloseEvent e) {
    Player player = (Player) e.getPlayer();
    PlayerKitEditing playerKitEditing = PlayerKitManager.getInstance().getEditing().get(player);
    playerKitEditing.scheduleReturnToMainEditor(player);
  }
}

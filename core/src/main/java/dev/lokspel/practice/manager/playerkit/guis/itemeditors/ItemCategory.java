package dev.lokspel.practice.manager.playerkit.guis.itemeditors;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.playerkit.DynamicCategory;
import dev.lokspel.practice.manager.playerkit.PlayerKitEditing;
import dev.lokspel.practice.manager.playerkit.PlayerKitManager;
import dev.lokspel.practice.manager.playerkit.StaticItems;
import dev.lokspel.practice.manager.playerkit.guis.ShulkerBoxEditorGui;
import dev.lokspel.practice.manager.playerkit.items.EditorIcon;
import dev.lokspel.practice.manager.playerkit.items.KitItem;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

/**
 * The item category picker. Fixed categories (hardcoded GUIType): Armor, Weapons/Tools, Bows,
 * Potions, Shulker Boxes. Dynamic categories: loaded from GUI.ITEMS.CATEGORY-GUI.DYNAMIC-CATEGORIES
 * in playerkit.yml. Adding a new category requires ONLY a config change — no code change.
 */
public class ItemCategory extends ItemEditor {

  public ItemCategory() {
    super(GuiType.PlayerCustom_Category, buildIconList());
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            StaticItems.categoryGuiTitle(), StaticItems.categoryGuiSize()));
    this.build();
  }

  /** Builds the icon list: fixed icons + all dynamic category icons (in config order). */
  private static List<EditorIcon> buildIconList() {
    List<EditorIcon> icons =
        new ArrayList<>(
            Arrays.asList(
                StaticItems.categoryGuiBackIcon(),
                StaticItems.categoryGuiNoneIcon(),
                // Fixed-type categories
                StaticItems.categoryGuiArmorIcon(),
                StaticItems.categoryGuiWeaponToolsIcon(),
                StaticItems.categoryGuiBowsIcon(),
                StaticItems.categoryGuiPotionsIcon(),
                StaticItems.categoryGuiShulkerIcon()));

    // Dynamic categories (loaded from config)
    for (DynamicCategory cat : PlayerKitManager.getInstance().getDynamicCategories()) {
      icons.add(cat.getIcon());
    }
    return icons;
  }

  private boolean isShulkerCategoryHidden(Player player) {
    PlayerKitEditing editing = PlayerKitManager.getInstance().getEditing().get(player);
    return editing != null && editing.isEditingShulker();
  }

  private Inventory buildInventoryFor(Player player) {
    Inventory source = gui.get(1);
    Inventory contextual =
        InventoryUtil.createInventory(
            StaticItems.categoryGuiTitle(), StaticItems.categoryGuiSize());

    if (source != null) {
      contextual.setContents(source.getContents().clone());
    }

    if (isShulkerCategoryHidden(player)) {
      contextual.setItem(StaticItems.categoryGuiShulkerIcon().getSlot(), null);
    }

    return contextual;
  }

  @Override
  public void open(Player player, int page) {
    if (page != 1) {
      super.open(player, page);
      return;
    }

    player.openInventory(buildInventoryFor(player));

    Bukkit.getScheduler()
        .runTaskLater(
            AstralPractice.getInstance(),
            () -> {
              inGuiPlayers.put(player, 1);
              GuiManager.getInstance().getOpenGui().put(player, this);
            },
            2L);
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    Player player = (Player) e.getWhoClicked();
    PlayerKitEditing editing = PlayerKitManager.getInstance().getEditing().get(player);
    if (editing == null) {
      return;
    }

    int slot = e.getRawSlot();
    if (slot == StaticItems.categoryGuiShulkerIcon().getSlot() && isShulkerCategoryHidden(player)) {
      return;
    }

    EditorIcon icon = getIcon(slot);
    if (icon == null) {
      return;
    }

    KitItem kitItem = editing.getKitItem();
    Gui mainGui = editing.getCustomLadder().getMainGui();

    // Back
    if (icon.equals(StaticItems.categoryGuiBackIcon())) {
      if (editing.isEditingShulker()) {
        ShulkerBoxEditorGui editor = editing.getShulkerEditor();
        editor.update(true);
        editor.open(player);
      } else {
        mainGui.open(player);
      }
      return;
    }

    // None
    if (icon.equals(StaticItems.categoryGuiNoneIcon())) {
      kitItem.reset();
      editing.returnToEditor(player, null);
      return;
    }

    // Fixed categories
    if (icon.equals(StaticItems.categoryGuiArmorIcon())) {
      GuiManager.getInstance().searchGui(GuiType.PlayerCustom_Armor).open(player);
      return;
    }
    if (icon.equals(StaticItems.categoryGuiWeaponToolsIcon())) {
      GuiManager.getInstance().searchGui(GuiType.PlayerCustom_Weapons_Tools).open(player);
      return;
    }
    if (icon.equals(StaticItems.categoryGuiBowsIcon())) {
      GuiManager.getInstance().searchGui(GuiType.PlayerCustom_Bows).open(player);
      return;
    }
    if (icon.equals(StaticItems.categoryGuiPotionsIcon())) {
      GuiManager.getInstance().searchGui(GuiType.PlayerCustom_Potions).open(player);
      return;
    }
    if (icon.equals(StaticItems.categoryGuiShulkerIcon())) {
      GuiManager.getInstance().searchGui(GuiType.PlayerCustom_Shulker).open(player);
      return;
    }

    // Dynamic categories
    for (DynamicCategory cat : PlayerKitManager.getInstance().getDynamicCategories()) {
      if (icon.equals(cat.getIcon())) {
        cat.getGui().open(player);
        return;
      }
    }
  }

  @Override
  public void handleCloseEvent(InventoryCloseEvent e) {
    Player player = (Player) e.getPlayer();
    PlayerKitEditing editing = PlayerKitManager.getInstance().getEditing().get(player);
    if (editing == null) {
      return;
    }
    editing.scheduleReturnToMainEditor(player);
  }
}

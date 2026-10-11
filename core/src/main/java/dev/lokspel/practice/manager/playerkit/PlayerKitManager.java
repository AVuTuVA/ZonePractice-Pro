package dev.lokspel.practice.manager.playerkit;

import dev.lokspel.practice.config.ItemConfig;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.playerkit.Items;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.ladder.abstraction.playercustom.CustomLadder;
import dev.lokspel.practice.manager.playerkit.guis.CategoryGui;
import dev.lokspel.practice.manager.playerkit.guis.PotionsGui;
import dev.lokspel.practice.manager.playerkit.guis.ShulkerCategoryGui;
import dev.lokspel.practice.manager.playerkit.guis.itemeditors.ArmorGui;
import dev.lokspel.practice.manager.playerkit.guis.itemeditors.ItemCategory;
import dev.lokspel.practice.manager.playerkit.items.EditorIcon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

@Getter
public class PlayerKitManager implements Listener {

  private static PlayerKitManager instance;

  public static PlayerKitManager getInstance() {
    if (instance == null) {
      instance = new PlayerKitManager();
    }
    return instance;
  }

  private PlayerKitManager() {}

  /** All config-driven item categories, in declaration order. */
  private final List<DynamicCategory> dynamicCategories = new ArrayList<>();

  private final Map<Player, PlayerKitEditing> editing = new HashMap<>();

  private final Map<String, CustomLadder> copy = new HashMap<>();
  private final Map<Player, CustomLadder> copying = new HashMap<>();

  public void load() {
    GuiManager guiManager = GuiManager.getInstance();

    // Load dynamic categories FIRST so ItemCategory can include them in its icon list
    loadDynamicCategories();

    guiManager.addGui(new ItemCategory());
    guiManager.addGui(
        new ArmorGui(GuiType.PlayerCustom_Helmet, StaticItems.MAIN_ARMOR_HELMET_ICONS));
    guiManager.addGui(
        new ArmorGui(GuiType.PlayerCustom_Chestplate, StaticItems.MAIN_ARMOR_CHEST_ICONS));
    guiManager.addGui(
        new ArmorGui(GuiType.PlayerCustom_Leggings, StaticItems.MAIN_ARMOR_LEG_ICONS));
    guiManager.addGui(new ArmorGui(GuiType.PlayerCustom_Boots, StaticItems.MAIN_ARMOR_BOOT_ICONS));
    guiManager.addGui(
        new CategoryGui(
            GuiType.PlayerCustom_Armor,
            StaticItems.CATEGORY_ARMOR_TITLE,
            StaticItems.CATEGORY_ARMOR_ITEMS));
    guiManager.addGui(
        new CategoryGui(
            GuiType.PlayerCustom_Weapons_Tools,
            StaticItems.CATEGORY_WEAPONS_TITLE,
            StaticItems.CATEGORY_WEAPONS_ITEMS));
    guiManager.addGui(
        new CategoryGui(
            GuiType.PlayerCustom_Bows,
            StaticItems.CATEGORY_BOWS_TITLE,
            StaticItems.CATEGORY_BOWS_ITEMS));
    guiManager.addGui(new ShulkerCategoryGui());
    guiManager.addGui(new PotionsGui());
  }

  /**
   * Scans GUI.ITEMS.CATEGORY-GUI.ICONS in playerkit.yml. Any key that is NOT one of the fixed
   * built-in categories is treated as a dynamic (custom) category. Its item list must exist under
   * GUI.ITEMS.ITEMS-GUI.CATEGORIES.<same-key>.TITLE / .ITEMS.
   *
   * <p>To add a new category: just add its icon entry under CATEGORY-GUI.ICONS and its item list
   * under ITEMS-GUI.CATEGORIES — no code change needed.
   */
  private static final java.util.Set<String> FIXED_ICON_KEYS =
      new java.util.HashSet<>(
          java.util.Arrays.asList(
              "BACK-TO",
              "NONE",
              "ARMOR",
              "WEAPONS-TOOLS",
              "BOWS-ARROWS",
              "POTIONS",
              "SHULKER-BOXES"));

  private void loadDynamicCategories() {
    dynamicCategories.clear();

    Items items = PracticeConfig.playerKit().getGui().getItems();
    Map<String, Items.CategoryGui.CategoryIcon> icons = items.getCategoryGui().getIcons();
    if (icons == null) {
      return;
    }

    Map<String, Items.ItemsGui.ItemCategory> categories = items.getItemsGui().getCategories();
    for (Map.Entry<String, Items.CategoryGui.CategoryIcon> entry : icons.entrySet()) {
      String id = entry.getKey();
      if (FIXED_ICON_KEYS.contains(id)) {
        continue;
      } // skip built-ins

      Items.ItemsGui.ItemCategory category = categories != null ? categories.get(id) : null;

      EditorIcon icon = getEditorItem(entry.getValue());
      String title =
          category != null && category.getTitle() != null
              ? category.getTitle()
              : "<dark_gray>" + id;
      List<String> itemList =
          category != null && category.getItems() != null ? category.getItems() : List.of();

      CategoryGui gui = new CategoryGui(GuiType.PlayerCustom_DynamicCategory, title, itemList);
      dynamicCategories.add(new DynamicCategory(id, icon, gui));
    }
  }

  /**
   * Creates the editor icon an item section describes, without a slot.
   *
   * @param item the section, or {@code null} for an empty icon
   */
  public EditorIcon getEditorItem(ItemConfig item) {
    return getEditorItem(item, null);
  }

  /** Creates the editor icon of a category picker entry, in the entry's slot. */
  public EditorIcon getEditorItem(Items.CategoryGui.CategoryIcon icon) {
    return getEditorItem(icon, icon != null ? icon.getSlot() : null);
  }

  /**
   * Creates the editor icon an item section describes. Only the name, material and lore of the
   * section are used.
   *
   * @param item the section, or {@code null} for an empty icon
   * @param slot the icon's slot, or {@code null} to leave it unset ({@code -1})
   */
  public EditorIcon getEditorItem(ItemConfig item, Integer slot) {
    EditorIcon guiItem = new EditorIcon();
    if (item == null) {
      return guiItem;
    }

    if (item.getName() != null) {
      guiItem.setName(item.getName());
    }

    if (item.getMaterial() != null) {
      guiItem.setMaterial(Material.valueOf(item.getMaterial()));
    }

    if (item.getLore() != null) {
      guiItem.setLore(item.getLore());
    }

    if (slot != null) {
      guiItem.setSlot(slot);
    }

    return guiItem;
  }

  public String getCopyCode() {
    String uuid = UUID.randomUUID().toString();
    String code;
    do {
      code = uuid.replace("[^a-zA-Z0-9]", "").substring(0, 7);
    } while (this.copy.containsKey(code));
    return code;
  }
}

package dev.lokspel.practice.manager.playerkit;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.playerkit.Gui;
import dev.lokspel.practice.config.playerkit.Items;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.ladder.util.LadderUtil;
import dev.lokspel.practice.manager.playerkit.items.EditorIcon;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum StaticItems {
  ;

  // Main GUI
  public static final String MAIN_GUI_TITLE =
      PracticeConfig.playerKit().getGui().getMain().getTitle();
  public static final ItemStack MAIN_GUI_BACK =
      BackendUtil.getGuiItem(PracticeConfig.playerKit().getGui().getMain().getIcons().getBackTo())
          .get();
  public static final ItemStack MAIN_GUI_SETTINGS_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit().getGui().getMain().getIcons().getCustomSettings())
          .get();
  public static final ItemStack MAIN_GUI_GUIDE_ICON =
      BackendUtil.getGuiItem(PracticeConfig.playerKit().getGui().getMain().getIcons().getGuide())
          .get();
  public static final ItemStack MAIN_GUI_RESET_KIT_ICON =
      BackendUtil.getGuiItem(PracticeConfig.playerKit().getGui().getMain().getIcons().getResetKit())
          .get();
  public static final ItemStack MAIN_GUI_CHANGE_NAME =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit().getGui().getMain().getIcons().getChangeName())
          .get();
  public static final ItemStack MAIN_GUI_SHARE_KIT =
      BackendUtil.getGuiItem(PracticeConfig.playerKit().getGui().getMain().getIcons().getShareKit())
          .get();

  // Main GUI placeholders
  public static final ItemStack MAIN_GUI_HEAD_PLACEHOLDER_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getMain()
                  .getIcons()
                  .getKitSlotPlaceholders()
                  .getHead())
          .get();
  public static final ItemStack MAIN_GUI_CHEST_PLACEHOLDER_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getMain()
                  .getIcons()
                  .getKitSlotPlaceholders()
                  .getChest())
          .get();
  public static final ItemStack MAIN_GUI_LEGS_PLACEHOLDER_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getMain()
                  .getIcons()
                  .getKitSlotPlaceholders()
                  .getLegs())
          .get();
  public static final ItemStack MAIN_GUI_BOOTS_PLACEHOLDER_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getMain()
                  .getIcons()
                  .getKitSlotPlaceholders()
                  .getBoots())
          .get();
  public static final ItemStack MAIN_GUI_OFF_HAND_PLACEHOLDER_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getMain()
                  .getIcons()
                  .getKitSlotPlaceholders()
                  .getOffhand())
          .get();
  public static final ItemStack MAIN_GUI_HOTBAR_PLACEHOLDER_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getMain()
                  .getIcons()
                  .getKitSlotPlaceholders()
                  .getHotbar())
          .get();
  public static final ItemStack MAIN_GUI_INVENTORY_PLACEHOLDER_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getMain()
                  .getIcons()
                  .getKitSlotPlaceholders()
                  .getOtherInventory())
          .get();

  // Category GUI
  public static final String CATEGORY_GUI_TITLE =
      PracticeConfig.playerKit().getGui().getItems().getCategoryGui().getTitle();
  public static final int CATEGORY_GUI_SIZE =
      PracticeConfig.playerKit().getGui().getItems().getCategoryGui().getSize();
  public static final EditorIcon CATEGORY_GUI_BACK_ICON =
      PlayerKitManager.getInstance().getEditorItem(categoryIcon("BACK-TO"));
  public static final EditorIcon CATEGORY_GUI_NONE_ICON =
      PlayerKitManager.getInstance().getEditorItem(categoryIcon("NONE"));
  public static final EditorIcon CATEGORY_GUI_ARMOR_ICON =
      PlayerKitManager.getInstance().getEditorItem(categoryIcon("ARMOR"));
  public static final EditorIcon CATEGORY_GUI_WEAPON_TOOLS_ICON =
      PlayerKitManager.getInstance().getEditorItem(categoryIcon("WEAPONS-TOOLS"));
  public static final EditorIcon CATEGORY_GUI_BOWS_ICON =
      PlayerKitManager.getInstance().getEditorItem(categoryIcon("BOWS-ARROWS"));
  public static final EditorIcon CATEGORY_GUI_POTIONS_ICON =
      PlayerKitManager.getInstance().getEditorItem(categoryIcon("POTIONS"));
  public static final EditorIcon CATEGORY_GUI_FOOD_ICON =
      PlayerKitManager.getInstance().getEditorItem(categoryIcon("FOOD"));
  public static final EditorIcon CATEGORY_GUI_BLOCKS_ICON =
      PlayerKitManager.getInstance().getEditorItem(categoryIcon("BLOCKS"));
  public static final EditorIcon CATEGORY_GUI_SHULKER_ICON =
      PlayerKitManager.getInstance().getEditorItem(categoryIcon("SHULKER-BOXES"));

  // Categories with page
  public static final EditorIcon CATEGORY_GUI_PAGE_BACK_ICON =
      PlayerKitManager.getInstance().getEditorItem(itemsGuiIcons().getBackTo());
  public static final EditorIcon CATEGORY_GUI_PAGE_NEXT_ICON =
      PlayerKitManager.getInstance().getEditorItem(itemsGuiIcons().getNextPage());
  public static final EditorIcon CATEGORY_GUI_PAGE_PREVIOUS_ICON =
      PlayerKitManager.getInstance().getEditorItem(itemsGuiIcons().getPreviousPage());
  public static final int CATEGORY_GUI_PAGE_SIZE =
      PracticeConfig.playerKit().getGui().getItems().getItemsGui().getSize();

  public static final String CATEGORY_ARMOR_TITLE =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("ARMOR")
          .getTitle();
  public static final List<String> CATEGORY_ARMOR_ITEMS =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("ARMOR")
          .getItems();
  public static final String CATEGORY_WEAPONS_TITLE =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("WEAPONS-TOOLS")
          .getTitle();
  public static final List<String> CATEGORY_WEAPONS_ITEMS =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("WEAPONS-TOOLS")
          .getItems();
  public static final String CATEGORY_BOWS_TITLE =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("BOWS-ARROWS")
          .getTitle();
  public static final List<String> CATEGORY_BOWS_ITEMS =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("BOWS-ARROWS")
          .getItems();
  public static final String CATEGORY_FOOD_TITLE =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("FOOD")
          .getTitle();
  public static final List<String> CATEGORY_FOOD_ITEMS =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("FOOD")
          .getItems();
  public static final String CATEGORY_BLOCKS_TITLE =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("BLOCKS")
          .getTitle();
  public static final List<String> CATEGORY_BLOCKS_ITEMS =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("BLOCKS")
          .getItems();
  public static final String CATEGORY_SHULKER_TITLE =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("SHULKER-BOXES")
          .getTitle();

  // Potions GUI
  public static final String POTIONS_GUI_TITLE =
      PracticeConfig.playerKit()
          .getGui()
          .getItems()
          .getItemsGui()
          .getCategories()
          .get("POTION")
          .getTitle();
  public static final ItemStack POTIONS_GUI_BACK_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getItemsGui()
                  .getCategories()
                  .get("POTION")
                  .getOfficialIcons()
                  .getBackTo())
          .get();
  public static final ItemStack POTIONS_GUI_SWITCH_SPLASH_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getItemsGui()
                  .getCategories()
                  .get("POTION")
                  .getOfficialIcons()
                  .getSwitchToSplash())
          .get();
  public static final ItemStack POTIONS_GUI_SWITCH_DRINKABLE_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getItemsGui()
                  .getCategories()
                  .get("POTION")
                  .getOfficialIcons()
                  .getSwitchToDrinkable())
          .get();
  public static final List<ItemStack> POTIONS_GUI_SPLASH_POTIONS = new ArrayList<>();
  public static final List<ItemStack> POTIONS_GUI_DRINKABLE_POTIONS = new ArrayList<>();

  static {
    for (String string :
        PracticeConfig.playerKit()
            .getGui()
            .getItems()
            .getItemsGui()
            .getCategories()
            .get("POTION")
            .getSplashPotions()
            .getItems()) {
      ItemStack itemStack = LadderUtil.getPotionItem(string);
      if (itemStack != null) {
        POTIONS_GUI_SPLASH_POTIONS.add(itemStack);
      }
    }

    for (String string :
        PracticeConfig.playerKit()
            .getGui()
            .getItems()
            .getItemsGui()
            .getCategories()
            .get("POTION")
            .getDrinkablePotions()
            .getItems()) {
      ItemStack itemStack = LadderUtil.getPotionItem(string);
      if (itemStack != null) {
        POTIONS_GUI_DRINKABLE_POTIONS.add(itemStack);
      }
    }
  }

  // Main Armor GUI
  public static final String MAIN_ARMOR_GUI_TITLE =
      PracticeConfig.playerKit().getGui().getMainArmor().getTitle();
  public static final int MAIN_ARMOR_GUI_SIZE =
      PracticeConfig.playerKit().getGui().getMainArmor().getSize();
  public static final EditorIcon MAIN_ARMOR_BACK_TO_ICON =
      PlayerKitManager.getInstance()
          .getEditorItem(
              mainArmorOfficial().getBackTo(), mainArmorOfficial().getBackTo().getSlot());
  public static final EditorIcon MAIN_ARMOR_NONE_ICON =
      PlayerKitManager.getInstance()
          .getEditorItem(mainArmorOfficial().getNone(), mainArmorOfficial().getNone().getSlot());
  public static final List<EditorIcon> MAIN_ARMOR_HELMET_ICONS = new ArrayList<>();
  public static final List<EditorIcon> MAIN_ARMOR_CHEST_ICONS = new ArrayList<>();
  public static final List<EditorIcon> MAIN_ARMOR_LEG_ICONS = new ArrayList<>();
  public static final List<EditorIcon> MAIN_ARMOR_BOOT_ICONS = new ArrayList<>();

  static {
    Gui.MainArmor.Icons icons = PracticeConfig.playerKit().getGui().getMainArmor().getIcons();
    addArmorIcons(MAIN_ARMOR_HELMET_ICONS, icons.getHelmets());
    addArmorIcons(MAIN_ARMOR_CHEST_ICONS, icons.getChestplates());
    addArmorIcons(MAIN_ARMOR_LEG_ICONS, icons.getLeggings());
    addArmorIcons(MAIN_ARMOR_BOOT_ICONS, icons.getBoots());
  }

  // Change amount
  public static final String CHANGE_AMOUNT_TITLE =
      PracticeConfig.playerKit().getGui().getItems().getChangeCountGui().getTitle();
  public static final ItemStack CHANGE_AMOUNT_BACK_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getChangeCountGui()
                  .getIcons()
                  .getBackTo())
          .get();
  public static final ItemStack CHANGE_CUSTOM_AMOUNT_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getChangeCountGui()
                  .getIcons()
                  .getCustomCount())
          .get();
  public static final String CHANGE_CUSTOM_AMOUNT_TITLE =
      PracticeConfig.playerKit().getGui().getItems().getCustomCountGui().getTitle();

  // Enchant GUI
  public static final String ENCHANT_GUI_TITLE =
      PracticeConfig.playerKit().getGui().getItems().getEnchantGui().getTitle();
  public static final ItemStack ENCHANT_GUI_BACK_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit().getGui().getItems().getEnchantGui().getIcons().getBackTo())
          .get();
  public static final ItemStack ENCHANT_GUI_UNBREAKABLE_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getEnchantGui()
                  .getIcons()
                  .getMakeUnbreakable())
          .get();
  public static final ItemStack ENCHANT_GUI_BREAKABLE_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getEnchantGui()
                  .getIcons()
                  .getMakeBreakable())
          .get();
  public static final ItemStack ENCHANT_GUI_CLEAR_ENCHANTS_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getEnchantGui()
                  .getIcons()
                  .getClearEnchants())
          .get();
  public static final ItemStack ENCHANT_GUI_CHANGE_DURABILITY_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getEnchantGui()
                  .getIcons()
                  .getChangeDurability())
          .get();
  public static final GuiItem ENCHANT_GUI_ENCHANT_ICON =
      BackendUtil.getGuiItem(
          PracticeConfig.playerKit()
              .getGui()
              .getItems()
              .getEnchantGui()
              .getIcons()
              .getEnchantmentIcon());
  public static final ItemStack ENCHANT_GUI_SET_LEVEL_PLACEHOLDER_ICON =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getItems()
                  .getEnchantGui()
                  .getIcons()
                  .getSetEnchantmentLevelPlaceholder())
          .get();
  public static final Map<Integer, GuiItem> ENCHANTMENT_LEVEL_MAP = new HashMap<>();
  public static final Map<String, List<Enchantment>> DISABLED_ENCHANTMENTS = new HashMap<>();

  static {
    for (int i = 1; i <= 5; i++) {
      ENCHANTMENT_LEVEL_MAP.put(
          i,
          BackendUtil.getGuiItem(
                  PracticeConfig.playerKit()
                      .getGui()
                      .getItems()
                      .getEnchantGui()
                      .getIcons()
                      .getSetEnchantmentLevel())
              .replace("%level%", String.valueOf(i)));
    }

    for (Map.Entry<String, List<String>> entry :
        PracticeConfig.playerKit().getDisabledEnchants().entrySet()) {
      String path = entry.getKey();
      List<Enchantment> enchantments = new ArrayList<>();
      for (String enchantment : entry.getValue() != null ? entry.getValue() : List.<String>of()) {
        try {
          Enchantment parsed = Common.resolveEnchantment(enchantment);
          if (parsed != null) {
            enchantments.add(parsed);
          }
        } catch (Exception e) {
          Common.sendConsoleMmMessage("<red>Invalid enchantment name: " + enchantment);
        }
      }
      DISABLED_ENCHANTMENTS.put(path, enchantments);
    }
  }

  public static final String CUSTOM_DURABILITY_GUI_TITLE =
      PracticeConfig.playerKit().getGui().getItems().getCustomDurabilityGui().getTitle();

  // Custom settings GUI
  public static final ItemStack BACK_TO_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.playerKit()
                  .getGui()
                  .getCustomSettings()
                  .getIcons()
                  .getNav()
                  .getGoBack())
          .get();
  public static final Pair<ItemStack, ItemStack> REGEN_ITEM =
      new Pair<>(
          BackendUtil.getGuiItem(
                  PracticeConfig.playerKit()
                      .getGui()
                      .getCustomSettings()
                      .getIcons()
                      .getNormalSettings()
                      .getRegeneration()
                      .getEnabled())
              .get(),
          BackendUtil.getGuiItem(
                  PracticeConfig.playerKit()
                      .getGui()
                      .getCustomSettings()
                      .getIcons()
                      .getNormalSettings()
                      .getRegeneration()
                      .getDisabled())
              .get());
  public static final Pair<ItemStack, ItemStack> HUNGER_ITEM =
      new Pair<>(
          BackendUtil.getGuiItem(
                  PracticeConfig.playerKit()
                      .getGui()
                      .getCustomSettings()
                      .getIcons()
                      .getNormalSettings()
                      .getHunger()
                      .getEnabled())
              .get(),
          BackendUtil.getGuiItem(
                  PracticeConfig.playerKit()
                      .getGui()
                      .getCustomSettings()
                      .getIcons()
                      .getNormalSettings()
                      .getHunger()
                      .getDisabled())
              .get());
  public static final Pair<ItemStack, ItemStack> BUILD_ITEM =
      new Pair<>(
          BackendUtil.getGuiItem(
                  PracticeConfig.playerKit()
                      .getGui()
                      .getCustomSettings()
                      .getIcons()
                      .getNormalSettings()
                      .getBuild()
                      .getEnabled())
              .get(),
          BackendUtil.getGuiItem(
                  PracticeConfig.playerKit()
                      .getGui()
                      .getCustomSettings()
                      .getIcons()
                      .getNormalSettings()
                      .getBuild()
                      .getDisabled())
              .get());
  public static final GuiItem ROUNDS_ITEM =
      BackendUtil.getGuiItem(
          PracticeConfig.playerKit()
              .getGui()
              .getCustomSettings()
              .getIcons()
              .getNormalSettings()
              .getRounds());
  public static final GuiItem KNOCKBACK_ITEM =
      BackendUtil.getGuiItem(
          PracticeConfig.playerKit()
              .getGui()
              .getCustomSettings()
              .getIcons()
              .getNormalSettings()
              .getKnockback());
  public static final GuiItem HITDELAY_ITEM =
      BackendUtil.getGuiItem(
          PracticeConfig.playerKit()
              .getGui()
              .getCustomSettings()
              .getIcons()
              .getNormalSettings()
              .getHitdelay());
  public static final GuiItem ENDERPEARL_ITEM =
      BackendUtil.getGuiItem(
          PracticeConfig.playerKit()
              .getGui()
              .getCustomSettings()
              .getIcons()
              .getNormalSettings()
              .getEnderpearlCooldown());
  public static final GuiItem GAPPLE_ITEM =
      BackendUtil.getGuiItem(
          PracticeConfig.playerKit()
              .getGui()
              .getCustomSettings()
              .getIcons()
              .getNormalSettings()
              .getGoldenappleCooldown());
  public static final GuiItem WIND_CHARGE_ITEM =
      BackendUtil.getGuiItem(
          PracticeConfig.playerKit()
              .getGui()
              .getCustomSettings()
              .getIcons()
              .getNormalSettings()
              .getWindChargeCooldown());
  public static final Pair<ItemStack, ItemStack> HEALTH_BELOW_NAME_ITEM =
      new Pair<>(
          BackendUtil.getGuiItem(
                  PracticeConfig.playerKit()
                      .getGui()
                      .getCustomSettings()
                      .getIcons()
                      .getNormalSettings()
                      .getHealthBelowName()
                      .getEnabled())
              .get(),
          BackendUtil.getGuiItem(
                  PracticeConfig.playerKit()
                      .getGui()
                      .getCustomSettings()
                      .getIcons()
                      .getNormalSettings()
                      .getHealthBelowName()
                      .getDisabled())
              .get());

  private static Items.CategoryGui.CategoryIcon categoryIcon(String key) {
    return PracticeConfig.playerKit().getGui().getItems().getCategoryGui().getIcons().get(key);
  }

  private static Items.ItemsGui.OfficialIcons itemsGuiIcons() {
    return PracticeConfig.playerKit().getGui().getItems().getItemsGui().getOfficialIcons();
  }

  private static Gui.MainArmor.Icons.Official mainArmorOfficial() {
    return PracticeConfig.playerKit().getGui().getMainArmor().getIcons().getOfficial();
  }

  private static void addArmorIcons(
      List<EditorIcon> target, Map<String, Gui.MainArmor.Icons.ArmorIcon> icons) {
    if (icons == null) {
      return;
    }
    for (Gui.MainArmor.Icons.ArmorIcon icon : icons.values()) {
      target.add(
          PlayerKitManager.getInstance().getEditorItem(icon, icon != null ? icon.getSlot() : null));
    }
  }
}

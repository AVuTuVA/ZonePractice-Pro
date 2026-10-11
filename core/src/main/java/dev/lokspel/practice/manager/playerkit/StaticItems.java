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
  public static String mainGuiTitle() {
    return PracticeConfig.playerKit().getGui().getMain().getTitle();
  }

  public static ItemStack mainGuiBack() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit().getGui().getMain().getIcons().getBackTo())
        .get();
  }

  public static ItemStack mainGuiSettingsIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit().getGui().getMain().getIcons().getCustomSettings())
        .get();
  }

  public static ItemStack mainGuiGuideIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit().getGui().getMain().getIcons().getGuide())
        .get();
  }

  public static ItemStack mainGuiResetKitIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit().getGui().getMain().getIcons().getResetKit())
        .get();
  }

  public static ItemStack mainGuiChangeName() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit().getGui().getMain().getIcons().getChangeName())
        .get();
  }

  public static ItemStack mainGuiShareKit() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit().getGui().getMain().getIcons().getShareKit())
        .get();
  }

  // Main GUI placeholders
  public static ItemStack mainGuiHeadPlaceholderIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getMain()
                .getIcons()
                .getKitSlotPlaceholders()
                .getHead())
        .get();
  }

  public static ItemStack mainGuiChestPlaceholderIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getMain()
                .getIcons()
                .getKitSlotPlaceholders()
                .getChest())
        .get();
  }

  public static ItemStack mainGuiLegsPlaceholderIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getMain()
                .getIcons()
                .getKitSlotPlaceholders()
                .getLegs())
        .get();
  }

  public static ItemStack mainGuiBootsPlaceholderIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getMain()
                .getIcons()
                .getKitSlotPlaceholders()
                .getBoots())
        .get();
  }

  public static ItemStack mainGuiOffHandPlaceholderIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getMain()
                .getIcons()
                .getKitSlotPlaceholders()
                .getOffhand())
        .get();
  }

  public static ItemStack mainGuiHotbarPlaceholderIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getMain()
                .getIcons()
                .getKitSlotPlaceholders()
                .getHotbar())
        .get();
  }

  public static ItemStack mainGuiInventoryPlaceholderIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getMain()
                .getIcons()
                .getKitSlotPlaceholders()
                .getOtherInventory())
        .get();
  }

  // Category GUI
  public static String categoryGuiTitle() {
    return PracticeConfig.playerKit().getGui().getItems().getCategoryGui().getTitle();
  }

  public static int categoryGuiSize() {
    return PracticeConfig.playerKit().getGui().getItems().getCategoryGui().getSize();
  }

  public static EditorIcon categoryGuiBackIcon() {
    return PlayerKitManager.getInstance().getEditorItem(categoryIcon("BACK-TO"));
  }

  public static EditorIcon categoryGuiNoneIcon() {
    return PlayerKitManager.getInstance().getEditorItem(categoryIcon("NONE"));
  }

  public static EditorIcon categoryGuiArmorIcon() {
    return PlayerKitManager.getInstance().getEditorItem(categoryIcon("ARMOR"));
  }

  public static EditorIcon categoryGuiWeaponToolsIcon() {
    return PlayerKitManager.getInstance().getEditorItem(categoryIcon("WEAPONS-TOOLS"));
  }

  public static EditorIcon categoryGuiBowsIcon() {
    return PlayerKitManager.getInstance().getEditorItem(categoryIcon("BOWS-ARROWS"));
  }

  public static EditorIcon categoryGuiPotionsIcon() {
    return PlayerKitManager.getInstance().getEditorItem(categoryIcon("POTIONS"));
  }

  public static EditorIcon categoryGuiFoodIcon() {
    return PlayerKitManager.getInstance().getEditorItem(categoryIcon("FOOD"));
  }

  public static EditorIcon categoryGuiBlocksIcon() {
    return PlayerKitManager.getInstance().getEditorItem(categoryIcon("BLOCKS"));
  }

  public static EditorIcon categoryGuiShulkerIcon() {
    return PlayerKitManager.getInstance().getEditorItem(categoryIcon("SHULKER-BOXES"));
  }

  // Categories with page
  public static EditorIcon categoryGuiPageBackIcon() {
    return PlayerKitManager.getInstance().getEditorItem(itemsGuiIcons().getBackTo());
  }

  public static EditorIcon categoryGuiPageNextIcon() {
    return PlayerKitManager.getInstance().getEditorItem(itemsGuiIcons().getNextPage());
  }

  public static EditorIcon categoryGuiPagePreviousIcon() {
    return PlayerKitManager.getInstance().getEditorItem(itemsGuiIcons().getPreviousPage());
  }

  public static int categoryGuiPageSize() {
    return PracticeConfig.playerKit().getGui().getItems().getItemsGui().getSize();
  }

  public static String categoryArmorTitle() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("ARMOR")
        .getTitle();
  }

  public static List<String> categoryArmorItems() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("ARMOR")
        .getItems();
  }

  public static String categoryWeaponsTitle() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("WEAPONS-TOOLS")
        .getTitle();
  }

  public static List<String> categoryWeaponsItems() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("WEAPONS-TOOLS")
        .getItems();
  }

  public static String categoryBowsTitle() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("BOWS-ARROWS")
        .getTitle();
  }

  public static List<String> categoryBowsItems() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("BOWS-ARROWS")
        .getItems();
  }

  public static String categoryFoodTitle() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("FOOD")
        .getTitle();
  }

  public static List<String> categoryFoodItems() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("FOOD")
        .getItems();
  }

  public static String categoryBlocksTitle() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("BLOCKS")
        .getTitle();
  }

  public static List<String> categoryBlocksItems() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("BLOCKS")
        .getItems();
  }

  public static String categoryShulkerTitle() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("SHULKER-BOXES")
        .getTitle();
  }

  // Potions GUI
  public static String potionsGuiTitle() {
    return PracticeConfig.playerKit()
        .getGui()
        .getItems()
        .getItemsGui()
        .getCategories()
        .get("POTION")
        .getTitle();
  }

  public static ItemStack potionsGuiBackIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getItemsGui()
                .getCategories()
                .get("POTION")
                .getOfficialIcons()
                .getBackTo())
        .get();
  }

  public static ItemStack potionsGuiSwitchSplashIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getItemsGui()
                .getCategories()
                .get("POTION")
                .getOfficialIcons()
                .getSwitchToSplash())
        .get();
  }

  public static ItemStack potionsGuiSwitchDrinkableIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getItemsGui()
                .getCategories()
                .get("POTION")
                .getOfficialIcons()
                .getSwitchToDrinkable())
        .get();
  }

  public static List<ItemStack> potionsGuiSplashPotions() {
    return potionItems(
        PracticeConfig.playerKit()
            .getGui()
            .getItems()
            .getItemsGui()
            .getCategories()
            .get("POTION")
            .getSplashPotions()
            .getItems());
  }

  public static List<ItemStack> potionsGuiDrinkablePotions() {
    return potionItems(
        PracticeConfig.playerKit()
            .getGui()
            .getItems()
            .getItemsGui()
            .getCategories()
            .get("POTION")
            .getDrinkablePotions()
            .getItems());
  }

  private static List<ItemStack> potionItems(List<String> potions) {
    List<ItemStack> items = new ArrayList<>();
    for (String string : potions) {
      ItemStack itemStack = LadderUtil.getPotionItem(string);
      if (itemStack != null) {
        items.add(itemStack);
      }
    }

    return items;
  }

  // Main Armor GUI
  public static String mainArmorGuiTitle() {
    return PracticeConfig.playerKit().getGui().getMainArmor().getTitle();
  }

  public static int mainArmorGuiSize() {
    return PracticeConfig.playerKit().getGui().getMainArmor().getSize();
  }

  public static EditorIcon mainArmorBackToIcon() {
    return PlayerKitManager.getInstance()
        .getEditorItem(mainArmorOfficial().getBackTo(), mainArmorOfficial().getBackTo().getSlot());
  }

  public static EditorIcon mainArmorNoneIcon() {
    return PlayerKitManager.getInstance()
        .getEditorItem(mainArmorOfficial().getNone(), mainArmorOfficial().getNone().getSlot());
  }

  public static List<EditorIcon> mainArmorHelmetIcons() {
    List<EditorIcon> icons = new ArrayList<>();
    addArmorIcons(
        icons, PracticeConfig.playerKit().getGui().getMainArmor().getIcons().getHelmets());

    return icons;
  }

  public static List<EditorIcon> mainArmorChestIcons() {
    List<EditorIcon> icons = new ArrayList<>();
    addArmorIcons(
        icons, PracticeConfig.playerKit().getGui().getMainArmor().getIcons().getChestplates());

    return icons;
  }

  public static List<EditorIcon> mainArmorLegIcons() {
    List<EditorIcon> icons = new ArrayList<>();
    addArmorIcons(
        icons, PracticeConfig.playerKit().getGui().getMainArmor().getIcons().getLeggings());

    return icons;
  }

  public static List<EditorIcon> mainArmorBootIcons() {
    List<EditorIcon> icons = new ArrayList<>();
    addArmorIcons(icons, PracticeConfig.playerKit().getGui().getMainArmor().getIcons().getBoots());

    return icons;
  }

  // Change amount
  public static String changeAmountTitle() {
    return PracticeConfig.playerKit().getGui().getItems().getChangeCountGui().getTitle();
  }

  public static ItemStack changeAmountBackIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getChangeCountGui()
                .getIcons()
                .getBackTo())
        .get();
  }

  public static ItemStack changeCustomAmountIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getChangeCountGui()
                .getIcons()
                .getCustomCount())
        .get();
  }

  public static String changeCustomAmountTitle() {
    return PracticeConfig.playerKit().getGui().getItems().getCustomCountGui().getTitle();
  }

  // Enchant GUI
  public static String enchantGuiTitle() {
    return PracticeConfig.playerKit().getGui().getItems().getEnchantGui().getTitle();
  }

  public static ItemStack enchantGuiBackIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit().getGui().getItems().getEnchantGui().getIcons().getBackTo())
        .get();
  }

  public static ItemStack enchantGuiUnbreakableIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getEnchantGui()
                .getIcons()
                .getMakeUnbreakable())
        .get();
  }

  public static ItemStack enchantGuiBreakableIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getEnchantGui()
                .getIcons()
                .getMakeBreakable())
        .get();
  }

  public static ItemStack enchantGuiClearEnchantsIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getEnchantGui()
                .getIcons()
                .getClearEnchants())
        .get();
  }

  public static ItemStack enchantGuiChangeDurabilityIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getEnchantGui()
                .getIcons()
                .getChangeDurability())
        .get();
  }

  public static GuiItem enchantGuiEnchantIcon() {
    return BackendUtil.getGuiItem(
        PracticeConfig.playerKit()
            .getGui()
            .getItems()
            .getEnchantGui()
            .getIcons()
            .getEnchantmentIcon());
  }

  public static ItemStack enchantGuiSetLevelPlaceholderIcon() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit()
                .getGui()
                .getItems()
                .getEnchantGui()
                .getIcons()
                .getSetEnchantmentLevelPlaceholder())
        .get();
  }

  public static Map<Integer, GuiItem> enchantmentLevelMap() {
    Map<Integer, GuiItem> levels = new HashMap<>();
    for (int i = 1; i <= 5; i++) {
      levels.put(
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

    return levels;
  }

  public static Map<String, List<Enchantment>> disabledEnchantments() {
    Map<String, List<Enchantment>> disabled = new HashMap<>();
    for (Map.Entry<String, List<String>> entry :
        PracticeConfig.playerKit().getDisabledEnchants().entrySet()) {
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
      disabled.put(entry.getKey(), enchantments);
    }

    return disabled;
  }

  public static String customDurabilityGuiTitle() {
    return PracticeConfig.playerKit().getGui().getItems().getCustomDurabilityGui().getTitle();
  }

  // Custom settings GUI
  public static ItemStack backToItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.playerKit().getGui().getCustomSettings().getIcons().getNav().getGoBack())
        .get();
  }

  public static Pair<ItemStack, ItemStack> regenItem() {
    return new Pair<>(
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
  }

  public static Pair<ItemStack, ItemStack> hungerItem() {
    return new Pair<>(
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
  }

  public static Pair<ItemStack, ItemStack> buildItem() {
    return new Pair<>(
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
  }

  public static GuiItem roundsItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.playerKit()
            .getGui()
            .getCustomSettings()
            .getIcons()
            .getNormalSettings()
            .getRounds());
  }

  public static GuiItem knockbackItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.playerKit()
            .getGui()
            .getCustomSettings()
            .getIcons()
            .getNormalSettings()
            .getKnockback());
  }

  public static GuiItem hitdelayItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.playerKit()
            .getGui()
            .getCustomSettings()
            .getIcons()
            .getNormalSettings()
            .getHitdelay());
  }

  public static GuiItem enderpearlItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.playerKit()
            .getGui()
            .getCustomSettings()
            .getIcons()
            .getNormalSettings()
            .getEnderpearlCooldown());
  }

  public static GuiItem gappleItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.playerKit()
            .getGui()
            .getCustomSettings()
            .getIcons()
            .getNormalSettings()
            .getGoldenappleCooldown());
  }

  public static GuiItem windChargeItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.playerKit()
            .getGui()
            .getCustomSettings()
            .getIcons()
            .getNormalSettings()
            .getWindChargeCooldown());
  }

  public static Pair<ItemStack, ItemStack> healthBelowNameItem() {
    return new Pair<>(
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
  }

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

package dev.lokspel.practice.manager.gui.guis.cosmetics.armortrim;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.cosmetics.CosmeticsPermissionManager;
import dev.lokspel.practice.manager.profile.cosmetics.armortrim.ArmorSlot;
import dev.lokspel.practice.manager.profile.cosmetics.armortrim.ArmorTrimTier;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class ArmorPieceHubGui extends Gui {

  private static ItemStack fillerItem() {
    return BackendUtil.getGuiItem(PracticeConfig.guis().getGeneralFillerItem()).get();
  }

  private static ItemStack backToItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis().getGuis().getCosmetics().getIcons().getBackTo())
        .get();
  }

  private static int inventoryRows() {
    return PracticeConfig.guis().getGuis().getCosmetics().getArmorPieceHub().getInventoryRows();
  }

  private static int backSlot() {
    return PracticeConfig.guis().getGuis().getCosmetics().getArmorPieceHub().getSlots().getBack();
  }

  private static int previewSlot() {
    return PracticeConfig.guis()
        .getGuis()
        .getCosmetics()
        .getArmorPieceHub()
        .getSlots()
        .getPreview();
  }

  private static int patternMenuSlot() {
    return PracticeConfig.guis()
        .getGuis()
        .getCosmetics()
        .getArmorPieceHub()
        .getSlots()
        .getPatternMenu();
  }

  private static int materialMenuSlot() {
    return PracticeConfig.guis()
        .getGuis()
        .getCosmetics()
        .getArmorPieceHub()
        .getSlots()
        .getMaterialMenu();
  }

  private static Material defaultPatternButtonMaterial() {
    return Material.valueOf(
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getArmorPieceHub()
            .getPatternSelectionButton()
            .getDefaultMaterial());
  }

  private static Material defaultMaterialButtonMaterial() {
    return Material.valueOf(
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getArmorPieceHub()
            .getMaterialSelectionButton()
            .getDefaultMaterial());
  }

  private final Profile profile;
  private final ArmorSlot armorSlot;
  private final Gui backToGui;

  public ArmorPieceHubGui(Profile profile, ArmorSlot armorSlot, Gui backToGui) {
    super(resolveGuiType(armorSlot));
    this.profile = profile;
    this.armorSlot = armorSlot;
    this.backToGui = backToGui;
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            "<dark_gray>" + armorSlot.getDisplayName() + " Cosmetics", inventoryRows()));
    build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    Inventory inventory = gui.get(1);
    inventory.clear();

    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, fillerItem());
    }

    ArmorTrimTier tier = profile.getCosmeticsData().getActiveTier();
    TrimPattern pattern = profile.getCosmeticsData().getPattern(tier, armorSlot);
    TrimMaterial material = profile.getCosmeticsData().getMaterial(tier, armorSlot);

    inventory.setItem(
        backSlot(), backToItem() == null ? new ItemStack(Material.ARROW) : backToItem());
    inventory.setItem(previewSlot(), buildPreviewItem(tier, pattern, material));
    inventory.setItem(patternMenuSlot(), buildPatternSelectionItem(pattern));
    inventory.setItem(materialMenuSlot(), buildMaterialSelectionItem(material));

    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();

    if (slot == backSlot()) {
      backToGui.update(true);
      backToGui.open(player);
      return;
    }

    if (slot == patternMenuSlot()) {
      new PatternSelectionGui(profile, armorSlot, this).open(player);
      return;
    }

    if (slot == materialMenuSlot()) {
      new MaterialSelectionGui(profile, armorSlot, this).open(player);
    }
  }

  private ItemStack buildPreviewItem(
      ArmorTrimTier tier, TrimPattern pattern, TrimMaterial material) {
    Material previewMaterial = tier.getMaterial(armorSlot);
    GuiItem item = new GuiItem(previewMaterial);
    item.setName(
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getArmorPieceHub()
            .getPreviewItem()
            .getName());

    ItemStack itemStack = item.get();
    if (armorSlot != ArmorSlot.SHIELD
        && pattern != null
        && material != null
        && itemStack.getItemMeta() instanceof ArmorMeta armorMeta) {
      armorMeta.setTrim(new ArmorTrim(material, pattern));
      itemStack.setItemMeta(armorMeta);
    }

    return itemStack;
  }

  private ItemStack buildNavigationItem(Material material, String name, String loreLine) {
    GuiItem item = new GuiItem(material);
    item.setName(name);

    List<String> lore = new ArrayList<>();
    lore.add(loreLine);
    lore.add("<yellow>Click to open.");
    item.setLore(lore);

    return item.get();
  }

  private ItemStack buildPatternSelectionItem(TrimPattern activePattern) {
    Material buttonMaterial = defaultPatternButtonMaterial();
    if (activePattern != null) {
      String patternId = CosmeticsPermissionManager.getTrimId(activePattern);
      Material activePatternMaterial =
          resolveMaterial(patternId.toUpperCase(Locale.ROOT) + "_ARMOR_TRIM_SMITHING_TEMPLATE");
      if (activePatternMaterial != null) {
        buttonMaterial = activePatternMaterial;
      }
    }

    String name =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getArmorPieceHub()
            .getPatternSelectionButton()
            .getName();
    String loreLine =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getArmorPieceHub()
            .getPatternSelectionButton()
            .getLore()
            .getFirst();
    return buildNavigationItem(buttonMaterial, name, loreLine);
  }

  private ItemStack buildMaterialSelectionItem(TrimMaterial activeMaterial) {
    Material buttonMaterial = defaultMaterialButtonMaterial();
    if (activeMaterial != null) {
      Material activeMaterialIcon =
          resolveTrimMaterialIcon(CosmeticsPermissionManager.getTrimId(activeMaterial));
      if (activeMaterialIcon != null) {
        buttonMaterial = activeMaterialIcon;
      }
    }

    String name =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getArmorPieceHub()
            .getMaterialSelectionButton()
            .getName();
    String loreLine =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getArmorPieceHub()
            .getMaterialSelectionButton()
            .getLore()
            .getFirst();
    return buildNavigationItem(buttonMaterial, name, loreLine);
  }

  private Material resolveTrimMaterialIcon(String materialId) {
    return switch (materialId) {
      case "lapis" -> Material.LAPIS_LAZULI;
      case "amethyst" -> Material.AMETHYST_SHARD;
      case "resin" ->
          resolveMaterial("RESIN_BRICK") == null ? Material.BRICK : resolveMaterial("RESIN_BRICK");
      default -> {
        Material ingot = resolveMaterial(materialId.toUpperCase(Locale.ROOT) + "_INGOT");
        if (ingot != null) {
          yield ingot;
        }
        yield resolveMaterial(materialId.toUpperCase(Locale.ROOT));
      }
    };
  }

  private static GuiType resolveGuiType(ArmorSlot armorSlot) {
    return switch (armorSlot) {
      case HELMET -> GuiType.Cosmetics_Helmet;
      case CHESTPLATE -> GuiType.Cosmetics_Chestplate;
      case LEGGINGS -> GuiType.Cosmetics_Leggings;
      case BOOTS -> GuiType.Cosmetics_Boots;
      case SHIELD -> GuiType.Cosmetics_Shield;
    };
  }

  private Material resolveMaterial(String materialName) {
    try {
      return Material.valueOf(materialName);
    } catch (IllegalArgumentException ignored) {
      return null;
    }
  }
}

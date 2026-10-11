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
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimMaterial;

public class MaterialSelectionGui extends Gui {

  private static final ItemStack FILLER_ITEM =
      BackendUtil.getGuiItem(PracticeConfig.guis().getGeneralFillerItem()).get();
  private static final ItemStack BACK_TO_ITEM =
      BackendUtil.getGuiItem(PracticeConfig.guis().getGuis().getCosmetics().getIcons().getBackTo())
          .get();

  private static final int INVENTORY_ROWS =
      PracticeConfig.guis().getGuis().getCosmetics().getMaterialSelection().getInventoryRows();
  private static final int BACK_SLOT =
      PracticeConfig.guis().getGuis().getCosmetics().getMaterialSelection().getBackSlot();
  private static final int START_SLOT =
      PracticeConfig.guis().getGuis().getCosmetics().getMaterialSelection().getStartSlot();

  private final Profile profile;
  private final ArmorSlot armorSlot;
  private final Gui backToGui;
  private final Map<Integer, TrimMaterial> materialBySlot = new HashMap<>();

  public MaterialSelectionGui(Profile profile, ArmorSlot armorSlot, Gui backToGui) {
    super(GuiType.Cosmetics_Material_Selection);
    this.profile = profile;
    this.armorSlot = armorSlot;
    this.backToGui = backToGui;
    String title =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getMaterialSelection()
            .getInventoryTitle()
            .replace("%armor%", armorSlot.getDisplayName());
    this.gui.put(1, InventoryUtil.createInventory(title, INVENTORY_ROWS));
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
    materialBySlot.clear();

    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, FILLER_ITEM);
    }

    inventory.setItem(
        BACK_SLOT, BACK_TO_ITEM == null ? new ItemStack(Material.ARROW) : BACK_TO_ITEM);

    ArmorTrimTier tier = profile.getCosmeticsData().getActiveTier();
    int slot = START_SLOT;
    for (TrimMaterial material : CosmeticsPermissionManager.getRegisteredMaterials()) {
      if (slot >= inventory.getSize()) {
        break;
      }

      while (slot < inventory.getSize() && (slot % 9 == 0 || slot % 9 == 8)) {
        slot++;
      }

      if (slot >= inventory.getSize()) {
        break;
      }

      materialBySlot.put(slot, material);
      inventory.setItem(slot, buildMaterialItem(profile.getPlayer().getPlayer(), tier, material));
      slot++;
    }

    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();

    if (slot == BACK_SLOT) {
      backToGui.update(true);
      backToGui.open(player);
      return;
    }

    TrimMaterial material = materialBySlot.get(slot);
    if (material == null) {
      return;
    }

    ArmorTrimTier tier = profile.getCosmeticsData().getActiveTier();
    if (!player.hasPermission(tier.getPermissionNode())) {
      String tierPermDeniedMessage =
          PracticeConfig.guis().getGuis().getCosmetics().getTierPermissionDeniedMessage();
      Common.sendMmMessage(player, tierPermDeniedMessage);
      return;
    }

    String permissionNode =
        "ap.cosmetics.armortrim.material." + CosmeticsPermissionManager.getTrimId(material);
    if (!player.hasPermission(permissionNode)) {
      String permDeniedMessage =
          PracticeConfig.guis().getGuis().getCosmetics().getMaterialPermissionDeniedMessage();
      Common.sendMmMessage(player, permDeniedMessage);
      return;
    }

    profile.getCosmeticsData().setMaterial(tier, armorSlot, material);
    profile.save();
    update(true);
  }

  private ItemStack buildMaterialItem(Player player, ArmorTrimTier tier, TrimMaterial material) {
    String materialId = CosmeticsPermissionManager.getTrimId(material);
    String permissionNode = "ap.cosmetics.armortrim.material." + materialId;
    boolean hasPermission = player != null && player.hasPermission(permissionNode);

    TrimMaterial activeMaterial = profile.getCosmeticsData().getMaterial(tier, armorSlot);
    boolean active =
        activeMaterial != null
            && CosmeticsPermissionManager.getTrimId(activeMaterial).equalsIgnoreCase(materialId);

    GuiItem item = new GuiItem(resolveIcon(materialId));

    String itemName =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getMaterialSelection()
            .getMaterialItem()
            .getName()
            .replace("%material_name%", StringUtil.getNormalizedName(materialId));
    item.setName(itemName);

    List<String> configLore =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getMaterialSelection()
            .getMaterialItem()
            .getLore();
    List<String> lore = new ArrayList<>();
    for (String loreLine : configLore) {
      lore.add(
          loreLine
              .replace("%state%", active ? "<green>Active" : "<red>Inactive")
              .replace("%access%", hasPermission ? "<green>Unlocked" : "<red>Locked")
              .replace("%permission%", permissionNode));
    }
    item.setLore(lore);
    item.setGlowing(active);

    return item.get();
  }

  private Material resolveIcon(String materialId) {
    // Check if material icon is configured
    Map<String, String> materialIcons =
        PracticeConfig.guis().getGuis().getCosmetics().getMaterialSelection().getMaterialIcons();
    String configuredMaterial =
        materialIcons != null ? materialIcons.get(materialId.toUpperCase()) : null;
    if (configuredMaterial != null) {
      try {
        return Material.valueOf(configuredMaterial);
      } catch (IllegalArgumentException ignored) {
        // Fall through to default resolution
      }
    }

    // Default resolution logic
    return switch (materialId) {
      case "lapis" -> Material.LAPIS_LAZULI;
      case "amethyst" -> Material.AMETHYST_SHARD;
      case "resin" -> resolveByName("RESIN_BRICK", Material.BRICK);
      default -> {
        Material resolved = resolveByName(materialId.toUpperCase() + "_INGOT", null);
        if (resolved == null) {
          resolved = resolveByName(materialId.toUpperCase(), null);
        }
        yield resolved == null ? Material.NETHER_STAR : resolved;
      }
    };
  }

  private Material resolveByName(String materialName, Material fallback) {
    try {
      return Material.valueOf(materialName);
    } catch (IllegalArgumentException ignored) {
      return fallback;
    }
  }
}

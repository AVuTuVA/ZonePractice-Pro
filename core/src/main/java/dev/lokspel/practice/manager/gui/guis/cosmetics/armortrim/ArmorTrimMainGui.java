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
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

/** Main cosmetics GUI that displays armor pieces for the player to customize. */
public class ArmorTrimMainGui extends Gui {

  private static final int MAIN_ROWS = 5;
  private static final int BACK_SLOT = 36;
  private static final int HELMET_SLOT = 10;
  private static final int CHESTPLATE_SLOT = 12;
  private static final int LEGGINGS_SLOT = 14;
  private static final int BOOTS_SLOT = 16;
  private static final int INFO_SLOT = 30;
  private static final int APPLY_ALL_SLOT = 32;

  private static ItemStack fillerItem() {
    return BackendUtil.getGuiItem(PracticeConfig.guis().getGeneralFillerItem()).get();
  }

  private static GuiItem helmetItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.guis().getGuis().getCosmetics().getIcons().getHelmetIcon());
  }

  private static GuiItem chestplateItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.guis().getGuis().getCosmetics().getIcons().getChestplateIcon());
  }

  private static GuiItem leggingsItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.guis().getGuis().getCosmetics().getIcons().getLeggingsIcon());
  }

  private static GuiItem bootsItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.guis().getGuis().getCosmetics().getIcons().getBootsIcon());
  }

  private static GuiItem infoItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.guis().getGuis().getCosmetics().getIcons().getInfoIcon());
  }

  private static GuiItem applyAllItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.guis().getGuis().getCosmetics().getIcons().getApplyAllTiers());
  }

  private static ItemStack backToItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis().getGuis().getCosmetics().getIcons().getBackTo())
        .get();
  }

  private static final ArmorSlot[] ARMOR_TYPE_SLOTS = {
    ArmorSlot.HELMET, ArmorSlot.CHESTPLATE, ArmorSlot.LEGGINGS, ArmorSlot.BOOTS
  };

  private final Profile profile;
  private final Gui backToGui;

  public ArmorTrimMainGui(Profile profile, Gui backToGui) {
    super(GuiType.ArmorTrimMainGui);
    this.profile = profile;
    this.backToGui = backToGui;

    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getCosmetics().getMainTitle(), MAIN_ROWS));
    this.build();
  }

  @Override
  public void build() {
    this.update();
  }

  @Override
  public void update() {
    Inventory inventory = this.gui.get(1);
    inventory.clear();

    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, fillerItem());
    }

    ArmorTrimTier activeTier = profile.getCosmeticsData().getActiveTier();
    Player player = profile.getPlayer().getPlayer();
    if (player != null && !player.hasPermission(activeTier.getPermissionNode())) {
      for (ArmorTrimTier tier : ArmorTrimTier.values()) {
        if (player.hasPermission(tier.getPermissionNode())) {
          activeTier = tier;
          profile.getCosmeticsData().setActiveTier(tier);
          break;
        }
      }
    }

    inventory.setItem(
        HELMET_SLOT, buildArmorPreviewItem(helmetItem(), activeTier, ArmorSlot.HELMET));
    inventory.setItem(
        CHESTPLATE_SLOT, buildArmorPreviewItem(chestplateItem(), activeTier, ArmorSlot.CHESTPLATE));
    inventory.setItem(
        LEGGINGS_SLOT, buildArmorPreviewItem(leggingsItem(), activeTier, ArmorSlot.LEGGINGS));
    inventory.setItem(BOOTS_SLOT, buildArmorPreviewItem(bootsItem(), activeTier, ArmorSlot.BOOTS));
    inventory.setItem(INFO_SLOT, buildTierToggleItem(activeTier));
    inventory.setItem(APPLY_ALL_SLOT, buildApplyToAllTiersItem());

    if (backToGui != null) {
      ItemStack backItem = backToItem() == null ? new ItemStack(Material.ARROW) : backToItem();
      inventory.setItem(BACK_SLOT, backItem);
    }

    this.updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();

    e.setCancelled(true);

    if (slot == BACK_SLOT && backToGui != null) {
      backToGui.open(player);
      return;
    }

    if (slot == INFO_SLOT) {
      if (e.isLeftClick()) {
        handleTierToggleClick(player, false);
      } else if (e.isRightClick()) {
        handleTierToggleClick(player, true);
      }
      return;
    }

    if (slot == APPLY_ALL_SLOT) {
      if (!player.hasPermission("ap.cosmetics.armortrim.apply-global")) {
        Common.sendMmMessage(
            player, PracticeConfig.guis().getGuis().getCosmetics().getApplyAllTiersErrorMessage());
        return;
      }

      applyActiveTierToAllTiers(player);
      return;
    }

    ArmorSlot armorSlot = getArmorSlotFromSlot(slot);
    if (armorSlot != null) {
      ArmorTrimTier activeTier = profile.getCosmeticsData().getActiveTier();
      if (!player.hasPermission(activeTier.getPermissionNode())) {
        String deniedMessage =
            PracticeConfig.guis().getGuis().getCosmetics().getPermissionDeniedMessage();
        Common.sendMmMessage(player, deniedMessage);
        return;
      }

      if (e.isRightClick()) {
        resetArmorCosmetic(activeTier, armorSlot);
        return;
      }

      openArmorSubGui(player, armorSlot);
    }
  }

  private void resetArmorCosmetic(ArmorTrimTier activeTier, ArmorSlot armorSlot) {
    profile.getCosmeticsData().setPattern(activeTier, armorSlot, null);
    profile.getCosmeticsData().setMaterial(activeTier, armorSlot, null);
    update(true);
  }

  private ArmorSlot getArmorSlotFromSlot(int slot) {
    return switch (slot) {
      case HELMET_SLOT -> ArmorSlot.HELMET;
      case CHESTPLATE_SLOT -> ArmorSlot.CHESTPLATE;
      case LEGGINGS_SLOT -> ArmorSlot.LEGGINGS;
      case BOOTS_SLOT -> ArmorSlot.BOOTS;
      default -> null;
    };
  }

  private ItemStack buildArmorPreviewItem(
      GuiItem configuredItem, ArmorTrimTier tier, ArmorSlot slot) {
    Material baseMaterial = tier.getMaterial(slot);
    GuiItem guiItem = configuredItem.cloneItem();
    guiItem.setMaterial(baseMaterial);

    TrimPattern activePattern = profile.getCosmeticsData().getPattern(tier, slot);
    TrimMaterial activeMaterial = profile.getCosmeticsData().getMaterial(tier, slot);

    List<String> lore =
        guiItem.getLore() == null ? new ArrayList<>() : new ArrayList<>(guiItem.getLore());

    // Get dynamic lore template from guis.yml
    List<String> lorTemplate = PracticeConfig.guis().getGuis().getCosmetics().getArmorPreviewLore();
    for (String line : lorTemplate) {
      String processedLine =
          line.replace("%tier%", tier.getDisplayName())
              .replace(
                  "%pattern%",
                  activePattern == null ? "<red>None" : "<aqua>" + formatDisplayName(activePattern))
              .replace(
                  "%material%",
                  activeMaterial == null
                      ? "<red>None"
                      : "<gold>" + formatDisplayName(activeMaterial));
      lore.add(processedLine);
    }
    guiItem.setLore(lore);

    ItemStack item = guiItem.get();
    if (slot != ArmorSlot.SHIELD) {
      applyTrimPreview(item, activePattern, activeMaterial);
    }
    return item;
  }

  private ItemStack buildTierToggleItem(ArmorTrimTier activeTier) {
    GuiItem guiItem = infoItem().cloneItem();
    guiItem.setMaterial(
        guiItem.getMaterial() == null ? Material.NETHER_STAR : guiItem.getMaterial());

    Player player = profile.getPlayer().getPlayer();
    final int totalPatternPermissions = CosmeticsPermissionManager.getRegisteredPatterns().size();
    final int totalMaterialPermissions = CosmeticsPermissionManager.getRegisteredMaterials().size();
    int playerPatternPermissions = 0;
    int playerMaterialPermissions = 0;

    if (player != null) {
      for (TrimPattern pattern : CosmeticsPermissionManager.getRegisteredPatterns()) {
        if (player.hasPermission("ap.cosmetics.armortrim.pattern." + getPermissionId(pattern))) {
          playerPatternPermissions++;
        }
      }

      for (TrimMaterial material : CosmeticsPermissionManager.getRegisteredMaterials()) {
        if (player.hasPermission("ap.cosmetics.armortrim.material." + getPermissionId(material))) {
          playerMaterialPermissions++;
        }
      }
    }

    guiItem.replace("%tier%", activeTier.getDisplayName());
    guiItem.replace("%tier_permission%", activeTier.getPermissionNode());
    guiItem.replace("%pattern_unlocked%", String.valueOf(playerPatternPermissions));
    guiItem.replace("%pattern_total%", String.valueOf(totalPatternPermissions));
    guiItem.replace("%material_unlocked%", String.valueOf(playerMaterialPermissions));
    guiItem.replace("%material_total%", String.valueOf(totalMaterialPermissions));
    guiItem.setName("<aqua>Armor Tier: <yellow>" + activeTier.getDisplayName());

    List<String> lore =
        guiItem.getLore() == null ? new ArrayList<>() : new ArrayList<>(guiItem.getLore());

    // Get dynamic lore template from guis.yml
    List<String> loreTemplate = PracticeConfig.guis().getGuis().getCosmetics().getTierToggleLore();
    for (String line : loreTemplate) {
      String processedLine = line.replace("%tier_permission%", activeTier.getPermissionNode());
      lore.add(processedLine);
    }
    guiItem.setLore(lore);
    return guiItem.get();
  }

  private ItemStack buildApplyToAllTiersItem() {
    GuiItem guiItem = applyAllItem().cloneItem();
    guiItem.setMaterial(
        guiItem.getMaterial() == null ? Material.SMITHING_TABLE : guiItem.getMaterial());

    if (guiItem.getName() == null || guiItem.getName().isEmpty()) {
      guiItem.setName("<green>Apply Current Tier To All Tiers");
    }

    if (guiItem.getLore() == null || guiItem.getLore().isEmpty()) {
      List<String> lore = new ArrayList<>();
      lore.add("<gray>Copies the active tier trim setup");
      lore.add("<gray>to every armor tier.");
      lore.add("");
      lore.add("<yellow>Helmet -> Helmet");
      lore.add("<yellow>Chestplate -> Chestplate");
      lore.add("<yellow>Leggings -> Leggings");
      lore.add("<yellow>Boots -> Boots");
      lore.add("");
      lore.add("<green>Click to apply");
      guiItem.setLore(lore);
    }

    return guiItem.get();
  }

  private void handleTierToggleClick(Player player, boolean forward) {
    ArmorTrimTier currentTier = profile.getCosmeticsData().getActiveTier();
    ArmorTrimTier nextTier = forward ? currentTier.next() : previousTier(currentTier);

    for (int i = 0; i < ArmorTrimTier.values().length; i++) {
      if (player.hasPermission(nextTier.getPermissionNode())) {
        profile.getCosmeticsData().setActiveTier(nextTier);
        profile.save();
        update(true);
        return;
      }

      nextTier = forward ? nextTier.next() : previousTier(nextTier);
    }

    String noPermMessage =
        PracticeConfig.guis().getGuis().getCosmetics().getNoTierPermissionMessage();
    Common.sendMmMessage(player, noPermMessage);
  }

  private void applyActiveTierToAllTiers(Player player) {
    ArmorTrimTier sourceTier = profile.getCosmeticsData().getActiveTier();

    if (!player.hasPermission(sourceTier.getPermissionNode())) {
      String deniedMessage =
          PracticeConfig.guis().getGuis().getCosmetics().getPermissionDeniedMessage();
      Common.sendMmMessage(player, deniedMessage);
      return;
    }

    for (ArmorTrimTier targetTier : ArmorTrimTier.values()) {
      for (ArmorSlot slot : ARMOR_TYPE_SLOTS) {
        TrimPattern sourcePattern = profile.getCosmeticsData().getPattern(sourceTier, slot);
        TrimMaterial sourceMaterial = profile.getCosmeticsData().getMaterial(sourceTier, slot);

        profile.getCosmeticsData().setPattern(targetTier, slot, sourcePattern);
        profile.getCosmeticsData().setMaterial(targetTier, slot, sourceMaterial);
      }
    }

    profile.save();
    update(true);

    String successMessage =
        PracticeConfig.guis().getGuis().getCosmetics().getApplyAllTiersSuccessMessage();
    Common.sendMmMessage(player, successMessage);
  }

  private ArmorTrimTier previousTier(ArmorTrimTier tier) {
    ArmorTrimTier[] tiers = ArmorTrimTier.values();
    int index = tier.ordinal() - 1;
    if (index < 0) {
      index = tiers.length - 1;
    }
    return tiers[index];
  }

  private static void applyTrimPreview(ItemStack item, TrimPattern pattern, TrimMaterial material) {
    if (item == null || pattern == null || material == null) {
      return;
    }

    ItemMeta itemMeta = item.getItemMeta();
    if (!(itemMeta instanceof ArmorMeta armorMeta)) {
      return;
    }

    armorMeta.setTrim(new ArmorTrim(material, pattern));
    item.setItemMeta(armorMeta);
  }

  private static String getPermissionId(Object trimValue) {
    if (trimValue instanceof TrimPattern trimPattern) {
      return CosmeticsPermissionManager.getTrimId(trimPattern);
    }

    if (trimValue instanceof TrimMaterial trimMaterial) {
      return CosmeticsPermissionManager.getTrimId(trimMaterial);
    }

    return "unknown";
  }

  private static String formatDisplayName(Object trimValue) {
    return StringUtil.getNormalizedName(getPermissionId(trimValue));
  }

  private void openArmorSubGui(Player player, ArmorSlot armorSlot) {
    new ArmorPieceHubGui(profile, armorSlot, this).open(player);
  }
}

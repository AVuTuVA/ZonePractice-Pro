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
import java.util.Locale;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class PatternSelectionGui extends Gui {

  private static final ItemStack FILLER_ITEM =
      BackendUtil.getGuiItem(PracticeConfig.guis().getGeneralFillerItem()).get();
  private static final ItemStack BACK_TO_ITEM =
      BackendUtil.getGuiItem(PracticeConfig.guis().getGuis().getCosmetics().getIcons().getBackTo())
          .get();

  private static final int INVENTORY_ROWS =
      PracticeConfig.guis().getGuis().getCosmetics().getPatternSelection().getInventoryRows();
  private static final int BACK_SLOT =
      PracticeConfig.guis().getGuis().getCosmetics().getPatternSelection().getBackSlot();
  private static final int START_SLOT =
      PracticeConfig.guis().getGuis().getCosmetics().getPatternSelection().getStartSlot();

  private final Profile profile;
  private final ArmorSlot armorSlot;
  private final Gui backToGui;
  private final Map<Integer, TrimPattern> patternBySlot = new HashMap<>();

  public PatternSelectionGui(Profile profile, ArmorSlot armorSlot, Gui backToGui) {
    super(GuiType.Cosmetics_Pattern_Selection);
    this.profile = profile;
    this.armorSlot = armorSlot;
    this.backToGui = backToGui;
    String title =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getPatternSelection()
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
    patternBySlot.clear();

    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, FILLER_ITEM);
    }

    inventory.setItem(
        BACK_SLOT, BACK_TO_ITEM == null ? new ItemStack(Material.ARROW) : BACK_TO_ITEM);

    ArmorTrimTier tier = profile.getCosmeticsData().getActiveTier();
    int slot = START_SLOT;
    for (TrimPattern pattern : CosmeticsPermissionManager.getRegisteredPatterns()) {
      if (slot >= inventory.getSize()) {
        break;
      }

      while (slot < inventory.getSize() && (slot % 9 == 0 || slot % 9 == 8)) {
        slot++;
      }

      if (slot >= inventory.getSize()) {
        break;
      }

      patternBySlot.put(slot, pattern);
      inventory.setItem(slot, buildPatternItem(profile.getPlayer().getPlayer(), tier, pattern));
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

    TrimPattern pattern = patternBySlot.get(slot);
    if (pattern == null) {
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
        "ap.cosmetics.armortrim.pattern." + CosmeticsPermissionManager.getTrimId(pattern);
    if (!player.hasPermission(permissionNode)) {
      String permDeniedMessage =
          PracticeConfig.guis().getGuis().getCosmetics().getPatternPermissionDeniedMessage();
      Common.sendMmMessage(player, permDeniedMessage);
      return;
    }

    profile.getCosmeticsData().setPattern(tier, armorSlot, pattern);
    profile.save();
    update(true);
  }

  private ItemStack buildPatternItem(Player player, ArmorTrimTier tier, TrimPattern pattern) {
    String patternId = CosmeticsPermissionManager.getTrimId(pattern);
    String permissionNode = "ap.cosmetics.armortrim.pattern." + patternId;
    boolean hasPermission = player != null && player.hasPermission(permissionNode);

    TrimPattern activePattern = profile.getCosmeticsData().getPattern(tier, armorSlot);
    boolean active =
        activePattern != null
            && CosmeticsPermissionManager.getTrimId(activePattern).equalsIgnoreCase(patternId);

    Material templateMaterial =
        resolveMaterial(patternId.toUpperCase(Locale.ROOT) + "_ARMOR_TRIM_SMITHING_TEMPLATE");
    if (templateMaterial == null) {
      templateMaterial = resolveMaterial(patternId.toUpperCase(Locale.ROOT) + "_SMITHING_TEMPLATE");
    }
    GuiItem item = new GuiItem(templateMaterial == null ? Material.PAPER : templateMaterial);

    String itemName =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getPatternSelection()
            .getPatternItem()
            .getName()
            .replace("%pattern_name%", StringUtil.getNormalizedName(patternId));
    item.setName(itemName);

    List<String> configLore =
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getPatternSelection()
            .getPatternItem()
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

  private Material resolveMaterial(String materialName) {
    try {
      return Material.valueOf(materialName);
    } catch (IllegalArgumentException ignored) {
      return null;
    }
  }
}

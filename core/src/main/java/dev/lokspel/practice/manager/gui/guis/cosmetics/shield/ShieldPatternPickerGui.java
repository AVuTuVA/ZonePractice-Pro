package dev.lokspel.practice.manager.gui.guis.cosmetics.shield;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.cosmetics.shield.ShieldLayout;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Shows ALL available PatternType values with live banner previews. Paginated: 28 patterns per page
 * (4 rows × 7 cols, slots 10-16, 19-25, 28-34, 37-43). Bottom row: prev page (slot 45), back (slot
 * 49), next page (slot 53).
 */
public class ShieldPatternPickerGui extends Gui {

  private static final int ROWS = 6;
  private static final int PREV_SLOT = 45;
  private static final int BACK_SLOT = 49;
  private static final int NEXT_SLOT = 53;
  private static final int[] PATTERN_SLOTS = {
    10, 11, 12, 13, 14, 15, 16,
    19, 20, 21, 22, 23, 24, 25,
    28, 29, 30, 31, 32, 33, 34,
    37, 38, 39, 40, 41, 42, 43
  };
  private static final int PER_PAGE = PATTERN_SLOTS.length; // 28

  private static final ItemStack FILLER =
      BackendUtil.getGuiItem(PracticeConfig.guis().getGeneralFillerItem()).get();
  private static final ItemStack BACK =
      BackendUtil.getGuiItem(PracticeConfig.guis().getGuis().getCosmetics().getIcons().getBackTo())
          .get();

  // All banner patterns discovered from the registry (sorted for stable paging).
  private static final List<PatternType> ALL_PATTERNS = loadAllPatterns();

  private final Profile profile;
  private final int layoutIndex;
  private final DyeColor chosenColor; // colour picked in the previous step

  /** -1 = editing base (should not happen here). >=0 = layer index (or size = new layer). */
  private final int layerIndex;

  private final Gui backToGui; // editor GUI to return to

  private int page = 0;

  public ShieldPatternPickerGui(
      Profile profile, int layoutIndex, DyeColor chosenColor, int layerIndex, Gui backToGui) {
    super(GuiType.Cosmetics_Shield_PatternPicker);
    this.profile = profile;
    this.layoutIndex = layoutIndex;
    this.chosenColor = chosenColor;
    this.layerIndex = layerIndex;
    this.backToGui = backToGui;

    String title =
        PracticeConfig.guis().getGuis().getCosmetics().getShield().getPatternPicker().getTitle();
    this.gui.put(1, InventoryUtil.createInventory(title, ROWS));
    build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    Inventory inv = gui.get(1);
    inv.clear();
    for (int i = 0; i < inv.getSize(); i++) {
      inv.setItem(i, FILLER);
    }

    int totalPages = (int) Math.ceil((double) ALL_PATTERNS.size() / PER_PAGE);
    int startIndex = page * PER_PAGE;

    for (int i = 0; i < PATTERN_SLOTS.length; i++) {
      int ptIndex = startIndex + i;
      if (ptIndex >= ALL_PATTERNS.size()) {
        break;
      }
      inv.setItem(PATTERN_SLOTS[i], buildPatternItem(ALL_PATTERNS.get(ptIndex)));
    }

    // Navigation
    if (page > 0) {
      GuiItem prev =
          new GuiItem(
              "<yellow>Previous Page <dark_gray>(" + page + "/" + totalPages + ")", Material.ARROW);
      inv.setItem(PREV_SLOT, prev.get());
    }
    if (page < totalPages - 1) {
      GuiItem next =
          new GuiItem(
              "<yellow>Next Page <dark_gray>(" + (page + 2) + "/" + totalPages + ")",
              Material.ARROW);
      inv.setItem(NEXT_SLOT, next.get());
    }

    inv.setItem(BACK_SLOT, BACK != null ? BACK : new ItemStack(Material.ARROW));
    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);
    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();

    if (slot == BACK_SLOT) {
      // Go back to color picker with current preselected color
      new ShieldColorPickerGui(profile, layoutIndex, chosenColor, layerIndex, backToGui)
          .open(player);
      return;
    }

    if (slot == PREV_SLOT && page > 0) {
      page--;
      update(true);
      return;
    }
    if (slot == NEXT_SLOT && page < (int) Math.ceil((double) ALL_PATTERNS.size() / PER_PAGE) - 1) {
      page++;
      update(true);
      return;
    }

    // Pattern slot clicked
    for (int i = 0; i < PATTERN_SLOTS.length; i++) {
      if (slot == PATTERN_SLOTS[i]) {
        int ptIndex = page * PER_PAGE + i;
        if (ptIndex < ALL_PATTERNS.size()) {
          handlePatternPick(player, ALL_PATTERNS.get(ptIndex));
        }
        return;
      }
    }
  }

  private void handlePatternPick(Player player, PatternType pattern) {
    ShieldLayout layout = getLayout();
    if (layout == null) {
      return;
    }

    List<ShieldLayout.PatternLayer> layers = layout.getLayers();

    if (layerIndex < layers.size()) {
      // Edit existing layer
      layers.set(layerIndex, new ShieldLayout.PatternLayer(chosenColor, pattern));
    } else {
      // Add new layer
      if (!layout.addLayer(chosenColor, pattern)) {
        Common.sendMmMessage(
            player,
            PracticeConfig.guis()
                .getGuis()
                .getCosmetics()
                .getShield()
                .getEditor()
                .getMaxLayers()
                .getMessage());
        return;
      }
    }

    profile.save();

    // Apply live if this layout is active
    if (profile.getCosmeticsData().getActiveShieldLayoutIndex() == layoutIndex) {
      ShieldCosmeticsUtil.applyShieldToPlayer(player);
    }

    Common.sendMmMessage(
        player,
        PracticeConfig.guis()
            .getGuis()
            .getCosmetics()
            .getShield()
            .getPatternPicker()
            .getAppliedMessage());

    // Go back to editor
    backToGui.update(true);
    backToGui.open(player);
  }

  // Item builder
  private ItemStack buildPatternItem(PatternType pattern) {
    // Material already encodes the base colour (e.g. RED_BANNER for RED).
    // In 1.21.1 BannerMeta no longer has setBaseColor(); the colour is the Material.
    Material bannerMat = ShieldEditorGui.dyeToBanner(chosenColor);
    GuiItem item = new GuiItem(bannerMat);
    item.setName("<white>" + getPatternDisplayName(pattern));

    ItemStack stack = item.get();
    // Apply the pattern using a contrasting colour so it is visible
    DyeColor contrast =
        (chosenColor == DyeColor.WHITE || chosenColor == DyeColor.LIGHT_GRAY)
            ? DyeColor.BLACK
            : DyeColor.WHITE;
    if (stack.getItemMeta() instanceof BannerMeta bm) {
      bm.addPattern(new Pattern(contrast, pattern));
      bm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
      List<net.kyori.adventure.text.Component> lore = new ArrayList<>();
      lore.add(tc("<gray>Color: <white>" + fmt(chosenColor.name())));
      lore.add(tc("<yellow>Click to apply."));
      bm.lore(lore);
      stack.setItemMeta(bm);
    } else if (stack.hasItemMeta()) {
      ItemMeta meta = stack.getItemMeta();
      List<net.kyori.adventure.text.Component> lore = new ArrayList<>();
      lore.add(tc("<gray>Color: <white>" + fmt(chosenColor.name())));
      lore.add(tc("<yellow>Click to apply."));
      meta.lore(lore);
      stack.setItemMeta(meta);
    }
    return stack;
  }

  private ShieldLayout getLayout() {
    List<ShieldLayout> layouts = profile.getCosmeticsData().getShieldLayouts();
    if (layoutIndex < 0 || layoutIndex >= layouts.size()) {
      return null;
    }
    return layouts.get(layoutIndex);
  }

  private static net.kyori.adventure.text.Component tc(String legacy) {
    return Common.deserializeMiniMessage(legacy);
  }

  private static String fmt(String raw) {
    String lower = raw.replace('_', ' ').toLowerCase();
    return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
  }

  private static List<PatternType> loadAllPatterns() {
    var registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.BANNER_PATTERN);
    return registry.stream()
        .sorted(
            Comparator.comparing(
                pattern -> {
                  NamespacedKey key = registry.getKey(pattern);
                  return key == null ? "" : key.toString();
                }))
        .toList();
  }

  private static String getPatternDisplayName(PatternType pattern) {
    if (pattern == null) {
      return "Unknown";
    }

    NamespacedKey key =
        RegistryAccess.registryAccess().getRegistry(RegistryKey.BANNER_PATTERN).getKey(pattern);

    if (key != null) {
      return fmt(key.getKey());
    }

    return fmt(String.valueOf(pattern));
  }
}

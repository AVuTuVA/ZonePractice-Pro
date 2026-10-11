package dev.lokspel.practice.manager.profile.cosmetics.armortrim;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.gui.Cosmetics;
import java.util.Locale;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Material;

/** Represents the armor base tier used for cosmetics preview and selection. */
public enum ArmorTrimTier {
  LEATHER(
      "leather",
      "Leather",
      Material.LEATHER_HELMET,
      Material.LEATHER_CHESTPLATE,
      Material.LEATHER_LEGGINGS,
      Material.LEATHER_BOOTS),
  GOLD(
      "gold",
      "Gold",
      Material.GOLDEN_HELMET,
      Material.GOLDEN_CHESTPLATE,
      Material.GOLDEN_LEGGINGS,
      Material.GOLDEN_BOOTS),
  IRON(
      "iron",
      "Iron",
      Material.IRON_HELMET,
      Material.IRON_CHESTPLATE,
      Material.IRON_LEGGINGS,
      Material.IRON_BOOTS),
  DIAMOND(
      "diamond",
      "Diamond",
      Material.DIAMOND_HELMET,
      Material.DIAMOND_CHESTPLATE,
      Material.DIAMOND_LEGGINGS,
      Material.DIAMOND_BOOTS),
  NETHERITE(
      "netherite",
      "Netherite",
      Material.NETHERITE_HELMET,
      Material.NETHERITE_CHESTPLATE,
      Material.NETHERITE_LEGGINGS,
      Material.NETHERITE_BOOTS);

  @Getter private final String id;
  private final String defaultDisplayName;
  private final Material defaultHelmetMaterial;
  private final Material defaultChestplateMaterial;
  private final Material defaultLeggingsMaterial;
  private final Material defaultBootsMaterial;

  ArmorTrimTier(
      String id,
      String defaultDisplayName,
      Material defaultHelmetMaterial,
      Material defaultChestplateMaterial,
      Material defaultLeggingsMaterial,
      Material defaultBootsMaterial) {
    this.id = id;
    this.defaultDisplayName = defaultDisplayName;
    this.defaultHelmetMaterial = defaultHelmetMaterial;
    this.defaultChestplateMaterial = defaultChestplateMaterial;
    this.defaultLeggingsMaterial = defaultLeggingsMaterial;
    this.defaultBootsMaterial = defaultBootsMaterial;
  }

  public String getDisplayName() {
    Cosmetics.ArmorTier config = config();
    String configValue = config != null ? config.getDisplayName() : null;
    return configValue != null && !configValue.isBlank() ? configValue : defaultDisplayName;
  }

  public String getPermissionNode() {
    return "ap.cosmetics.armortrim.base." + id;
  }

  public Material getMaterial(ArmorSlot slot) {
    if (slot == null) {
      return Material.AIR;
    }

    Cosmetics.ArmorTier config = config();
    String materialName = config != null ? getConfiguredMaterial(config, slot) : null;

    if (materialName != null && !materialName.isBlank()) {
      try {
        return Material.valueOf(materialName.toUpperCase(Locale.ROOT));
      } catch (IllegalArgumentException ignored) {
        // Fall through to default
      }
    }

    return switch (slot) {
      case HELMET -> defaultHelmetMaterial;
      case CHESTPLATE -> defaultChestplateMaterial;
      case LEGGINGS -> defaultLeggingsMaterial;
      case BOOTS -> defaultBootsMaterial;
      case SHIELD -> Material.SHIELD;
    };
  }

  /** The GUIS.COSMETICS.ARMOR-TIERS entry of this tier, or null when it is not configured. */
  private Cosmetics.ArmorTier config() {
    Map<String, Cosmetics.ArmorTier> tiers =
        PracticeConfig.guis().getGuis().getCosmetics().getArmorTiers();
    return tiers != null ? tiers.get(this.name()) : null;
  }

  private static String getConfiguredMaterial(Cosmetics.ArmorTier config, ArmorSlot slot) {
    return switch (slot) {
      case HELMET -> config.getHelmetMaterial();
      case CHESTPLATE -> config.getChestplateMaterial();
      case LEGGINGS -> config.getLeggingsMaterial();
      case BOOTS -> config.getBootsMaterial();
      // The tiers have no shield material; the shield always uses the default.
      case SHIELD -> null;
    };
  }

  public ArmorTrimTier next() {
    ArmorTrimTier[] values = values();
    return values[(this.ordinal() + 1) % values.length];
  }

  public static ArmorTrimTier fromId(String id) {
    if (id == null || id.isBlank()) {
      return LEATHER;
    }

    String normalized = id.toLowerCase(Locale.ROOT);
    for (ArmorTrimTier tier : values()) {
      if (tier.id.equals(normalized)) {
        return tier;
      }
    }

    return LEATHER;
  }
}

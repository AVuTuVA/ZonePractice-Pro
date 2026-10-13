package dev.lokspel.practice.manager.profile.cosmetics.shield;

import dev.lokspel.practice.manager.gui.guis.cosmetics.shield.ShieldCosmeticsUtil;
import dev.lokspel.practice.util.Common;
import java.util.Locale;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public record ShieldPreset(String id, String name, ShieldLayout layout) {

  public static ShieldPreset fromConfig(String key, ConfigurationSection entry) {
    if (entry == null || !entry.getBoolean("ENABLED", true)) {
      return null;
    }

    String id = key.toLowerCase(Locale.ROOT);
    String baseColor = entry.getString("BASE-COLOR", "WHITE").toUpperCase(Locale.ROOT);
    StringBuilder serialized = new StringBuilder(id).append("|").append(baseColor);
    for (String layer : entry.getStringList("LAYERS")) {
      String[] parts = layer.split(":", 2);
      if (parts.length != 2) {
        continue;
      }
      serialized
          .append("|")
          .append(parts[0].trim().toUpperCase(Locale.ROOT))
          .append(":")
          .append(parts[1].trim());
    }

    ShieldLayout layout = ShieldLayout.deserialise(serialized.toString());
    if (layout == null) {
      Common.sendConsoleMmMessage("<red>Shield preset '" + key + "' is invalid.");
      return null;
    }

    return new ShieldPreset(id, entry.getString("NAME", key), layout);
  }

  public ItemStack createItem() {
    ItemStack item = new ItemStack(Material.SHIELD);
    ShieldCosmeticsUtil.applyLayoutToItem(item, layout);
    return item;
  }
}

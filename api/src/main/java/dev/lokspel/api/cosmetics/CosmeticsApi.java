package dev.lokspel.api.cosmetics;

import java.util.List;
import org.bukkit.entity.Player;

/** API section for managing practice cosmetics and custom categories. */
public interface CosmeticsApi {

  /**
   * Register a custom cosmetic category in the cosmetics hub menu.
   *
   * @param category The cosmetic category to register
   */
  void registerCategory(CosmeticCategory category);

  /**
   * Unregister a custom cosmetic category by its identifier.
   *
   * @param categoryId The ID of the category to unregister
   * @return true if the category was removed, false otherwise
   */
  boolean unregisterCategory(String categoryId);

  /**
   * Unregister a custom cosmetic category.
   *
   * @param category The category to unregister
   * @return true if the category was removed, false otherwise
   */
  default boolean unregisterCategory(CosmeticCategory category) {
    return category != null && unregisterCategory(category.getId());
  }

  /**
   * Get all registered custom cosmetic categories.
   *
   * @return List of registered categories
   */
  List<CosmeticCategory> getCategories();

  /**
   * Get a registered cosmetic category by its identifier.
   *
   * @param categoryId The category ID
   * @return The cosmetic category, or null if not found
   */
  CosmeticCategory getCategory(String categoryId);

  /**
   * Open the cosmetics menu for the specified player.
   *
   * @param player The player
   */
  void openMenu(Player player);
}

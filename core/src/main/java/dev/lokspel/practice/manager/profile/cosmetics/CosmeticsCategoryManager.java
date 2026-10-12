package dev.lokspel.practice.manager.profile.cosmetics;

import dev.lokspel.api.cosmetics.CosmeticCategory;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;

public class CosmeticsCategoryManager {

  @Getter private static final CosmeticsCategoryManager instance = new CosmeticsCategoryManager();

  private final Map<String, CosmeticCategory> categories = new LinkedHashMap<>();

  private CosmeticsCategoryManager() {}

  public synchronized void registerCategory(CosmeticCategory category) {
    Objects.requireNonNull(category, "Category cannot be null");
    Objects.requireNonNull(category.getId(), "Category ID cannot be null");
    categories.put(category.getId().toLowerCase(), category);
  }

  public synchronized boolean unregisterCategory(String id) {
    if (id == null) {
      return false;
    }
    return categories.remove(id.toLowerCase()) != null;
  }

  public boolean unregisterCategory(CosmeticCategory category) {
    return category != null && unregisterCategory(category.getId());
  }

  public synchronized CosmeticCategory getCategory(String id) {
    if (id == null) {
      return null;
    }
    return categories.get(id.toLowerCase());
  }

  public synchronized List<CosmeticCategory> getCategories() {
    return List.copyOf(categories.values());
  }

  public synchronized void clear() {
    categories.clear();
  }
}

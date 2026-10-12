package dev.lokspel.practice.manager.profile.cosmetics;

import dev.lokspel.api.cosmetics.CosmeticCategory;
import dev.lokspel.api.cosmetics.CosmeticsApi;
import dev.lokspel.practice.manager.gui.guis.cosmetics.CosmeticsHubGui;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import org.bukkit.entity.Player;

public class CosmeticsCategoryManager implements CosmeticsApi {

  @Getter private static final CosmeticsCategoryManager instance = new CosmeticsCategoryManager();

  private final Map<String, CosmeticCategory> categories = new LinkedHashMap<>();

  private CosmeticsCategoryManager() {}

  @Override
  public synchronized void registerCategory(CosmeticCategory category) {
    Objects.requireNonNull(category, "Category cannot be null");
    Objects.requireNonNull(category.getId(), "Category ID cannot be null");
    categories.put(category.getId().toLowerCase(), category);
  }

  @Override
  public synchronized boolean unregisterCategory(String id) {
    if (id == null) {
      return false;
    }
    return categories.remove(id.toLowerCase()) != null;
  }

  @Override
  public boolean unregisterCategory(CosmeticCategory category) {
    return category != null && unregisterCategory(category.getId());
  }

  @Override
  public synchronized CosmeticCategory getCategory(String id) {
    if (id == null) {
      return null;
    }
    return categories.get(id.toLowerCase());
  }

  @Override
  public synchronized List<CosmeticCategory> getCategories() {
    return List.copyOf(categories.values());
  }

  @Override
  public void openMenu(Player player) {
    if (player == null || !player.isOnline()) {
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return;
    }

    new CosmeticsHubGui(profile).open(player);
  }

  public synchronized void clear() {
    categories.clear();
  }
}

package dev.lokspel.api.cosmetics;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a custom cosmetic category that can be registered by addons and displayed in the main
 * cosmetics hub menu.
 */
public interface CosmeticCategory {

  /**
   * Unique identifier for this category (e.g. "trails", "auras", "balloons").
   *
   * @return The unique category identifier
   */
  String getId();

  /**
   * Get the display icon for this category in the cosmetics menu for the specified player.
   *
   * @param player The player viewing the menu
   * @return The display {@link ItemStack}
   */
  ItemStack getIcon(Player player);

  /**
   * Called when a player clicks this category in the cosmetics menu.
   *
   * @param player The player who clicked
   * @param event The inventory click event
   */
  default void onClick(Player player, InventoryClickEvent event) {
    onClick(player);
  }

  /**
   * Called when a player clicks this category in the cosmetics menu.
   *
   * @param player The player who clicked
   */
  default void onClick(Player player) {}

  /**
   * Preferred inventory slot (0-53) in the cosmetics menu, or -1 for automatic placement.
   *
   * @return The slot index, or -1 for automatic placement
   */
  default int getSlot() {
    return -1;
  }

  /**
   * Determines whether this category is visible in the cosmetics menu for the specified player.
   *
   * @param player The player viewing the menu
   * @return true if visible, false to hide
   */
  default boolean isVisible(Player player) {
    return true;
  }

  /**
   * Creates a new builder for constructing a {@link CosmeticCategory}.
   *
   * @param id The unique category identifier
   * @return A category builder
   */
  static Builder builder(String id) {
    return new Builder(id);
  }

  /** Builder for constructing {@link CosmeticCategory} instances fluently. */
  class Builder {

    private final String id;
    private Function<Player, ItemStack> iconFunction;
    private BiConsumer<Player, InventoryClickEvent> clickHandler;
    private int slot = -1;
    private Predicate<Player> visibility = player -> true;

    private Builder(String id) {
      this.id = Objects.requireNonNull(id, "id cannot be null");
    }

    /**
     * Sets a static display icon for all players.
     *
     * @param icon The item icon
     * @return This builder
     */
    public Builder icon(ItemStack icon) {
      Objects.requireNonNull(icon, "icon cannot be null");
      this.iconFunction = player -> icon.clone();
      return this;
    }

    /**
     * Sets a dynamic icon function that creates an icon for the viewing player.
     *
     * @param iconFunction Function mapping a player to an ItemStack icon
     * @return This builder
     */
    public Builder icon(Function<Player, ItemStack> iconFunction) {
      this.iconFunction = Objects.requireNonNull(iconFunction, "iconFunction cannot be null");
      return this;
    }

    /**
     * Sets the click action executed when a player clicks this category.
     *
     * @param onClick Consumer taking the clicking player
     * @return This builder
     */
    public Builder onClick(Consumer<Player> onClick) {
      Objects.requireNonNull(onClick, "onClick cannot be null");
      this.clickHandler = (player, event) -> onClick.accept(player);
      return this;
    }

    /**
     * Sets the click action executed when a player clicks this category.
     *
     * @param onClick BiConsumer taking the clicking player and the {@link InventoryClickEvent}
     * @return This builder
     */
    public Builder onClick(BiConsumer<Player, InventoryClickEvent> onClick) {
      this.clickHandler = Objects.requireNonNull(onClick, "onClick cannot be null");
      return this;
    }

    /**
     * Sets the preferred slot for this category, or -1 for automatic placement.
     *
     * @param slot The preferred slot index
     * @return This builder
     */
    public Builder slot(int slot) {
      this.slot = slot;
      return this;
    }

    /**
     * Sets the visibility predicate for this category.
     *
     * @param visibility Predicate determining if the category is visible to a player
     * @return This builder
     */
    public Builder visible(Predicate<Player> visibility) {
      this.visibility = Objects.requireNonNull(visibility, "visibility cannot be null");
      return this;
    }

    /**
     * Builds the {@link CosmeticCategory}.
     *
     * @return The built CosmeticCategory
     */
    public CosmeticCategory build() {
      Objects.requireNonNull(iconFunction, "icon or iconFunction must be provided");
      return new CosmeticCategory() {
        @Override
        public String getId() {
          return id;
        }

        @Override
        public ItemStack getIcon(Player player) {
          return iconFunction.apply(player);
        }

        @Override
        public void onClick(Player player, InventoryClickEvent event) {
          if (clickHandler != null) {
            clickHandler.accept(player, event);
          }
        }

        @Override
        public int getSlot() {
          return slot;
        }

        @Override
        public boolean isVisible(Player player) {
          return visibility.test(player);
        }
      };
    }
  }
}

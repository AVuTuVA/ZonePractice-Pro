package dev.lokspel.practice.config.playerkit;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokspel.practice.config.ItemConfig;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
public class Items {
  @JsonProperty("CATEGORY-GUI")
  private CategoryGui categoryGui = new CategoryGui();

  @JsonProperty("ITEMS-GUI")
  private ItemsGui itemsGui = new ItemsGui();

  @JsonProperty("ENCHANT-GUI")
  private EnchantGui enchantGui = new EnchantGui();

  @JsonProperty("CUSTOM-DURABILITY-GUI")
  private CustomDurabilityGui customDurabilityGui = new CustomDurabilityGui();

  @JsonProperty("CHANGE-COUNT-GUI")
  private ChangeCountGui changeCountGui = new ChangeCountGui();

  @JsonProperty("CUSTOM-COUNT-GUI")
  private CustomCountGui customCountGui = new CustomCountGui();

  @Getter
  public static class CategoryGui {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Item categories";

    @JsonProperty("SIZE")
    private int size = 6;

    @Comment({
      "Any key added here that isn't a built-in becomes a custom category.",
      "Add a matching entry under ITEMS-GUI.CATEGORIES with TITLE and ITEMS."
    })
    @JsonProperty("ICONS")
    private Map<String, CategoryIcon> icons = defaultIcons();

    private static Map<String, CategoryIcon> defaultIcons() {
      Map<String, CategoryIcon> values = new LinkedHashMap<>();
      values.put("BACK-TO", CategoryIcon.backTo());
      values.put("NONE", CategoryIcon.none());
      values.put("ARMOR", CategoryIcon.armor());
      values.put("WEAPONS-TOOLS", CategoryIcon.weaponsTools());
      values.put("BOWS-ARROWS", CategoryIcon.bowsArrows());
      values.put("POTIONS", CategoryIcon.potions());
      values.put("SHULKER-BOXES", CategoryIcon.shulkerBoxes());
      values.put("FOOD", CategoryIcon.food());
      values.put("BLOCKS", CategoryIcon.blocks());

      return values;
    }

    @Getter
    public static class CategoryIcon extends ItemConfig {
      @JsonProperty("SLOT")
      private Integer slot;

      static CategoryIcon backTo() {
        CategoryIcon value = new CategoryIcon();
        value.slot = 45;
        value.material("ARROW").name("<gray><bold>Back").lore("<gray>Click to go back");

        return value;
      }

      static CategoryIcon none() {
        CategoryIcon value = new CategoryIcon();
        value.slot = 18;
        value.material("GRAY_STAINED_GLASS_PANE").name("<aqua>None");

        return value;
      }

      static CategoryIcon armor() {
        CategoryIcon value = new CategoryIcon();
        value.slot = 19;
        value.material("DIAMOND_CHESTPLATE").name("<aqua>Armor");

        return value;
      }

      static CategoryIcon weaponsTools() {
        CategoryIcon value = new CategoryIcon();
        value.slot = 20;
        value.material("IRON_SWORD").name("<aqua>Weapons & Tools");

        return value;
      }

      static CategoryIcon bowsArrows() {
        CategoryIcon value = new CategoryIcon();
        value.slot = 21;
        value.material("BOW").name("<aqua>Bows & Arrows");

        return value;
      }

      static CategoryIcon potions() {
        CategoryIcon value = new CategoryIcon();
        value.slot = 22;
        value.material("POTION").name("<aqua>Potions");

        return value;
      }

      static CategoryIcon shulkerBoxes() {
        CategoryIcon value = new CategoryIcon();
        value.slot = 32;
        value.material("PURPLE_SHULKER_BOX").name("<aqua>Shulker Boxes");

        return value;
      }

      static CategoryIcon food() {
        CategoryIcon value = new CategoryIcon();
        value.slot = 23;
        value.material("COOKED_BEEF").name("<aqua>Food");

        return value;
      }

      static CategoryIcon blocks() {
        CategoryIcon value = new CategoryIcon();
        value.slot = 24;
        value.material("COBBLESTONE").name("<aqua>Blocks");

        return value;
      }
    }
  }

  @Getter
  public static class ItemsGui {
    @JsonProperty("SIZE")
    private int size = 6;

    @JsonProperty("OFFICIAL-ICONS")
    private OfficialIcons officialIcons = new OfficialIcons();

    @JsonProperty("CATEGORIES")
    private Map<String, ItemCategory> categories = defaultCategories();

    private static Map<String, ItemCategory> defaultCategories() {
      Map<String, ItemCategory> values = new LinkedHashMap<>();
      values.put("ARMOR", ItemCategory.armor());
      values.put("WEAPONS-TOOLS", ItemCategory.weaponsTools());
      values.put("BOWS-ARROWS", ItemCategory.bowsArrows());
      values.put("POTION", ItemCategory.potion());
      values.put("FOOD", ItemCategory.food());
      values.put("BLOCKS", ItemCategory.blocks());
      values.put("SHULKER-BOXES", ItemCategory.shulkerBoxes());

      return values;
    }

    @Getter
    public static class OfficialIcons {
      @JsonProperty("BACK-TO")
      private ItemConfig backTo =
          ItemConfig.of("ARROW").name("<gray><bold>Back").lore("<gray>Click to go back");

      @JsonProperty("NEXT-PAGE")
      private ItemConfig nextPage =
          ItemConfig.of("ARROW").name("<gray><bold>Next Page").lore("<gray>Click");

      @JsonProperty("PREVIOUS-PAGE")
      private ItemConfig previousPage =
          ItemConfig.of("ARROW").name("<gray><bold>Previous Page").lore("<gray>Click");
    }

    @Getter
    public static class ItemCategory {
      @JsonProperty("TITLE")
      private String title;

      @JsonProperty("ITEMS")
      private List<String> items;

      @JsonProperty("OFFICIAL-ICONS")
      private OfficialIcons officialIcons;

      @JsonProperty("DRINKABLE-POTIONS")
      private DrinkablePotions drinkablePotions;

      @JsonProperty("SPLASH-POTIONS")
      private SplashPotions splashPotions;

      static ItemCategory armor() {
        ItemCategory value = new ItemCategory();
        value.title = "<dark_gray>Armor";
        value.items =
            List.of(
                "LEATHER_HELMET",
                "GOLDEN_HELMET",
                "CHAINMAIL_HELMET",
                "IRON_HELMET",
                "DIAMOND_HELMET",
                "NETHERITE_HELMET",
                "TURTLE_HELMET",
                "",
                "",
                "LEATHER_CHESTPLATE",
                "GOLDEN_CHESTPLATE",
                "CHAINMAIL_CHESTPLATE",
                "IRON_CHESTPLATE",
                "DIAMOND_CHESTPLATE",
                "NETHERITE_CHESTPLATE",
                "ELYTRA",
                "",
                "",
                "LEATHER_LEGGINGS",
                "GOLDEN_LEGGINGS",
                "CHAINMAIL_LEGGINGS",
                "IRON_LEGGINGS",
                "DIAMOND_LEGGINGS",
                "NETHERITE_LEGGINGS",
                "",
                "",
                "",
                "LEATHER_BOOTS",
                "GOLDEN_BOOTS",
                "CHAINMAIL_BOOTS",
                "IRON_BOOTS",
                "DIAMOND_BOOTS",
                "NETHERITE_BOOTS");

        return value;
      }

      static ItemCategory weaponsTools() {
        ItemCategory value = new ItemCategory();
        value.title = "<dark_gray>Weapons and Tools";
        value.items =
            List.of(
                "WOODEN_SWORD",
                "GOLDEN_SWORD",
                "STONE_SWORD",
                "IRON_SWORD",
                "DIAMOND_SWORD",
                "NETHERITE_SWORD",
                "WOODEN_SPEAR",
                "GOLDEN_SPEAR",
                "STONE_SPEAR",
                "IRON_SPEAR",
                "DIAMOND_SPEAR",
                "NETHERITE_SPEAR",
                "MACE",
                "SHIELD",
                "TRIDENT",
                "TOTEM_OF_UNDYING",
                "WOODEN_AXE",
                "GOLDEN_AXE",
                "STONE_AXE",
                "IRON_AXE",
                "DIAMOND_AXE",
                "NETHERITE_AXE",
                "FLINT_AND_STEEL",
                "SHEARS",
                "FISHING_ROD",
                "WOODEN_PICKAXE",
                "GOLDEN_PICKAXE",
                "STONE_PICKAXE",
                "IRON_PICKAXE",
                "DIAMOND_PICKAXE",
                "NETHERITE_PICKAXE",
                "BUCKET",
                "WATER_BUCKET",
                "LAVA_BUCKET",
                "WOODEN_SHOVEL",
                "GOLDEN_SHOVEL",
                "STONE_SHOVEL",
                "IRON_SHOVEL",
                "DIAMOND_SHOVEL",
                "NETHERITE_SHOVEL",
                "MILK_BUCKET",
                "END_CRYSTAL",
                "ENDER_PEARL",
                "WOODEN_HOE",
                "GOLDEN_HOE",
                "STONE_HOE",
                "IRON_HOE",
                "DIAMOND_HOE",
                "WIND_CHARGE",
                "EXPERIENCE_BOTTLE",
                "SNOWBALL",
                "FIREWORK_ROCKET");

        return value;
      }

      static ItemCategory bowsArrows() {
        ItemCategory value = new ItemCategory();
        value.title = "<dark_gray>Bows and Arrows";
        value.items =
            List.of(
                "BOW",
                "CROSSBOW",
                "ARROW",
                "SPECTRAL_ARROW",
                "TIPPED_ARROW::NIGHT_VISION",
                "TIPPED_ARROW::LONG_NIGHT_VISION",
                "TIPPED_ARROW::INVISIBILITY",
                "TIPPED_ARROW::LONG_INVISIBILITY",
                "TIPPED_ARROW::LEAPING",
                "TIPPED_ARROW::LONG_LEAPING",
                "TIPPED_ARROW::STRONG_LEAPING",
                "TIPPED_ARROW::FIRE_RESISTANCE",
                "TIPPED_ARROW::LONG_FIRE_RESISTANCE",
                "TIPPED_ARROW::SWIFTNESS",
                "TIPPED_ARROW::LONG_SWIFTNESS",
                "TIPPED_ARROW::STRONG_SWIFTNESS",
                "TIPPED_ARROW::SLOWNESS",
                "TIPPED_ARROW::LONG_SLOWNESS",
                "TIPPED_ARROW::STRONG_SLOWNESS",
                "TIPPED_ARROW::WATER_BREATHING",
                "TIPPED_ARROW::LONG_WATER_BREATHING",
                "TIPPED_ARROW::INSTANT_HEAL",
                "TIPPED_ARROW::STRONG_HEALING",
                "TIPPED_ARROW::INSTANT_DAMAGE",
                "TIPPED_ARROW::STRONG_HARMING",
                "TIPPED_ARROW::POISON",
                "TIPPED_ARROW::LONG_POISON",
                "TIPPED_ARROW::STRONG_POISON",
                "TIPPED_ARROW::REGENERATION",
                "TIPPED_ARROW::LONG_REGENERATION",
                "TIPPED_ARROW::STRONG_REGENERATION",
                "TIPPED_ARROW::STRENGTH",
                "TIPPED_ARROW::LONG_STRENGTH",
                "TIPPED_ARROW::STRONG_STRENGTH",
                "TIPPED_ARROW::WEAKNESS",
                "TIPPED_ARROW::LONG_WEAKNESS",
                "TIPPED_ARROW::LUCK",
                "TIPPED_ARROW::TURTLE_MASTER",
                "TIPPED_ARROW::LONG_TURTLE_MASTER",
                "TIPPED_ARROW::STRONG_TURTLE_MASTER",
                "TIPPED_ARROW::SLOW_FALLING",
                "TIPPED_ARROW::LONG_SLOW_FALLING");

        return value;
      }

      static ItemCategory potion() {
        ItemCategory value = new ItemCategory();
        value.title = "<dark_gray>Potions";
        value.officialIcons = OfficialIcons.potion();
        value.drinkablePotions = DrinkablePotions.potion();
        value.splashPotions = SplashPotions.potion();

        return value;
      }

      static ItemCategory food() {
        ItemCategory value = new ItemCategory();
        value.title = "<dark_gray>Food";
        value.items =
            List.of(
                "APPLE",
                "MUSHROOM_STEW",
                "BREAD",
                "PORKCHOP",
                "COOKED_PORKCHOP",
                "GOLDEN_APPLE",
                "ENCHANTED_GOLDEN_APPLE",
                "COD",
                "SALMON",
                "TROPICAL_FISH",
                "PUFFERFISH",
                "COOKED_COD",
                "COOKED_SALMON",
                "CAKE",
                "COOKIE",
                "MELON_SLICE",
                "DRIED_KELP",
                "BEEF",
                "COOKED_BEEF",
                "CHICKEN",
                "COOKED_CHICKEN",
                "ROTTEN_FLESH",
                "SPIDER_EYE",
                "CARROT",
                "POTATO",
                "BAKED_POTATO",
                "GOLDEN_CARROT",
                "PUMPKIN_PIE",
                "RABBIT",
                "COOKED_RABBIT",
                "RABBIT_STEW",
                "MUTTON",
                "COOKED_MUTTON",
                "CHORUS_FRUIT",
                "BEETROOT",
                "BEETROOT_SOUP",
                "SUSPICIOUS_STEW",
                "SWEET_BERRIES",
                "GLOW_BERRIES",
                "HONEY_BOTTLE");

        return value;
      }

      static ItemCategory blocks() {
        ItemCategory value = new ItemCategory();
        value.title = "<dark_gray>Blocks";
        value.items =
            List.of(
                "OBSIDIAN",
                "ANVIL",
                "NETHERITE_BLOCK",
                "CRYING_OBSIDIAN",
                "ENCHANTING_TABLE",
                "ENDER_CHEST",
                "END_STONE",
                "COBBLESTONE",
                "COAL_BLOCK",
                "IRON_BLOCK",
                "GOLD_BLOCK",
                "DIAMOND_BLOCK",
                "EMERALD_BLOCK",
                "COPPER_BLOCK",
                "DEEPSLATE",
                "IRON_DOOR",
                "COBWEB",
                "OAK_PLANKS",
                "LAPIS_BLOCK",
                "OAK_DOOR",
                "POINTED_DRIPSTONE",
                "BLUE_ICE",
                "CRAFTING_TABLE",
                "BONE_BLOCK",
                "OAK_LOG",
                "GRAY_CONCRETE",
                "AMETHYST_BLOCK",
                "PUMPKIN",
                "MELON",
                "SHROOMLIGHT",
                "SANDSTONE",
                "WHITE_WOOL",
                "QUARTZ_BLOCK",
                "GRAVEL",
                "CLAY",
                "SPONGE",
                "PACKED_ICE",
                "MAGMA_BLOCK",
                "SOUL_SAND",
                "HAY_BLOCK",
                "DIRT",
                "SAND",
                "ICE",
                "TARGET",
                "SOUL_SOIL",
                "NETHERRACK",
                "GLASS",
                "GLOWSTONE",
                "SNOW_BLOCK",
                "SLIME_BLOCK",
                "HONEY_BLOCK",
                "SCAFFOLDING",
                "TNT",
                "REDSTONE_BLOCK",
                "NOTE_BLOCK",
                "PISTON",
                "STICKY_PISTON",
                "DISPENSER",
                "REPEATER",
                "COMPARATOR",
                "RAIL",
                "POWERED_RAIL",
                "ACTIVATOR_RAIL",
                "DETECTOR_RAIL",
                "TRIPWIRE_HOOK",
                "STRING",
                "MOSS_BLOCK",
                "STONE");

        return value;
      }

      static ItemCategory shulkerBoxes() {
        ItemCategory value = new ItemCategory();
        value.title = "<dark_gray>Shulker Boxes";

        return value;
      }

      @Getter
      public static class OfficialIcons {
        @JsonProperty("BACK-TO")
        private ItemConfig backTo;

        @JsonProperty("SWITCH-TO-SPLASH")
        private ItemConfig switchToSplash;

        @JsonProperty("SWITCH-TO-DRINKABLE")
        private ItemConfig switchToDrinkable;

        static OfficialIcons potion() {
          OfficialIcons value = new OfficialIcons();
          value.backTo =
              ItemConfig.of("ARROW").name("<gray><bold>Back").lore("<gray>Click to go back");
          value.switchToSplash = ItemConfig.of("SPLASH_POTION").name("<aqua>Show splash potions");
          value.switchToDrinkable = ItemConfig.of("POTION").name("<aqua>Show drinkable potions");

          return value;
        }
      }

      @Getter
      public static class DrinkablePotions {
        @JsonProperty("ITEMS")
        private List<String> items;

        static DrinkablePotions potion() {
          DrinkablePotions value = new DrinkablePotions();
          value.items =
              List.of(
                  "POTION::NIGHT_VISION",
                  "POTION::LONG_NIGHT_VISION",
                  "POTION::INVISIBILITY",
                  "POTION::LONG_INVISIBILITY",
                  "POTION::JUMP",
                  "POTION::LONG_LEAPING",
                  "POTION::STRONG_LEAPING",
                  "POTION::FIRE_RESISTANCE",
                  "POTION::LONG_FIRE_RESISTANCE",
                  "POTION::SPEED",
                  "POTION::LONG_SWIFTNESS",
                  "POTION::STRONG_SWIFTNESS",
                  "POTION::SLOWNESS",
                  "POTION::LONG_SLOWNESS",
                  "POTION::STRONG_SLOWNESS",
                  "POTION::WATER_BREATHING",
                  "POTION::LONG_WATER_BREATHING",
                  "POTION::INSTANT_HEAL",
                  "POTION::STRONG_HEALING",
                  "POTION::INSTANT_DAMAGE",
                  "POTION::STRONG_HARMING",
                  "POTION::POISON",
                  "POTION::LONG_POISON",
                  "POTION::STRONG_POISON",
                  "POTION::REGEN",
                  "POTION::LONG_REGENERATION",
                  "POTION::STRONG_REGENERATION",
                  "POTION::STRENGTH",
                  "POTION::LONG_STRENGTH",
                  "POTION::STRONG_STRENGTH",
                  "POTION::WEAKNESS",
                  "POTION::LONG_WEAKNESS",
                  "POTION::LUCK",
                  "POTION::TURTLE_MASTER",
                  "POTION::LONG_TURTLE_MASTER",
                  "POTION::STRONG_TURTLE_MASTER",
                  "POTION::SLOW_FALLING",
                  "POTION::LONG_SLOW_FALLING");

          return value;
        }
      }

      @Getter
      public static class SplashPotions {
        @JsonProperty("ITEMS")
        private List<String> items;

        static SplashPotions potion() {
          SplashPotions value = new SplashPotions();
          value.items =
              List.of(
                  "SPLASH_POTION::NIGHT_VISION",
                  "SPLASH_POTION::LONG_NIGHT_VISION",
                  "SPLASH_POTION::INVISIBILITY",
                  "SPLASH_POTION::LONG_INVISIBILITY",
                  "SPLASH_POTION::JUMP",
                  "SPLASH_POTION::LONG_LEAPING",
                  "SPLASH_POTION::STRONG_LEAPING",
                  "SPLASH_POTION::FIRE_RESISTANCE",
                  "SPLASH_POTION::LONG_FIRE_RESISTANCE",
                  "SPLASH_POTION::SPEED",
                  "SPLASH_POTION::LONG_SWIFTNESS",
                  "SPLASH_POTION::STRONG_SWIFTNESS",
                  "SPLASH_POTION::SLOWNESS",
                  "SPLASH_POTION::LONG_SLOWNESS",
                  "SPLASH_POTION::STRONG_SLOWNESS",
                  "SPLASH_POTION::WATER_BREATHING",
                  "SPLASH_POTION::LONG_WATER_BREATHING",
                  "SPLASH_POTION::INSTANT_HEAL",
                  "SPLASH_POTION::STRONG_HEALING",
                  "SPLASH_POTION::INSTANT_DAMAGE",
                  "SPLASH_POTION::STRONG_HARMING",
                  "SPLASH_POTION::POISON",
                  "SPLASH_POTION::LONG_POISON",
                  "SPLASH_POTION::STRONG_POISON",
                  "SPLASH_POTION::REGEN",
                  "SPLASH_POTION::LONG_REGENERATION",
                  "SPLASH_POTION::STRONG_REGENERATION",
                  "SPLASH_POTION::STRENGTH",
                  "SPLASH_POTION::LONG_STRENGTH",
                  "SPLASH_POTION::STRONG_STRENGTH",
                  "SPLASH_POTION::WEAKNESS",
                  "SPLASH_POTION::LONG_WEAKNESS",
                  "SPLASH_POTION::LUCK",
                  "SPLASH_POTION::TURTLE_MASTER",
                  "SPLASH_POTION::LONG_TURTLE_MASTER",
                  "SPLASH_POTION::STRONG_TURTLE_MASTER",
                  "SPLASH_POTION::SLOW_FALLING",
                  "SPLASH_POTION::LONG_SLOW_FALLING");

          return value;
        }
      }
    }
  }

  @Getter
  public static class EnchantGui {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Add enchantments";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("BACK-TO")
      private ItemConfig backTo =
          ItemConfig.of("ARROW").name("<gray><bold>Back").lore("<gray>Click to go back");

      @JsonProperty("MAKE-UNBREAKABLE")
      private ItemConfig makeUnbreakable =
          ItemConfig.of("STONE_BRICKS").name("<aqua>Make unbreakable");

      @JsonProperty("MAKE-BREAKABLE")
      private ItemConfig makeBreakable = ItemConfig.of("STONE_BRICKS").name("<aqua>Make breakable");

      @JsonProperty("CLEAR-ENCHANTS")
      private ItemConfig clearEnchants = ItemConfig.of("BARRIER").name("<aqua>Clear Enchants");

      @JsonProperty("CHANGE-DURABILITY")
      private ItemConfig changeDurability = ItemConfig.of("ANVIL").name("<aqua>Change Durability");

      @JsonProperty("SET-ENCHANTMENT-LEVEL")
      private ItemConfig setEnchantmentLevel =
          ItemConfig.of("SUGAR")
              .name("<light_purple>%enchantment% <gray>- <yellow>%level%")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Click here to set the level of",
                  "<gray>the <light_purple>%enchantment% <gray>enchantment.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("SET-ENCHANTMENT-LEVEL-PLACEHOLDER")
      private ItemConfig setEnchantmentLevelPlaceholder =
          ItemConfig.of("BLUE_STAINED_GLASS_PANE").name(" ");

      @JsonProperty("ENCHANTMENT-ICON")
      private ItemConfig enchantmentIcon =
          ItemConfig.of("ENCHANTED_BOOK")
              .name("<light_purple>%enchantment%")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Click here to select",
                  "<gray>the <light_purple>%enchantment% <gray>enchantment.",
                  "<dark_gray><st>------------------------");
    }
  }

  @Getter
  public static class CustomDurabilityGui {
    @JsonProperty("TITLE")
    private String title = "1-%max%";
  }

  @Getter
  public static class ChangeCountGui {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Change item count";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("BACK-TO")
      private ItemConfig backTo =
          ItemConfig.of("ARROW").name("<gray><bold>Back").lore("<gray>Click to go back");

      @JsonProperty("CUSTOM-COUNT")
      private ItemConfig customCount = ItemConfig.of("PAPER").name("<aqua>Custom Amount");
    }
  }

  @Getter
  public static class CustomCountGui {
    @JsonProperty("TITLE")
    private String title = "Enter a number 1-%max%";
  }
}

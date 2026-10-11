package dev.lokspel.practice.config.gui;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokspel.practice.config.ItemConfig;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
public class Cosmetics {
  @JsonProperty("MAIN-TITLE")
  private String mainTitle = "<dark_gray>Armor Cosmetics";

  @Comment("Error messages for permission denied")
  @JsonProperty("PERMISSION-DENIED-MESSAGE")
  private String permissionDeniedMessage =
      "<red>You do not have permission for the selected armor tier.";

  @JsonProperty("TIER-PERMISSION-DENIED-MESSAGE")
  private String tierPermissionDeniedMessage =
      "<red>You do not have permission for this armor tier.";

  @JsonProperty("PATTERN-PERMISSION-DENIED-MESSAGE")
  private String patternPermissionDeniedMessage =
      "<red>You do not have permission to use this trim pattern.";

  @JsonProperty("MATERIAL-PERMISSION-DENIED-MESSAGE")
  private String materialPermissionDeniedMessage =
      "<red>You do not have permission to use this trim material.";

  @JsonProperty("NO-TIER-PERMISSION-MESSAGE")
  private String noTierPermissionMessage = "<red>You do not have permission for any armor tier.";

  @JsonProperty("APPLY-ALL-TIERS-ERROR-MESSAGE")
  private String applyAllTiersErrorMessage =
      "<red>You don't have permission to apply this tier to all armor.";

  @JsonProperty("APPLY-ALL-TIERS-SUCCESS-MESSAGE")
  private String applyAllTiersSuccessMessage =
      "<green>Applied the current tier trim setup to all armor tiers.";

  @Comment("Armor Tier customization")
  @JsonProperty("ARMOR-TIERS")
  private Map<String, ArmorTier> armorTiers = defaultArmorTiers();

  @JsonProperty("ICONS")
  private Icons icons = new Icons();

  @Comment("Dynamically built lore templates for armor cosmetics")
  @JsonProperty("ARMOR-PREVIEW-LORE")
  private List<String> armorPreviewLore =
      List.of(
          "<gray>Tier: <yellow>%tier%",
          "<gray>Active Pattern: %pattern%",
          "<gray>Active Material: %material%",
          "",
          "<yellow>Left-Click to customize",
          "<red>Right-Click to reset");

  @JsonProperty("TIER-TOGGLE-LORE")
  private List<String> tierToggleLore =
      List.of("", "<gray>Left-click: <red>Previous tier", "<gray>Right-click: <green>Next tier");

  @JsonProperty("SUB-TITLE")
  private String subTitle = "<dark_gray>Customize %armor% Cosmetics";

  @Comment("Armor Piece Hub Configuration")
  @JsonProperty("ARMOR-PIECE-HUB")
  private ArmorPieceHub armorPieceHub = new ArmorPieceHub();

  @Comment("Pattern Selection GUI Configuration")
  @JsonProperty("PATTERN-SELECTION")
  private PatternSelection patternSelection = new PatternSelection();

  @Comment("Material Selection GUI Configuration")
  @JsonProperty("MATERIAL-SELECTION")
  private MaterialSelection materialSelection = new MaterialSelection();

  @JsonProperty("HUB")
  private Hub hub = new Hub();

  @JsonProperty("LOBBY-ITEMS")
  private LobbyItems lobbyItems = new LobbyItems();

  @JsonProperty("DEATH-EFFECTS")
  private DeathEffects deathEffects = new DeathEffects();

  @Comment("── Shield Cosmetics ──────────────────────────────────────────────────────")
  @JsonProperty("SHIELD")
  private Shield shield = new Shield();

  private static Map<String, ArmorTier> defaultArmorTiers() {
    Map<String, ArmorTier> values = new LinkedHashMap<>();
    values.put("LEATHER", ArmorTier.leather());
    values.put("GOLD", ArmorTier.gold());
    values.put("IRON", ArmorTier.iron());
    values.put("DIAMOND", ArmorTier.diamond());
    values.put("NETHERITE", ArmorTier.netherite());

    return values;
  }

  @Getter
  public static class ArmorTier {
    @JsonProperty("DISPLAY-NAME")
    private String displayName;

    @JsonProperty("HELMET-MATERIAL")
    private String helmetMaterial;

    @JsonProperty("CHESTPLATE-MATERIAL")
    private String chestplateMaterial;

    @JsonProperty("LEGGINGS-MATERIAL")
    private String leggingsMaterial;

    @JsonProperty("BOOTS-MATERIAL")
    private String bootsMaterial;

    static ArmorTier leather() {
      ArmorTier value = new ArmorTier();
      value.displayName = "Leather";
      value.helmetMaterial = "LEATHER_HELMET";
      value.chestplateMaterial = "LEATHER_CHESTPLATE";
      value.leggingsMaterial = "LEATHER_LEGGINGS";
      value.bootsMaterial = "LEATHER_BOOTS";

      return value;
    }

    static ArmorTier gold() {
      ArmorTier value = new ArmorTier();
      value.displayName = "Gold";
      value.helmetMaterial = "GOLDEN_HELMET";
      value.chestplateMaterial = "GOLDEN_CHESTPLATE";
      value.leggingsMaterial = "GOLDEN_LEGGINGS";
      value.bootsMaterial = "GOLDEN_BOOTS";

      return value;
    }

    static ArmorTier iron() {
      ArmorTier value = new ArmorTier();
      value.displayName = "Iron";
      value.helmetMaterial = "IRON_HELMET";
      value.chestplateMaterial = "IRON_CHESTPLATE";
      value.leggingsMaterial = "IRON_LEGGINGS";
      value.bootsMaterial = "IRON_BOOTS";

      return value;
    }

    static ArmorTier diamond() {
      ArmorTier value = new ArmorTier();
      value.displayName = "Diamond";
      value.helmetMaterial = "DIAMOND_HELMET";
      value.chestplateMaterial = "DIAMOND_CHESTPLATE";
      value.leggingsMaterial = "DIAMOND_LEGGINGS";
      value.bootsMaterial = "DIAMOND_BOOTS";

      return value;
    }

    static ArmorTier netherite() {
      ArmorTier value = new ArmorTier();
      value.displayName = "Netherite";
      value.helmetMaterial = "NETHERITE_HELMET";
      value.chestplateMaterial = "NETHERITE_CHESTPLATE";
      value.leggingsMaterial = "NETHERITE_LEGGINGS";
      value.bootsMaterial = "NETHERITE_BOOTS";

      return value;
    }
  }

  @Getter
  public static class Icons {
    @JsonProperty("HELMET-ICON")
    private ItemConfig helmetIcon =
        ItemConfig.of("LEATHER_HELMET")
            .name("<yellow>Helmet")
            .lore(
                "",
                "<gray>Click here to customize",
                "<gray>armor trim patterns and materials",
                "<gray>for your helmet.",
                "");

    @JsonProperty("CHESTPLATE-ICON")
    private ItemConfig chestplateIcon =
        ItemConfig.of("LEATHER_CHESTPLATE")
            .name("<yellow>Chestplate")
            .lore(
                "",
                "<gray>Click here to customize",
                "<gray>armor trim patterns and materials",
                "<gray>for your chestplate.",
                "");

    @JsonProperty("LEGGINGS-ICON")
    private ItemConfig leggingsIcon =
        ItemConfig.of("LEATHER_LEGGINGS")
            .name("<yellow>Leggings")
            .lore(
                "",
                "<gray>Click here to customize",
                "<gray>armor trim patterns and materials",
                "<gray>for your leggings.",
                "");

    @JsonProperty("BOOTS-ICON")
    private ItemConfig bootsIcon =
        ItemConfig.of("LEATHER_BOOTS")
            .name("<yellow>Boots")
            .lore(
                "",
                "<gray>Click here to customize",
                "<gray>armor trim patterns and materials",
                "<gray>for your boots.",
                "");

    @JsonProperty("SHIELD-ICON")
    private ItemConfig shieldIcon =
        ItemConfig.of("SHIELD")
            .name("<yellow>Shield")
            .lore(
                "", "<gray>Click here to customize", "<gray>the trim pattern for your shield.", "");

    @JsonProperty("INFO-ICON")
    private ItemConfig infoIcon =
        ItemConfig.of("BOOK")
            .name("<aqua>Cosmetics Information")
            .lore(
                "<dark_gray><st>------------------------",
                "<gray>Unlocked Patterns: <aqua>%pattern_unlocked%<gray>/<aqua>%pattern_total%",
                "<gray>Unlocked Materials: <gold>%material_unlocked%<gray>/<gold>%material_total%",
                "",
                "<gray>Select a category item to edit",
                "<gray>your active trim cosmetics.",
                "<dark_gray><st>------------------------");

    @JsonProperty("APPLY-ALL-TIERS")
    private ItemConfig applyAllTiers =
        ItemConfig.of("SMITHING_TABLE")
            .name("<green>Apply To All Armor Tiers")
            .lore(
                "<dark_gray><st>------------------------",
                "<gray>Copies the active tier trim",
                "<gray>pattern and material to all tiers.",
                "",
                "<yellow>Helmet -> Helmet",
                "<yellow>Chestplate -> Chestplate",
                "<yellow>Leggings -> Leggings",
                "<yellow>Boots -> Boots",
                "",
                "<green>Click to apply",
                "<dark_gray><st>------------------------");

    @JsonProperty("BACK-TO")
    private ItemConfig backTo = ItemConfig.of("ARROW").name("<red>Back");
  }

  @Getter
  public static class ArmorPieceHub {
    @JsonProperty("INVENTORY-ROWS")
    private int inventoryRows = 4;

    @JsonProperty("SLOTS")
    private Slots slots = new Slots();

    @JsonProperty("PREVIEW-ITEM")
    private PreviewItem previewItem = new PreviewItem();

    @JsonProperty("PATTERN-SELECTION-BUTTON")
    private PatternSelectionButton patternSelectionButton = new PatternSelectionButton();

    @JsonProperty("MATERIAL-SELECTION-BUTTON")
    private MaterialSelectionButton materialSelectionButton = new MaterialSelectionButton();

    @Getter
    public static class Slots {
      @JsonProperty("BACK")
      private int back = 27;

      @JsonProperty("PREVIEW")
      private int preview = 13;

      @JsonProperty("PATTERN-MENU")
      private int patternMenu = 20;

      @JsonProperty("MATERIAL-MENU")
      private int materialMenu = 24;
    }

    @Getter
    public static class PreviewItem {
      @JsonProperty("NAME")
      private String name = "<yellow>Current Preview";
    }

    @Getter
    public static class PatternSelectionButton {
      @JsonProperty("NAME")
      private String name = "<aqua>Pattern Selection";

      @JsonProperty("LORE")
      private List<String> lore =
          List.of("<gray>Open all available trim patterns.", "<yellow>Click to open.");

      @JsonProperty("DEFAULT-MATERIAL")
      private String defaultMaterial = "SMITHING_TABLE";
    }

    @Getter
    public static class MaterialSelectionButton {
      @JsonProperty("NAME")
      private String name = "<gold>Material Selection";

      @JsonProperty("LORE")
      private List<String> lore =
          List.of("<gray>Open all available trim materials.", "<yellow>Click to open.");

      @JsonProperty("DEFAULT-MATERIAL")
      private String defaultMaterial = "ANVIL";
    }
  }

  @Getter
  public static class PatternSelection {
    @JsonProperty("INVENTORY-ROWS")
    private int inventoryRows = 5;

    @JsonProperty("BACK-SLOT")
    private int backSlot = 36;

    @JsonProperty("START-SLOT")
    private int startSlot = 10;

    @JsonProperty("INVENTORY-TITLE")
    private String inventoryTitle = "<dark_gray>Select Pattern - %armor%";

    @JsonProperty("PATTERN-ITEM")
    private PatternItem patternItem = new PatternItem();

    @Getter
    public static class PatternItem {
      @JsonProperty("NAME")
      private String name = "<aqua>%pattern_name% Pattern";

      @JsonProperty("LORE")
      private List<String> lore = List.of("<gray>Status: %state%", "<gray>Access: %access%");
    }
  }

  @Getter
  public static class MaterialSelection {
    @JsonProperty("INVENTORY-ROWS")
    private int inventoryRows = 5;

    @JsonProperty("BACK-SLOT")
    private int backSlot = 36;

    @JsonProperty("START-SLOT")
    private int startSlot = 10;

    @JsonProperty("INVENTORY-TITLE")
    private String inventoryTitle = "<dark_gray>Select Material - %armor%";

    @JsonProperty("MATERIAL-ITEM")
    private MaterialItem materialItem = new MaterialItem();

    @JsonProperty("MATERIAL-ICONS")
    private Map<String, String> materialIcons = defaultMaterialIcons();

    private static Map<String, String> defaultMaterialIcons() {
      Map<String, String> values = new LinkedHashMap<>();
      values.put("LAPIS", "LAPIS_LAZULI");
      values.put("AMETHYST", "AMETHYST_SHARD");
      values.put("RESIN", "RESIN_BRICK");

      return values;
    }

    @Getter
    public static class MaterialItem {
      @JsonProperty("NAME")
      private String name = "<gold>%material_name% Material";

      @JsonProperty("LORE")
      private List<String> lore = List.of("<gray>Status: %state%", "<gray>Access: %access%");
    }
  }

  @Getter
  public static class Hub {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>✦ Cosmetics";

    @JsonProperty("BUTTONS")
    private Buttons buttons = new Buttons();

    @Getter
    public static class Buttons {
      @JsonProperty("ARMOR-TRIMS")
      private ArmorTrims armorTrims = new ArmorTrims();

      @JsonProperty("SHIELD")
      private Shield shield = new Shield();

      @JsonProperty("LOBBY-ITEMS")
      private LobbyItems lobbyItems = new LobbyItems();

      @JsonProperty("KILL-EFFECTS")
      private KillEffects killEffects = new KillEffects();

      @Getter
      public static class ArmorTrims extends ItemConfig {
        @JsonProperty("GLOW")
        private boolean glow = true;

        {
          material("DIAMOND_CHESTPLATE");
          name("<gold>✦ Armor Trims");
          lore(
              "",
              "<gray>Customize your armor tier,",
              "<gray>trim patterns and materials.",
              "",
              "<yellow>Click to open.");
        }
      }

      @Getter
      public static class Shield extends ItemConfig {
        @JsonProperty("GLOW")
        private boolean glow = false;

        {
          material("SHIELD");
          name("<blue>✦ Shield");
          lore(
              "",
              "<gray>Design your shield with any",
              "<gray>color and pattern combination.",
              "<gray>Save multiple layouts.",
              "",
              "<yellow>Click to open.");
        }
      }

      @Getter
      public static class LobbyItems extends ItemConfig {
        @JsonProperty("GLOW")
        private boolean glow = false;

        {
          material("ELYTRA");
          name("<aqua>✦ Lobby Items");
          lore(
              "",
              "<gray>Choose your lobby movement",
              "<gray>cosmetic item loadout.",
              "",
              "<yellow>Click to open.");
        }
      }

      @Getter
      public static class KillEffects extends ItemConfig {
        @JsonProperty("GLOW")
        private boolean glow = false;

        {
          material("BLAZE_POWDER");
          name("<red>✦ Death Effects");
          lore(
              "",
              "<gray>Choose a particle effect that",
              "<gray>plays when you kill someone.",
              "",
              "<yellow>Click to open.");
        }
      }
    }
  }

  @Getter
  public static class LobbyItems {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>✦ Lobby Items";

    @JsonProperty("NO-PERMISSION-MESSAGE")
    private String noPermissionMessage =
        "<red>You don't have permission for this lobby cosmetic item!";

    @JsonProperty("ENTRIES")
    private Map<String, ItemConfig> entries = defaultEntries();

    private static Map<String, ItemConfig> defaultEntries() {
      Map<String, ItemConfig> values = new LinkedHashMap<>();
      values.put(
          "NONE",
          ItemConfig.of("BARRIER")
              .name("<gray>None")
              .lore(
                  "", "<gray>Disable lobby movement cosmetics.", "", "<gray>Status: %status%", ""));
      values.put(
          "WIND_CHARGE",
          ItemConfig.of("WIND_CHARGE")
              .name("<aqua>Wind Charge")
              .lore(
                  "",
                  "<gray>Infinite lobby wind charge",
                  "<gray>while you are in lobby states.",
                  "",
                  "<gray>Status: %status%",
                  ""));
      values.put(
          "TRIDENT",
          ItemConfig.of("TRIDENT")
              .name("<dark_aqua>Riptide Trident")
              .lore(
                  "",
                  "<gray>Right-click with Trident in main hand",
                  "<gray>to trigger riptide boost in lobby.",
                  "",
                  "<gray>Status: %status%",
                  ""));
      values.put(
          "SPEAR",
          ItemConfig.of("NETHERITE_SPEAR")
              .name("<dark_purple>Lunge Spear")
              .lore(
                  "",
                  "<gray>Right-click with Spear in main hand",
                  "<gray>to trigger lunge dash in lobby.",
                  "",
                  "<gray>Status: %status%",
                  ""));

      return values;
    }
  }

  @Getter
  public static class DeathEffects {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>✦ Death Effects";

    @JsonProperty("NO-PERMISSION-MESSAGE")
    private String noPermissionMessage = "<red>You don't have permission for this death effect!";

    @JsonProperty("SELECTED-PREFIX")
    private String selectedPrefix = "<green>✔ ";

    @JsonProperty("UNLOCKED-PREFIX")
    private String unlockedPrefix = "<yellow>";

    @JsonProperty("LOCKED-PREFIX")
    private String lockedPrefix = "<red>🔒 ";

    @JsonProperty("CLICK-TO-SELECT")
    private String clickToSelect = "<yellow>Click to select.";

    @JsonProperty("CLICK-TO-DESELECT")
    private String clickToDeselect = "<gray>Click to deselect.";

    @JsonProperty("NO-PERMISSION-LORE")
    private String noPermissionLore = "<red>Requires: <gray>%permission%";

    @JsonProperty("DEFAULT-LORE")
    private List<String> defaultLore = List.of("", "<gray>Status: %status%", "");

    @JsonProperty("ENTRIES")
    private Map<String, DeathEffectEntry> entries = defaultEntries();

    private static Map<String, DeathEffectEntry> defaultEntries() {
      Map<String, DeathEffectEntry> values = new LinkedHashMap<>();
      values.put("NONE", DeathEffectEntry.none());
      values.put("FLAME", DeathEffectEntry.flame());
      values.put("LIGHTNING", DeathEffectEntry.lightning());
      values.put("FIREWORK", DeathEffectEntry.firework());
      values.put("EXPLOSION", DeathEffectEntry.explosion());
      values.put("BLOOD", DeathEffectEntry.blood());
      values.put("ENCHANT", DeathEffectEntry.enchant());
      values.put("ENDER", DeathEffectEntry.ender());
      values.put("HEARTS", DeathEffectEntry.hearts());
      values.put("ICE", DeathEffectEntry.ice());
      values.put("SUPERNOVA", DeathEffectEntry.supernova());
      values.put("VOIDSTORM", DeathEffectEntry.voidstorm());
      values.put("PHOENIX", DeathEffectEntry.phoenix());
      values.put("COMET", DeathEffectEntry.comet());

      return values;
    }

    @Getter
    public static class DeathEffectEntry {
      @JsonProperty("DISPLAY-NAME")
      private String displayName;

      @JsonProperty("ICON")
      private String icon;

      @JsonProperty("LORE")
      private List<String> lore;

      static DeathEffectEntry none() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "None";
        value.icon = "BARRIER";
        value.lore = List.of("", "<gray>No kill effect.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry flame() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Flame";
        value.icon = "BLAZE_POWDER";
        value.lore = List.of("", "<gray>Bursts of fire on kill.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry lightning() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Lightning";
        value.icon = "LIGHTNING_ROD";
        value.lore = List.of("", "<gray>A lightning strike on kill.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry firework() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Firework";
        value.icon = "FIREWORK_ROCKET";
        value.lore = List.of("", "<gray>Fireworks burst on kill.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry explosion() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Explosion";
        value.icon = "TNT";
        value.lore =
            List.of("", "<gray>Smoke & fire explosion on kill.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry blood() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Blood";
        value.icon = "REDSTONE";
        value.lore = List.of("", "<gray>Red dust particles on kill.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry enchant() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Enchant";
        value.icon = "ENCHANTING_TABLE";
        value.lore =
            List.of("", "<gray>Magic enchant particles on kill.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry ender() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Ender";
        value.icon = "ENDER_PEARL";
        value.lore =
            List.of("", "<gray>Ender portal particles on kill.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry hearts() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Hearts";
        value.icon = "PINK_DYE";
        value.lore = List.of("", "<gray>Floating hearts on kill.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry ice() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Ice";
        value.icon = "PACKED_ICE";
        value.lore =
            List.of("", "<gray>Snowflake & ice particles on kill.", "<gray>Status: %status%", "");

        return value;
      }

      static DeathEffectEntry supernova() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Supernova";
        value.icon = "NETHER_STAR";
        value.lore =
            List.of(
                "",
                "<gray>Massive cosmic blast with",
                "<gray>bright starburst visuals.",
                "<gray>Status: %status%",
                "");

        return value;
      }

      static DeathEffectEntry voidstorm() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Voidstorm";
        value.icon = "ENDER_EYE";
        value.lore =
            List.of(
                "",
                "<gray>Dark void surge with portal",
                "<gray>and dragon-breath energy.",
                "<gray>Status: %status%",
                "");

        return value;
      }

      static DeathEffectEntry phoenix() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Phoenix";
        value.icon = "TOTEM_OF_UNDYING";
        value.lore =
            List.of(
                "",
                "<gray>Fiery rebirth burst with",
                "<gray>totem-like impact effects.",
                "<gray>Status: %status%",
                "");

        return value;
      }

      static DeathEffectEntry comet() {
        DeathEffectEntry value = new DeathEffectEntry();
        value.displayName = "Comet";
        value.icon = "FIRE_CHARGE";
        value.lore =
            List.of(
                "",
                "<gray>A high-energy comet strike",
                "<gray>with sparkling shockwaves.",
                "<gray>Status: %status%",
                "");

        return value;
      }
    }
  }

  @Getter
  public static class Shield {
    @JsonProperty("NO-PERMISSION-MESSAGE")
    private String noPermissionMessage = "<red>You don't have permission to use shield cosmetics!";

    @Comment("Layout list GUI")
    @JsonProperty("LAYOUTS")
    private Layouts layouts = new Layouts();

    @Comment("Editor GUI")
    @JsonProperty("EDITOR")
    private Editor editor = new Editor();

    @Comment("Color picker GUI")
    @JsonProperty("COLOR-PICKER")
    private ColorPicker colorPicker = new ColorPicker();

    @Comment("Pattern picker GUI")
    @JsonProperty("PATTERN-PICKER")
    private PatternPicker patternPicker = new PatternPicker();

    @Getter
    public static class Layouts {
      @JsonProperty("TITLE")
      private String title = "<dark_gray>❖ Shield Layouts";

      @JsonProperty("NAME-TITLE")
      private String nameTitle = "Layout Name";

      @JsonProperty("RENAME-TITLE")
      private String renameTitle = "Rename Layout";

      @JsonProperty("LIMIT-REACHED")
      private String limitReached =
          "<red>You've reached your layout limit! Get a higher rank for more.";

      @JsonProperty("DELETED-MESSAGE")
      private String deletedMessage = "<green>Layout deleted.";

      @JsonProperty("NEW-BUTTON")
      private NewButton newButton = new NewButton();

      @Getter
      public static class NewButton {
        @JsonProperty("NAME")
        private String name = "<green>New Layout";
      }
    }

    @Getter
    public static class Editor {
      @JsonProperty("TITLE")
      private String title = "<dark_gray>Editing: <yellow>%name%";

      @JsonProperty("APPLIED-MESSAGE")
      private String appliedMessage = "<green>Shield layout applied!";

      @JsonProperty("UNAPPLIED-MESSAGE")
      private String unappliedMessage = "<gray>Shield cosmetic removed.";

      @JsonProperty("ADD-LAYER")
      private AddLayer addLayer = new AddLayer();

      @JsonProperty("MAX-LAYERS")
      private MaxLayers maxLayers = new MaxLayers();

      @JsonProperty("REMOVE-LAYER")
      private RemoveLayer removeLayer = new RemoveLayer();

      @Getter
      public static class AddLayer {
        @JsonProperty("NAME")
        private String name = "<green>Add Layer";
      }

      @Getter
      public static class MaxLayers {
        @JsonProperty("NAME")
        private String name = "<red>Max Layers Reached";

        @JsonProperty("MESSAGE")
        private String message = "<red>Maximum of 6 layers reached!";
      }

      @Getter
      public static class RemoveLayer {
        @JsonProperty("NAME")
        private String name = "<red>Remove Top Layer";
      }
    }

    @Getter
    public static class ColorPicker {
      @JsonProperty("BASE-TITLE")
      private String baseTitle = "<dark_gray>Pick Base Color";

      @JsonProperty("LAYER-TITLE")
      private String layerTitle = "<dark_gray>Pick Layer Color";
    }

    @Getter
    public static class PatternPicker {
      @JsonProperty("TITLE")
      private String title = "<dark_gray>Pick Pattern";

      @JsonProperty("APPLIED-MESSAGE")
      private String appliedMessage = "<green>Layer applied!";
    }
  }
}

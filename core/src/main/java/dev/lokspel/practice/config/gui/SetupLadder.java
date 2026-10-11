package dev.lokspel.practice.config.gui;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokspel.practice.config.ItemConfig;
import lombok.Getter;

@Getter
public class SetupLadder {
  @JsonProperty("LADDER-MANAGER")
  private LadderManager ladderManager = new LadderManager();

  @JsonProperty("LADDER-MAIN")
  private LadderMain ladderMain = new LadderMain();

  @JsonProperty("SETTINGS")
  private SetupLadderSettings settings = new SetupLadderSettings();

  @JsonProperty("INVENTORY")
  private Inventory inventory = new Inventory();

  @JsonProperty("DESTROYABLE-BLOCKS")
  private DestroyableBlocks destroyableBlocks = new DestroyableBlocks();

  @JsonProperty("CUSTOM-KIT")
  private CustomKit customKit = new CustomKit();

  @JsonProperty("MATCH-TYPE")
  private MatchType matchType = new MatchType();

  @JsonProperty("SKYWARS-LOOT")
  private SkywarsLoot skywarsLoot = new SkywarsLoot();

  @Getter
  public static class LadderManager {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Ladder Manager";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("PAGE-LEFT")
      private ItemConfig pageLeft = ItemConfig.of("ARROW").name("<gold>Go to Page %page%");

      @JsonProperty("BACK-TO")
      private ItemConfig backTo = ItemConfig.of("ARROW").name("<red>Back to Server Manager");

      @JsonProperty("PAGE-RIGHT")
      private ItemConfig pageRight = ItemConfig.of("ARROW").name("<gold>Go to Page %page%");

      @JsonProperty("LADDER-ICON")
      private LadderIcon ladderIcon = new LadderIcon();

      @Getter
      public static class LadderIcon extends ItemConfig {
        @JsonProperty("STATUS-NAMES")
        private StatusNames statusNames = new StatusNames();

        {
          material("BEDROCK");
          name("<gold>%ladder%");
          lore(
              "",
              "<gold>Ladder Information:",
              " <gray>» <yellow>Type: <aqua>%type%",
              " <gray>» <yellow>State: %ladderState%",
              " <gray>» <yellow>Ranked: %rankedState%",
              " <gray>» <yellow>Freeze: %freezeState%",
              "",
              "<aqua><bold>Click here <aqua>to open ladder settings.");
        }

        @Getter
        public static class StatusNames {
          @JsonProperty("ENABLED")
          private String enabled = "<green>Enabled";

          @JsonProperty("DISABLED")
          private String disabled = "<red>Disabled";
        }
      }
    }
  }

  @Getter
  public static class LadderMain {
    @JsonProperty("TITLE")
    private String title = "<dark_blue>%ladder% <dark_gray>- Ladder";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("BACK-TO")
      private ItemConfig backTo = ItemConfig.of("ARROW").name("<red>Go Back");

      @JsonProperty("LADDER-NAME")
      private ItemConfig ladderName =
          ItemConfig.of("BEDROCK")
              .name("<gold>%ladder%")
              .lore(
                  "",
                  "<yellow>Ladder Type: <aqua>%type%",
                  "",
                  "<gray>You can set the ladders icon by using the",
                  "<gray><bold>/ladder set icon %ladder% <gray>command.",
                  "",
                  "<aqua><bold>Note: <gray>You have to hold the item in your hand and",
                  "<gray>you have to name it first with the <bold>/prac rename <gray>command.");

      @JsonProperty("STATUS")
      private Status status = new Status();

      @JsonProperty("FREEZE")
      private Freeze freeze = new Freeze();

      @JsonProperty("INVENTORY")
      private ItemConfig inventory =
          ItemConfig.of("DIAMOND_CHESTPLATE")
              .name("<dark_aqua>Inventory & Armor")
              .lore(
                  "",
                  "<aqua><bold>Click here <gray>to edit the inventory",
                  "<gray>and armor content of the ladder.",
                  "<gray>You can also view the added effects as well.");

      @JsonProperty("DESTROYABLE-BLOCKS")
      private ItemConfig destroyableBlocks =
          ItemConfig.of("END_STONE")
              .name("<red>Destroyable Blocks")
              .lore(
                  "",
                  "<red><bold>Click here <gray>to edit the blocks",
                  "<gray>that can be destroyed during the game",
                  "<gray>and are already placed in the arena.");

      @JsonProperty("SETTINGS")
      private ItemConfig settings =
          ItemConfig.of("REDSTONE")
              .name("<dark_red>Settings")
              .lore(
                  "",
                  "<yellow><bold>Click here <gray>to adjust the ladders",
                  "<gray>other settings, such as:",
                  " <dark_gray>» <red>Rounds",
                  " <dark_gray>» <red>Ranked",
                  " <dark_gray>» <red>Player Regeneration",
                  " <dark_gray>» <red>Player Hunger",
                  "   <red>etc...");

      @JsonProperty("MATCH-TYPE")
      private ItemConfig matchType =
          ItemConfig.of("BOOK")
              .name("<gold>Match Types")
              .lore(
                  "",
                  "<gold><bold>Click here <gray>to set which",
                  "<gray>match types should be available",
                  "<gray>for the ladder.");

      @JsonProperty("DELETE")
      private ItemConfig delete = ItemConfig.of("RED_DYE").name("<red>Delete Ladder");

      @Getter
      public static class Status {
        @JsonProperty("ENABLED")
        private ItemConfig enabled =
            ItemConfig.of("LIME_STAINED_GLASS_PANE")
                .name("<gray>Status: <green>Enabled")
                .lore(
                    "",
                    "<yellow><bold>Click here <gray>to <red>disable <gray>the ladder.",
                    "",
                    "<red><bold>Note: <red>This will delete all the players",
                    "<red>custom kits with this ladder.",
                    "<red><bold>You won't be able to reverse this action.");

        @JsonProperty("DISABLED")
        private ItemConfig disabled =
            ItemConfig.of("RED_STAINED_GLASS_PANE")
                .name("<gray>Status: <red>Disabled")
                .lore(
                    "",
                    "<yellow><bold>Click here <gray>to <green>enable <gray>the ladder.",
                    "",
                    "<red><bold>Note: <gray>The ladder has to meet some",
                    "<gray>requirements to be enable:",
                    " <dark_gray>» <yellow>Set an icon.",
                    " <dark_gray>» <yellow>Set the inventory.");
      }

      @Getter
      public static class Freeze {
        @JsonProperty("FROZEN")
        private ItemConfig frozen =
            ItemConfig.of("SNOWBALL")
                .name("<yellow>The ladder is <aqua>frozen<yellow>.")
                .lore(
                    "",
                    "<gray>Click here to <green>defrost<gray>.",
                    "",
                    "<gray>The players will be able to edit",
                    "<gray>and play with this ladder again.");

        @JsonProperty("NOT-FROZEN")
        private ItemConfig notFrozen =
            ItemConfig.of("SUNFLOWER")
                .name("<yellow>The ladder is <green>not frozen<yellow>.")
                .lore(
                    "",
                    "<yellow>Click here to <aqua>freeze <yellow>it.",
                    "",
                    "<gray>This means that the players",
                    "<gray>won't be able to start a new match",
                    "<gray>and edit this ladder.");
      }
    }
  }

  @Getter
  public static class Inventory {
    @JsonProperty("TITLE")
    private String title = "<dark_blue>%ladder% <dark_gray>- Inventory";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("BACK-TO")
      private ItemConfig backTo = ItemConfig.of("ARROW").name("<red>Go Back");

      @JsonProperty("CUSTOM-KIT-EDITOR")
      private CustomKitEditor customKitEditor = new CustomKitEditor();

      @JsonProperty("EFFECTS")
      private Effects effects = new Effects();

      @Getter
      public static class CustomKitEditor {
        @JsonProperty("EDITABLE")
        private ItemConfig editable =
            ItemConfig.of("BOOK")
                .name("<gold>Custom Kit <gray>Editor")
                .lore(
                    "",
                    "<gold><bold>Click here <gray>to customize",
                    "<gray>what extra items the players",
                    "<gray>can add to the custom kit.");

        @JsonProperty("NOT-EDITABLE")
        private ItemConfig notEditable =
            ItemConfig.of("BOOK")
                .name("<gold>Custom Kit <gray>Editor")
                .lore("", "<red>This ladder is not editable", "<red>by the players.");
      }

      @Getter
      public static class Effects {
        @JsonProperty("HAS-EFFECT")
        private HasEffect hasEffect = new HasEffect();

        @JsonProperty("NO-EFFECT")
        private NoEffect noEffect = new NoEffect();

        @Getter
        public static class HasEffect extends ItemConfig {
          @JsonProperty("EFFECT-FORMAT")
          private String effectFormat = "<light_purple>%name% %amplifier% <gray>for %time%";

          @JsonProperty("DAMAGE")
          private int damage = 8233;

          {
            material("POTION");
            name("<dark_purple>Effects");
            lore(
                "<dark_gray><st>------------------------",
                "<red><bold>Note: <gray>You can set the effects by using",
                "<gray>the <gray><bold>/ladder seteffects %ladder% <gray>command.",
                "",
                "<green>Currently set effects:",
                "%effects%",
                "<dark_gray><st>------------------------");
          }
        }

        @Getter
        public static class NoEffect extends ItemConfig {
          @JsonProperty("DAMAGE")
          private int damage = 8233;

          {
            material("POTION");
            name("<dark_purple>Effects");
            lore(
                "<dark_gray><st>------------------------",
                "<gray>This ladder has no effects.",
                "<dark_gray><st>------------------------");
          }
        }
      }
    }
  }

  @Getter
  public static class DestroyableBlocks {
    @JsonProperty("TITLE")
    private String title = "<dark_blue>%ladder% <dark_gray>- D-Blocks";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("BACK-TO")
      private ItemConfig backTo = ItemConfig.of("ARROW").name("<red>Go Back");
    }
  }

  @Getter
  public static class CustomKit {
    @JsonProperty("TITLE")
    private String title = "<dark_blue>%ladder% <dark_gray>- Custom Kit";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("GO-BACK")
      private ItemConfig goBack = ItemConfig.of("ARROW").name("<red>Go Back");

      @JsonProperty("SAVE")
      private ItemConfig save =
          ItemConfig.of("LIME_WOOL")
              .name("<green><bold>Save")
              .lore(
                  "",
                  "<red>This is just a showcase item, which",
                  "<red>currently has no functionality, but",
                  "<red>it demonstrates how the kit editor will look like.");

      @JsonProperty("LOAD")
      private ItemConfig load =
          ItemConfig.of("YELLOW_WOOL")
              .name("<yellow><bold>Load default kit")
              .lore(
                  "",
                  "<red>This is just a showcase item, which",
                  "<red>currently has no functionality, but",
                  "<red>it demonstrates how the kit editor will look like.");

      @JsonProperty("CANCEL")
      private ItemConfig cancel =
          ItemConfig.of("RED_WOOL")
              .name("<red><bold>Cancel")
              .lore(
                  "",
                  "<red>This is just a showcase item, which",
                  "<red>currently has no functionality, but",
                  "<red>it demonstrates how the kit editor will look like.");

      @JsonProperty("SWITCH-WEIGHTCLASS")
      private SwitchWeightclass switchWeightclass = new SwitchWeightclass();

      @Getter
      public static class SwitchWeightclass {
        @JsonProperty("ONLY-UNRANKED")
        private ItemConfig onlyUnranked =
            ItemConfig.of("REDSTONE").name("<red>This ladder is only %weightClass%.");

        @JsonProperty("ONLY-RANKED")
        private ItemConfig onlyRanked =
            ItemConfig.of("REDSTONE").name("<red>This ladder is only %weightClass%.");

        @JsonProperty("SWITCH-TO-UNRANKED")
        private ItemConfig switchToUnranked =
            ItemConfig.of("WOODEN_SWORD").name("<green>Switch to %weightClass%");

        @JsonProperty("SWITCH-TO-RANKED")
        private ItemConfig switchToRanked =
            ItemConfig.of("IRON_SWORD").name("<green>Switch to %weightClass%");
      }
    }
  }

  @Getter
  public static class MatchType {
    @JsonProperty("TITLE")
    private String title = "<dark_blue>%ladder% <dark_gray>- Match Types";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("GO-BACK")
      private ItemConfig goBack = ItemConfig.of("ARROW").name("<red>Go Back");

      @JsonProperty("ENABLED")
      private ItemConfig enabled =
          ItemConfig.of("LIME_DYE")
              .name("%matchType%")
              .lore(
                  "",
                  "<yellow>Click here <gray>to <red>disable %matchType%",
                  "<gray>match type for the ladder.");

      @JsonProperty("DISABLED")
      private ItemConfig disabled =
          ItemConfig.of("GRAY_DYE")
              .name("%matchType%")
              .lore(
                  "",
                  "<yellow>Click here <gray>to <green>enable %matchType%",
                  "<gray>match type for the ladder.");
    }
  }

  @Getter
  public static class SkywarsLoot {
    @JsonProperty("TITLE")
    private String title = "<dark_blue>%ladder% <dark_gray>- SkyWars loot";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("GO-BACK")
      private ItemConfig goBack = ItemConfig.of("ARROW").name("<red>Go Back");
    }
  }
}

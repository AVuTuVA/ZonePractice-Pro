package dev.lokspel.practice.config.gui;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokspel.practice.config.ItemConfig;
import java.util.List;
import lombok.Getter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
public class KitEditor {
  @JsonProperty("EDITOR-MENU")
  private EditorMenu editorMenu = new EditorMenu();

  @JsonProperty("PLAYER-CUSTOM-KIT-SELECTOR")
  private PlayerCustomKitSelector playerCustomKitSelector = new PlayerCustomKitSelector();

  @JsonProperty("LADDER-SELECTOR")
  private LadderSelector ladderSelector = new LadderSelector();

  @JsonProperty("DUEL-ROUND-SELECTOR")
  private DuelRoundSelector duelRoundSelector = new DuelRoundSelector();

  @JsonProperty("KIT-SELECTOR")
  private KitSelector kitSelector = new KitSelector();

  @JsonProperty("KIT-EDITOR")
  private KitEditorSection kitEditor = new KitEditorSection();

  @Getter
  public static class EditorMenu {
    @JsonProperty("TITLE")
    private String title = "<gold><bold>Select what to edit";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("FILLER-ITEM")
      private ItemConfig fillerItem = ItemConfig.of("BLACK_STAINED_GLASS_PANE");

      @JsonProperty("PREMADE-LADDER")
      private ItemConfig premadeLadder =
          ItemConfig.of("GOLDEN_SWORD")
              .name("<gold><bold>Premade Ladder")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Click here to edit a <gold>premade",
                  "<gray>ladder.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("PLAYER-LADDER")
      private ItemConfig playerLadder =
          ItemConfig.of("FIRE_CHARGE")
              .name("<green><bold>Own Ladder")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Click here to edit your <green>own",
                  "<gray>ladder.",
                  "<dark_gray><st>------------------------");
    }
  }

  @Getter
  public static class PlayerCustomKitSelector {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Select a kit to edit";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("KIT")
      private ItemConfig kit = ItemConfig.of("WRITABLE_BOOK").name("<gray>[<white>%kit%<gray>]");

      @JsonProperty("UNSELECTED")
      private ItemConfig unselected =
          ItemConfig.of("CYAN_TERRACOTTA").name("<gray>[<white>Unselected<gray>]");

      @JsonProperty("SELECTED")
      private ItemConfig selected =
          ItemConfig.of("LIME_TERRACOTTA").name("<gray>[<green>Selected<gray>]");

      @JsonProperty("GUIDE")
      private ItemConfig guide =
          ItemConfig.of("PAPER")
              .name("<aqua>Guide")
              .lore(
                  "<white>After selecting a kit, you can",
                  "<white>play with it by dueling other",
                  "<white>players or play in parties.");
    }
  }

  @Getter
  public static class LadderSelector {
    @JsonProperty("TITLE")
    private String title = "<blue><bold>Select a kit to edit.";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("NAME")
      private String name = "%ladder%";

      @JsonProperty("LORE")
      private List<String> lore =
          List.of("", "<green>Click here to select <yellow>%ladder%<green>.");

      @JsonProperty("PAGE-LEFT")
      private ItemConfig pageLeft = ItemConfig.of("ARROW").name("<gold>Go to Page %page%");

      @JsonProperty("PAGE-RIGHT")
      private ItemConfig pageRight = ItemConfig.of("ARROW").name("<gold>Go to Page %page%");
    }
  }

  @Getter
  public static class DuelRoundSelector {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Select the amount of rounds";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("BACK-TO")
      private ItemConfig backTo = ItemConfig.of("ARROW").name("<red>Back to");

      @JsonProperty("ROUND-SELECTOR")
      private ItemConfig roundSelector =
          ItemConfig.of("FIREWORK_STAR")
              .name("<gray>Rounds: <gold>%rounds%")
              .lore(
                  "",
                  "<gray>The first player/team to reach",
                  "<gray>the, winning rounds wins.",
                  "",
                  "<gray>Recommended: <yellow>%recommended_rounds% rounds<gray>.",
                  "",
                  "<green><bold>LEFT-CLICK <green>to reduce the rounds.",
                  "<aqua><bold>RIGHT-CLICK <aqua>to increase the rounds.");

      @JsonProperty("SHOW-ARENA")
      private ItemConfig showArena =
          ItemConfig.of("DIRT")
              .name("<yellow>Selected Arena: %arena%")
              .lore("", "<gray>You've selected this arena for the match.");

      @JsonProperty("SHOW-LADDER")
      private ItemConfig showLadder =
          ItemConfig.of("DIRT")
              .name("<yellow>Selected Ladder: %ladder%")
              .lore("", "<gray>You've selected this ladder for the match.");

      @JsonProperty("START-MATCH")
      private ItemConfig startMatch =
          ItemConfig.of("EMERALD")
              .name("<green>Start Match")
              .lore(
                  "",
                  "<gray>Click here to start the match.",
                  "<gray>The match will start with the selected",
                  "<gray>arena and ladder.");
    }
  }

  @Getter
  public static class KitSelector {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Viewing %ladder% kits";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("BACK-TO-KIT-SELECTOR")
      private ItemConfig backToKitSelector =
          ItemConfig.of("ARROW").name("<red>Back to kit selector");

      @JsonProperty("CREATE-KIT")
      private ItemConfig createKit = ItemConfig.of("STONE_SWORD").name("<green><bold>Create Kit");

      @JsonProperty("EXISTING-KIT")
      private ItemConfig existingKit = ItemConfig.of("GOLDEN_SWORD").name("<gold><bold>Kit %kit%");

      @JsonProperty("EDIT-KIT")
      private ItemConfig editKit = ItemConfig.of("BOOK").name("<green><bold>Edit Kit");

      @JsonProperty("DELETE-KIT")
      private ItemConfig deleteKit =
          ItemConfig.of("REDSTONE")
              .name("<red><bold>Delete Kit")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<red><bold>You won't be able to",
                  "<red>recover this kit.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("NO-PERMISSION")
      private ItemConfig noPermission =
          ItemConfig.of("WOODEN_SWORD").name("<red>You don't have permission");

      @JsonProperty("FILLER-ITEM-1")
      private ItemConfig fillerItem1 = ItemConfig.of("RED_STAINED_GLASS_PANE").name(" ");

      @JsonProperty("FILLER-ITEM-2")
      private ItemConfig fillerItem2 = ItemConfig.of("BLACK_STAINED_GLASS_PANE").name(" ");
    }
  }

  @Getter
  public static class KitEditorSection {
    @Comment("Also available: %weightClass%")
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Editing Kit %kit%";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("INFO")
      private ItemConfig info =
          ItemConfig.of("NAME_TAG")
              .name("<dark_green>Informations")
              .lore(
                  "<dark_gray><st>------------------------",
                  " <yellow>» <gray>Ladder: <yellow>%ladder%",
                  " <yellow>» <gray>Kit: <yellow>%kit%",
                  " <yellow>» <gray>WeightClass: <yellow>%weightClass%",
                  "<dark_gray><st>------------------------");

      @JsonProperty("SAVE")
      private ItemConfig save =
          ItemConfig.of("LIME_WOOL")
              .name("<green><bold>Save")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<yellow><bold>Click here <yellow>to save",
                  "<yellow>the custom kit.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("LOAD-DEFAULT")
      private ItemConfig loadDefault =
          ItemConfig.of("YELLOW_WOOL")
              .name("<yellow><bold>Load Default Kit")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<yellow><bold>Click here <yellow>to load the default",
                  "<yellow>kit to your inventory.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("CANCEL")
      private ItemConfig cancel =
          ItemConfig.of("RED_WOOL")
              .name("<red><bold>Cancel")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<yellow><bold>Click here <yellow>to abort editing the",
                  "<yellow>kit, and return to the kit menu.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("FILLER-ITEM")
      private ItemConfig fillerItem = ItemConfig.of("ORANGE_STAINED_GLASS_PANE").name(" ");

      @JsonProperty("SWITCH-TO-UNRANKED")
      private ItemConfig switchToUnranked =
          ItemConfig.of("WOODEN_SWORD").name("<green>Switch to %weightClass%");

      @JsonProperty("SWITCH-TO-RANKED")
      private ItemConfig switchToRanked =
          ItemConfig.of("IRON_SWORD").name("<green>Switch to %weightClass%");

      @JsonProperty("ONLY-UNRANKED")
      private ItemConfig onlyUnranked =
          ItemConfig.of("REDSTONE").name("<red>This ladder is only %weightClass%");

      @JsonProperty("ONLY-RANKED")
      private ItemConfig onlyRanked =
          ItemConfig.of("REDSTONE").name("<red>This ladder is only %weightClass%");

      @JsonProperty("NO-EFFECT")
      private NoEffect noEffect = new NoEffect();

      @JsonProperty("HAS-EFFECT")
      private HasEffect hasEffect = new HasEffect();

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

      @Getter
      public static class HasEffect {
        @JsonProperty("FORMAT")
        private String format = "<light_purple>%name% %amplifier% <gray>for %time%";

        @JsonProperty("ICON")
        private Icon icon = new Icon();

        @Getter
        public static class Icon extends ItemConfig {
          @JsonProperty("DAMAGE")
          private int damage = 8233;

          {
            material("POTION");
            name("<dark_purple>Effects");
            lore(
                "<dark_gray><st>------------------------",
                "<yellow>You will get these effects,",
                "<yellow>when the game starts.",
                "",
                "%effects%",
                "<dark_gray><st>------------------------");
          }
        }
      }
    }
  }
}

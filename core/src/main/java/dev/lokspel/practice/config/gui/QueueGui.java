package dev.lokspel.practice.config.gui;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokspel.practice.config.ItemConfig;
import lombok.Getter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
public class QueueGui {
  @JsonProperty("LB-FORMAT")
  private String lbFormat;

  @JsonProperty("TITLE")
  private String title;

  @JsonProperty("ICONS")
  private Icons icons;

  static QueueGui unrankedGui() {
    QueueGui value = new QueueGui();
    value.lbFormat = "<yellow>%placement%# <white>%player% <gray>» <white>%score%";
    value.title = "%weight_class% Queue <dark_gray>- <aqua>%category%";
    value.icons = Icons.unrankedGui();

    return value;
  }

  static QueueGui rankedGui() {
    QueueGui value = new QueueGui();
    value.lbFormat = "<yellow>%placement%# <white>%player% <gray>» <white>%score%";
    value.title = "%weight_class% Queue <dark_gray>- <red>%category%";
    value.icons = Icons.rankedGui();

    return value;
  }

  @Getter
  public static class Icons {
    @JsonProperty("FILLER-ITEM")
    private ItemConfig fillerItem;

    @Comment(
        "Fills ladder/category/quick-match slots that ended up empty (e.g. deleted ladder). Set"
            + " MATERIAL: \"\" to disable.")
    @JsonProperty("EMPTY-SLOT")
    private ItemConfig emptySlot;

    @JsonProperty("CATEGORY-SELECTOR")
    private ItemConfig categorySelector;

    @JsonProperty("SELECTED-CATEGORY-SELECTOR")
    private ItemConfig selectedCategorySelector;

    @JsonProperty("QUICK-MATCH")
    private ItemConfig quickMatch;

    @JsonProperty("SWITCH-TO-UNRANKED")
    private ItemConfig switchToUnranked;

    @JsonProperty("CUSTOM-KIT-QUEUE")
    private ItemConfig customKitQueue;

    @Comment(
        "LORE options: %elo%, %win%, %kills%, %deaths%, %win_streak%, %best_win_streak%,"
            + " %lose_streak%, %best_lose_streak%")
    @JsonProperty("LADDER")
    private ItemConfig ladder;

    @Comment("You can set different material and damage for this item.")
    @JsonProperty("FROZEN-LADDER-ITEM")
    private ItemConfig frozenLadderItem;

    @Comment("You can set different material and damage for this item.")
    @JsonProperty("DISABLED-LADDER-ITEM")
    private ItemConfig disabledLadderItem;

    @JsonProperty("SWITCH-TO-RANKED")
    private ItemConfig switchToRanked;

    static Icons unrankedGui() {
      Icons value = new Icons();
      value.fillerItem = ItemConfig.of("BLACK_STAINED_GLASS_PANE");
      value.emptySlot = ItemConfig.of("GRAY_STAINED_GLASS_PANE");
      value.categorySelector =
          ItemConfig.of("BOOK")
              .name("<gray>%category%")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Browse ladders in this category",
                  "<gray>Find your favorite queue faster",
                  "",
                  "<gray>Ladders",
                  "<white>%category_ladders%",
                  "",
                  "<yellow>Click to select this category",
                  "<dark_gray><st>------------------------");
      value.selectedCategorySelector =
          ItemConfig.of("ENCHANTED_BOOK")
              .name("<aqua>%category%")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Browse ladders in this category",
                  "<gray>Find your favorite queue faster",
                  "",
                  "<gray>Ladders",
                  "<white>%category_ladders%",
                  "",
                  "<red>Already selected",
                  "<dark_gray><st>------------------------");
      value.quickMatch =
          ItemConfig.of("NETHER_STAR")
              .name("<green><bold>Quick Match")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Instantly pick a random kit",
                  "<gray>from the selected category",
                  "",
                  "<yellow>Click to queue immediately",
                  "<dark_gray><st>------------------------");
      value.customKitQueue =
          ItemConfig.of("BOOK")
              .name("<gold><bold>Custom Kit Queue")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Host your own custom kit",
                  "<gray>or join someone else's",
                  "",
                  "<yellow>Click to open",
                  "<dark_gray><st>------------------------");
      value.ladder =
          new ItemConfig()
              .name("%ladder%")
              .lore(
                  " <red><bold>» <yellow>Unranked",
                  "     <white>Fighting: <yellow>%in_fight%",
                  "     <white>Queueing: <yellow>%in_queue%",
                  "",
                  "<yellow>Winstreak Top 3",
                  "%lb_win_streak_1%",
                  "%lb_win_streak_2%",
                  "%lb_win_streak_3%",
                  "",
                  "<yellow>Click here to join the %weight_class% %ladder% <yellow>queue.");
      value.frozenLadderItem =
          new ItemConfig()
              .name("%ladder%")
              .lore("", "<yellow>This ladder is <aqua>frozen<yellow>.");
      value.disabledLadderItem =
          new ItemConfig()
              .name("%ladder%")
              .lore("", "<yellow>This ladder is <red>disabled<yellow>.");
      value.switchToRanked =
          ItemConfig.of("IRON_SWORD")
              .name("<red>Switch to Ranked")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Switch to the ranked",
                  "<gray>queue selector",
                  "",
                  "<yellow>Click to switch",
                  "<dark_gray><st>------------------------");

      return value;
    }

    static Icons rankedGui() {
      Icons value = new Icons();
      value.fillerItem = ItemConfig.of("BLACK_STAINED_GLASS_PANE");
      value.emptySlot = ItemConfig.of("GRAY_STAINED_GLASS_PANE");
      value.categorySelector =
          ItemConfig.of("BOOK")
              .name("<gray>%category%")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Browse ladders in this category",
                  "<gray>Find your favorite queue faster",
                  "",
                  "<gray>Ladders",
                  "<white>%category_ladders%",
                  "",
                  "<yellow>Click to select this category",
                  "<dark_gray><st>------------------------");
      value.selectedCategorySelector =
          ItemConfig.of("ENCHANTED_BOOK")
              .name("<red>%category%")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Browse ladders in this category",
                  "<gray>Find your favorite queue faster",
                  "",
                  "<gray>Ladders",
                  "<white>%category_ladders%",
                  "",
                  "<red>Already selected",
                  "<dark_gray><st>------------------------");
      value.quickMatch =
          ItemConfig.of("NETHER_STAR")
              .name("<green><bold>Quick Match")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Instantly pick a random kit",
                  "<gray>from the selected category",
                  "",
                  "<yellow>Click to queue immediately",
                  "<dark_gray><st>------------------------");
      value.switchToUnranked =
          ItemConfig.of("WOODEN_SWORD")
              .name("<green>Switch to Unranked")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Switch to the unranked",
                  "<gray>queue selector",
                  "",
                  "<yellow>Click to switch",
                  "<dark_gray><st>------------------------");
      value.customKitQueue =
          ItemConfig.of("BOOK")
              .name("<gold><bold>Custom Kit Queue")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Host your own custom kit",
                  "<gray>or join someone else's",
                  "",
                  "<yellow>Click to open",
                  "<dark_gray><st>------------------------");
      value.ladder =
          new ItemConfig()
              .name("%ladder%")
              .lore(
                  " <red><bold>» <red>Ranked",
                  "     <white>Fighting: <yellow>%in_fight%",
                  "     <white>Queueing: <yellow>%in_queue%",
                  "",
                  "<yellow>Elo Top 3",
                  "%lb_elo_1%",
                  "%lb_elo_2%",
                  "%lb_elo_3%",
                  "",
                  "<yellow>Click here to join the %weight_class% %ladder% <yellow>queue.");
      value.frozenLadderItem =
          new ItemConfig()
              .name("%ladder%")
              .lore("", "<yellow>This ladder is <aqua>frozen<yellow>.");
      value.disabledLadderItem =
          new ItemConfig()
              .name("%ladder%")
              .lore("", "<yellow>This ladder is <red>disabled<yellow>.");

      return value;
    }
  }
}

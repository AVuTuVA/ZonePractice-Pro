package dev.lokspel.practice.config.gui;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokspel.practice.config.ItemConfig;
import java.util.List;
import lombok.Getter;

@Getter
public class LeaderboardGui {
  @JsonProperty("TITLE")
  private String title;

  @JsonProperty("ICONS")
  private Icons icons;

  static LeaderboardGui eloLeaderboard() {
    LeaderboardGui value = new LeaderboardGui();
    value.title = "<yellow>Top ELO Leaderboard";
    value.icons = Icons.eloLeaderboard();

    return value;
  }

  static LeaderboardGui winLeaderboard() {
    LeaderboardGui value = new LeaderboardGui();
    value.title = "<gold>Top Wins Leaderboards";
    value.icons = Icons.winLeaderboard();

    return value;
  }

  @Getter
  public static class Icons {
    @JsonProperty("FILLER-ITEM")
    private ItemConfig fillerItem;

    @JsonProperty("BACK-TO-HUB")
    private ItemConfig backToHub;

    @JsonProperty("REFRESH-ITEM")
    private ItemConfig refreshItem;

    @JsonProperty("GLOBAL-LEADERBOARD")
    private GlobalLeaderboard globalLeaderboard;

    @JsonProperty("LADDER-LEADERBOARD")
    private LadderLeaderboard ladderLeaderboard;

    static Icons eloLeaderboard() {
      Icons value = new Icons();
      value.fillerItem = ItemConfig.of("YELLOW_STAINED_GLASS_PANE").name(" ");
      value.backToHub = ItemConfig.of("ARROW").name("<yellow>Back to hub");
      value.refreshItem =
          ItemConfig.of("CLAY_BALL")
              .name("<red>Refresh")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Click here to refresh",
                  "<gray>the leaderboard",
                  "",
                  "<red><bold>NOTE: <gray>The leaderboards automatically",
                  "<gray>refresh every time you open the GUI.",
                  "<dark_gray><st>------------------------");
      value.globalLeaderboard = GlobalLeaderboard.eloLeaderboard();
      value.ladderLeaderboard = LadderLeaderboard.eloLeaderboard();

      return value;
    }

    static Icons winLeaderboard() {
      Icons value = new Icons();
      value.fillerItem = ItemConfig.of("ORANGE_STAINED_GLASS_PANE").name(" ");
      value.backToHub = ItemConfig.of("ARROW").name("<yellow>Back to hub");
      value.globalLeaderboard = GlobalLeaderboard.winLeaderboard();
      value.ladderLeaderboard = LadderLeaderboard.winLeaderboard();

      return value;
    }

    @Getter
    public static class GlobalLeaderboard {
      @JsonProperty("NAME")
      private String name;

      @JsonProperty("MATERIAL")
      private String material;

      @JsonProperty("LORE")
      private Lore lore;

      static GlobalLeaderboard eloLeaderboard() {
        GlobalLeaderboard value = new GlobalLeaderboard();
        value.name = "<yellow>Global Elo Leaderboard <gray>- <white>Top %number%";
        value.material = "NETHER_STAR";
        value.lore = Lore.eloLeaderboard();

        return value;
      }

      static GlobalLeaderboard winLeaderboard() {
        GlobalLeaderboard value = new GlobalLeaderboard();
        value.name = "<yellow>Global Win Leaderboard <gray>- <white>Top %number%";
        value.material = "NETHER_STAR";
        value.lore = Lore.winLeaderboard();

        return value;
      }

      @Getter
      public static class Lore {
        @JsonProperty("FORMAT")
        private String format;

        @JsonProperty("FORMAT-NULL")
        private String formatNull;

        @JsonProperty("LEADERBOARD")
        private List<String> leaderboard;

        @JsonProperty("NO-LEADERBOARD")
        private List<String> noLeaderboard;

        static Lore eloLeaderboard() {
          Lore value = new Lore();
          value.format = "<gold>%number%. %division% | %player%<dark_gray>: <yellow>%global_elo%";
          value.formatNull = "<gold>%number%. <gray>| <red>N/A";
          value.leaderboard =
              List.of(
                  "<dark_gray><st>------------------------",
                  "%top%",
                  "<dark_gray><st>------------------------");
          value.noLeaderboard =
              List.of(
                  "<dark_gray><st>------------------------",
                  "<red>There is no leaderboard to display",
                  "<dark_gray><st>------------------------");

          return value;
        }

        static Lore winLeaderboard() {
          Lore value = new Lore();
          value.format = "<gold>%number%. %division% | %player%<dark_gray>: <yellow>%global_win%";
          value.formatNull = "<gold>%number%. <gray>| <red>N/A";
          value.leaderboard =
              List.of(
                  "<dark_gray><st>------------------------",
                  "%top%",
                  "<dark_gray><st>------------------------");
          value.noLeaderboard =
              List.of(
                  "<dark_gray><st>------------------------",
                  "<red>There is no leaderboard to display",
                  "<dark_gray><st>------------------------");

          return value;
        }
      }
    }

    @Getter
    public static class LadderLeaderboard {
      @JsonProperty("NAME")
      private String name;

      @JsonProperty("LORE")
      private Lore lore;

      static LadderLeaderboard eloLeaderboard() {
        LadderLeaderboard value = new LadderLeaderboard();
        value.name = "%ladder% <yellow>ELO <gray>- <white>Top %number%";
        value.lore = Lore.eloLeaderboard();

        return value;
      }

      static LadderLeaderboard winLeaderboard() {
        LadderLeaderboard value = new LadderLeaderboard();
        value.name = "%ladder% <gold>Win <gray>- <white>Top %number%";
        value.lore = Lore.winLeaderboard();

        return value;
      }

      @Getter
      public static class Lore {
        @JsonProperty("FORMAT")
        private String format;

        @JsonProperty("FORMAT-NULL")
        private String formatNull;

        @JsonProperty("LEADERBOARD")
        private List<String> leaderboard;

        @JsonProperty("NO-LEADERBOARD")
        private List<String> noLeaderboard;

        static Lore eloLeaderboard() {
          Lore value = new Lore();
          value.format = "<gold>%number%. %division% | %player%<dark_gray>: <yellow>%ladder_elo%";
          value.formatNull = "<gold>%number%. <gray>| <red>N/A";
          value.leaderboard =
              List.of(
                  "<dark_gray><st>------------------------",
                  "%top%",
                  "<dark_gray><st>------------------------");
          value.noLeaderboard =
              List.of(
                  "<dark_gray><st>------------------------",
                  "<red>There is no leaderboard to display",
                  "<dark_gray><st>------------------------");

          return value;
        }

        static Lore winLeaderboard() {
          Lore value = new Lore();
          value.format = "<gold>%number%. <gray>| <yellow>%player%<dark_gray>: <white>%ladder_win%";
          value.formatNull = "<gold>%number%. <gray>| <red>N/A";
          value.leaderboard =
              List.of(
                  "<dark_gray><st>------------------------",
                  "%top%",
                  "<dark_gray><st>------------------------");
          value.noLeaderboard =
              List.of(
                  "<dark_gray><st>------------------------",
                  "<red>There is no leaderboard to display",
                  "<dark_gray><st>------------------------");

          return value;
        }
      }
    }
  }
}

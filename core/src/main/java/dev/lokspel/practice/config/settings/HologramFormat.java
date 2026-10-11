package dev.lokspel.practice.config.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class HologramFormat {
  @JsonProperty("TITLE-LINE-SPACING")
  private Double titleLineSpacing;

  @JsonProperty("LINE-SPACING")
  private Double lineSpacing;

  @JsonProperty("FORMAT")
  private String format;

  @JsonProperty("LINES")
  private Lines lines;

  static HologramFormat elo() {
    HologramFormat value = new HologramFormat();
    value.titleLineSpacing = 0.5;
    value.lineSpacing = 0.3;
    value.format = "<gold>%placement%. %division% | %player%<dark_gray>: <yellow>%score%";
    value.lines = Lines.elo();

    return value;
  }

  static HologramFormat win() {
    HologramFormat value = new HologramFormat();
    value.titleLineSpacing = 0.5;
    value.lineSpacing = 0.3;
    value.format = "<gold>%placement%. %division% | %player%<dark_gray>: <yellow>%score%";
    value.lines = Lines.win();

    return value;
  }

  static HologramFormat kills() {
    HologramFormat value = new HologramFormat();
    value.titleLineSpacing = 0.5;
    value.lineSpacing = 0.3;
    value.format = "<gold>%placement%. %division% | %player%<dark_gray>: <yellow>%score%";
    value.lines = Lines.kills();

    return value;
  }

  static HologramFormat deaths() {
    HologramFormat value = new HologramFormat();
    value.titleLineSpacing = 0.5;
    value.lineSpacing = 0.3;
    value.format = "<gold>%placement%. %division% | %player%<dark_gray>: <yellow>%score%";
    value.lines = Lines.deaths();

    return value;
  }

  static HologramFormat winStreak() {
    HologramFormat value = new HologramFormat();
    value.titleLineSpacing = 0.5;
    value.lineSpacing = 0.3;
    value.format = "<gold>%placement%. %division% | %player%<dark_gray>: <yellow>%score%";
    value.lines = Lines.winStreak();

    return value;
  }

  static HologramFormat bestWinStreak() {
    HologramFormat value = new HologramFormat();
    value.titleLineSpacing = 0.5;
    value.lineSpacing = 0.3;
    value.format = "<gold>%placement%. %division% | %player%<dark_gray>: <yellow>%score%";
    value.lines = Lines.bestWinStreak();

    return value;
  }

  static HologramFormat loseStreak() {
    HologramFormat value = new HologramFormat();
    value.titleLineSpacing = 0.5;
    value.lineSpacing = 0.3;
    value.format = "<gold>%placement%. %division% | %player%<dark_gray>: <yellow>%score%";
    value.lines = Lines.loseStreak();

    return value;
  }

  static HologramFormat bestLoseStreak() {
    HologramFormat value = new HologramFormat();
    value.titleLineSpacing = 0.5;
    value.lineSpacing = 0.3;
    value.format = "<gold>%placement%. %division% | %player%<dark_gray>: <yellow>%score%";
    value.lines = Lines.bestLoseStreak();

    return value;
  }

  @Getter
  public static class Lines {
    @JsonProperty("GLOBAL")
    private List<String> global;

    @JsonProperty("LADDER")
    private List<String> ladder;

    static Lines elo() {
      Lines value = new Lines();
      value.global = List.of("<gold>ELO Top", "%top%");
      value.ladder = List.of("<gold>ELO Top <gray>- <yellow>%ladder_displayName%", "%top%");

      return value;
    }

    static Lines win() {
      Lines value = new Lines();
      value.global = List.of("<gold>Win Top", "%top%");
      value.ladder = List.of("<gold>Win Top <gray>- <yellow>%ladder_displayName%", "%top%");

      return value;
    }

    static Lines kills() {
      Lines value = new Lines();
      value.global = List.of("<gold>Kill Top", "%top%");
      value.ladder = List.of("<gold>Kill Top <gray>- <yellow>%ladder_displayName%", "%top%");

      return value;
    }

    static Lines deaths() {
      Lines value = new Lines();
      value.global = List.of("<gold>Deaths Top", "%top%");
      value.ladder = List.of("<gold>Deaths Top <gray>- <yellow>%ladder_displayName%", "%top%");

      return value;
    }

    static Lines winStreak() {
      Lines value = new Lines();
      value.global = List.of("<gold>Win Streak Top", "%top%");
      value.ladder = List.of("<gold>Win Streak Top <gray>- <yellow>%ladder_displayName%", "%top%");

      return value;
    }

    static Lines bestWinStreak() {
      Lines value = new Lines();
      value.global = List.of("<gold>Best Win Streak Top", "%top%");
      value.ladder =
          List.of("<gold>Best Win Streak Top <gray>- <yellow>%ladder_displayName%", "%top%");

      return value;
    }

    static Lines loseStreak() {
      Lines value = new Lines();
      value.global = List.of("<gold>Lose Streak Top", "%top%");
      value.ladder = List.of("<gold>Lose Streak Top <gray>- <yellow>%ladder_displayName%", "%top%");

      return value;
    }

    static Lines bestLoseStreak() {
      Lines value = new Lines();
      value.global = List.of("<gold>Best Lose Streak Top", "%top%");
      value.ladder =
          List.of("<gold>Best Lose Streak Top <gray>- <yellow>%ladder_displayName%", "%top%");

      return value;
    }
  }
}

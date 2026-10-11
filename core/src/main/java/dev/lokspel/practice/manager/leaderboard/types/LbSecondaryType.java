package dev.lokspel.practice.manager.leaderboard.types;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.settings.HologramFormat;
import dev.lokspel.practice.config.settings.SettingsConfig.Leaderboard.Hologram.Format;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import lombok.Getter;

@Getter
public enum LbSecondaryType {
  ELO(true, Format::getElo),
  WIN(false, Format::getWin),
  KILLS(false, Format::getKills),
  DEATHS(false, Format::getDeaths),
  WIN_STREAK(false, Format::getWinStreak),
  BEST_WIN_STREAK(false, Format::getBestWinStreak),
  LOSE_STREAK(false, Format::getLoseStreak),
  BEST_LOSE_STREAK(false, Format::getBestLoseStreak);

  private final boolean rankedRelated;

  private final double titleLineSpacing;
  private final double lineSpacing;

  private final String format;
  private final List<String> globalLines;
  private final List<String> ladderLines;

  LbSecondaryType(
      final boolean rankedRelated, final Function<Format, HologramFormat> formatSection) {
    this.rankedRelated = rankedRelated;

    HologramFormat config =
        formatSection.apply(PracticeConfig.settings().getLeaderboard().getHologram().getFormat());
    this.titleLineSpacing =
        config != null ? Objects.requireNonNullElse(config.getTitleLineSpacing(), 0.0D) : 0.0D;
    this.lineSpacing =
        config != null ? Objects.requireNonNullElse(config.getLineSpacing(), 0.0D) : 0.0D;
    this.format = config != null ? Objects.requireNonNullElse(config.getFormat(), "") : "";

    HologramFormat.Lines lines = config != null ? config.getLines() : null;
    this.globalLines =
        lines != null ? Objects.requireNonNullElse(lines.getGlobal(), List.of()) : List.of();
    this.ladderLines =
        lines != null ? Objects.requireNonNullElse(lines.getLadder(), List.of()) : List.of();
  }
}

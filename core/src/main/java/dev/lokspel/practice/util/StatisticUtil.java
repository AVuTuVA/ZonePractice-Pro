package dev.lokspel.practice.util;

public final class StatisticUtil {

  private StatisticUtil() {}

  public static String getProgressBar(final double progress) {
    int numberOfColoredBars = Math.clamp((int) Math.floor(progress / 10.0), 0, 10);
    int numberOfEmptyBars = 10 - numberOfColoredBars;

    if (numberOfEmptyBars > 0) {
      return "<bold>"
          + "┃".repeat(numberOfColoredBars)
          + "<gray><bold>"
          + "┃".repeat(numberOfEmptyBars);
    }
    return "<bold>" + "┃".repeat(numberOfColoredBars);
  }
}

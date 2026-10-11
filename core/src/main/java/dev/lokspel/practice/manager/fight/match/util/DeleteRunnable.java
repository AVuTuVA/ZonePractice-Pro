package dev.lokspel.practice.manager.fight.match.util;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import org.bukkit.Bukkit;

public enum DeleteRunnable {
  ;

  public static void start(Match match) {
    Bukkit.getScheduler()
        .runTaskLater(
            AstralPractice.getInstance(),
            () -> MatchManager.getInstance().getMatches().remove(match.getId()),
            20L
                * PracticeConfig.settings()
                    .getMatchSettings()
                    .getMatchStatistic()
                    .getRemoveAfter());
  }
}

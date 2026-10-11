package dev.lokspel.practice.manager.server;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.backend.BackendManager;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramManager;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.NumberUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSaveRunnable extends BukkitRunnable {

  @Getter private boolean running = false;

  private final long interval = PracticeConfig.settings().getAutoSave().getInterval() * 60 * 20L;
  private final boolean alert = PracticeConfig.settings().getAutoSave().isAlert();

  public void begin() {
    running = true;
    this.runTaskTimerAsynchronously(AstralPractice.getInstance(), interval, interval);
  }

  @Override
  public void run() {
    if (alert) {
      ServerManager.getInstance()
          .alertPlayers("ap.autosave.alert", PracticeConfig.language().getAutoSave().getStarted());

      Bukkit.getScheduler()
          .runTaskLaterAsynchronously(
              AstralPractice.getInstance(),
              () ->
                  ServerManager.getInstance()
                      .alertPlayers(
                          "ap.autosave.alert", PracticeConfig.language().getAutoSave().getEnded()),
              NumberUtil.getRandomNumber(4, 10) * 20L);
    }

    save();
  }

  public void save() {
    EventManager.getInstance().saveEventData();
    ArenaManager.getInstance().saveArenas();
    LadderManager.getInstance().saveLadders();
    ProfileManager.getInstance().saveProfiles();
    Bukkit.getScheduler()
        .runTask(AstralPractice.getInstance(), () -> HologramManager.getInstance().saveHolograms());
    BackendManager.save();
  }
}

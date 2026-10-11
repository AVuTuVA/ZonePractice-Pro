package dev.lokspel.practice.manager.server;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.profile.ProfileManager;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class MariadbSaveRunnable extends BukkitRunnable {

  private final int interval = PracticeConfig.settings().getMariadbDatabase().getSavePeriod();

  public void begin() {
    this.runTaskTimerAsynchronously(
        AstralPractice.getInstance(), interval * 60 * 20L, interval * 60 * 20L);
  }

  @Override
  public void run() {
    save();
  }

  public void save() {
    ProfileManager.getInstance().saveProfilesToDatabase();
  }
}

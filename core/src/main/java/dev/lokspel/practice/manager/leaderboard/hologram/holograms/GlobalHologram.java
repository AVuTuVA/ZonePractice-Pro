package dev.lokspel.practice.manager.leaderboard.hologram.holograms;

import dev.lokspel.practice.manager.leaderboard.Leaderboard;
import dev.lokspel.practice.manager.leaderboard.LeaderboardManager;
import dev.lokspel.practice.manager.leaderboard.hologram.Hologram;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramData;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramType;
import org.bukkit.Location;

public class GlobalHologram extends Hologram {

  public GlobalHologram(String name, Location baseLocation) {
    super(name, baseLocation, HologramType.GLOBAL);
  }

  public GlobalHologram(String name) {
    super(name, HologramType.GLOBAL);
  }

  @Override
  public void getAbstractData(HologramData data) {}

  @Override
  public void setAbstractData(HologramData data) {}

  @Override
  public boolean isReadyToEnable() {
    return leaderboardType != null;
  }

  @Override
  public Leaderboard getNextLeaderboard() {
    return LeaderboardManager.getInstance()
        .searchLb(hologramType.getLbMainType(), leaderboardType, null);
  }
}

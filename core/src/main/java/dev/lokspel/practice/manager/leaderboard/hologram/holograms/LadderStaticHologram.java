package dev.lokspel.practice.manager.leaderboard.hologram.holograms;

import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.leaderboard.Leaderboard;
import dev.lokspel.practice.manager.leaderboard.LeaderboardManager;
import dev.lokspel.practice.manager.leaderboard.hologram.Hologram;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramData;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class LadderStaticHologram extends Hologram {

  private NormalLadder ladder;

  public LadderStaticHologram(String name, Location baseLocation) {
    super(name, baseLocation, HologramType.LADDER_STATIC);
  }

  public LadderStaticHologram(String name) {
    super(name, HologramType.LADDER_STATIC);
  }

  @Override
  public void getAbstractData(HologramData data) {
    if (data.getLadder() != null) {
      NormalLadder ladder = LadderManager.getInstance().getLadder(data.getLadder());
      if (ladder != null && ladder.isEnabled()) {
        this.ladder = ladder;
      }
    }
  }

  @Override
  public void setAbstractData(HologramData data) {
    data.setLadder(ladder != null ? ladder.getName() : null);
  }

  @Override
  public boolean isReadyToEnable() {
    return ladder != null && leaderboardType != null;
  }

  @Override
  public Leaderboard getNextLeaderboard() {
    return LeaderboardManager.getInstance()
        .searchLb(hologramType.getLbMainType(), leaderboardType, ladder);
  }
}

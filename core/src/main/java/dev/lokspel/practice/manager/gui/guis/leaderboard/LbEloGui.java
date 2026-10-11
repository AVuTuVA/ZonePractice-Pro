package dev.lokspel.practice.manager.gui.guis.leaderboard;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.inventory.ItemStack;

public class LbEloGui extends LeaderboardGui {

  public LbEloGui(Gui backTo) {
    super(
        GuiType.Leaderboard_ELO,
        () -> PracticeConfig.guis().getGuis().getStatistics().getEloLeaderboard(),
        backTo);
    build();
  }

  @Override
  protected boolean isLadderVisible(NormalLadder ladder) {
    return super.isLadderVisible(ladder) && ladder.isRanked();
  }

  @Override
  protected ItemStack createLadderItem(NormalLadder ladder) {
    return LbGuiUtil.createEloLbItem(ladder);
  }

  @Override
  protected ItemStack createGlobalItem() {
    return LbGuiUtil.createGlobalEloLb();
  }
}

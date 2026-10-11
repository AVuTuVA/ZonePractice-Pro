package dev.lokspel.practice.manager.gui.guis.leaderboard;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import org.bukkit.inventory.ItemStack;

public class LbWinGui extends LeaderboardGui {

  public LbWinGui(Gui backTo) {
    super(
        GuiType.Leaderboard_WIN,
        () -> PracticeConfig.guis().getGuis().getStatistics().getWinLeaderboard(),
        backTo);
    build();
  }

  @Override
  protected ItemStack createLadderItem(NormalLadder ladder) {
    return LbGuiUtil.createWinLbItem(ladder);
  }

  @Override
  protected ItemStack createGlobalItem() {
    return LbGuiUtil.createGlobalWinLb();
  }
}

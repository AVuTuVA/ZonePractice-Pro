package dev.lokspel.practice.manager.fight.match.runnable.game;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.util.runnable.GameRunnable;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.util.cooldown.CooldownObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BridgeArrowRunnable extends GameRunnable {

  private static final int TIME =
      PracticeConfig.settings()
          .getMatchSettings()
          .getLadderSettings()
          .getBridge()
          .getRegeneratingArrow()
          .getTime();

  private final GuiItem guiItem = new GuiItem(Material.ARROW);

  public BridgeArrowRunnable(Player player, Match match) {
    super(
        player,
        match.getMatchPlayers().get(player),
        TIME,
        CooldownObject.BRIDGE_ARROW,
        PracticeConfig.settings()
            .getMatchSettings()
            .getLadderSettings()
            .getBridge()
            .getRegeneratingArrow()
            .isExpBar());
  }

  @Override
  public void abstractCancel() {
    if (player.getInventory().firstEmpty() == -1) {
      return;
    }

    player.getInventory().addItem(guiItem.get());
    player.updateInventory();
  }
}

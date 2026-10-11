package dev.lokspel.practice.manager.sidebar.adapter;

import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface SidebarAdapter {

  /**
   * Get's the scoreboard title.
   *
   * @param player who's title is being displayed.
   * @return title.
   */
  Component getTitle(Player player);

  /**
   * Get's the scoreboard lines.
   *
   * @param player who's lines are being displayed.
   * @return lines.
   */
  List<Component> getLines(Player player);
}

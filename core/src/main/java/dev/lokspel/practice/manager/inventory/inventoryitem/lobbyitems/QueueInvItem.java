package dev.lokspel.practice.manager.inventory.inventoryitem.lobbyitems;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.inventory.inventoryitem.InvItem;
import java.util.Locale;
import org.bukkit.entity.Player;

public class QueueInvItem extends InvItem {

  public QueueInvItem() {
    super(PracticeConfig.inventories().getLobbyBasic().getNormal().get("QUEUE"));
  }

  @Override
  public void handleClickEvent(Player player) {
    String defaultGui =
        PracticeConfig.settings()
            .getQueue()
            .getCombined()
            .getDefaultGui()
            .trim()
            .toUpperCase(Locale.ROOT);

    if (defaultGui.equals("RANKED")) {
      player.performCommand("ranked");
      return;
    }

    player.performCommand("unranked");
  }
}

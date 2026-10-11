package dev.lokspel.practice.manager.inventory.inventoryitem;

import dev.lokspel.practice.config.inventory.InventoryLayout;
import org.bukkit.entity.Player;

public class ExtraInvItem extends InvItem {

  private final String command;

  public ExtraInvItem(InventoryLayout.ExtraItem config) {
    super(config);

    this.command = config != null ? config.getCommand() : null;
  }

  @Override
  public void handleClickEvent(Player player) {
    String command = this.command;
    if (command == null) {
      return;
    }

    command = command.trim();
    if (command.isEmpty()) {
      return;
    }

    if (command.startsWith("/")) {
      command = command.substring(1);
    }

    player.performCommand(command.replace("%player%", player.getName()));
  }
}

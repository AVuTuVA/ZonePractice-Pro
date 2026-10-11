package dev.lokspel.practice.command.event.arguments;

import dev.lokspel.practice.manager.backend.ConfigManager;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.enums.EventStatus;
import dev.lokspel.practice.manager.fight.event.interfaces.Event;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class HostArg {

  private HostArg() {}

  public static void run(Player player, String label, String[] args) {
    if (args.length != 1) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.HOST.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    if (!player.hasPermission("ap.event.host")) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.HOST.NO-PERMISSION"));
      return;
    }

    if (!EventManager.getInstance().getEvents().isEmpty()
        && ConfigManager.getBoolean("EVENT.MULTIPLE")) {
      for (Event event : EventManager.getInstance().getEvents()) {
        if (!event.getStatus().equals(EventStatus.COLLECTING)) {
          continue;
        }

        Common.sendMmMessage(
            player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.HOST.CANT-HOST-NOW"));
        return;
      }
    } else if (!EventManager.getInstance().getEvents().isEmpty()
        && !ConfigManager.getBoolean("EVENT.MULTIPLE")) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.HOST.CANT-HOST-MULTIPLE"));
      return;
    }

    GuiManager.getInstance().searchGui(GuiType.Event_Host).open(player);
  }
}

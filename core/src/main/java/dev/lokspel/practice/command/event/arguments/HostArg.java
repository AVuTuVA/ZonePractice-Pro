package dev.lokspel.practice.command.event.arguments;

import dev.lokspel.practice.config.PracticeConfig;
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
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getHost()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    if (!player.hasPermission("ap.event.host")) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getHost()
              .getNoPermission());
      return;
    }

    if (!EventManager.getInstance().getEvents().isEmpty()
        && PracticeConfig.settings().getEvent().isMultiple()) {
      for (Event event : EventManager.getInstance().getEvents()) {
        if (!event.getStatus().equals(EventStatus.COLLECTING)) {
          continue;
        }

        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getEvent()
                .getArguments()
                .getHost()
                .getCantHostNow());
        return;
      }
    } else if (!EventManager.getInstance().getEvents().isEmpty()
        && !PracticeConfig.settings().getEvent().isMultiple()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getHost()
              .getCantHostMultiple());
      return;
    }

    GuiManager.getInstance().searchGui(GuiType.Event_Host).open(player);
  }
}

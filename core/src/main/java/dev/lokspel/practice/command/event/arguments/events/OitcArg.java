package dev.lokspel.practice.command.event.arguments.events;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.enums.EventType;
import dev.lokspel.practice.manager.fight.event.events.ffa.oitc.OitcData;
import dev.lokspel.practice.manager.fight.event.util.EventUtil;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class OitcArg {

  private OitcArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getEvent().getNoPermission());
      return;
    }

    OitcData oitcData = (OitcData) EventManager.getInstance().getEventData().get(EventType.OITC);

    // Checking if the event is live, if it is, it will send a message to the player and return.
    if (EventManager.getInstance().isEventLive(EventType.OITC)) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getOitc()
              .getEventLive());
      return;
    }

    // Checking if the player is trying to enable or disable the arena.
    if (args.length == 2 && args[1].equalsIgnoreCase("enable")) {
      if (!oitcData.isEnabled()) {
        EventUtil.changeStatus(oitcData, player);
      } else {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getEvent()
                .getArguments()
                .getOitc()
                .getEventAlreadyEnabled());
      }
    } else if (args.length == 2 && args[1].equalsIgnoreCase("disable")) {
      if (oitcData.isEnabled()) {
        EventUtil.changeStatus(oitcData, player);
      } else {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getEvent()
                .getArguments()
                .getOitc()
                .getEventAlreadyDisabled());
      }
    } else {
      sendHelpMsg(player, label);
    }
  }

  private static void sendHelpMsg(Player player, String label) {
    for (String line :
        PracticeConfig.language()
            .getCommand()
            .getEvent()
            .getArguments()
            .getOitc()
            .getCommandHelp()) {
      Common.sendMmMessage(player, line.replace("%label%", label));
    }
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();

    if (!player.hasPermission("ap.setup")) {
      return arguments;
    }

    if (args.length == 2) {
      arguments.add("enable");
      arguments.add("disable");

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

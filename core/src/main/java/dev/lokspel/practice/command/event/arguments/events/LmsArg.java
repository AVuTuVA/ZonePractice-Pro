package dev.lokspel.practice.command.event.arguments.events;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.enums.EventType;
import dev.lokspel.practice.manager.fight.event.events.ffa.lms.LmsData;
import dev.lokspel.practice.manager.fight.event.util.EventUtil;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.KitData;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class LmsArg {

  private LmsArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.EVENT.NO-PERMISSION"));
      return;
    }

    LmsData lmsData = (LmsData) EventManager.getInstance().getEventData().get(EventType.LMS);

    // Checking if the event is live, if it is, it will send a message to the player and return.
    if (EventManager.getInstance().isEventLive(EventType.LMS)) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.LMS.EVENT-LIVE"));
      return;
    }

    // Checking if the player is trying to enable or disable the arena.
    if (args.length == 2 && args[1].equalsIgnoreCase("enable")) {
      if (!lmsData.isEnabled()) {
        EventUtil.changeStatus(lmsData, player);
      } else {
        Common.sendMmMessage(
            player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.LMS.EVENT-ALREADY-ENABLED"));
      }

      return;
    } else if (args.length == 2 && args[1].equalsIgnoreCase("disable")) {
      if (lmsData.isEnabled()) {
        EventUtil.changeStatus(lmsData, player);
      } else {
        Common.sendMmMessage(
            player,
            LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.LMS.EVENT-ALREADY-DISABLED"));
      }

      return;
    } else if (args.length == 2 && args[1].equalsIgnoreCase("help")) {
      sendHelpMsg(player, label);

      return;
    }

    // Checking if the arena is enabled, if it is, it will send a message to the player and return.
    if (lmsData.isEnabled()) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.LMS.CANT-EDIT"));
      return;
    }

    // This is setting the kit for the event.
    if (args.length == 2 && args[1].equalsIgnoreCase("setkit")) {
      KitData kitData = lmsData.getKitData();
      kitData.setKitData(player, true);

      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.LMS.KIT-SET"));
    } else {
      sendHelpMsg(player, label);
    }
  }

  private static void sendHelpMsg(Player player, String label) {
    for (String line : LanguageManager.getList("COMMAND.EVENT.ARGUMENTS.LMS.COMMAND-HELP")) {
      Common.sendMmMessage(player, line.replace("%label%", label));
    }
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();

    if (!player.hasPermission("ap.setup")) {
      return arguments;
    }

    if (args.length == 2) {
      arguments.add("setkit");
      arguments.add("enable");
      arguments.add("disable");

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

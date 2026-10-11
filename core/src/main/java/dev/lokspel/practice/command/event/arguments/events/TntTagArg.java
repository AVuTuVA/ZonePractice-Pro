package dev.lokspel.practice.command.event.arguments.events;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.enums.EventType;
import dev.lokspel.practice.manager.fight.event.events.onevsall.tnttag.TntTagData;
import dev.lokspel.practice.manager.fight.event.util.EventUtil;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class TntTagArg {

  private TntTagArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.EVENT.NO-PERMISSION"));
      return;
    }

    TntTagData tntTagData =
        (TntTagData) EventManager.getInstance().getEventData().get(EventType.TNTTAG);

    // Checking if the event is live, if it is, it will send a message to the player and return.
    if (EventManager.getInstance().isEventLive(EventType.TNTTAG)) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.TNTTAG.EVENT-LIVE"));
      return;
    }

    // Checking if the player is trying to enable or disable the arena.
    if (args.length == 2 && args[1].equalsIgnoreCase("enable")) {
      if (!tntTagData.isEnabled()) {
        EventUtil.changeStatus(tntTagData, player);
      } else {
        Common.sendMmMessage(
            player,
            LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.TNTTAG.EVENT-ALREADY-ENABLED"));
      }
    } else if (args.length == 2 && args[1].equalsIgnoreCase("disable")) {
      if (tntTagData.isEnabled()) {
        EventUtil.changeStatus(tntTagData, player);
      } else {
        Common.sendMmMessage(
            player,
            LanguageManager.getString("COMMAND.EVENT.ARGUMENTS.TNTTAG.EVENT-ALREADY-DISABLED"));
      }
    } else {
      sendHelpMsg(player, label);
    }
  }

  private static void sendHelpMsg(Player player, String label) {
    for (String line : LanguageManager.getList("COMMAND.EVENT.ARGUMENTS.TNTTAG.COMMAND-HELP")) {
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

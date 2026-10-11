package dev.lokspel.practice.command.event.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.enums.EventStatus;
import dev.lokspel.practice.manager.fight.event.interfaces.Event;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class JoinArg {

  private JoinArg() {}

  public static void run(Player player, String label, String[] args) {
    if (args.length != 1) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getJoin()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    if (!player.hasPermission("ap.event.join")) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getJoin()
              .getNoPermission());
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (!profile.getStatus().equals(ProfileStatus.LOBBY) || profile.isParty()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language().getCommand().getEvent().getArguments().getJoin().getCantJoin());
      return;
    }

    if (EventManager.getInstance().getEventByPlayer(player) != null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getJoin()
              .getAlreadyInEvent());
      return;
    }

    for (Event event : EventManager.getInstance().getEvents()) {
      if (event.getStatus().equals(EventStatus.COLLECTING)) {
        event.addPlayer(player);
        return;
      }
    }
    Common.sendMmMessage(
        player,
        PracticeConfig.language().getCommand().getEvent().getArguments().getJoin().getNoEvent());
  }
}

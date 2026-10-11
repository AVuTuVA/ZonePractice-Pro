package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PartyAcceptArg {

  private PartyAcceptArg() {}

  public static void acceptCommand(Player player, String label, String[] args) {
    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.ACCEPT.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (!profile.getStatus().equals(ProfileStatus.LOBBY)) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.ACCEPT.CANT-JOIN-NOW"));
      return;
    }

    if (profile.isParty()) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.ACCEPT.ALREADY-MEMBER"));
      return;
    }

    Player target = Bukkit.getPlayer(args[1]);
    if (target == null) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.ACCEPT.TARGET-OFFLINE")
              .replace("%target%", args[1]));
      return;
    }

    Party party = PartyManager.getInstance().getParty(target);
    if (party == null) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.ACCEPT.PARTY-NOT-EXISTS"));
      return;
    }

    if (party.getMembers().size() >= party.getMaxPlayerLimit()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.ACCEPT.PARTY-FULL")
              .replace("%leader%", party.getLeader().getName()));
      return;
    }

    if (party.isPublicParty()) {
      party.addMember(player);
    } else {
      if (!party.getInvites().containsKey(player)) {
        Common.sendMmMessage(
            player,
            LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.ACCEPT.NO-INVITE")
                .replace("%leader%", party.getLeader().getName()));
        return;
      }

      if (party.getInvites().get(player) + PartyManager.INVITE_COOLDOWN
          > System.currentTimeMillis()) {
        profile.setSpectatorMode(false);
        party.addMember(player);
      } else {
        Common.sendMmMessage(
            player,
            LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.ACCEPT.INVITE-EXPIRED")
                .replace("%leader%", party.getLeader().getName()));
      }

      party.getInvites().remove(player);
    }
  }
}

package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.config.PracticeConfig;
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
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getAccept()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (!profile.getStatus().equals(ProfileStatus.LOBBY)) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getAccept()
              .getCantJoinNow());
      return;
    }

    if (profile.isParty()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getAccept()
              .getAlreadyMember());
      return;
    }

    Player target = Bukkit.getPlayer(args[1]);
    if (target == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getAccept()
              .getTargetOffline()
              .replace("%target%", args[1]));
      return;
    }

    Party party = PartyManager.getInstance().getParty(target);
    if (party == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getAccept()
              .getPartyNotExists());
      return;
    }

    if (party.getMembers().size() >= party.getMaxPlayerLimit()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getAccept()
              .getPartyFull()
              .replace("%leader%", party.getLeader().getName()));
      return;
    }

    if (party.isPublicParty()) {
      party.addMember(player);
    } else {
      if (!party.getInvites().containsKey(player)) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getParty()
                .getArguments()
                .getAccept()
                .getNoInvite()
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
            PracticeConfig.language()
                .getCommand()
                .getParty()
                .getArguments()
                .getAccept()
                .getInviteExpired()
                .replace("%leader%", party.getLeader().getName()));
      }

      party.getInvites().remove(player);
    }
  }
}

package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PartyInfoArg {

  private PartyInfoArg() {}

  public static void infoCommand(Player player, String label, String[] args) {
    if (args.length != 1 && args.length != 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getInfo()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    Party party;
    if (args.length == 1) {
      party = PartyManager.getInstance().getParty(player);
      if (party == null) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getParty()
                .getArguments()
                .getInfo()
                .getNoParty());
        return;
      }
    } else {
      if (!player.hasPermission("ap.party.info.others")) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getParty()
                .getArguments()
                .getInfo()
                .getNoPermission());
      }

      Player target = Bukkit.getPlayer(args[1]);
      if (target == null) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getParty()
                .getArguments()
                .getInfo()
                .getNotOnline()
                .replace("%target%", args[1]));
        return;
      }

      party = PartyManager.getInstance().getParty(target);
      if (party == null) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getParty()
                .getArguments()
                .getInfo()
                .getPlayerNoParty()
                .replace("%target%", target.getName()));
        return;
      }
    }

    for (String line :
        PracticeConfig.language().getCommand().getParty().getArguments().getInfo().getPartyInfo()) {
      Common.sendMmMessage(
          player,
          line.replace("%leader%", party.getLeader().getName())
              .replace(
                  "%partyState%",
                  (party.isPublicParty()
                      ? PracticeConfig.language()
                          .getCommand()
                          .getParty()
                          .getArguments()
                          .getInfo()
                          .getPartyStates()
                          .getPublicValue()
                      : PracticeConfig.language()
                          .getCommand()
                          .getParty()
                          .getArguments()
                          .getInfo()
                          .getPartyStates()
                          .getPrivateValue()))
              .replace("%maxPlayerLimit%", String.valueOf(party.getMaxPlayerLimit()))
              .replace("%memberSize%", String.valueOf(party.getMembers().size()))
              .replace(
                  "%members%",
                  party.getMemberNames().toString().replace("[", "").replace("]", "")));
    }
  }
}

package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class PartyLeaveArg {

  private PartyLeaveArg() {}

  public static void leaveCommand(Player player, String label, String[] args) {
    if (args.length != 1) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getLeave()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    Party party = PartyManager.getInstance().getParty(player);
    if (party == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language().getCommand().getParty().getArguments().getLeave().getNoParty());
      return;
    }

    party.removeMember(player, false);
  }
}

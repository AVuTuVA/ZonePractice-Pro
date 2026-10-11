package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class PartyKickArg {

  private PartyKickArg() {}

  public static void kickCommand(Player player, String label, String[] args) {
    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getKick()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    Party party = PartyManager.getInstance().getParty(player);
    if (party == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language().getCommand().getParty().getArguments().getKick().getNoParty());
      return;
    }

    if (!party.getLeader().equals(player)) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getKick()
              .getNotLeader());
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
              .getKick()
              .getTargetOffline()
              .replace("%target%", args[1]));
      return;
    }

    if (target.equals(player)) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getKick()
              .getCantKickYourself());
      return;
    }

    if (!party.getMembers().contains(target)) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getParty()
              .getArguments()
              .getKick()
              .getNotMember()
              .replace("%target%", target.getName()));
      return;
    }

    party.removeMember(target, true);
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();

    Party party = PartyManager.getInstance().getParty(player);
    if (party == null || !party.getLeader().equals(player)) {
      return arguments;
    }

    if (args.length == 2) {
      for (Player member : party.getMembers()) {
        if (member.equals(player)) {
          continue;
        }

        arguments.add(member.getName());
      }

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

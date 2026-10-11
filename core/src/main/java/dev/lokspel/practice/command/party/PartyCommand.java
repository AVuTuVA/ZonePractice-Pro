package dev.lokspel.practice.command.party;

import dev.lokspel.practice.command.party.arguments.PartyAcceptArg;
import dev.lokspel.practice.command.party.arguments.PartyChatArg;
import dev.lokspel.practice.command.party.arguments.PartyDisbandArg;
import dev.lokspel.practice.command.party.arguments.PartyHelpArg;
import dev.lokspel.practice.command.party.arguments.PartyInfoArg;
import dev.lokspel.practice.command.party.arguments.PartyInviteArg;
import dev.lokspel.practice.command.party.arguments.PartyJoinArg;
import dev.lokspel.practice.command.party.arguments.PartyKickArg;
import dev.lokspel.practice.command.party.arguments.PartyLeaderArg;
import dev.lokspel.practice.command.party.arguments.PartyLeaveArg;
import dev.lokspel.practice.command.party.arguments.PartyPublicArg;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class PartyCommand implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player player)) {
      Common.sendConsoleMmMessage(LanguageManager.getString("CANT-USE-CONSOLE"));
      return false;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);

    if (profile.isStaffMode() || profile.isSpectatorMode()) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.PARTY.CANT-USE-COMMAND"));
      return false;
    }

    if (args.length > 0) {
      switch (args[0]) {
        case "accept":
          PartyAcceptArg.acceptCommand(player, label, args);
          break;
        case "chat":
          PartyChatArg.chatCommand(player, args);
          break;
        case "disband":
          PartyDisbandArg.disbandCommand(player, label, args);
          break;
        case "info":
          PartyInfoArg.infoCommand(player, label, args);
          break;
        case "invite":
          PartyInviteArg.inviteCommand(player, label, args);
          break;
        case "join":
          PartyJoinArg.joinCommand(player, label, args);
          break;
        case "public":
          PartyPublicArg.publicCommand(player);
          break;
        case "create":
          PartyManager.getInstance().createParty(player);
          break;
        case "kick":
          PartyKickArg.kickCommand(player, label, args);
          break;
        case "leader":
          PartyLeaderArg.leaderCommand(player, label, args);
          break;
        case "leave":
          PartyLeaveArg.leaveCommand(player, label, args);
          break;
        default:
          PartyHelpArg.helpCommand(player, label);
      }
    } else {
      PartyHelpArg.helpCommand(player, label);
    }

    return true;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command command, String alias, String[] args) {
    List<String> arguments = new ArrayList<>();
    List<String> completion = new ArrayList<>();
    if (!(sender instanceof Player player)) {
      return arguments;
    }

    Party party = PartyManager.getInstance().getParty(player);

    if (args.length == 1) {
      if (party != null) {
        if (party.getLeader().equals(player)) {
          arguments.add("disband");
          arguments.add("kick");
          arguments.add("leader");
        }
        if (party.getLeader().equals(player) || party.isAllInvite()) {
          arguments.add("invite");
        }

        arguments.add("leave");
        arguments.add("chat");
      } else {
        arguments.add("create");
        arguments.add("join");
        arguments.add("public");
        arguments.add("accept");
      }

      arguments.add("help");
      arguments.add("info");

      StringUtil.copyPartialMatches(args[0], arguments, completion);
    } else {
      switch (args[0]) {
        case "invite":
          completion = PartyInviteArg.tabComplete(player, args);
          break;
        case "kick":
          completion = PartyKickArg.tabComplete(player, args);
          break;
        case "leader":
          completion = PartyLeaderArg.tabComplete(player, args);
          break;

        default:
          break;
      }
    }

    Collections.sort(completion);
    return completion;
  }
}

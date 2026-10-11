package dev.lokspel.practice.command.ffa;

import dev.lokspel.practice.command.ffa.arguments.HelpArg;
import dev.lokspel.practice.command.ffa.arguments.JoinArg;
import dev.lokspel.practice.command.ffa.arguments.KitArg;
import dev.lokspel.practice.command.ffa.arguments.LeaveArg;
import dev.lokspel.practice.command.ffa.arguments.ListArg;
import dev.lokspel.practice.command.ffa.arguments.SpectateArg;
import dev.lokspel.practice.manager.backend.LanguageManager;
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

public class FfaCommand implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player player)) {
      Common.sendConsoleMmMessage(LanguageManager.getString("CANT-USE-CONSOLE"));
      return false;
    }

    if (args.length > 0) {
      switch (args[0]) {
        case "join":
          JoinArg.run(player, label, args);
          break;
        case "leave":
          LeaveArg.run(player);
          break;
        case "kit":
          KitArg.run(player);
          break;
        case "spec":
        case "spectate":
          SpectateArg.run(player, label, args);
          break;
        case "list":
          ListArg.run(player);
          break;
        default:
          HelpArg.run(player, label);
          break;
      }
    } else {
      JoinArg.run(player, label, args);
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

    if (args.length == 1) {
      arguments.add("join");
      arguments.add("leave");
      arguments.add("kit");
      arguments.add("list");

      StringUtil.copyPartialMatches(args[0], arguments, completion);
    } else {
      if (args[0].equalsIgnoreCase("join")) {
        completion = JoinArg.tabComplete(player, args);
      } else if (args[0].equalsIgnoreCase("spectate")) {
        completion = SpectateArg.tabComplete(player, args);
      }
    }

    Collections.sort(completion);
    return completion;
  }
}

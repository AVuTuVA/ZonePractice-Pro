package dev.lokspel.practice.command.ffa.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.arena.arenas.FfaArena;
import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import dev.lokspel.practice.manager.fight.ffa.game.Ffa;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class JoinArg {

  private JoinArg() {}

  public static void run(Player player, String label, String[] args) {
    if (args.length > 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getFfa()
              .getCommand()
              .getJoin()
              .getHelp()
              .replace("%label%", label));
      return;
    }

    if (args.length < 2) {
      Profile profile = ProfileManager.getInstance().getProfile(player);
      if (!profile.getStatus().equals(ProfileStatus.LOBBY)) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getFfa().getCommand().getJoin().getCantJoinFfa());
        return;
      }

      FfaManager.getInstance().getArenaSelectorGui().update();
      FfaManager.getInstance().getArenaSelectorGui().open(player);
      return;
    }

    FfaArena arena = ArenaManager.getInstance().getFfaArena(args[1]);
    FfaManager.getInstance().joinArena(player, arena);
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (args.length != 2 || !profile.getStatus().equals(ProfileStatus.LOBBY)) {
      return arguments;
    }

    for (FfaArena arena : ArenaManager.getInstance().getFfaArenas()) {
      Ffa ffa = arena.getFfa();
      if (arena.isEnabled() && ffa != null && ffa.isOpen()) {
        arguments.add(arena.getName());
      }
    }

    return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
  }
}

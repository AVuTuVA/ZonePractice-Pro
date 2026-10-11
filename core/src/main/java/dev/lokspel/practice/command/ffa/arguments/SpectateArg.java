package dev.lokspel.practice.command.ffa.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.arena.arenas.FfaArena;
import dev.lokspel.practice.manager.fight.ffa.game.Ffa;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public final class SpectateArg {

  private SpectateArg() {}

  public static void run(Player player, String label, String[] args) {
    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getFfa()
              .getCommand()
              .getSpectate()
              .getHelp()
              .replace("%label%", label));
      return;
    }

    FfaArena ffaArena = ArenaManager.getInstance().getFfaArena(args[1]);
    if (ffaArena == null) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getFfa().getCommand().getSpectate().getArenaNotFound());
      return;
    }

    Ffa ffa = ffaArena.getFfa();
    if (ffa == null || !ffa.isOpen()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getFfa()
              .getCommand()
              .getSpectate()
              .getArenaClosed()
              .replace("%arena%", ffaArena.getDisplayName()));
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (!profile.getStatus().equals(ProfileStatus.LOBBY)
        && !profile.getStatus().equals(ProfileStatus.SPECTATE)) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getFfa().getCommand().getSpectate().getCantJoinFfa());
      return;
    }

    ffa.addSpectator(player, null, true, true);
  }

  public static List<String> tabComplete(Player player, String[] args) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    List<String> arguments = new ArrayList<>();

    if (!profile.getStatus().equals(ProfileStatus.LOBBY)
        && !profile.getStatus().equals(ProfileStatus.SPECTATE)) {
      return arguments;
    }

    if (args.length == 2) {
      for (FfaArena ffaArena : ArenaManager.getInstance().getFfaArenas()) {
        Ffa ffa = ffaArena.getFfa();
        if (ffa != null && ffa.isOpen()) {
          arguments.add(ffaArena.getName());
        }
      }

      return StringUtil.copyPartialMatches(args[1], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

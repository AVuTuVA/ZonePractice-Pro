package dev.lokspel.practice.command.singlecommands;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import dev.lokspel.practice.manager.fight.ffa.game.Ffa;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import dev.lokspel.practice.manager.fight.match.type.duel.Duel;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class LeaveCommand extends BukkitCommand {

  public LeaveCommand() {
    super("leave");

    try {
      Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
      field.setAccessible(true);
      CommandMap map = (CommandMap) field.get(Bukkit.getServer());
      map.register(this.getName(), this);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      Common.sendConsoleMmMessage("<red>Error: " + e.getMessage());
    }
  }

  @Override
  public boolean execute(CommandSender commandSender, String s, String[] strings) {
    if (!(commandSender instanceof Player player)) {
      return false;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null) {
      return false;
    }

    // First check if player is in an FFA as a participant
    Ffa ffa = FfaManager.getInstance().getFfaByPlayer(player);
    if (ffa != null) {
      if (ffa.isFfaLeaveBlocked() && ffa.isFfaInCombat(player)) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getFfa().getCommand().getLeave().getInCombat());
        return true;
      }

      ffa.removePlayer(player);
      return true;
    }

    // Then check if player is spectating an FFA
    Ffa spectatingFfa = FfaManager.getInstance().getFfaBySpectator(player);
    if (spectatingFfa != null) {
      spectatingFfa.removeSpectator(player);
      return true;
    }

    // Check if player is in a regular match as a spectator
    Match liveMatch = MatchManager.getInstance().getLiveMatchBySpectator(player);
    if (liveMatch != null) {
      liveMatch.removeSpectator(player);
      return true;
    }

    // Otherwise, check if player is in a match as a participant
    if (!profile.getStatus().equals(ProfileStatus.MATCH)) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getLeave().getNotInMatch());
      return false;
    }

    Match match = MatchManager.getInstance().getLiveMatchByPlayer(player);
    if (match == null) {
      return false;
    }

    if (match instanceof Duel duel) {
      if (!duel.isRanked()) {
        if (!PracticeConfig.settings()
            .getMatchSettings()
            .getLeaveCommand()
            .getWeightClass()
            .isUnranked()) {
          Common.sendMmMessage(
              player, PracticeConfig.language().getCommand().getLeave().getCantLeaveDuelUnranked());
          return false;
        }
      } else {
        if (!PracticeConfig.settings()
            .getMatchSettings()
            .getLeaveCommand()
            .getWeightClass()
            .isRanked()) {
          Common.sendMmMessage(
              player, PracticeConfig.language().getCommand().getLeave().getCantLeaveDuelRanked());
          return false;
        }
      }
    }

    match.removePlayer(player, true);

    return true;
  }

  @Override
  public List<String> tabComplete(CommandSender sender, String alias, String[] args)
      throws IllegalArgumentException {
    return Collections.emptyList();
  }
}

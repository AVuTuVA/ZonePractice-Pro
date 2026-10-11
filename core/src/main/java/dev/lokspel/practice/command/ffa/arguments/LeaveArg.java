package dev.lokspel.practice.command.ffa.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import dev.lokspel.practice.manager.fight.ffa.game.Ffa;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class LeaveArg {

  private LeaveArg() {}

  public static void run(Player player) {
    // First check if player is in an FFA as a participant
    Ffa ffa = FfaManager.getInstance().getFfaByPlayer(player);

    if (ffa != null) {
      if (ffa.isFfaLeaveBlocked() && ffa.isFfaInCombat(player)) {
        Common.sendMmMessage(player, LanguageManager.getString("FFA.COMMAND.LEAVE.IN-COMBAT"));
        return;
      }

      ffa.removePlayer(player);
      return;
    }

    // Then check if player is spectating an FFA
    Ffa spectatingFfa = FfaManager.getInstance().getFfaBySpectator(player);
    if (spectatingFfa != null) {
      spectatingFfa.removeSpectator(player);
      return;
    }

    // Player is not in any FFA
    Common.sendMmMessage(player, LanguageManager.getString("FFA.COMMAND.LEAVE.NOT-IN-FFA"));
  }
}

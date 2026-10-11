package dev.lokspel.practice.command.ffa.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import dev.lokspel.practice.manager.fight.ffa.game.Ffa;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class KitArg {

  private KitArg() {}

  public static void run(Player player) {
    Ffa ffa = FfaManager.getInstance().getFfaByPlayer(player);
    if (ffa == null) {
      Common.sendMmMessage(player, LanguageManager.getString("FFA.COMMAND.KIT.NOT-IN-FFA"));
      return;
    }

    if (ffa.isFfaKitBlocked() && ffa.isFfaInCombat(player)) {
      Common.sendMmMessage(player, LanguageManager.getString("FFA.COMMAND.KIT.IN-COMBAT"));
      return;
    }

    ffa.getLadderSelectorGui().update();
    ffa.getLadderSelectorGui().open(player);
  }
}

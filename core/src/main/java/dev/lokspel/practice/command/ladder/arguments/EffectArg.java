package dev.lokspel.practice.command.ladder.arguments;

import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.ladder.LadderSetupManager;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public final class EffectArg {

  private EffectArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.LADDER.NO-PERMISSION"));
      return;
    }

    if (args.length != 3) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.EFFECT.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    Ladder ladder = LadderManager.getInstance().getLadder(args[2]);
    if (ladder == null) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.EFFECT.NOT-EXISTS")
              .replace("%ladder%", args[2]));
      return;
    }

    if (ladder.isEnabled()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.EFFECT.LADDER-ENABLED")
              .replace("%ladder%", ladder.getDisplayName()));
      return;
    }

    List<PotionEffect> effects = new ArrayList<>(player.getActivePotionEffects());
    if (effects.isEmpty()) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.EFFECT.NO-EFFECTS"));
      return;
    }

    ladder.getKitData().setEffects(effects);
    LadderSetupManager.getInstance()
        .getLadderSetupGuis()
        .get(ladder)
        .get(GuiType.Ladder_Inventory)
        .update();

    Common.sendMmMessage(
        player,
        LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.EFFECT.SET-EFFECTS")
            .replace("%ladder%", ladder.getDisplayName()));
  }

  public static List<String> tabComplete(Player player, String[] args) {
    return IconArg.tabComplete(player, args);
  }
}

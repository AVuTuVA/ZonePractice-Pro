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
import org.bukkit.util.StringUtil;

public final class InventoryArg {

  private InventoryArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.LADDER.NO-PERMISSION"));
      return;
    }

    if (args.length != 3) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.INVENTORY.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    Ladder ladder = LadderManager.getInstance().getLadder(args[2]);
    if (ladder == null) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.INVENTORY.NOT-EXISTS")
              .replace("%ladder%", args[2]));
      return;
    }

    if (ladder.isEnabled()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.INVENTORY.LADDER-ENABLED")
              .replace("%ladder%", ladder.getDisplayName()));
      return;
    }

    ladder.getKitData().setKitData(player, true);

    LadderSetupManager.getInstance()
        .getLadderSetupGuis()
        .get(ladder)
        .get(GuiType.Ladder_Inventory)
        .update();

    Common.sendMmMessage(
        player,
        LanguageManager.getString("COMMAND.LADDER.ARGUMENTS.INVENTORY.SAVED-INVENTORY")
            .replace("%ladder%", ladder.getDisplayName()));
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();
    if (!player.hasPermission("ap.setup")) {
      return arguments;
    }

    if (args.length == 3) {
      for (Ladder ladder : LadderManager.getInstance().getLadders()) {
        arguments.add(ladder.getName());
      }

      return StringUtil.copyPartialMatches(args[2], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

package dev.lokspel.practice.command.arena.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.gui.guis.arena.ArenaCreateGui;
import dev.lokspel.practice.util.Common;
import java.text.Normalizer;
import org.bukkit.entity.Player;

public final class CreateArg {

  private CreateArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getCommand().getArena().getNoPermission());
      return;
    }

    if (args.length != 2) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getArena()
              .getArguments()
              .getCreate()
              .getCommandHelp()
              .replace("%label%", label));
      return;
    }

    if (ArenaManager.getInstance().getArenaList().size() == 243) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getArena()
              .getArguments()
              .getCreate()
              .getReachedMax());
      return;
    }

    String name = args[1];
    String response = checkName(name);
    if (response != null) {
      Common.sendMmMessage(player, response);
      return;
    }

    new ArenaCreateGui(name).open(player);
  }

  public static String checkName(String input) {
    if (input.isEmpty()) {
      return PracticeConfig.language().getArena().getCreate().getNoName();
    } else if (input.contains(" ")) {
      return PracticeConfig.language().getArena().getCreate().getOnlyAlphanumeric();
    } else if (!Normalizer.isNormalized(input, Normalizer.Form.NFD)) {
      return PracticeConfig.language().getArena().getCreate().getNoAccents();
    } else if (input.length() < 4) {
      return PracticeConfig.language().getArena().getCreate().getFewChar();
    } else if (input.length() > 16) {
      return PracticeConfig.language().getArena().getCreate().getManyChar();
    } else if (ArenaManager.getInstance().getArena(input) != null) {
      return PracticeConfig.language().getArena().getCreate().getNameTaken();
    } else {
      return null;
    }
  }
}

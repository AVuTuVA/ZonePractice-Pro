package dev.lokspel.practice.command.ffa.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.arena.arenas.FfaArena;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public final class ListArg {

  private ListArg() {}

  public static void run(Player player) {
    List<String> ffas = new ArrayList<>();
    for (FfaArena ffaArena : ArenaManager.getInstance().getFfaArenas()) {
      if (ffaArena.getFfa().isOpen()) {
        ffas.add(
            PracticeConfig.language()
                .getFfa()
                .getCommand()
                .getList()
                .getFormat()
                .replace("%arena%", ffaArena.getDisplayName())
                .replace("%players%", String.valueOf(ffaArena.getFfa().getPlayers().size())));
      }
    }

    for (String line : PracticeConfig.language().getFfa().getCommand().getList().getList()) {
      if (line.contains("%arenas%")) {
        if (!ffas.isEmpty()) {
          for (String ffa : ffas) {
            Common.sendMmMessage(player, ffa);
          }
        } else {
          Common.sendMmMessage(
              player, PracticeConfig.language().getFfa().getCommand().getList().getEmpty());
        }
      } else {
        Common.sendMmMessage(player, line);
      }
    }
  }
}

package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class PartyHelpArg {

  private PartyHelpArg() {}

  public static void helpCommand(Player player, String label) {
    String shortcut = PracticeConfig.settings().getChat().getPartyChat().getShortcut();

    for (String line : PracticeConfig.language().getCommand().getParty().getArguments().getHelp()) {
      if (line.contains("%shortcut%") && shortcut.isEmpty()) {
        continue;
      }

      Common.sendMmMessage(player, line.replace("%label%", label).replace("%shortcut%", shortcut));
    }
  }
}

package dev.lokspel.practice.command.party.arguments;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.ChatFormatUtil;
import dev.lokspel.practice.util.Common;
import org.bukkit.entity.Player;

public final class PartyChatArg {

  private PartyChatArg() {}

  public static void chatCommand(Player player, String[] args) {
    if (!PracticeConfig.settings().getChat().getPartyChat().isEnabled()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language().getCommand().getParty().getArguments().getChat().getDisabled());
      return;
    }

    Party party = PartyManager.getInstance().getParty(player);
    if (party == null) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language().getCommand().getParty().getArguments().getChat().getNoParty());
      return;
    }

    if (!party.canUsePartyChat(player)) {
      Common.sendMmMessage(
          player, PracticeConfig.language().getParty().getPartyChatDisabledByLeader());
      return;
    }

    if (args.length == 1) {
      Profile profile = ProfileManager.getInstance().getProfile(player);
      profile.setPartyChat(!profile.isPartyChat());

      if (profile.isPartyChat()) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getParty()
                .getArguments()
                .getChat()
                .getChatEnabled());
      } else {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getCommand()
                .getParty()
                .getArguments()
                .getChat()
                .getChatDisabled());
      }
      return;
    }

    StringBuilder message = new StringBuilder();
    for (int i = 1; i < args.length; i++) {
      message.append(args[i]).append(" ");
    }

    party.sendMessage(
        ChatFormatUtil.buildPartyChatMessage(player).replace("%%message%%", message.toString()));
  }
}

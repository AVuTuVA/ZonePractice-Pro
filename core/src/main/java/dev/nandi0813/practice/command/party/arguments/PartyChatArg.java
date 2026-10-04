package dev.nandi0813.practice.command.party.arguments;

import dev.nandi0813.practice.manager.backend.ConfigManager;
import dev.nandi0813.practice.manager.backend.LanguageManager;
import dev.nandi0813.practice.manager.party.Party;
import dev.nandi0813.practice.manager.party.PartyManager;
import dev.nandi0813.practice.manager.profile.Profile;
import dev.nandi0813.practice.manager.profile.ProfileManager;
import dev.nandi0813.practice.util.ChatFormatUtil;
import dev.nandi0813.practice.util.Common;
import org.bukkit.entity.Player;

public final class PartyChatArg {

    private PartyChatArg() {}

    public static void ChatCommand(Player player, String[] args) {
        if (!ConfigManager.isPartyChatEnabled()) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.CHAT.DISABLED"));
            return;
        }

        Party party = PartyManager.getInstance().getParty(player);
        if (party == null) {
            Common.sendMMMessage(player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.CHAT.NO-PARTY"));
            return;
        }

        if (!party.canUsePartyChat(player)) {
            Common.sendMMMessage(player, LanguageManager.getString("PARTY.PARTY-CHAT-DISABLED-BY-LEADER"));
            return;
        }

        if (args.length == 1) {
            Profile profile = ProfileManager.getInstance().getProfile(player);
            profile.setPartyChat(!profile.isPartyChat());

            if (profile.isPartyChat())
                Common.sendMMMessage(player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.CHAT.CHAT-ENABLED"));
            else
                Common.sendMMMessage(player, LanguageManager.getString("COMMAND.PARTY.ARGUMENTS.CHAT.CHAT-DISABLED"));
            return;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++)
            message.append(args[i]).append(" ");

        party.sendMessage(ChatFormatUtil.buildPartyChatMessage(player).replace("%%message%%", message.toString()));
    }

}

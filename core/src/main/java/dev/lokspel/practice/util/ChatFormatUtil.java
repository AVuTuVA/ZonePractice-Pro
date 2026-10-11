package dev.lokspel.practice.util;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.group.Group;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ChatFormatUtil {

  private ChatFormatUtil() {}

  private static String normalizeStaticSpacing(String text) {
    if (text == null || text.isEmpty()) {
      return text;
    }

    return text.replaceAll(" {2,}", " ");
  }

  public static String buildPartyChatMessage(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);

    String playerName =
        profile != null
            ? AstralPractice.getMiniMessage()
                .serialize(NameFormatUtil.resolveFullName(profile, player.getName()))
            : player.getName();

    return normalizeStaticSpacing(
        PracticeConfig.language()
            .getGeneralChat()
            .getPartyChat()
            .replace("%%player%%", playerName));
  }

  public static String buildStaffChatMessage(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);

    String playerName =
        profile != null
            ? AstralPractice.getMiniMessage()
                .serialize(NameFormatUtil.resolveFullName(profile, player.getName()))
            : player.getName();

    return normalizeStaticSpacing(
        PracticeConfig.language()
            .getGeneralChat()
            .getStaffChat()
            .replace("%%player%%", playerName));
  }

  public static String buildServerChatMessage(Profile profile, Player player) {
    String format;

    if (PracticeConfig.settings().getPlayer().getGroupChat().isEnabled()) {
      Group group = profile.getGroup();

      if (group != null && group.getChatFormat() != null) {
        format = group.getChatFormat();
      } else {
        format = PracticeConfig.language().getGeneralChat().getServerChat();
      }
    } else {
      format = PracticeConfig.language().getGeneralChat().getServerChat();
    }

    String decoratedPlayer =
        AstralPractice.getMiniMessage()
            .serialize(NameFormatUtil.resolveFullName(profile, player.getName()));

    return normalizeStaticSpacing(format.replace("%%player%%", decoratedPlayer));
  }

  public static List<Player> getStaffRecipients() {
    List<Player> staff = new ArrayList<>();

    for (Player online : Bukkit.getOnlinePlayers()) {
      if (online.hasPermission("ap.staffmode.chat")) {
        staff.add(online);
      }
    }

    return staff;
  }
}

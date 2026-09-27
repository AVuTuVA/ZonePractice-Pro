package dev.nandi0813.practice.listener;

import dev.nandi0813.practice.manager.backend.LanguageManager;
import dev.nandi0813.practice.util.Common;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Locale;

public class PlayerCommandPreprocess implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onReloadCommand(PlayerCommandPreprocessEvent e) {
        String command = e.getMessage().split(" ", 2)[0]
                .substring(1)
                .toLowerCase(Locale.ROOT);

        if (command.startsWith("bukkit:")) {
            command = command.substring("bukkit:".length());
        }

        if (command.equals("reload") || command.equals("rl")) {
            e.setCancelled(true);
            Common.sendMMMessage(e.getPlayer(), LanguageManager.getString("RELOAD-DISABLED"));
        }
    }
}
package dev.lokspel.practice.command.arena.arguments.set;

import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.arena.arenas.interfaces.DisplayArena;
import dev.lokspel.practice.manager.backend.ConfigManager;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.util.PlayerUtil;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.arena.ArenaGuiSetupManager;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

public final class IconArg {

  private IconArg() {}

  public static void run(Player player, String label, String[] args) {
    if (!player.hasPermission("ap.setup")) {
      Common.sendMmMessage(player, LanguageManager.getString("COMMAND.ARENA.NO-PERMISSION"));
      return;
    }

    if (args.length != 3) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.ICON.COMMAND-HELP")
              .replace("%label%", label));
      return;
    }

    DisplayArena arena = ArenaManager.getInstance().getArena(args[2]);
    if (arena == null) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.ICON.ARENA-NOT-EXISTS")
              .replace("%arena%", args[2]));
      return;
    }

    if (arena.isEnabled() && !ConfigManager.getBoolean("SETUP.ALLOW-ICON-EDIT-WHILE-ENABLED")) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.ICON.CANT-EDIT")
              .replace("%arena%", arena.getName()));
      return;
    }

    ItemStack icon = PlayerUtil.getPlayerMainHand(player);
    if (icon.getType().equals(Material.AIR)) {
      Common.sendMmMessage(
          player, LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.ICON.ICON-IN-HAND"));
      return;
    }

    if (icon.getItemMeta() == null || !icon.getItemMeta().hasDisplayName()) {
      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.ICON.NO-DISPLAYNAME")
              .replace("%arena%", arena.getDisplayName()));
      return;
    }

    arena.setIcon(icon);

    GuiManager.getInstance().searchGui(GuiType.Arena_Summary).update();
    ArenaGuiSetupManager.getInstance()
        .getArenaSetupGuis()
        .get(arena)
        .get(GuiType.Arena_Main)
        .update();

    Common.sendMmMessage(
        player,
        LanguageManager.getString("COMMAND.ARENA.ARGUMENTS.ICON.SAVED-ICON")
            .replace("%arena%", arena.getName()));
  }

  public static List<String> tabComplete(Player player, String[] args) {
    List<String> arguments = new ArrayList<>();
    if (!player.hasPermission("ap.setup")) {
      return arguments;
    }

    if (args.length == 3) {
      for (DisplayArena arena : ArenaManager.getInstance().getArenaList()) {
        arguments.add(arena.getName());
      }

      return StringUtil.copyPartialMatches(args[2], arguments, new ArrayList<>());
    }

    return arguments;
  }
}

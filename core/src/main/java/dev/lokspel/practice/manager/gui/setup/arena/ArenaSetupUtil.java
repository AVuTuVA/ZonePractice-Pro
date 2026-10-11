package dev.lokspel.practice.manager.gui.setup.arena;

import dev.lokspel.practice.manager.arena.arenas.Arena;
import dev.lokspel.practice.manager.arena.arenas.interfaces.DisplayArena;
import dev.lokspel.practice.manager.arena.util.ArenaUtil;
import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.ItemCreateUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ArenaSetupUtil {
  ;

  @Getter private static final Map<ItemStack, DisplayArena> arenaMarkerList = new HashMap<>();

  // Name & Icon & Information item
  public static ItemStack getNameItem(Arena arena) {
    List<String> lore = new ArrayList<>();
    for (String line : GuiFile.getStringList("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.ARENA-NAME.LORE")) {
      lore.add(
          line.replace("%arenaName%", arena.getName())
              .replace("%arenaDisplayName%", arena.getDisplayName())
              .replace("%arenaType%", arena.getType().getName()));
    }

    if (arena.getIcon() != null) {
      return ItemCreateUtil.createItem(
          arena.getIcon(),
          GuiFile.getString("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.ARENA-NAME.NAME")
              .replace("%arenaDisplayName%", arena.getDisplayName())
              .replace("%arenaName%", arena.getName()),
          lore);
    } else {
      return ItemCreateUtil.createItem(
          GuiFile.getString("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.ARENA-NAME.NAME")
              .replace("%arenaDisplayName%", arena.getDisplayName())
              .replace("%arenaName%", arena.getName()),
          Material.valueOf(
              GuiFile.getString("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.ARENA-NAME.MATERIAL")),
          lore);
    }
  }

  // Status item
  public static ItemStack getStatusItem(Arena arena) {
    if (arena.isEnabled()) {
      return GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.STATUS.ENABLED").get();
    } else {
      return GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.STATUS.DISABLED").get();
    }
  }

  // Arena copies item
  public static ItemStack getArenaCopiesItem(Arena arena) {
    if (!arena.isBuild()) {
      return null;
    }

    int size = arena.getCopies().size();
    if (size < 1) {
      size = 1;
    }

    ItemStack item = GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.COPIES").get();
    item.setAmount(size);

    return item;
  }

  public static ItemStack getCopyArenaItem(Arena arena, int number) {
    GuiItem guiItem = GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-COPY.ICONS.COPY-ARENA");

    guiItem
        .replace("%arenaName%", arena.getName())
        .replace("%arenaDisplayName%", arena.getDisplayName())
        .replace("%copyNumber%", String.valueOf(number));

    ItemStack itemStack = guiItem.get();
    itemStack.setAmount(number);

    return itemStack;
  }

  // Locations item
  public static ItemStack getLocationItem(Arena arena) {
    GuiItem guiItem;
    if (arena.isBuild()) {
      guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.LOCATION.BUILD")
              .replace("%arenaName%", arena.getName())
              .replace("%arenaDisplayName%", arena.getDisplayName())
              .replace(
                  "%corner1%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getCorner1())))
              .replace(
                  "%corner2%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getCorner2())))
              .replace(
                  "%position1%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getPosition1())))
              .replace(
                  "%position2%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getPosition2())))
              .replace("%bed1%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getBedLoc1())))
              .replace("%bed2%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getBedLoc2())))
              .replace(
                  "%portal1%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getPortalLoc1())))
              .replace(
                  "%portal2%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getPortalLoc2())))
              .replace("%sideBuildLimit%", String.valueOf(arena.getSideBuildLimit()))
              .replace(
                  "%buildMaxY%",
                  arena.isBuildMax() ? String.valueOf(arena.getBuildMaxValue()) : "<red>Not Set")
              .replace(
                  "%deathZoneY%",
                  arena.isDeadZone() ? String.valueOf(arena.getDeadZoneValue()) : "<red>Not Set");
    } else {
      guiItem =
          GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.LOCATION.NOT-BUILD")
              .replace("%arenaName%", arena.getName())
              .replace("%arenaDisplayName%", arena.getDisplayName())
              .replace(
                  "%corner1%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getCorner1())))
              .replace(
                  "%corner2%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getCorner2())))
              .replace(
                  "%position1%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getPosition1())))
              .replace(
                  "%position2%", Common.mmToNormal(ArenaUtil.convertLocation(arena.getPosition2())))
              .replace(
                  "%deathZoneY%",
                  arena.isDeadZone() ? String.valueOf(arena.getDeadZoneValue()) : "<red>Not Set");
    }

    return guiItem.get();
  }

  public static ItemStack getFreezeItem(Arena arena) {
    if (arena.isFrozen()) {
      return GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.FREEZE.FROZEN").get();
    } else {
      return GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-MAIN.ICONS.FREEZE.NOT-FROZEN").get();
    }
  }

  public static ItemStack getCopyGuiNavMainItem(Arena arena, int copies) {
    return GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-COPY.ICONS.NAV-MAIN")
        .replace("%arenaDisplayName%", arena.getDisplayName())
        .replace("%arenaName%", arena.getName())
        .replace("%copies%", String.valueOf(copies))
        .get();
  }

  public static ItemStack getAssignedLadderItem(Ladder ladder) {
    return GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-LADDERS-SINGLE.ICONS.LADDER-ICONS.ASSIGNED")
        .replace("%ladderDisplayName%", ladder.getDisplayName())
        .replace("%ladderName%", ladder.getName())
        .get();
  }

  public static ItemStack getNotAssignedLadderItem(Ladder ladder) {
    return GuiFile.getGuiItem(
            "GUIS.SETUP.ARENA.ARENA-LADDERS-SINGLE.ICONS.LADDER-ICONS.NOT-ASSIGNED")
        .replace("%ladderDisplayName%", ladder.getDisplayName())
        .replace("%ladderName%", ladder.getName())
        .get();
  }

  public static ItemStack getDisabledLadderItem(Ladder ladder) {
    return GuiFile.getGuiItem("GUIS.SETUP.ARENA.ARENA-LADDERS-SINGLE.ICONS.LADDER-ICONS.DISABLED")
        .replace("%ladderDisplayName%", ladder.getDisplayName())
        .replace("%ladderName%", ladder.getName())
        .get();
  }

  public static ItemStack getNonCompatibleLadderItem(Ladder ladder) {
    return GuiFile.getGuiItem(
            "GUIS.SETUP.ARENA.ARENA-LADDERS-SINGLE.ICONS.LADDER-ICONS.NOT-COMPATIBLE")
        .replace("%ladderDisplayName%", ladder.getDisplayName())
        .replace("%ladderName%", ladder.getName())
        .get();
  }
}

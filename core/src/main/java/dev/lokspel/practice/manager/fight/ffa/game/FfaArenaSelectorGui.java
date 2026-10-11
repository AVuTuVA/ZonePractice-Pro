package dev.lokspel.practice.manager.fight.ffa.game;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.arena.arenas.FfaArena;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.ffa.FfaManager;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FfaArenaSelectorGui extends Gui {

  private static final String BUILD_ON =
      PracticeConfig.guis()
          .getGuis()
          .getFfa()
          .getArenaSelector()
          .getIcons()
          .getArena()
          .getBuildStatus()
          .getEnabled();
  private static final String BUILD_OFF =
      PracticeConfig.guis()
          .getGuis()
          .getFfa()
          .getArenaSelector()
          .getIcons()
          .getArena()
          .getBuildStatus()
          .getDisabled();
  private static final GuiItem ARENA_ITEM =
      BackendUtil.getGuiItem(
          PracticeConfig.guis().getGuis().getFfa().getArenaSelector().getIcons().getArena());
  private static final ItemStack FILLER_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis().getGuis().getFfa().getArenaSelector().getIcons().getFiller())
          .get();

  private final Map<Integer, FfaArena> arenaSlots = new HashMap<>();

  public FfaArenaSelectorGui() {
    super(GuiType.FFA_Arena_Selector);

    int rows = Math.max(1, PracticeConfig.guis().getGuis().getFfa().getArenaSelector().getRows());
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getFfa().getArenaSelector().getTitle(), rows));
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    Inventory inventory = gui.get(1);
    inventory.clear();
    arenaSlots.clear();

    List<FfaArena> openArenas =
        ArenaManager.getInstance().getFfaArenas().stream()
            .filter(arena -> arena.isEnabled() && arena.getFfa() != null && arena.getFfa().isOpen())
            .toList();

    for (FfaArena arena : openArenas) {
      Ffa ffa = arena.getFfa();

      GuiItem guiItem =
          ARENA_ITEM
              .cloneItem()
              .replace("%arena%", arena.getDisplayName())
              .replace("%players%", String.valueOf(ffa.getPlayers().size()))
              .replace("%build_status%", arena.isBuild() ? BUILD_ON : BUILD_OFF)
              .replace("%rekit_after_kill%", arena.isReKitAfterKill() ? BUILD_ON : BUILD_OFF)
              .replace("%health_reset_on_kill%", arena.isHealthResetOnKill() ? BUILD_ON : BUILD_OFF)
              .replace("%lobby_after_death%", arena.isLobbyAfterDeath() ? BUILD_ON : BUILD_OFF)
              .replace("%ladders%", String.valueOf(arena.getAssignedLadders().size()));

      if (arena.getIcon() != null) {
        guiItem.setBaseItem(arena.getIcon());
      }

      int slot = inventory.firstEmpty();
      if (slot == -1) {
        break;
      }

      inventory.setItem(slot, guiItem.get());
      arenaSlots.put(slot, arena);
    }

    for (int i = 0; i < inventory.getSize(); i++) {
      if (inventory.getItem(i) == null) {
        inventory.setItem(i, FILLER_ITEM);
      }
    }

    this.updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    Player player = (Player) e.getWhoClicked();
    int slot = e.getSlot();

    if (!arenaSlots.containsKey(slot)) {
      return;
    }

    FfaArena arena = arenaSlots.get(slot);
    if (arena == null) {
      return;
    }

    if (!FfaManager.getInstance().joinArena(player, arena)) {
      player.closeInventory();
    }
  }
}

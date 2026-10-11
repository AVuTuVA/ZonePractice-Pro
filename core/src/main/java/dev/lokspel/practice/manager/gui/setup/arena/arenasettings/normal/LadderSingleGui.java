package dev.lokspel.practice.manager.gui.setup.arena.arenasettings.normal;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.arenas.Arena;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.arena.ArenaGuiSetupManager;
import dev.lokspel.practice.manager.gui.setup.arena.ArenaSetupUtil;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.PageUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LadderSingleGui extends Gui {

  private final int spaces = 45;
  private final Map<Integer, Map<Integer, String>> ladderSlots = new HashMap<>();
  private final Arena arena;

  public LadderSingleGui(Arena arena) {
    super(GuiType.Arena_Ladders_Single);

    this.arena = arena;

    build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    Set<NormalLadder> assignableLadders = arena.getAssignableLadders();
    List<NormalLadder> ladders = new ArrayList<>(LadderManager.getInstance().getLadders());
    ladders.sort(Comparator.comparing(NormalLadder::getName, String::compareToIgnoreCase));

    Map<Integer, Inventory> existingInventories = new HashMap<>(gui);
    Map<Integer, Inventory> newGui = new HashMap<>();
    Map<Integer, Map<Integer, String>> newLadderSlots = new HashMap<>();

    for (int page = 1; PageUtil.isPageValid(ladders.size(), page, spaces) || page == 1; page++) {
      Inventory inventory = existingInventories.get(page);
      String title =
          PracticeConfig.guis()
              .getGuis()
              .getSetup()
              .getArena()
              .getArenaLaddersSingle()
              .getTitle()
              .replace("%arenaName%", arena.getName())
              .replace("%page%", String.valueOf(page));
      if (inventory == null || inventory.getSize() != 6 * 9) {
        inventory = InventoryUtil.createInventory(title, 6);
      } else {
        inventory.clear();
      }
      newGui.put(page, inventory);

      for (int i = 45; i < 54; i++) {
        inventory.setItem(i, GuiManager.getFILLER_ITEM());
      }

      Map<Integer, String> pageSlots = new HashMap<>();
      int startIndex = (page - 1) * spaces;
      int endIndex = Math.min(startIndex + spaces, ladders.size());
      for (int i = startIndex; i < endIndex; i++) {
        NormalLadder ladder = ladders.get(i);
        ItemStack ladderItem;

        if (assignableLadders.contains(ladder)) {
          if (arena.getAssignedLadderTypes().contains(ladder.getType()) && ladder.isEnabled()) {
            if (arena.getAssignedLadders().contains(ladder)) {
              ladderItem = ArenaSetupUtil.getAssignedLadderItem(ladder);
            } else {
              ladderItem = ArenaSetupUtil.getNotAssignedLadderItem(ladder);
            }
          } else {
            ladderItem = ArenaSetupUtil.getDisabledLadderItem(ladder);
          }
        } else {
          ladderItem = ArenaSetupUtil.getNonCompatibleLadderItem(ladder);
        }

        int slot = inventory.firstEmpty();
        if (slot == -1 || slot >= 45) {
          break;
        }

        inventory.setItem(slot, ladderItem);
        pageSlots.put(slot, ladder.getName());
      }
      newLadderSlots.put(page, pageSlots);

      ItemStack left =
          page == 1
              ? BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getSetup()
                          .getArena()
                          .getArenaLaddersSingle()
                          .getIcons()
                          .getBackTo())
                  .get()
              : BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getSetup()
                          .getArena()
                          .getArenaLaddersSingle()
                          .getIcons()
                          .getPageLeft())
                  .replace("%page%", String.valueOf(page - 1))
                  .get();
      inventory.setItem(45, left);

      ItemStack right =
          PageUtil.isPageValid(ladders.size(), page + 1, spaces)
              ? BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getSetup()
                          .getArena()
                          .getArenaLaddersSingle()
                          .getIcons()
                          .getPageRight())
                  .replace("%page%", String.valueOf(page + 1))
                  .get()
              : GuiManager.getFILLER_ITEM();
      inventory.setItem(53, right);
    }

    updatePages(newGui, ladderSlots, newLadderSlots);
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    final Player player = (Player) e.getWhoClicked();
    Inventory inventory = e.getView().getTopInventory();

    int slot = e.getRawSlot();
    ItemStack item = e.getCurrentItem();
    e.setCancelled(true);

    if (inventory.getSize() <= slot) {
      return;
    }
    if (item == null) {
      return;
    }

    int page = inGuiPlayers.getOrDefault(player, 1);

    if (slot == 45) {
      if (page == 1) {
        ArenaGuiSetupManager.getInstance()
            .getArenaSetupGuis()
            .get(arena)
            .get(GuiType.Arena_Ladders_Type)
            .open(player);
      } else if (gui.containsKey(page - 1)) {
        open(player, page - 1);
      }
    } else if (slot == 53) {
      if (gui.containsKey(page + 1)) {
        open(player, page + 1);
      }
    } else if (ladderSlots.containsKey(page) && ladderSlots.get(page).containsKey(slot)) {
      NormalLadder ladder = LadderManager.getInstance().getLadder(ladderSlots.get(page).get(slot));
      if (ladder != null) {
        if (arena.getAssignedLadders().contains(ladder)) {
          if (!arena.isEnabled()) {
            arena.getAssignedLadders().remove(ladder);

            update();
            GuiManager.getInstance().searchGui(GuiType.Arena_Summary).update();
          } else {
            Common.sendMmMessage(
                player,
                PracticeConfig.language().getCommand().getSetup().getArena().getCantRemoveLadder());
          }
        } else {
          if (arena.getAssignedLadderTypes().contains(ladder.getType()) && ladder.isEnabled()) {
            arena.getAssignedLadders().add(ladder);

            update();
            GuiManager.getInstance().searchGui(GuiType.Arena_Summary).update();
          }
        }
      } else {
        update();
      }
    }
  }
}

package dev.lokspel.practice.manager.gui.setup.ladder;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.PageUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class LadderSummaryGui extends Gui {

  private final int spaces = 45;
  private final Map<Integer, Map<Integer, NormalLadder>> ladderSlots = new HashMap<>();
  private final Map<Player, Integer> backToPage = new HashMap<>();

  public LadderSummaryGui() {
    super(GuiType.Ladder_Summary);
    build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    List<NormalLadder> ladders = new ArrayList<>(LadderManager.getInstance().getLadders());
    ladders.sort(Comparator.comparing(Ladder::getName, String::compareToIgnoreCase));

    Map<Integer, Inventory> existingInventories = new HashMap<>(gui);
    Map<Integer, Inventory> newGui = new HashMap<>();
    Map<Integer, Map<Integer, NormalLadder>> newLadderSlots = new HashMap<>();

    for (int page = 1; PageUtil.isPageValid(ladders.size(), page, spaces) || page == 1; page++) {
      Inventory inventory = existingInventories.get(page);
      String title =
          PracticeConfig.guis()
              .getGuis()
              .getSetup()
              .getLadder()
              .getLadderManager()
              .getTitle()
              .replace("%page%", String.valueOf(page));
      if (inventory == null || inventory.getSize() != 6 * 9) {
        inventory = InventoryUtil.createInventory(title, 6);
      } else {
        inventory.clear();
      }
      newGui.put(page, inventory);

      for (int i = 45; i < 54; i++) {
        inventory.setItem(i, GuiManager.fillerItem());
      }
      for (int i = 0; i < 45; i++) {
        inventory.setItem(i, null);
      }

      Map<Integer, NormalLadder> pageSlots = new HashMap<>();
      int startIndex = (page - 1) * spaces;
      int endIndex = Math.min(startIndex + spaces, ladders.size());
      for (int i = startIndex; i < endIndex; i++) {
        int slot = inventory.firstEmpty();
        if (slot == -1 || slot >= 45) {
          break;
        }

        NormalLadder ladder = ladders.get(i);
        inventory.setItem(slot, getLadderItem(ladder));
        pageSlots.put(slot, ladder);
      }
      newLadderSlots.put(page, pageSlots);

      ItemStack left =
          page == 1
              ? BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getSetup()
                          .getLadder()
                          .getLadderManager()
                          .getIcons()
                          .getBackTo())
                  .get()
              : BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getSetup()
                          .getLadder()
                          .getLadderManager()
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
                          .getLadder()
                          .getLadderManager()
                          .getIcons()
                          .getPageRight())
                  .replace("%page%", String.valueOf(page + 1))
                  .get()
              : GuiManager.fillerItem();
      inventory.setItem(53, right);
    }

    updatePages(newGui, ladderSlots, newLadderSlots);
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    final Player player = (Player) e.getWhoClicked();
    Inventory inventory = e.getView().getTopInventory();

    int slot = e.getRawSlot();
    ItemStack currentItem = e.getCurrentItem();

    e.setCancelled(true);

    if (inventory.getSize() <= slot) {
      return;
    }
    if (currentItem == null) {
      return;
    }
    if (currentItem.equals(GuiManager.fillerItem())) {
      return;
    }

    int page = inGuiPlayers.getOrDefault(player, 1);
    if (slot == 45) {
      if (page == 1) {
        GuiManager.getInstance().searchGui(GuiType.Setup_Hub).open(player);
      } else {
        open(player, page - 1);
      }
    } else if (slot == 53) {
      if (gui.containsKey(page + 1)) {
        open(player, page + 1);
      }
    } else if (ladderSlots.containsKey(page) && ladderSlots.get(page).containsKey(slot)) {
      Ladder ladder = ladderSlots.get(page).get(slot);
      if (ladder == null) {
        this.update();
        return;
      }

      backToPage.put(player, page);
      LadderSetupManager.getInstance()
          .getLadderSetupGuis()
          .get(ladder)
          .get(GuiType.Ladder_Main)
          .open(player);
    }
  }

  private ItemStack getLadderItem(NormalLadder ladder) {
    String enabledStatus =
        PracticeConfig.guis()
            .getGuis()
            .getSetup()
            .getLadder()
            .getLadderManager()
            .getIcons()
            .getLadderIcon()
            .getStatusNames()
            .getEnabled();
    String disabledStatus =
        PracticeConfig.guis()
            .getGuis()
            .getSetup()
            .getLadder()
            .getLadderManager()
            .getIcons()
            .getLadderIcon()
            .getStatusNames()
            .getDisabled();

    GuiItem guiItem =
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getLadder()
                    .getLadderManager()
                    .getIcons()
                    .getLadderIcon())
            .replace("%ladder%", ladder.getName())
            .replace("%type%", ladder.getType().getName())
            .replace("%ladderState%", ladder.isEnabled() ? enabledStatus : disabledStatus)
            .replace("%rankedState%", ladder.isRanked() ? enabledStatus : disabledStatus)
            .replace("%freezeState%", ladder.isFrozen() ? enabledStatus : disabledStatus);

    if (ladder.getIcon() != null) {
      guiItem.setBaseItem(ladder.getIcon());
    }

    return guiItem.get();
  }
}

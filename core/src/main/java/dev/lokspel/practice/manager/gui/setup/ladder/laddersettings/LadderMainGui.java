package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.confirmgui.ConfirmGuiType;
import dev.lokspel.practice.manager.gui.setup.ladder.LadderSetupManager;
import dev.lokspel.practice.manager.gui.setup.ladder.LadderSummaryGui;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.ladder.util.LadderUtil;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class LadderMainGui extends Gui {

  private static final ItemStack BACK_TO_ITEM =
      GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.BACK-TO").get();
  private static final ItemStack DELETE_ITEM =
      GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.DELETE").get();
  private static final ItemStack DESTROYABLE_BLOCK_ITEM =
      GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.DESTROYABLE-BLOCKS").get();
  private static final ItemStack INVENTORY_ITEM =
      GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.INVENTORY").get();
  private static final ItemStack SETTINGS_ITEM =
      GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.SETTINGS").get();
  private static final ItemStack MATCH_TYPE_ITEM =
      GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.MATCH-TYPE").get();

  private final NormalLadder ladder;

  public LadderMainGui(NormalLadder ladder) {
    super(GuiType.Ladder_Main);
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            GuiFile.getString("GUIS.SETUP.LADDER.LADDER-MAIN.TITLE")
                .replace("%ladder%", ladder.getName()),
            4));

    this.ladder = ladder;

    build();
  }

  @Override
  public void build() {
    Inventory inventory = gui.get(1);

    // Navigation
    inventory.setItem(27, BACK_TO_ITEM);
    inventory.setItem(35, DELETE_ITEM);

    for (int i : new int[] {28, 29, 30, 31, 32, 33}) {
      inventory.setItem(i, GuiManager.getFILLER_ITEM());
    }

    if (ladder.isBuild()) {
      inventory.setItem(13, DESTROYABLE_BLOCK_ITEM);
    }
    inventory.setItem(14, INVENTORY_ITEM);
    inventory.setItem(15, SETTINGS_ITEM);
    inventory.setItem(16, MATCH_TYPE_ITEM);

    update();
  }

  @Override
  public void update() {
    Inventory inventory = gui.get(1);

    inventory.setItem(10, getNameItem(ladder));
    inventory.setItem(11, getStatusItem(ladder));
    inventory.setItem(34, getFreezeItem(ladder.isFrozen()));

    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    final Player player = (Player) e.getWhoClicked();
    Inventory inventory = e.getView().getTopInventory();

    int slot = e.getRawSlot();

    e.setCancelled(true);

    if (inventory.getSize() <= slot) {
      return;
    }
    if (e.getCurrentItem() == null) {
      return;
    }

    switch (slot) {
      case 27:
        Gui summaryGui = GuiManager.getInstance().searchGui(GuiType.Ladder_Summary);
        if (summaryGui instanceof LadderSummaryGui ladderSummaryGui) {
          ladderSummaryGui.open(player, ladderSummaryGui.getBackToPage().getOrDefault(player, 1));
          ladderSummaryGui.getBackToPage().remove(player);
        } else {
          summaryGui.open(player);
        }
        break;
      case 11:
        if (ladder.isEnabled()) {
          openConfirmGui(player, ConfirmGuiType.LADDER_DISABLE, this, this);
        } else {
          LadderUtil.changeStatus(player, ladder);
        }
        break;
      case 13:
        if (ladder.isBuild()) {
          LadderSetupManager.getInstance()
              .getLadderSetupGuis()
              .get(ladder)
              .get(GuiType.Ladder_DestroyableBlock)
              .open(player);
        }
        break;
      case 14:
        LadderSetupManager.getInstance()
            .getLadderSetupGuis()
            .get(ladder)
            .get(GuiType.Ladder_Inventory)
            .open(player);
        break;
      case 15:
        LadderSetupManager.getInstance()
            .getLadderSetupGuis()
            .get(ladder)
            .get(GuiType.Ladder_Settings)
            .open(player);
        break;
      case 16:
        LadderSetupManager.getInstance()
            .getLadderSetupGuis()
            .get(ladder)
            .get(GuiType.Ladder_MatchType)
            .open(player);
        break;
      case 34:
        player.performCommand("ladder freeze " + ladder.getName());
        break;
      case 35:
        if (ladder.isEnabled()) {
          Common.sendMmMessage(
              player, LanguageManager.getString("COMMAND.SETUP.LADDER.CANT-DELETE-ENABLED"));
          return;
        }

        openConfirmGui(
            player,
            ConfirmGuiType.LADDER_DELETE,
            GuiManager.getInstance().searchGui(GuiType.Ladder_Summary),
            this);
        break;

      default:
        break;
    }
  }

  private static ItemStack getNameItem(Ladder ladder) {
    GuiItem guiItem =
        GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.LADDER-NAME")
            .replace("%ladder%", ladder.getName())
            .replace("%type%", ladder.getType().getName());

    if (ladder.getIcon() != null) {
      guiItem.setBaseItem(ladder.getIcon());
    }

    return guiItem.get();
  }

  private static ItemStack getStatusItem(Ladder ladder) {
    if (ladder.isEnabled()) {
      return GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.STATUS.ENABLED").get();
    } else {
      return GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.STATUS.DISABLED").get();
    }
  }

  private static ItemStack getFreezeItem(boolean isFrozen) {
    if (isFrozen) {
      return GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.FREEZE.FROZEN").get();
    } else {
      return GuiFile.getGuiItem("GUIS.SETUP.LADDER.LADDER-MAIN.ICONS.FREEZE.NOT-FROZEN").get();
    }
  }

  @Override
  public void handleConfirm(Player player, ConfirmGuiType confirmGuiType) {
    switch (confirmGuiType) {
      case LADDER_DISABLE:
        LadderUtil.changeStatus(player, ladder);
        break;
      case LADDER_DELETE:
        player.performCommand("ladder delete " + ladder.getName());
        Gui summaryGui = GuiManager.getInstance().searchGui(GuiType.Ladder_Summary);
        if (summaryGui instanceof LadderSummaryGui ladderSummaryGui) {
          ladderSummaryGui.open(player, ladderSummaryGui.getBackToPage().getOrDefault(player, 1));
          ladderSummaryGui.getBackToPage().remove(player);
        } else {
          summaryGui.open(player);
        }
        break;

      default:
        break;
    }
  }
}

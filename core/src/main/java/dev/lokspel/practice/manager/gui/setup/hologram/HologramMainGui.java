package dev.lokspel.practice.manager.gui.setup.hologram;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.confirmgui.ConfirmGuiType;
import dev.lokspel.practice.manager.leaderboard.hologram.Hologram;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramManager;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramType;
import dev.lokspel.practice.manager.leaderboard.hologram.holograms.LadderDynamicHologram;
import dev.lokspel.practice.manager.leaderboard.hologram.holograms.LadderStaticHologram;
import dev.lokspel.practice.manager.leaderboard.types.LbSecondaryType;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class HologramMainGui extends Gui {

  private final Hologram hologram;

  public HologramMainGui(Hologram hologram) {
    super(GuiType.Hologram_Main);
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            GuiFile.getString("GUIS.SETUP.HOLOGRAM.HOLOGRAM-MAIN.TITLE")
                .replace("%hologram%", hologram.getName()),
            4));
    this.hologram = hologram;

    build();
  }

  @Override
  public void build() {
    // Navigation
    gui.get(1)
        .setItem(27, GuiFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-MAIN.ICONS.BACK-TO").get());
    for (int i : new int[] {28, 29, 30, 31, 32, 33, 34}) {
      gui.get(1).setItem(i, GuiManager.getFILLER_ITEM());
    }

    gui.get(1)
        .setItem(35, GuiFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-MAIN.ICONS.DELETE").get());

    update();
  }

  @Override
  public void update() {
    gui.get(1).setItem(10, getTypeItem(hologram));
    gui.get(1).setItem(12, getLadderItem(hologram));
    gui.get(1).setItem(14, getShowItem(hologram));
    gui.get(1).setItem(16, getStatusItem(hologram));

    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    final Player player = (Player) e.getWhoClicked();
    Inventory inventory = e.getView().getTopInventory();

    final ClickType click = e.getClick();
    int slot = e.getRawSlot();

    e.setCancelled(true);

    if (inventory.getSize() <= slot) {
      return;
    }
    if (e.getCurrentItem() == null) {
      return;
    }
    if (e.getCurrentItem().equals(GuiManager.getFILLER_ITEM())) {
      return;
    }

    switch (slot) {
      case 27:
        GuiManager.getInstance().searchGui(GuiType.Hologram_Summary).open(player);
        break;
      case 10:
        if (!hologram.isEnabled()) {
          hologram.setLeaderboardType(
              HologramManager.getInstance().getNextType(hologram.getLeaderboardType()));

          if (hologram.getLeaderboardType().isRankedRelated()) {
            if (hologram instanceof LadderStaticHologram staticHologram) {

              if (staticHologram.getLadder() != null && !staticHologram.getLadder().isRanked()) {
                staticHologram.setLadder(null);
              }
            } else if (hologram instanceof LadderDynamicHologram dynamicHologram) {
              dynamicHologram.getLadders().removeIf(ladder -> !ladder.isRanked());
            }
          }

          this.update();
          GuiManager.getInstance().searchGui(GuiType.Hologram_Summary).update();
          Gui ladderGui =
              HologramSetupManager.getInstance()
                  .getHologramSetupGuis()
                  .get(hologram)
                  .get(GuiType.Hologram_Ladder);
          if (ladderGui != null) {
            ladderGui.update();
          }
        } else {
          Common.sendMmMessage(
              player, LanguageManager.getString("COMMAND.SETUP.HOLOGRAM.CANT-EDIT-ENABLED"));
        }
        break;
      case 12:
        if (hologram.getHologramType() != null
            && !hologram.getHologramType().equals(HologramType.GLOBAL)) {
          HologramSetupManager.getInstance()
              .getHologramSetupGuis()
              .get(hologram)
              .get(GuiType.Hologram_Ladder)
              .open(player);
        }
        break;
      case 14:
        if (!hologram.isEnabled()) {
          int currentShowStat = hologram.getShowStat();

          if (click.isLeftClick() && currentShowStat > 3) {
            hologram.setShowStat(currentShowStat - 1);
          } else if (click.isRightClick() && currentShowStat < 15) {
            hologram.setShowStat(currentShowStat + 1);
          }

          update();
          GuiManager.getInstance().searchGui(GuiType.Hologram_Summary).update();
        } else {
          Common.sendMmMessage(
              player, LanguageManager.getString("COMMAND.SETUP.HOLOGRAM.CANT-EDIT-ENABLED"));
        }
        break;
      case 16:
        if (hologram.isEnabled()) {
          hologram.setEnabled(false);
        } else {
          if (!hologram.isReadyToEnable()) {
            Common.sendMmMessage(
                player, LanguageManager.getString("COMMAND.SETUP.HOLOGRAM.NO-REQUIREMENTS"));
            break;
          } else {
            hologram.setEnabled(true);
          }
        }

        update();
        GuiManager.getInstance().searchGui(GuiType.Hologram_Summary).update();
        break;
      case 35:
        openConfirmGui(
            player,
            ConfirmGuiType.HOLOGRAM_DELETE,
            GuiManager.getInstance().searchGui(GuiType.Hologram_Summary),
            this);
        break;

      default:
        break;
    }
  }

  private static ItemStack getTypeItem(Hologram hologram) {
    List<String> typeExtension = new ArrayList<>();
    for (LbSecondaryType hologramType : HologramManager.getInstance().getLbSecondaryTypes()) {
      typeExtension.add(
          (hologramType.equals(hologram.getLeaderboardType()) ? "<green>" : "<red>")
              + " <bold>► <yellow>"
              + StringUtil.getNormalizedName(hologramType.name()));
    }

    GuiItem guiItem = GuiFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-MAIN.ICONS.EVENT-TYPE");

    List<String> lore = new ArrayList<>();
    for (String line : guiItem.getLore()) {
      if (line.contains("%eventTypes%")) {
        lore.addAll(typeExtension);
      }
    }
    guiItem.setLore(lore);

    return guiItem.get();
  }

  private static ItemStack getLadderItem(Hologram hologram) {
    if (hologram.getHologramType().equals(HologramType.GLOBAL)) {
      return GuiFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-MAIN.ICONS.LADDER.NO-LADDER-SETTINGS")
          .get();
    } else {
      return GuiFile.getGuiItem(
              "GUIS.SETUP.HOLOGRAM.HOLOGRAM-MAIN.ICONS.LADDER.HAS-LADDER-SETTINGS")
          .get();
    }
  }

  private static ItemStack getShowItem(Hologram hologram) {
    return GuiFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-MAIN.ICONS.SHOW-STATS")
        .replace("%showStats%", String.valueOf(hologram.getShowStat()))
        .get();
  }

  private static ItemStack getStatusItem(Hologram hologram) {
    if (hologram.isEnabled()) {
      return GuiFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-MAIN.ICONS.STATUS.ENABLED").get();
    } else {
      return GuiFile.getGuiItem("GUIS.SETUP.HOLOGRAM.HOLOGRAM-MAIN.ICONS.STATUS.DISABLED").get();
    }
  }

  @Override
  public void handleConfirm(Player player, ConfirmGuiType confirmGuiType) {
    if (confirmGuiType.equals(ConfirmGuiType.HOLOGRAM_DELETE)) {
      hologram.deleteHologram(true);

      update();
      GuiManager.getInstance().searchGui(GuiType.Hologram_Summary).update();

      Common.sendMmMessage(
          player,
          LanguageManager.getString("COMMAND.SETUP.HOLOGRAM.HOLOGRAM-DELETED")
              .replace("%hologram%", hologram.getName()));
    }
  }
}

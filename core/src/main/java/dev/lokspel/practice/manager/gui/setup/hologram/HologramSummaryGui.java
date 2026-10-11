package dev.lokspel.practice.manager.gui.setup.hologram;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.leaderboard.hologram.Hologram;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramManager;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.PageUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HologramSummaryGui extends Gui {

  @Getter private final int spaces = 18;
  private final Map<Integer, Map<Integer, Hologram>> hologramSlots = new HashMap<>();

  public HologramSummaryGui() {
    super(GuiType.Hologram_Summary);

    build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    List<Hologram> holograms = new ArrayList<>(HologramManager.getInstance().getHolograms());
    holograms.sort(Comparator.comparing(Hologram::getName, String::compareToIgnoreCase));

    Map<Integer, Inventory> existingInventories = new HashMap<>(gui);
    Map<Integer, Inventory> newGui = new HashMap<>();
    Map<Integer, Map<Integer, Hologram>> newHologramSlots = new HashMap<>();

    for (int page = 1; PageUtil.isPageValid(holograms.size(), page, spaces) || page == 1; page++) {
      Inventory inventory = existingInventories.get(page);
      String title =
          PracticeConfig.guis()
              .getGuis()
              .getSetup()
              .getHologram()
              .getHologramManager()
              .getTitle()
              .replace("%page%", String.valueOf(page));
      if (inventory == null || inventory.getSize() != 3 * 9) {
        inventory = InventoryUtil.createInventory(title, 3);
      } else {
        inventory.clear();
      }
      newGui.put(page, inventory);

      // Frame
      for (int i = 18; i < 27; i++) {
        inventory.setItem(i, GuiManager.getFILLER_ITEM());
      }

      Map<Integer, Hologram> pageSlots = new HashMap<>();
      int startIndex = (page - 1) * spaces;
      int endIndex = Math.min(startIndex + spaces, holograms.size());
      for (int i = startIndex; i < endIndex; i++) {
        int slot = inventory.firstEmpty();
        if (slot == -1 || slot >= 18) {
          break;
        }

        Hologram hologram = holograms.get(i);
        inventory.setItem(slot, this.getSummaryHologramMainItem(hologram));
        pageSlots.put(slot, hologram);
      }
      newHologramSlots.put(page, pageSlots);

      // Left navigation
      ItemStack left;
      if (page == 1) {
        left =
            BackendUtil.getGuiItem(
                    PracticeConfig.guis()
                        .getGuis()
                        .getSetup()
                        .getHologram()
                        .getHologramManager()
                        .getIcons()
                        .getBackTo())
                .get();
      } else {
        left =
            BackendUtil.getGuiItem(
                    PracticeConfig.guis()
                        .getGuis()
                        .getSetup()
                        .getHologram()
                        .getHologramManager()
                        .getIcons()
                        .getPageLeft())
                .replace("%page%", String.valueOf(page - 1))
                .get();
      }
      inventory.setItem(18, left);

      // Right navigation
      ItemStack right;
      if (PageUtil.isPageValid(holograms.size(), page + 1, spaces)) {
        right =
            BackendUtil.getGuiItem(
                    PracticeConfig.guis()
                        .getGuis()
                        .getSetup()
                        .getHologram()
                        .getHologramManager()
                        .getIcons()
                        .getPageRight())
                .replace("%page%", String.valueOf(page + 1))
                .get();
      } else {
        right = GuiManager.getFILLER_ITEM();
      }
      inventory.setItem(26, right);
    }

    updatePages(newGui, hologramSlots, newHologramSlots);
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    final Player player = (Player) e.getWhoClicked();
    Inventory inventory = e.getView().getTopInventory();

    final ClickType click = e.getClick();
    int slot = e.getRawSlot();
    ItemStack currentItem = e.getCurrentItem();

    e.setCancelled(true);

    if (inventory.getSize() <= slot) {
      return;
    }
    if (currentItem == null) {
      return;
    }
    if (currentItem.equals(GuiManager.getFILLER_ITEM())) {
      return;
    }

    int page = inGuiPlayers.getOrDefault(player, 1);
    if (slot == 18) {
      if (page == 1) {
        GuiManager.getInstance().searchGui(GuiType.Setup_Hub).open(player);
      } else if (gui.containsKey(page - 1)) {
        open(player, page - 1);
      }
    } else if (slot == 26) {
      if (gui.containsKey(page + 1)) {
        open(player, page + 1);
      }
    } else if (hologramSlots.containsKey(page) && hologramSlots.get(page).containsKey(slot)) {
      Hologram hologram = hologramSlots.get(page).get(slot);

      if (click.isRightClick()) {
        player.teleport(hologram.getBaseLocation().clone().subtract(0, -2, 0));
      } else {
        HologramSetupManager.getInstance()
            .getHologramSetupGuis()
            .get(hologram)
            .get(GuiType.Hologram_Main)
            .open(player);
      }
    }
  }

  private ItemStack getSummaryHologramMainItem(Hologram hologram) {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getHologram()
                .getHologramManager()
                .getIcons()
                .getHologramIcon())
        .replace("%hologramName%", hologram.getName())
        .replace(
            "%state%",
            (hologram.isEnabled()
                ? PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getHologram()
                    .getHologramManager()
                    .getIcons()
                    .getHologramIcon()
                    .getStatusNames()
                    .getEnabled()
                : PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getHologram()
                    .getHologramManager()
                    .getIcons()
                    .getHologramIcon()
                    .getStatusNames()
                    .getDisabled()))
        .replace(
            "%type%",
            (hologram.getHologramType() != null
                ? hologram.getHologramType().getName()
                : PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getHologram()
                    .getHologramManager()
                    .getIcons()
                    .getHologramIcon()
                    .getStatusNames()
                    .getTypeNull()))
        .replace("%statsShow%", String.valueOf(hologram.getShowStat()))
        .get();
  }
}

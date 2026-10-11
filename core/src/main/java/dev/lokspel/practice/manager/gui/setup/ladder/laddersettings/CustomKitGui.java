package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.match.enums.WeightClass;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.ladder.LadderSetupManager;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Getter
public class CustomKitGui extends Gui {

  private final NormalLadder ladder;
  private final boolean ranked;

  public CustomKitGui(NormalLadder ladder, boolean ranked) {
    super(GuiType.Ladder_Main);
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getLadder()
                .getCustomKit()
                .getTitle()
                .replace("%ladder%", ladder.getName()),
            6));
    this.ladder = ladder;
    this.ranked = ranked;

    build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    Bukkit.getScheduler()
        .runTaskAsynchronously(
            AstralPractice.getInstance(),
            () -> {
              Inventory inventory = gui.get(1);
              inventory.clear();

              inventory.setItem(
                  0,
                  BackendUtil.getGuiItem(
                          PracticeConfig.guis()
                              .getGuis()
                              .getSetup()
                              .getLadder()
                              .getCustomKit()
                              .getIcons()
                              .getGoBack())
                      .get());
              inventory.setItem(
                  6,
                  BackendUtil.getGuiItem(
                          PracticeConfig.guis()
                              .getGuis()
                              .getSetup()
                              .getLadder()
                              .getCustomKit()
                              .getIcons()
                              .getSave())
                      .get());
              inventory.setItem(
                  7,
                  BackendUtil.getGuiItem(
                          PracticeConfig.guis()
                              .getGuis()
                              .getSetup()
                              .getLadder()
                              .getCustomKit()
                              .getIcons()
                              .getLoad())
                      .get());
              inventory.setItem(
                  8,
                  BackendUtil.getGuiItem(
                          PracticeConfig.guis()
                              .getGuis()
                              .getSetup()
                              .getLadder()
                              .getCustomKit()
                              .getIcons()
                              .getCancel())
                      .get());
              inventory.setItem(2, getRankedItem(ladder, ranked));

              // Frame
              for (int i :
                  new int[] {1, 3, 4, 5, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 28, 37, 46}) {
                inventory.setItem(i, GuiManager.fillerItem());
              }

              // Set Armor content
              if (ladder.getKitData().getArmor() != null) {
                List<ItemStack> armorContent =
                    new ArrayList<>(Arrays.asList(ladder.getKitData().getArmor()));
                for (int i : new int[] {18, 27, 36, 45}) {
                  if (armorContent.get(Math.abs(i / 9 - 5)) != null) {
                    inventory.setItem(i, armorContent.get(Math.abs(i / 9 - 5)));
                  } else {
                    inventory.setItem(i, GuiManager.getDUMMY_ITEM());
                  }
                }
              }

              // Set inventory content
              if (ladder.getCustomKitExtraItems().get(ranked) != null) {
                for (ItemStack item : ladder.getCustomKitExtraItems().get(ranked)) {
                  if (item != null) {
                    inventory.setItem(inventory.firstEmpty(), item);
                  } else {
                    inventory.setItem(inventory.firstEmpty(), GuiManager.getDUMMY_ITEM());
                  }
                }
              }

              inventory.remove(GuiManager.getDUMMY_ITEM());

              updatePlayers();
            });
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    Inventory inventory = e.getView().getTopInventory();

    int slot = e.getRawSlot();

    if (inventory.getSize() <= slot) {
      return;
    }

    if (Objects.equals(e.getCurrentItem(), GuiManager.fillerItem())
        || slot == 0
        || slot == 2
        || slot == 6
        || slot == 7
        || slot == 8
        || slot == 18
        || slot == 27
        || slot == 36
        || slot == 45) {
      e.setCancelled(true);

      if (slot == 0) {
        LadderSetupManager.getInstance()
            .getLadderSetupGuis()
            .get(ladder)
            .get(GuiType.Ladder_Inventory)
            .open(player, 1);
      } else if (slot == 2) {
        switch (ladder.getWeightClass()) {
          case UNRANKED:
          case RANKED:
            return;
          case UNRANKED_AND_RANKED:
            if (ranked) {
              LadderSetupManager.getInstance()
                  .getLadderSetupGuis()
                  .get(ladder)
                  .get(GuiType.Ladder_CustomKitExtra_unRanked)
                  .open(player);
            } else {
              LadderSetupManager.getInstance()
                  .getLadderSetupGuis()
                  .get(ladder)
                  .get(GuiType.Ladder_CustomKitExtra_Ranked)
                  .open(player);
            }
            break;

          default:
            break;
        }
      }
    } else {
      if (ladder.isEnabled()) {
        e.setCancelled(true);
        Common.sendMmMessage(
            player,
            PracticeConfig.language().getCommand().getSetup().getLadder().getCantEditEnabled());
      }
    }
  }

  public void handleCloseEvent(InventoryCloseEvent e) {
    if (LadderManager.getInstance().getLadders().contains(ladder) && !ladder.isEnabled()) {
      this.save();
    }
  }

  public void save() {
    List<ItemStack> items = new ArrayList<>();
    for (int i = 20; i <= 26; i++) {
      items.add(gui.get(1).getItem(i));
    }
    for (int i = 29; i <= 35; i++) {
      items.add(gui.get(1).getItem(i));
    }
    for (int i = 38; i <= 44; i++) {
      items.add(gui.get(1).getItem(i));
    }
    for (int i = 47; i <= 53; i++) {
      items.add(gui.get(1).getItem(i));
    }

    ladder.getCustomKitExtraItems().put(ranked, items.toArray(new ItemStack[0]));
  }

  private static @Nullable ItemStack getRankedItem(NormalLadder ladder, boolean ranked) {
    switch (ladder.getWeightClass()) {
      case UNRANKED:
        return BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getLadder()
                    .getCustomKit()
                    .getIcons()
                    .getSwitchWeightclass()
                    .getOnlyUnranked())
            .replace("%weightClass%", WeightClass.UNRANKED.getName())
            .get();
      case RANKED:
        return BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getLadder()
                    .getCustomKit()
                    .getIcons()
                    .getSwitchWeightclass()
                    .getOnlyRanked())
            .replace("%weightClass%", WeightClass.RANKED.getName())
            .get();
      case UNRANKED_AND_RANKED:
        if (ranked) {
          return BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getLadder()
                      .getCustomKit()
                      .getIcons()
                      .getSwitchWeightclass()
                      .getSwitchToUnranked())
              .replace("%weightClass%", WeightClass.UNRANKED.getName())
              .get();
        } else {
          return BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getLadder()
                      .getCustomKit()
                      .getIcons()
                      .getSwitchWeightclass()
                      .getSwitchToRanked())
              .replace("%weightClass%", WeightClass.RANKED.getName())
              .get();
        }

      default:
        break;
    }

    return GuiManager.fillerItem();
  }
}

package dev.lokspel.practice.manager.gui.setup.ladder.laddersettings;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.match.enums.MatchType;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.ladder.LadderSetupManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.ladder.enums.LadderType;
import dev.lokspel.practice.manager.ladder.enums.WeightClassType;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class MatchTypeGui extends Gui {

  private final NormalLadder ladder;
  private final Map<Integer, MatchType> matchTypeSlots = new HashMap<>();

  public MatchTypeGui(NormalLadder ladder) {
    super(GuiType.Ladder_MatchType);
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getLadder()
                .getMatchType()
                .getTitle()
                .replace("%ladder%", ladder.getName()),
            1));
    this.ladder = ladder;

    build();
  }

  @Override
  public void build() {
    LadderType ladderType = ladder.getType();
    Inventory inventory = gui.get(1);

    inventory.setItem(
        0,
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getLadder()
                    .getMatchType()
                    .getIcons()
                    .getGoBack())
            .get());

    for (int i : new int[] {1, 2, 3, 4}) {
      inventory.setItem(i, GuiManager.getDUMMY_ITEM());
    }

    if (!ladderType.isPartyFfaSupported()) {
      inventory.setItem(5, GuiManager.getDUMMY_ITEM());
    }

    for (MatchType matchType : MatchType.values()) {
      if (!ladderType.isPartyFfaSupported() && (matchType.equals(MatchType.PARTY_FFA))) {
        continue;
      }

      int slot = inventory.firstEmpty();
      inventory.setItem(slot, GuiManager.getDUMMY_ITEM());
      matchTypeSlots.put(slot, matchType);
    }

    inventory.remove(GuiManager.getDUMMY_ITEM());

    update();
  }

  @Override
  public void update() {
    for (Map.Entry<Integer, MatchType> slot : matchTypeSlots.entrySet()) {
      gui.get(1).setItem(slot.getKey(), getMatchTypeItem(ladder, slot.getValue()));
    }

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

    if (slot == 0) {
      LadderSetupManager.getInstance()
          .getLadderSetupGuis()
          .get(ladder)
          .get(GuiType.Ladder_Main)
          .open(player);
      return;
    }

    if (ladder.isEnabled()) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language().getCommand().getSetup().getLadder().getCantEditEnabled());
      return;
    }

    MatchType matchType = matchTypeSlots.get(slot);
    if (matchType == null) {
      return;
    }

    if (ladder.getWeightClass().equals(WeightClassType.RANKED)) {
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getSetup()
              .getLadder()
              .getCantEditRankedWeightclassMatchtype());
      return;
    }

    if (ladder.getMatchTypes().contains(matchType)) {
      ladder.getMatchTypes().remove(matchType);
    } else {
      ladder.getMatchTypes().add(matchType);
    }

    update();
  }

  private ItemStack getMatchTypeItem(Ladder ladder, MatchType matchType) {
    if (ladder.getMatchTypes().contains(matchType)) {
      return BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getMatchType()
                  .getIcons()
                  .getEnabled())
          .replace("%matchType%", matchType.getName(false))
          .get();
    } else {
      return BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getLadder()
                  .getMatchType()
                  .getIcons()
                  .getDisabled())
          .replace("%matchType%", matchType.getName(false))
          .get();
    }
  }
}

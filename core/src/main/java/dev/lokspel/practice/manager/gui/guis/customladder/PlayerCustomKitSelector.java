package dev.lokspel.practice.manager.gui.guis.customladder;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.ladder.abstraction.playercustom.CustomLadder;
import dev.lokspel.practice.manager.playerkit.PlayerKitManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerCustomKitSelector extends Gui {

  private static final Map<Integer, List<Integer>> slotsPerKitLimit =
      Map.of(
          1, List.of(13),
          2, List.of(12, 14),
          3, List.of(11, 13, 15),
          4, List.of(10, 12, 14, 16),
          5, List.of(9, 11, 13, 15, 17));

  private static final GuiItem KIT_ITEM =
      BackendUtil.getGuiItem(
          PracticeConfig.guis()
              .getGuis()
              .getKitEditor()
              .getPlayerCustomKitSelector()
              .getIcons()
              .getKit());
  private static final ItemStack SELECTED_KIT_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getKitEditor()
                  .getPlayerCustomKitSelector()
                  .getIcons()
                  .getSelected())
          .get();
  private static final ItemStack UNSELECTED_KIT_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getKitEditor()
                  .getPlayerCustomKitSelector()
                  .getIcons()
                  .getUnselected())
          .get();
  private static final ItemStack GUIDE_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getKitEditor()
                  .getPlayerCustomKitSelector()
                  .getIcons()
                  .getGuide())
          .get();

  private final Profile profile;
  private final int kitLimit;
  private final Map<Integer, CustomLadder> slots = new HashMap<>();
  private final Map<Integer, CustomLadder> selectSlots = new HashMap<>();

  public PlayerCustomKitSelector(final Profile profile) {
    super(GuiType.CustomLadder_CustomKitSelector);
    this.profile = profile;
    this.kitLimit = profile.getGroup().getCustomKitLimit();

    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getKitEditor().getPlayerCustomKitSelector().getTitle(),
            6));

    this.build();
  }

  @Override
  public void build() {
    this.update();
  }

  @Override
  public void update() {
    Inventory inventory = this.gui.get(1);

    inventory.clear();
    this.slots.clear();

    inventory.setItem(49, GUIDE_ITEM);

    for (int i = 0; i < this.kitLimit; i++) {
      CustomLadder customLadder = this.getCustomLadder(i);
      if (customLadder == null) {
        continue;
      }

      GuiItem kitItem = KIT_ITEM.cloneItem();
      kitItem.replace(
          "%kit%",
          customLadder.getDisplayName() != null ? customLadder.getDisplayName() : "Kit " + i);

      int slot = slotsPerKitLimit.get(kitLimit).get(i);
      inventory.setItem(slot, kitItem.get());

      if (profile.getSelectedCustomLadder() == customLadder) {
        inventory.setItem(slot + 9, SELECTED_KIT_ITEM);
      } else {
        inventory.setItem(slot + 9, UNSELECTED_KIT_ITEM);
      }

      this.slots.put(slot, customLadder);
      this.selectSlots.put(slot + 9, customLadder);
    }

    this.updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();

    CustomLadder customLadder = slots.get(slot);
    if (customLadder != null) {
      if (PlayerKitManager.getInstance().getCopying().containsKey(player)) {
        int index = profile.getCustomLadders().indexOf(customLadder);
        if (index != -1) {
          CustomLadder copyCustomLadder = PlayerKitManager.getInstance().getCopying().get(player);
          CustomLadder newLadder =
              new CustomLadder(copyCustomLadder, this.profile, customLadder.getKey());

          this.profile.getCustomLadders().set(index, newLadder);
          newLadder.getMainGui().open(player);
          Common.sendMmMessage(
              player,
              PracticeConfig.language()
                  .getCustomPlayerKit()
                  .getCopykit()
                  .getCopySuccess()
                  .replace("%name%", copyCustomLadder.getDisplayName()));
        }
      } else {
        customLadder.getMainGui().open(player);
      }
    } else {
      customLadder = selectSlots.get(slot);
      if (customLadder != null) {
        if (customLadder.isEnabled()) {
          if (this.profile.getSelectedCustomLadder() != customLadder) {
            this.profile.setSelectedCustomLadder(customLadder);
            this.update();
          }
        } else {
          Common.sendMmMessage(
              player, PracticeConfig.language().getCustomPlayerKit().getOnlySelectEnabledCustoms());
        }
      }
    }
  }

  private CustomLadder getCustomLadder(int i) {
    if (i < 0 || i >= profile.getCustomLadders().size()) {
      return null;
    }

    return profile.getCustomLadders().get(i);
  }

  @Override
  public void handleCloseEvent(InventoryCloseEvent e) {
    PlayerKitManager.getInstance().getCopying().remove((Player) e.getPlayer());
  }
}

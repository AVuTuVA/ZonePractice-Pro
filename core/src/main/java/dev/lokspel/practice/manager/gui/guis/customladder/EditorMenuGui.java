package dev.lokspel.practice.manager.gui.guis.customladder;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EditorMenuGui extends Gui {

  private static ItemStack fillerItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis()
                .getGuis()
                .getKitEditor()
                .getEditorMenu()
                .getIcons()
                .getFillerItem())
        .get();
  }

  public EditorMenuGui() {
    super(GuiType.CustomLadder_EditorMenu);

    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getKitEditor().getEditorMenu().getTitle(), 3));
    this.build();
  }

  @Override
  public void build() {
    this.update();
  }

  @Override
  public void update() {
    Inventory inventory = this.gui.get(1);

    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, fillerItem());
    }

    inventory.setItem(
        11,
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getKitEditor()
                    .getEditorMenu()
                    .getIcons()
                    .getPremadeLadder())
            .get());
    inventory.setItem(
        15,
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getKitEditor()
                    .getEditorMenu()
                    .getIcons()
                    .getPlayerLadder())
            .get());

    this.updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    Profile profile = ProfileManager.getInstance().getProfile(player);
    int slot = e.getRawSlot();

    e.setCancelled(true);
    switch (slot) {
      case 11:
        GuiManager.getInstance().searchGui(GuiType.CustomLadder_Selector).open(player);
        break;
      case 15:
        if (profile.getGroup() == null || profile.getGroup().getCustomKitLimit() == 0) {
          Common.sendMmMessage(
              player, PracticeConfig.language().getCustomPlayerKit().getNoCustomKit());
          return;
        }

        profile.getPlayerCustomKitSelector().update();
        profile.getPlayerCustomKitSelector().open(player);
        break;

      default:
        break;
    }
  }
}

package dev.lokspel.practice.manager.gui.guis.customladder.premadecustom;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.util.PlayerUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.inventory.InventoryManager;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomLadderSumGui extends Gui {

  private final Profile profile;
  private final NormalLadder ladder;

  public CustomLadderSumGui(Profile profile, NormalLadder ladder) {
    super(GuiType.CustomLadder_Summary);

    this.profile = profile;
    this.ladder = ladder;
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis()
                .getGuis()
                .getKitEditor()
                .getKitSelector()
                .getTitle()
                .replace("%ladder%", ladder.getDisplayName())
                .replace("%ladderOriginal%", ladder.getName()),
            4));

    build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    Inventory inventory = gui.get(1);

    // Set kit columns
    for (int kit = 1; kit <= 4; kit++) {
      if (profile.getAllowedCustomKits() >= kit) {
        GuiItem fillerItem =
            BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getKitEditor()
                    .getKitSelector()
                    .getIcons()
                    .getFillerItem2());

        if (profile.getUnrankedCustomKits().get(ladder) != null
            && profile.getUnrankedCustomKits().get(ladder).get(kit) != null) {
          inventory.setItem(
              (kit - 1) * 2 + 1,
              BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getKitEditor()
                          .getKitSelector()
                          .getIcons()
                          .getExistingKit())
                  .replace("%kit%", String.valueOf(kit))
                  .get());
          inventory.setItem(
              (kit - 1) * 2 + 1 + 9 * 2,
              BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getKitEditor()
                          .getKitSelector()
                          .getIcons()
                          .getEditKit())
                  .get());
          inventory.setItem(
              (kit - 1) * 2 + 1 + 9 * 3,
              BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getKitEditor()
                          .getKitSelector()
                          .getIcons()
                          .getDeleteKit())
                  .get());

          inventory.setItem((kit - 1) * 2 + 1 + 9, fillerItem.get());
        } else {
          inventory.setItem(
              (kit - 1) * 2 + 1,
              BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getKitEditor()
                          .getKitSelector()
                          .getIcons()
                          .getCreateKit())
                  .get());
          for (int i = 1; i <= 3; i++) {
            inventory.setItem((kit - 1) * 2 + 1 + 9 * i, fillerItem.get());
          }
        }
      } else {
        inventory.setItem(
            (kit - 1) * 2 + 1,
            BackendUtil.getGuiItem(
                    PracticeConfig.guis()
                        .getGuis()
                        .getKitEditor()
                        .getKitSelector()
                        .getIcons()
                        .getNoPermission())
                .get());
        for (int i = 1; i <= 3; i++) {
          inventory.setItem(
              (kit - 1) * 2 + 1 + 9 * i,
              BackendUtil.getGuiItem(
                      PracticeConfig.guis()
                          .getGuis()
                          .getKitEditor()
                          .getKitSelector()
                          .getIcons()
                          .getFillerItem1())
                  .get());
        }
      }
    }

    // Set filler items between kits
    for (int i = 0; i < inventory.getSize(); i++) {
      if (inventory.getItem(i) == null) {
        inventory.setItem(i, GuiManager.fillerItem());
      }
    }

    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();

    Inventory inventory = e.getView().getTopInventory();
    int slot = e.getRawSlot();
    ItemStack item = e.getCurrentItem();

    e.setCancelled(true);

    if (inventory.getSize() > slot && item != null) {
      if (!ladder.isEnabled() || !ladder.isEditable() || ladder.isFrozen()) {
        Common.sendMmMessage(
            player,
            PracticeConfig.language()
                .getLadder()
                .getKitEditor()
                .getKitSelector()
                .getNotAvailable()
                .replace("%ladder%", ladder.getDisplayName()));
        GuiManager.getInstance().searchGui(GuiType.CustomLadder_Selector).open(player);
        return;
      }

      int kit;
      if ((slot == 1 || slot == 19 || slot == 28) && profile.getAllowedCustomKits() >= 1) {
        kit = 1;
      } else if ((slot == 3 || slot == 21 || slot == 30) && profile.getAllowedCustomKits() >= 2) {
        kit = 2;
      } else if ((slot == 5 || slot == 23 || slot == 32) && profile.getAllowedCustomKits() >= 3) {
        kit = 3;
      } else if ((slot == 7 || slot == 25 || slot == 34) && profile.getAllowedCustomKits() >= 4) {
        kit = 4;
      } else {
        kit = -1;
      }

      if (kit != -1) {
        if (slot == (kit - 1) * 2 + 1) {
          this.openEditor(kit, player);
          return;
        }

        if (profile.getUnrankedCustomKits().get(ladder).containsKey(kit)) {
          if (slot == (kit - 1) * 2 + 1 + 9 * 2) {
            this.openEditor(kit, player);
          } else {
            profile.getUnrankedCustomKits().get(ladder).remove(kit);

            if (profile.getRankedCustomKits().containsKey(ladder)) {
              profile.getRankedCustomKits().get(ladder).remove(kit);
            }

            profile.getFile().deleteCustomKit(ladder, kit);

            this.update();
          }
        }
      }
    } else {
      if (item != null && item.getType().equals(Material.ARROW)) {
        GuiManager.getInstance().searchGui(GuiType.CustomLadder_Selector).open(player);
      }
    }
  }

  @Override
  public void handleCloseEvent(InventoryCloseEvent e) {
    Player player = (Player) e.getPlayer();
    Profile profile = ProfileManager.getInstance().getProfile(player);

    PlayerUtil.clearInventory(player);

    Bukkit.getScheduler()
        .runTaskLater(
            AstralPractice.getInstance(),
            () -> {
              if (GuiManager.getInstance().getOpenGui().containsKey(player)
                  && (GuiManager.getInstance().getOpenGui().get(player)
                      instanceof CustomLadderEditorGui)) {
                return;
              }

              if (profile.getStatus().equals(ProfileStatus.MATCH)
                  || profile.getStatus().equals(ProfileStatus.EVENT)) {
                return;
              }

              InventoryManager.getInstance().setLobbyInventory(player, false);
            },
            3L);
  }

  @Override
  public void open(Player player) {
    open(player, 1);
    ProfileManager.getInstance().getProfile(player).setStatus(ProfileStatus.EDITOR);

    ItemStack backToItem =
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getKitEditor()
                    .getKitSelector()
                    .getIcons()
                    .getBackToKitSelector())
            .get();

    PlayerUtil.clearInventory(player);
    for (int i = 0; i < 9; i++) {
      player.getInventory().setItem(i, backToItem);
    }
  }

  private void openEditor(int kit, Player player) {
    Gui editorGui =
        switch (ladder.getWeightClass()) {
          case RANKED -> new CustomLadderEditorGui(profile, ladder, kit, true, this);
          case UNRANKED, UNRANKED_AND_RANKED ->
              new CustomLadderEditorGui(profile, ladder, kit, false, this);
        };

    editorGui.open(player);
  }
}

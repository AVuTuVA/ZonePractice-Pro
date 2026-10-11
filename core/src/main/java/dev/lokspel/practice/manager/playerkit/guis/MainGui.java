package dev.lokspel.practice.manager.playerkit.guis;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.util.PlayerUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.confirmgui.ConfirmGuiType;
import dev.lokspel.practice.manager.ladder.abstraction.playercustom.CustomLadder;
import dev.lokspel.practice.manager.playerkit.KitItems;
import dev.lokspel.practice.manager.playerkit.PlayerKitEditing;
import dev.lokspel.practice.manager.playerkit.PlayerKitManager;
import dev.lokspel.practice.manager.playerkit.StaticItems;
import dev.lokspel.practice.manager.playerkit.items.KitItem;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainGui extends Gui {

  private final CustomLadder customLadder;
  private final KitItems kitItems;
  private final CustomSettingGui customSettingGui;
  private final Map<Player, KitItem> copying = new HashMap<>();

  public MainGui(final CustomLadder customLadder) {
    super(GuiType.PlayerCustom_MainMenu);

    this.gui.put(
        1,
        InventoryUtil.createInventory(
            StaticItems.mainGuiTitle().replace("%name%", customLadder.getDisplayName()), 6));
    this.customLadder = customLadder;
    this.kitItems = new KitItems(customLadder.getKitData());
    this.customSettingGui = new CustomSettingGui(customLadder, this);

    this.build();
  }

  @Override
  public void build() {
    Inventory inventory = gui.get(1);

    inventory.setItem(0, StaticItems.mainGuiBack());
    inventory.setItem(3, StaticItems.mainGuiSettingsIcon());
    inventory.setItem(5, StaticItems.mainGuiResetKitIcon());
    inventory.setItem(7, StaticItems.mainGuiShareKit());
    inventory.setItem(8, StaticItems.mainGuiGuideIcon());
    inventory.setItem(17, StaticItems.mainGuiChangeName());

    this.update();
  }

  @Override
  public void update() {
    Bukkit.getScheduler()
        .runTaskAsynchronously(
            AstralPractice.getInstance(),
            () -> {
              Inventory inventory = gui.get(1);

              for (Map.Entry<Integer, KitItem> entry : kitItems.getSlots().entrySet()) {
                inventory.setItem(entry.getKey(), entry.getValue().getForDisplay());
              }

              this.updatePlayers();
            });
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    if (e.getClickedInventory() == null) {
      return;
    }

    Player player = (Player) e.getWhoClicked();
    Profile profile = ProfileManager.getInstance().getProfile(player);
    int slot = e.getRawSlot();

    if (kitItems.getSlots().containsKey(slot)) {
      KitItem kitItem = kitItems.getSlots().get(slot);

      // Any click on a shulker box opens the contents editor
      if (!kitItem.isNull() && ShulkerCategoryGui.isShulkerBox(kitItem.getMaterial())) {
        if (e.getClick() == org.bukkit.event.inventory.ClickType.SHIFT_LEFT) {
          kitItem.reset();
          this.update();
        } else {
          new ShulkerBoxEditorGui(this, kitItem).open(player);
        }
        return;
      }

      switch (e.getClick()) {
        case LEFT:
          PlayerKitManager.getInstance().getEditing().get(player).setKitItem(kitItem);
          GuiType guiType =
              switch (slot) {
                case 10 -> GuiType.PlayerCustom_Helmet;
                case 11 -> GuiType.PlayerCustom_Chestplate;
                case 12 -> GuiType.PlayerCustom_Leggings;
                case 13 -> GuiType.PlayerCustom_Boots;
                default -> GuiType.PlayerCustom_Category;
              };

          GuiManager.getInstance().searchGui(guiType).open(player);
          break;
        case RIGHT:
          if (!kitItem.isNull()) {
            if (kitItem.getMaterial().getMaxStackSize() > 1) {
              new AmountChangeGui(kitItem, this).open(player);
            } else if (canBeEnchanted(kitItem.get())) {
              new EnchantGui(kitItem, this).open(player);
            }
          }
          break;
        case SHIFT_LEFT:
          kitItem.reset();
          this.update();
          break;
        case SHIFT_RIGHT:
          if (!kitItem.isNull()) {
            copying.put(player, kitItem);
          } else if (kitItem.isNull()
              && copying.containsKey(player)
              && !copying.get(player).isNull()) {
            kitItem.setItemStack(copying.get(player).get());
            this.update();
          }
          break;

        default:
          break;
      }
    } else {
      switch (slot) {
        case 0:
          profile.getPlayerCustomKitSelector().update();
          profile.getPlayerCustomKitSelector().open(player);
          break;
        case 3:
          customSettingGui.open(player);
          break;
        case 5:
          openConfirmGui(player, ConfirmGuiType.RESET_CUSTOM_KIT, this, this);
          break;
        case 7:
          if (!player.hasPermission("ap.playerkit.share")) {
            Common.sendMmMessage(
                player, PracticeConfig.language().getCustomPlayerKit().getNoPermission());
            return;
          }

          String code = null;
          if (PlayerKitManager.getInstance().getCopy().containsValue(customLadder)) {
            for (Map.Entry<String, CustomLadder> entry :
                PlayerKitManager.getInstance().getCopy().entrySet()) {
              if (entry.getValue() == customLadder) {
                code = entry.getKey();
                break;
              }
            }
          } else {
            code = PlayerKitManager.getInstance().getCopyCode();
            PlayerKitManager.getInstance().getCopy().put(code, customLadder);
          }

          for (String line : PracticeConfig.language().getCustomPlayerKit().getCustomKitShare()) {
            Common.sendMmMessage(player, line.replace("%code%", code != null ? code : "N/A"));
          }
          break;
        case 17:
          new AnvilGUI.Builder()
              .plugin(AstralPractice.getInstance())
              .onClose(
                  stateSnapshot ->
                      Bukkit.getScheduler()
                          .runTaskLater(
                              AstralPractice.getInstance(),
                              () -> {
                                this.update();
                                this.open(player);
                              },
                              2L))
              .onClick(
                  (anvilSlot, stateSnapshot) -> {
                    if (anvilSlot != AnvilGUI.Slot.OUTPUT) {
                      return Collections.emptyList();
                    }

                    try {
                      String text = stateSnapshot.getText();

                      if (text.isEmpty()) {
                        return List.of(AnvilGUI.ResponseAction.replaceInputText("Invalid name"));
                      }

                      if (text.length() < 3 || text.length() > 12) {
                        return List.of(
                            AnvilGUI.ResponseAction.replaceInputText("3 - 13 characters"));
                      }

                      customLadder.setDisplayName(text);
                      return List.of(AnvilGUI.ResponseAction.close());
                    } catch (Exception exception) {
                      return List.of(AnvilGUI.ResponseAction.replaceInputText("Invalid name"));
                    }
                  })
              .text(customLadder.getDisplayName())
              .title("Rename the kit")
              .open(player);
          break;

        default:
          break;
      }
    }
  }

  @Override
  public void handleConfirm(Player player, ConfirmGuiType confirmGuiType) {
    if (confirmGuiType.equals(ConfirmGuiType.RESET_CUSTOM_KIT)) {
      kitItems.reset();
      this.update();
    }
  }

  @Override
  public void handleCloseEvent(InventoryCloseEvent e) {
    kitItems.save();
  }

  @Override
  public void open(Player player) {
    super.open(player);

    PlayerUtil.setActiveInventoryTitle(
        player, StaticItems.mainGuiTitle().replace("%name%", customLadder.getDisplayName()));

    PlayerKitManager.getInstance().getEditing().remove(player);
    PlayerKitManager.getInstance().getEditing().put(player, new PlayerKitEditing(customLadder));
  }

  private static boolean canBeEnchanted(ItemStack itemStack) {
    if (itemStack == null || itemStack.getType() == Material.AIR) {
      return false;
    }

    for (Enchantment enchantment : Common.getAllEnchantments()) {
      if (enchantment.canEnchantItem(itemStack)) {
        return true;
      }
    }

    return false;
  }
}

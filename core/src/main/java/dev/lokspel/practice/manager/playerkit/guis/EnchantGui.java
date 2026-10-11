package dev.lokspel.practice.manager.playerkit.guis;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.playerkit.StaticItems;
import dev.lokspel.practice.manager.playerkit.items.KitItem;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.StringUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantGui extends Gui {

  private final Map<ItemStack, Enchantment> icons = new HashMap<>();

  private Enchantment selectedEnchantment = null;
  private final KitItem kitItem;
  private final int maxDurability;
  private final Gui backTo;

  public EnchantGui(KitItem kitItem, Gui backTo) {
    super(GuiType.PlayerCustom_Enchant);
    this.kitItem = kitItem;
    this.maxDurability = kitItem.getMaterial().getMaxDurability();
    this.backTo = backTo;
    this.gui.put(1, InventoryUtil.createInventory(StaticItems.enchantGuiTitle(), 6));
    this.build();
  }

  @Override
  public void build() {
    Inventory inventory = gui.get(1);

    inventory.setItem(45, StaticItems.enchantGuiBackIcon());
    inventory.setItem(44, StaticItems.enchantGuiChangeDurabilityIcon());
    inventory.setItem(53, StaticItems.enchantGuiClearEnchantsIcon());

    for (Enchantment enchantment : Common.getAllEnchantments()) {
      String name = kitItem.getMaterial().name().toLowerCase();

      if (!enchantment.canEnchantItem(kitItem.get())) {
        continue;
      }

      boolean disabled = false;
      for (Map.Entry<String, List<Enchantment>> entry :
          StaticItems.disabledEnchantments().entrySet()) {
        if (name.contains(entry.getKey().toLowerCase())) {
          if (entry.getValue().contains(enchantment)) {
            disabled = true;
          }
        }
      }

      if (!disabled) {
        icons.put(
            StaticItems.enchantGuiEnchantIcon()
                .cloneItem()
                .replace(
                    "%enchantment%", StringUtil.getNormalizedName(enchantment.getKey().getKey()))
                .get(),
            enchantment);
      }
    }

    Map<ItemStack, Enchantment> sorted = sortByValue(icons);
    for (ItemStack icon : sorted.keySet()) {
      int slot = inventory.firstEmpty();
      if (slot < 4 * 9) {
        inventory.setItem(slot, icon);
      } else {
        break;
      }
    }

    this.update();
  }

  @Override
  public void update() {
    Bukkit.getScheduler()
        .runTaskAsynchronously(
            AstralPractice.getInstance(),
            () -> {
              Inventory inventory = gui.get(1);

              setEnchantmentLevelPlaceholderItems(inventory);

              inventory.setItem(40, kitItem.get());

              if (kitItem.isUnbreakable()) {
                inventory.setItem(52, StaticItems.enchantGuiBreakableIcon());
              } else {
                inventory.setItem(52, StaticItems.enchantGuiUnbreakableIcon());
              }

              this.updatePlayers();
            });
  }

  private static Map<ItemStack, Enchantment> sortByValue(Map<ItemStack, Enchantment> map) {
    LinkedHashMap<ItemStack, Enchantment> sortedMap = new LinkedHashMap<>();

    map.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.comparing(Enchantment::getName)))
        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

    return sortedMap;
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();
    Inventory inventory = e.getView().getTopInventory();

    if (e.getClickedInventory() == null) {
      return;
    }
    if (inventory.getSize() <= slot) {
      return;
    }

    switch (slot) {
      case 45:
        backTo.open(player);
        backTo.update();
        break;
      case 44:
        new AnvilGUI.Builder()
            .plugin(AstralPractice.getInstance())
            .onClose(
                stateSnapshot ->
                    Bukkit.getScheduler()
                        .runTaskLater(AstralPractice.getInstance(), () -> backTo.open(player), 2L))
            .onClick(
                (anvilSlot, stateSnapshot) -> {
                  if (anvilSlot != AnvilGUI.Slot.OUTPUT) {
                    return Collections.emptyList();
                  }

                  try {
                    String text = stateSnapshot.getText();
                    if (text.startsWith("#")) {
                      text = text.replaceFirst("#", "");
                    }

                    int number = Integer.parseInt(text);
                    if (number < 1) {
                      return List.of(AnvilGUI.ResponseAction.replaceInputText("Invalid number"));
                    } else {
                      kitItem.setDurability(Math.min(number, maxDurability));
                      backTo.update();
                    }

                    return List.of(AnvilGUI.ResponseAction.close());
                  } catch (Exception exception) {
                    return List.of(AnvilGUI.ResponseAction.replaceInputText("Invalid number"));
                  }
                })
            .text("#")
            .title(
                StaticItems.customDurabilityGuiTitle()
                    .replace("%max%", String.valueOf(maxDurability)))
            .open(player);
        break;
      case 53:
        kitItem.clearEnchants();
        this.update();
        backTo.update();
        break;
      case 52:
        kitItem.setUnbreakable(!kitItem.isUnbreakable());
        this.update();
        backTo.update();
        break;
      default:
        if (icons.containsKey(e.getCurrentItem())) {
          Enchantment enchantment = icons.get(e.getCurrentItem());

          if (enchantment.getMaxLevel() == 1) {
            kitItem.addEnchant(enchantment, 1);
            this.update();
            backTo.update();
          } else {
            selectedEnchantment = enchantment;
            setEnchantmentLevelItems(inventory, selectedEnchantment);
          }
        } else {
          if (slot >= 47 && slot <= 51) {
            if (selectedEnchantment == null) {
              return;
            }
            if (selectedEnchantment.getMaxLevel() == 1) {
              return;
            }

            int level = slot - 46;
            if (level > selectedEnchantment.getMaxLevel()) {
              return;
            }

            kitItem.addEnchant(selectedEnchantment, level);
            this.update();
            backTo.update();
          }
        }
        break;
    }
  }

  private static void setEnchantmentLevelPlaceholderItems(Inventory inventory) {
    for (int i = 47; i <= 51; i++) {
      inventory.setItem(i, StaticItems.enchantGuiSetLevelPlaceholderIcon());
    }
  }

  private static void setEnchantmentLevelItems(Inventory inventory, Enchantment enchantment) {
    setEnchantmentLevelPlaceholderItems(inventory);

    for (int i = 47; i <= 51; i++) {
      int currentLevel = i - 46;
      if (enchantment.getMaxLevel() < currentLevel) {
        break;
      }

      inventory.setItem(
          i,
          StaticItems.enchantmentLevelMap()
              .get(i - 46)
              .cloneItem()
              .replace("%enchantment%", StringUtil.getNormalizedName(enchantment.getName()))
              .get());
    }
  }

  @Override
  public void handleCloseEvent(InventoryCloseEvent e) {
    Player player = (Player) e.getPlayer();

    Bukkit.getScheduler()
        .runTaskLater(
            AstralPractice.getInstance(),
            () -> {
              if (player.getOpenInventory() != null
                  && player.getOpenInventory().getType().equals(InventoryType.ANVIL)) {
                return;
              } else if (GuiManager.getInstance().getOpenGui().containsKey(player)) {
                return;
              }

              backTo.open(player);
            },
            5L);
  }
}

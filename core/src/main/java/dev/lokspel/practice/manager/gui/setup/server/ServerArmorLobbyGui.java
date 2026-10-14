package dev.lokspel.practice.manager.gui.setup.server;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.util.PlayerUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.inventory.Inventory.InventoryType;
import dev.lokspel.practice.manager.inventory.InventoryManager;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ServerArmorLobbyGui extends Gui {

  private final Gui backTo;

  private static ItemStack backToItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getServer()
                .getLobbyArmors()
                .getIcons()
                .getBackTo())
        .get();
  }

  private static ItemStack fillerItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getServer()
                .getLobbyArmors()
                .getIcons()
                .getFillerItem())
        .get();
  }

  private static ItemStack fillerItem2() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getServer()
                .getLobbyArmors()
                .getIcons()
                .getFillerItem2())
        .get();
  }

  private static GuiItem nameItem() {
    return BackendUtil.getGuiItem(
        PracticeConfig.guis()
            .getGuis()
            .getSetup()
            .getServer()
            .getLobbyArmors()
            .getIcons()
            .getNameItem());
  }

  private static final List<Integer> helmetSlots = Arrays.asList(9, 11, 13, 15, 17);
  private static final List<Integer> chestplateSlots = Arrays.asList(18, 20, 22, 24, 26);
  private static final List<Integer> leggingsSlots = Arrays.asList(27, 29, 31, 33, 35);
  private static final List<Integer> bootsSlots = Arrays.asList(36, 38, 40, 42, 44);

  public ServerArmorLobbyGui(Gui backTo) {
    super(GuiType.Server_LobbyArmor);
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getSetup().getServer().getLobbyArmors().getTitle(), 6));
    this.backTo = backTo;

    this.build();
  }

  @Override
  public void build() {
    Inventory inventory = this.gui.get(1);

    // Filler item
    for (int x : new int[] {1, 3, 5, 7}) {
      for (int y = 0; y < 6; y++) {
        inventory.setItem(x + (y * 9), fillerItem());
      }
    }

    // Back to item
    inventory.setItem(45, backToItem());

    this.update();
  }

  @Override
  public void update() {
    Inventory inventory = this.gui.get(1);

    for (ArmorStatusType type : ArmorStatusType.values()) {
      var inv = InventoryManager.getInstance().getInventories().get(type.getInventoryType());
      int column = type.getColumn();

      for (int c = 0; c < 6; c++) {
        int slot = column + (c * 9);
        switch (c) {
          case 0:
            inventory.setItem(
                slot, nameItem().cloneItem().replace("%inventoryName%", type.getName()).get());
            break;
          case 1:
            inventory.setItem(slot, inv.getInvArmor().getHelmet());
            break;
          case 2:
            inventory.setItem(slot, inv.getInvArmor().getChestplate());
            break;
          case 3:
            inventory.setItem(slot, inv.getInvArmor().getLeggings());
            break;
          case 4:
            inventory.setItem(slot, inv.getInvArmor().getBoots());
            break;
          case 5:
            if (column != 0) {
              inventory.setItem(slot, fillerItem2());
            }
            break;

          default:
            break;
        }
      }
    }

    this.updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();
    Inventory clickedInv = e.getClickedInventory();

    if (slot == 45) {
      e.setCancelled(true);
      this.save();
      backTo.open(player);
      return;
    }

    if (clickedInv != null && clickedInv.equals(e.getView().getBottomInventory())) {
      return;
    }

    if (!helmetSlots.contains(slot)
        && !chestplateSlots.contains(slot)
        && !leggingsSlots.contains(slot)
        && !bootsSlots.contains(slot)) {
      e.setCancelled(true);
      return;
    }

    ClickType clickType = e.getClick();
    if (clickType.equals(ClickType.LEFT) || clickType.equals(ClickType.RIGHT)) {
      ItemStack cursor = player.getItemOnCursor();
      if (!cursor.getType().isAir()) {
        EquipmentSlot requiredSlot = null;
        if (helmetSlots.contains(slot)) {
          requiredSlot = EquipmentSlot.HEAD;
        } else if (chestplateSlots.contains(slot)) {
          requiredSlot = EquipmentSlot.CHEST;
        } else if (leggingsSlots.contains(slot)) {
          requiredSlot = EquipmentSlot.LEGS;
        } else if (bootsSlots.contains(slot)) {
          requiredSlot = EquipmentSlot.FEET;
        }

        if (requiredSlot != null && cursor.getType().getEquipmentSlot() != requiredSlot) {
          e.setCancelled(true);
        }
      }
    } else {
      e.setCancelled(true);
    }
  }

  public void handleCloseEvent(InventoryCloseEvent e) {
    this.save();
  }

  @Override
  public void handleDragEvent(InventoryDragEvent e) {
    e.setCancelled(true);
  }

  public void save() {
    Inventory inventory = this.gui.get(1);
    if (inventory == null) {
      return;
    }

    for (ArmorStatusType type : ArmorStatusType.values()) {
      var inv = InventoryManager.getInstance().getInventories().get(type.getInventoryType());
      if (inv == null) {
        continue;
      }
      int column = type.getColumn();

      for (int c = 0; c < 6; c++) {
        int slot = column + (c * 9);
        ItemStack item = inventory.getItem(slot);
        ItemStack cleaned = cleanItem(item);

        switch (c) {
          case 1 -> inv.getInvArmor().setHelmet(cleaned);
          case 2 -> inv.getInvArmor().setChestplate(cleaned);
          case 3 -> inv.getInvArmor().setLeggings(cleaned);
          case 4 -> inv.getInvArmor().setBoots(cleaned);
          default -> {}
        }
      }

      for (Player player : inv.getPlayers()) {
        PlayerUtil.clearArmor(player);
        inv.setArmor(player);
        if (inv.getType() == InventoryType.LOBBY) {
          InventoryManager.getInstance().applyLobbyCosmetics(player);
        }
        player.updateInventory();
      }
    }

    InventoryManager.getInstance().setData();
  }

  private static ItemStack cleanItem(ItemStack item) {
    return item == null || item.getType().isAir() ? null : item.clone();
  }

  @Getter
  private enum ArmorStatusType {
    LOBBY("Lobby", 0, InventoryType.LOBBY),
    QUEUE("Queue", 2, InventoryType.MATCH_QUEUE),
    PARTY("Party", 4, InventoryType.PARTY),
    SPECTATE_MODE("Spectate Mode", 6, InventoryType.SPEC_MODE_LOBBY),
    STAFF_MODE("Staff Mode", 8, InventoryType.STAFF_MODE);

    private final String name;
    private final int column;
    private final InventoryType inventoryType;

    ArmorStatusType(final String name, final int column, final InventoryType inventoryType) {
      this.name = name;
      this.column = column;
      this.inventoryType = inventoryType;
    }
  }
}

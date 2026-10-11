package dev.lokspel.practice.manager.gui.guis.queue.customkit;

import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.manager.queue.CustomKitQueueManager;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.ItemCreateUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChooseQueueTypeGui extends Gui {

  private static final String GUI_PATH = "GUIS.CUSTOM-KIT-QUEUE.CHOOSE-TYPE";
  private final Map<UUID, GuiType> backTargets = new HashMap<>();

  public ChooseQueueTypeGui() {
    super(GuiType.Queue_CustomKitChooseType);

    this.gui.put(
        1, InventoryUtil.createInventory(GuiFile.getString(GUI_PATH + ".TITLE"), getRows()));
    build();
  }

  private int getRows() {
    int rows = GuiFile.getInt(GUI_PATH + ".SIZE");
    return Math.clamp(rows <= 0 ? 3 : rows, 1, 6);
  }

  private int hostOwnSlot() {
    return GuiFile.getInt(GUI_PATH + ".SLOTS.HOST-OWN");
  }

  private int joinOthersSlot() {
    return GuiFile.getInt(GUI_PATH + ".SLOTS.JOIN-OTHERS");
  }

  private int backSlot() {
    return GuiFile.getInt(GUI_PATH + ".SLOTS.BACK");
  }

  public void openFor(Player player, GuiType backTarget) {
    if (player == null) {
      return;
    }

    if (backTarget != null) {
      backTargets.put(player.getUniqueId(), backTarget);
    }

    open(player);
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    Inventory inventory = this.gui.get(1);
    inventory.clear();

    ItemStack filler = GuiFile.getGuiItem(GUI_PATH + ".ICONS.FILLER-ITEM").get();
    if (filler == null) {
      filler = ItemCreateUtil.createItem(" ", Material.BLACK_STAINED_GLASS_PANE);
    }

    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, filler);
    }

    ItemStack hostItem = GuiFile.getGuiItem(GUI_PATH + ".ICONS.QUEUE-OWN-KIT").get();
    if (hostItem == null) {
      hostItem = ItemCreateUtil.createItem("<green>Queue Your Own Kit", Material.WRITABLE_BOOK);
    }

    ItemStack joinOthersItem = GuiFile.getGuiItem(GUI_PATH + ".ICONS.QUEUE-OTHERS-KITS").get();
    if (joinOthersItem == null) {
      joinOthersItem = ItemCreateUtil.createItem("<yellow>Queue Others Kits", Material.BOOK);
    }

    ItemStack backItem = GuiFile.getGuiItem(GUI_PATH + ".ICONS.BACK").get();
    if (backItem == null) {
      backItem = ItemCreateUtil.createItem("<red>Back", Material.ARROW);
    }

    if (hostOwnSlot() >= 0 && hostOwnSlot() < inventory.getSize()) {
      inventory.setItem(hostOwnSlot(), hostItem);
    }
    if (joinOthersSlot() >= 0 && joinOthersSlot() < inventory.getSize()) {
      inventory.setItem(joinOthersSlot(), joinOthersItem);
    }
    if (backSlot() >= 0 && backSlot() < inventory.getSize()) {
      inventory.setItem(backSlot(), backItem);
    }

    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();

    if (slot >= e.getView().getTopInventory().getSize()) {
      return;
    }

    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null || profile.getStatus() != ProfileStatus.LOBBY) {
      Common.sendMmMessage(player, LanguageManager.getString("CANT-USE-COMMAND"));
      player.closeInventory();
      return;
    }

    if (slot == hostOwnSlot()) {
      if (CustomKitQueueManager.getInstance().getHostableKits(profile).isEmpty()) {
        Common.sendMmMessage(player, LanguageManager.getString("QUEUES.CUSTOM.NO-SAVED-KITS"));
        return;
      }

      if (!player.hasPermission("ap.playerkit.queue.host")) {
        Common.sendMmMessage(player, LanguageManager.getString("QUEUES.CUSTOM.NO-PERMISSION"));
        return;
      }

      new CustomKitHostSelectorGui(profile).open(player);
      return;
    }

    if (slot == joinOthersSlot()) {
      if (CustomKitQueueManager.getInstance().startJoinSearch(player)) {
        player.closeInventory();
      }
      return;
    }

    if (slot == backSlot()) {
      GuiType backTarget = backTargets.getOrDefault(player.getUniqueId(), GuiType.Queue_Unranked);
      Gui backGui = GuiManager.getInstance().searchGui(backTarget);
      if (backGui != null) {
        backGui.open(player);
      }
    }
  }

  @Override
  public void handleCloseEvent(InventoryCloseEvent e) {
    if (e.getPlayer() instanceof Player player) {
      backTargets.remove(player.getUniqueId());
    }
  }
}

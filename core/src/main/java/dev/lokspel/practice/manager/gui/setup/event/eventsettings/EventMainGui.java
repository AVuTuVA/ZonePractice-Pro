package dev.lokspel.practice.manager.gui.setup.event.eventsettings;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.util.ArenaUtil;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.event.interfaces.EventData;
import dev.lokspel.practice.manager.fight.event.setup.EventWandSetupManager;
import dev.lokspel.practice.manager.fight.event.util.EventUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.event.EventSetupManager;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class EventMainGui extends Gui {

  private final EventData eventData;

  public EventMainGui(EventData eventData) {
    super(GuiType.Event_Main);
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getEvent()
                .getEventMain()
                .getTitle()
                .replace("%eventName%", eventData.getType().getName()),
            4));
    this.eventData = eventData;

    build();
  }

  @Override
  public void build() {
    Inventory inventory = gui.get(1);

    inventory.setItem(
        27,
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getEvent()
                    .getEventMain()
                    .getIcons()
                    .getBackTo())
            .get());

    for (int i : new int[] {28, 29, 30, 31, 32, 33, 34, 35}) {
      inventory.setItem(i, GuiManager.fillerItem());
    }

    inventory.setItem(
        15,
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getEvent()
                    .getEventMain()
                    .getIcons()
                    .getSettings())
            .get());

    update();
  }

  @Override
  public void update() {
    Inventory inventory = gui.get(1);

    inventory.setItem(
        10,
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getEvent()
                    .getEventMain()
                    .getIcons()
                    .getEventName())
            .replace("%eventName%", eventData.getType().getName())
            .setBaseItem(eventData.getIcon().get())
            .get());

    inventory.setItem(
        11,
        eventData.isEnabled()
            ? BackendUtil.getGuiItem(
                    PracticeConfig.guis()
                        .getGuis()
                        .getSetup()
                        .getEvent()
                        .getEventMain()
                        .getIcons()
                        .getStatus()
                        .getEnabled())
                .get()
            : BackendUtil.getGuiItem(
                    PracticeConfig.guis()
                        .getGuis()
                        .getSetup()
                        .getEvent()
                        .getEventMain()
                        .getIcons()
                        .getStatus()
                        .getDisabled())
                .get());

    inventory.setItem(
        16,
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getSetup()
                    .getEvent()
                    .getEventMain()
                    .getIcons()
                    .getLocation())
            .replace("%eventName%", eventData.getType().getName())
            .replace("%corner1%", ArenaUtil.convertLocation(eventData.getCuboidLoc1()))
            .replace("%corner2%", ArenaUtil.convertLocation(eventData.getCuboidLoc2()))
            .replace("%spawnPositions%", String.valueOf(eventData.getSpawns().size()))
            .get());

    this.updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    Inventory inventory = e.getView().getTopInventory();
    ClickType click = e.getClick();

    int slot = e.getRawSlot();

    if (inventory.getSize() > slot && e.getCurrentItem() != null) {
      e.setCancelled(true);

      switch (slot) {
        case 10:
          if (click.isLeftClick()) {
            if (eventData.isEnabled()) {
              Common.sendMmMessage(
                  player,
                  PracticeConfig.language()
                      .getCommand()
                      .getSetup()
                      .getEvent()
                      .getCantEditEnabled());
              return;
            }
            ItemStack cursorItem = player.getItemOnCursor();
            if (cursorItem == null || cursorItem.getType().equals(Material.AIR)) {
              Common.sendMmMessage(
                  player,
                  PracticeConfig.language().getCommand().getSetup().getEvent().getNoItemOnCursor());
              return;
            }

            try {
              eventData.setIcon(new GuiItem(cursorItem));
            } catch (Exception ex) {
              Common.sendMmMessage(player, "<red>" + ex.getMessage());
              return;
            }

            update();
            GuiManager.getInstance().searchGui(GuiType.Event_Summary).update();
            GuiManager.getInstance().searchGui(GuiType.Event_Host).update();
          } else if (click.isRightClick()) {
            Location location = eventData.getAvailableLocation();
            if (location == null) {
              Common.sendMmMessage(
                  player,
                  PracticeConfig.language().getCommand().getSetup().getEvent().getNoLocation());
              return;
            }

            player.teleport(location);
            player.setAllowFlight(true);
            player.setFlying(true);
          }
          break;
        case 11:
          EventUtil.changeStatus(eventData, player);
          this.update();
          break;
        case 15:
          EventSetupManager.getInstance()
              .getEventSetupGuis()
              .get(eventData)
              .get(GuiType.Event_Settings)
              .open(player);
          break;
        case 16:
          if (eventData.isEnabled()) {
            Common.sendMmMessage(
                player,
                PracticeConfig.language().getCommand().getSetup().getEvent().getCantEditEnabled());
            return;
          }

          if (click.isLeftClick()) {
            // Start wand-based setup mode
            player.closeInventory();
            EventWandSetupManager.getInstance().startSetup(player, eventData);
          }
          break;
        case 27:
          GuiManager.getInstance().searchGui(GuiType.Event_Summary).open(player, 1);
          break;

        default:
          break;
      }
    }
  }
}

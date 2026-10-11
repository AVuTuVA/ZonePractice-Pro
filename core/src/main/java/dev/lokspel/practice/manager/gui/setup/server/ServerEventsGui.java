package dev.lokspel.practice.manager.gui.setup.server;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.interfaces.Event;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.cooldown.CooldownObject;
import dev.lokspel.practice.util.cooldown.PlayerCooldown;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ServerEventsGui extends Gui {

  private final Gui backTo;
  private final Map<Integer, Event> eventSlots = new HashMap<>();

  public ServerEventsGui(Gui backTo) {
    super(GuiType.Server_Events);
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getSetup().getServer().getEvents().getTitle(), 2));
    this.backTo = backTo;
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
              eventSlots.clear();

              for (int i : new int[] {10, 11, 12, 13, 14, 15, 16}) {
                inventory.setItem(i, GuiManager.fillerItem());
              }

              inventory.setItem(
                  9,
                  BackendUtil.getGuiItem(
                          PracticeConfig.guis()
                              .getGuis()
                              .getSetup()
                              .getServer()
                              .getEvents()
                              .getIcons()
                              .getBackTo())
                      .get());
              inventory.setItem(
                  17,
                  BackendUtil.getGuiItem(
                          PracticeConfig.guis()
                              .getGuis()
                              .getSetup()
                              .getServer()
                              .getEvents()
                              .getIcons()
                              .getRefreshPage())
                      .get());

              for (Event event : EventManager.getInstance().getEvents()) {
                int slot = inventory.firstEmpty();

                eventSlots.put(slot, event);
                inventory.setItem(slot, getEventItem(event));
              }

              updatePlayers();
            });
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    final Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();
    Inventory inventory = e.getClickedInventory();
    final ClickType click = e.getClick();
    ItemStack item = e.getCurrentItem();

    e.setCancelled(true);

    if (inventory.getSize() <= slot) {
      return;
    }
    if (item == GuiManager.fillerItem()) {
      return;
    }

    switch (slot) {
      case 9:
        backTo.open(player);
        break;
      case 17:
        if (PlayerCooldown.isActive(player, CooldownObject.SERVER_SETUP_EVENT_REFRESH)) {
          Common.sendMmMessage(
              player,
              PracticeConfig.language().getCommand().getSetup().getServer().getWaitBefore());
          return;
        }

        update();
        PlayerCooldown.addCooldown(player, CooldownObject.SERVER_SETUP_EVENT_REFRESH, 5);
        break;
      default:
        if (eventSlots.containsKey(slot)) {
          Event event = eventSlots.get(slot);

          if (click.isLeftClick()) {
            event.addSpectator(player, null, true, false);
          } else if (click.isRightClick()) {
            event.forceEnd(player);
            Common.sendMmMessage(
                player,
                PracticeConfig.language()
                    .getCommand()
                    .getSetup()
                    .getServer()
                    .getEndedEvent()
                    .replace("%event%", event.getType().getName()));
          }
        }
        break;
    }
  }

  private ItemStack getEventItem(Event event) {
    GuiItem guiItem = event.getEventData().getIcon();
    guiItem.setName(
        PracticeConfig.guis()
            .getGuis()
            .getSetup()
            .getServer()
            .getEvents()
            .getIcons()
            .getEventItem()
            .getName());
    guiItem.setLore(
        PracticeConfig.guis()
            .getGuis()
            .getSetup()
            .getServer()
            .getEvents()
            .getIcons()
            .getEventItem()
            .getLore());

    guiItem
        .replace("%eventName%", event.getEventData().getType().getName())
        .replace("%type%", event.getType().getName())
        .replace("%players%", String.valueOf(event.getPlayers().size()))
        .replace("%spectators%", String.valueOf(event.getSpectators().size()));

    return guiItem.get();
  }
}

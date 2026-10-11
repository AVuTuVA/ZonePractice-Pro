package dev.lokspel.practice.manager.gui.setup.event;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.interfaces.EventData;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EventSummaryGui extends Gui {

  private final Map<Integer, EventData> eventSlots = new HashMap<>();

  public EventSummaryGui() {
    super(GuiType.Event_Summary);
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getSetup().getEvent().getEventManager().getTitle(), 3));

    build();
  }

  @Override
  public void build() {
    // Frame
    for (int i = 0; i < gui.get(1).getSize(); i++) {
      gui.get(1).setItem(i, GuiManager.fillerItem());
    }

    // Back to Manager Icon
    gui.get(1)
        .setItem(
            18,
            BackendUtil.getGuiItem(
                    PracticeConfig.guis()
                        .getGuis()
                        .getSetup()
                        .getEvent()
                        .getEventManager()
                        .getIcons()
                        .getBackTo())
                .get());

    update();
  }

  @Override
  public void update() {
    for (EventData eventData : EventManager.getInstance().getEventData().values()) {
      ItemStack icon =
          BackendUtil.getGuiItem(
                  PracticeConfig.guis()
                      .getGuis()
                      .getSetup()
                      .getEvent()
                      .getEventManager()
                      .getIcons()
                      .getEventIcon())
              .replace("%eventName%", eventData.getType().getName())
              .replace(
                  "%state%",
                  eventData.isEnabled()
                      ? PracticeConfig.guis()
                          .getGuis()
                          .getSetup()
                          .getEvent()
                          .getEventManager()
                          .getIcons()
                          .getEventIcon()
                          .getStatusNames()
                          .getEnabled()
                      : PracticeConfig.guis()
                          .getGuis()
                          .getSetup()
                          .getEvent()
                          .getEventManager()
                          .getIcons()
                          .getEventIcon()
                          .getStatusNames()
                          .getDisabled())
              .setBaseItem(eventData.getIcon().get())
              .get();

      int slot = eventData.getType().getGuiSlot();
      gui.get(1).setItem(slot, icon);
      eventSlots.put(slot, eventData);
    }

    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    Inventory inventory = e.getView().getTopInventory();

    int slot = e.getRawSlot();
    ItemStack currentItem = e.getCurrentItem();

    e.setCancelled(true);

    if (inventory.getSize() > slot
        && currentItem != null
        && !currentItem.equals(GuiManager.fillerItem())) {
      if (slot == 18) {
        GuiManager.getInstance().searchGui(GuiType.Setup_Hub).open(player);
      } else if (eventSlots.containsKey(slot)) {
        EventData eventData = eventSlots.get(slot);

        EventSetupManager.getInstance()
            .getEventSetupGuis()
            .get(eventData)
            .get(GuiType.Event_Main)
            .open(player);
      }
    }
  }
}

package dev.lokspel.practice.manager.gui.setup.server;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.manager.arena.ArenaManager;
import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramManager;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.server.ServerManager;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ServerSaveGui extends Gui {

  private final Gui backTo;

  public ServerSaveGui(Gui backTo) {
    super(GuiType.Server_Save);
    this.gui.put(
        1,
        InventoryUtil.createInventory(GuiFile.getString("GUIS.SETUP.SERVER.FILE-SAVE.TITLE"), 1));
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

              for (int i : new int[] {1, 2}) {
                inventory.setItem(i, GuiManager.getFILLER_ITEM());
              }
              inventory.setItem(
                  0, GuiFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.BACK-TO").get());

              inventory.setItem(
                  3,
                  GuiFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE")
                      .replace("%data%", "Arena")
                      .get());
              inventory.setItem(
                  4,
                  GuiFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE")
                      .replace("%data%", "Ladder")
                      .get());
              inventory.setItem(
                  5,
                  GuiFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE")
                      .replace("%data%", "Event")
                      .get());
              inventory.setItem(
                  6,
                  GuiFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE")
                      .replace("%data%", "Player")
                      .get());
              inventory.setItem(
                  7,
                  GuiFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE")
                      .replace("%data%", "Hologram")
                      .get());
              inventory.setItem(
                  8,
                  GuiFile.getGuiItem("GUIS.SETUP.SERVER.FILE-SAVE.ICONS.DATA-SAVE")
                      .replace("%data%", "Mariadb")
                      .get());

              updatePlayers();
            });
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();
    Inventory inventory = e.getClickedInventory();

    e.setCancelled(true);

    if (inventory == null) {
      return;
    }

    if (inventory.getSize() > slot) {
      switch (slot) {
        case 0:
          backTo.update();
          backTo.open(player);
          break;
        case 3:
          Bukkit.getScheduler()
              .runTaskAsynchronously(
                  AstralPractice.getInstance(), () -> ArenaManager.getInstance().saveArenas());
          Common.sendMmMessage(
              player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
          break;
        case 4:
          Bukkit.getScheduler()
              .runTaskAsynchronously(
                  AstralPractice.getInstance(), () -> LadderManager.getInstance().saveLadders());
          Common.sendMmMessage(
              player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
          break;
        case 5:
          Bukkit.getScheduler()
              .runTaskAsynchronously(
                  AstralPractice.getInstance(), () -> EventManager.getInstance().saveEventData());
          Common.sendMmMessage(
              player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
          break;
        case 6:
          Bukkit.getScheduler()
              .runTaskAsynchronously(
                  AstralPractice.getInstance(), () -> ProfileManager.getInstance().saveProfiles());
          Common.sendMmMessage(
              player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
          break;
        case 7:
          Bukkit.getScheduler()
              .runTaskAsynchronously(
                  AstralPractice.getInstance(),
                  () -> HologramManager.getInstance().saveHolograms());
          Common.sendMmMessage(
              player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
          break;
        case 8:
          if (AstralPractice.getDatabase() != null) {
            Bukkit.getScheduler()
                .runTaskAsynchronously(
                    AstralPractice.getInstance(),
                    () -> ServerManager.getInstance().getMariadbSaveRunnable().save());
            Common.sendMmMessage(
                player, LanguageManager.getString("COMMAND.SETUP.SERVER.DATA-SAVED-MANUALLY"));
          } else {
            Common.sendMmMessage(
                player, LanguageManager.getString("COMMAND.SETUP.SERVER.MARIADB-DISABLED"));
          }
          break;

        default:
          break;
      }
    }
  }
}

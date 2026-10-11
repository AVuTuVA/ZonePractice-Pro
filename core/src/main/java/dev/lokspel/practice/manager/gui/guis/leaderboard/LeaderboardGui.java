package dev.lokspel.practice.manager.gui.guis.leaderboard;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.manager.backend.GuiFile;
import dev.lokspel.practice.manager.fight.match.enums.MatchType;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiCache;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.util.InventoryUtil;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class LeaderboardGui extends Gui {

  private final String configPath;
  private final Gui backTo;

  protected LeaderboardGui(GuiType type, String configPath, Gui backTo) {
    super(type);
    this.configPath = configPath;
    this.backTo = backTo;
    this.gui.put(1, InventoryUtil.createInventory(GuiFile.getString(configPath + ".TITLE"), 6));
  }

  @Override
  public void build() {
    update(false);
  }

  @Override
  public void update() {
    Bukkit.getScheduler()
        .runTaskAsynchronously(
            AstralPractice.getInstance(),
            () -> {
              Inventory inventory = gui.get(1);
              inventory.clear();

              for (int i = 45; i < 54; i++) {
                inventory.setItem(i, GuiManager.getFILLER_ITEM());
              }

              for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
                if (!isLadderVisible(ladder)) {
                  continue;
                }

                int slot = inventory.firstEmpty();
                if (slot == -1) {
                  break;
                }
                inventory.setItem(slot, createLadderItem(ladder));
              }

              ItemStack fillerItem = GuiFile.getGuiItem(configPath + ".ICONS.FILLER-ITEM").get();
              for (int i = 0; i < 45; i++) {
                ItemStack current = inventory.getItem(i);
                if (current == null || current.getType().equals(Material.AIR)) {
                  inventory.setItem(i, fillerItem);
                }
              }

              inventory.setItem(45, GuiFile.getGuiItem(configPath + ".ICONS.BACK-TO-HUB").get());
              inventory.setItem(49, createGlobalItem());
              inventory.setItem(53, LbGuiUtil.getCacheInfoItem());

              updatePlayers();

              if (GuiCache.shouldCache(type)) {
                GuiCache.putCache(type, gui);
              }
            });
  }

  protected boolean isLadderVisible(NormalLadder ladder) {
    return ladder.isEnabled() && ladder.getMatchTypes().contains(MatchType.DUEL);
  }

  protected abstract ItemStack createLadderItem(NormalLadder ladder);

  protected abstract ItemStack createGlobalItem();

  @Override
  public void open(Player player, int page) {
    if (GuiCache.shouldCache(type) && GuiCache.isCacheValid(type)) {
      Map<Integer, Inventory> cached = GuiCache.getCached(type);
      if (cached != null) {
        gui.clear();
        gui.putAll(cached);
      }
    } else {
      update();
    }

    super.open(player, page);
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();

    e.setCancelled(true);

    if (slot == 45) {
      if (backTo != null) {
        backTo.open(player);
      } else {
        player.closeInventory();
      }
    }
  }
}

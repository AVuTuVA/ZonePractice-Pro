package dev.lokspel.practice.manager.gui.setup.arena.arenasettings.ffa;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.arenas.FfaArena;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FfaSettingsGui extends Gui {

  private static final ItemStack GO_BACK_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getGoBack())
          .get();
  private static final ItemStack FILLER_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getFiller())
          .get();
  private static final ItemStack BUILD_ENABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getBuild()
                  .getEnabled())
          .get();
  private static final ItemStack BUILD_DISABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getBuild()
                  .getDisabled())
          .get();
  private static final ItemStack TNT_ENABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getMapBlowable()
                  .getEnabled())
          .get();
  private static final ItemStack TNT_DISABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getMapBlowable()
                  .getDisabled())
          .get();
  private static final ItemStack REKIT_ENABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getReKitAfterKill()
                  .getEnabled())
          .get();
  private static final ItemStack REKIT_DISABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getReKitAfterKill()
                  .getDisabled())
          .get();
  private static final ItemStack HEALTH_BELOW_NAME_ENABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getHealthBelowName()
                  .getEnabled())
          .get();
  private static final ItemStack HEALTH_BELOW_NAME_DISABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getHealthBelowName()
                  .getDisabled())
          .get();
  private static final ItemStack HEALTH_RESET_ENABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getHealthResetOnKill()
                  .getEnabled())
          .get();
  private static final ItemStack HEALTH_RESET_DISABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getHealthResetOnKill()
                  .getDisabled())
          .get();
  private static final ItemStack LOBBYDEATH_ENABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getLobbyAfterDeath()
                  .getEnabled())
          .get();
  private static final ItemStack LOBBYDEATH_DISABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getLobbyAfterDeath()
                  .getDisabled())
          .get();
  private static final ItemStack ALLOW_DROP_ENABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getAllowDropItems()
                  .getEnabled())
          .get();
  private static final ItemStack ALLOW_DROP_DISABLED_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getSetup()
                  .getFfaArena()
                  .getSettings()
                  .getIcons()
                  .getAllowDropItems()
                  .getDisabled())
          .get();

  private final FfaArena ffaArena;
  private final ArenaMainGui arenaMainGui;

  public FfaSettingsGui(FfaArena ffaArena, ArenaMainGui arenaMainGui) {
    super(GuiType.FFA_Arena_Settings);

    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getFfaArena()
                .getSettings()
                .getTitle()
                .replace("%arenaName%", ffaArena.getName()),
            4));
    this.ffaArena = ffaArena;
    this.arenaMainGui = arenaMainGui;

    this.build();
  }

  @Override
  public void build() {
    Inventory inventory = this.gui.get(1);

    inventory.setItem(27, GO_BACK_ITEM);
    for (int i : new int[] {28, 29, 30, 31, 32, 33, 34, 35}) {
      inventory.setItem(i, FILLER_ITEM);
    }

    this.update();
  }

  @Override
  public void update() {
    Inventory inventory = this.gui.get(1);

    if (ffaArena.isBuild()) {
      inventory.setItem(10, BUILD_ENABLED_ITEM);
    } else {
      inventory.setItem(10, BUILD_DISABLED_ITEM);
    }

    if (ffaArena.isMapBlowable()) {
      inventory.setItem(11, TNT_ENABLED_ITEM);
    } else {
      inventory.setItem(11, TNT_DISABLED_ITEM);
    }

    if (ffaArena.isReKitAfterKill()) {
      inventory.setItem(12, REKIT_ENABLED_ITEM);
    } else {
      inventory.setItem(12, REKIT_DISABLED_ITEM);
    }

    if (ffaArena.isLobbyAfterDeath()) {
      inventory.setItem(14, LOBBYDEATH_ENABLED_ITEM);
    } else {
      inventory.setItem(14, LOBBYDEATH_DISABLED_ITEM);
    }

    if (ffaArena.isHealthBelowName()) {
      inventory.setItem(15, HEALTH_BELOW_NAME_ENABLED_ITEM);
    } else {
      inventory.setItem(15, HEALTH_BELOW_NAME_DISABLED_ITEM);
    }

    if (ffaArena.isHealthResetOnKill()) {
      inventory.setItem(16, HEALTH_RESET_ENABLED_ITEM);
    } else {
      inventory.setItem(16, HEALTH_RESET_DISABLED_ITEM);
    }

    if (ffaArena.isAllowDropItems()) {
      inventory.setItem(17, ALLOW_DROP_ENABLED_ITEM);
    } else {
      inventory.setItem(17, ALLOW_DROP_DISABLED_ITEM);
    }

    this.updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();

    e.setCancelled(true);

    try {
      switch (e.getRawSlot()) {
        case 10:
          ffaArena.setBuild(!ffaArena.isBuild());
          this.update();
          break;
        case 11:
          ffaArena.setMapBlowable(!ffaArena.isMapBlowable());
          this.update();
          break;
        case 12:
          ffaArena.setReKitAfterKill(!ffaArena.isReKitAfterKill());
          this.update();
          break;
        case 14:
          ffaArena.setLobbyAfterDeath(!ffaArena.isLobbyAfterDeath());
          this.update();
          break;
        case 16:
          ffaArena.setHealthResetOnKill(!ffaArena.isHealthResetOnKill());
          this.update();
          break;
        case 15:
          ffaArena.setHealthBelowName(!ffaArena.isHealthBelowName());
          this.update();
          break;
        case 17:
          ffaArena.setAllowDropItems(!ffaArena.isAllowDropItems());
          this.update();
          break;
        case 27:
          arenaMainGui.open(player);
          break;

        default:
          break;
      }

    } catch (IllegalStateException exception) {
      Common.sendMmMessage(player, "<red>" + exception.getMessage());
    }
  }
}

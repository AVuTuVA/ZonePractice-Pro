package dev.lokspel.practice.manager.gui.setup.server;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import dev.lokspel.practice.manager.fight.match.enums.WeightClass;
import dev.lokspel.practice.manager.fight.match.type.duel.Duel;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.PageUtil;
import dev.lokspel.practice.util.cooldown.CooldownObject;
import dev.lokspel.practice.util.cooldown.PlayerCooldown;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ServerMatchesGui extends Gui {

  private final Gui backTo;
  private static final int spaces = 27;
  private final Map<ItemStack, Match> matchIcons = new HashMap<>();

  public ServerMatchesGui(Gui backTo) {
    super(GuiType.Server_Matches);
    this.backTo = backTo;

    build();
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
              matchIcons.clear();

              for (Match match : MatchManager.getInstance().getLiveMatches()) {
                matchIcons.put(getMatchItem(match), match);
              }

              List<ItemStack> iconsList = new ArrayList<>(matchIcons.keySet());

              int page = 1;
              while (PageUtil.isPageValid(iconsList, page, spaces) || page == 1) {
                if (!gui.containsKey(page)) {
                  gui.put(
                      page,
                      InventoryUtil.createInventory(
                          PracticeConfig.guis()
                              .getGuis()
                              .getSetup()
                              .getServer()
                              .getMatches()
                              .getTitle()
                              .replace("%page%", String.valueOf(page)),
                          6));
                }

                Inventory inventory = gui.get(page);
                inventory.clear();

                // Frame
                for (int i : new int[] {46, 47, 48, 49, 50, 51, 52}) {
                  inventory.setItem(i, GuiManager.getFILLER_ITEM());
                }

                // Match icons
                for (ItemStack icon : PageUtil.getPageItems(iconsList, page, spaces)) {
                  int slot = inventory.firstEmpty();
                  inventory.setItem(slot, icon);
                }

                // Left navigation
                ItemStack left;
                if (PageUtil.isPageValid(iconsList, page - 1, spaces)) {
                  left =
                      BackendUtil.getGuiItem(
                              PracticeConfig.guis()
                                  .getGuis()
                                  .getSetup()
                                  .getServer()
                                  .getMatches()
                                  .getIcons()
                                  .getGoPageLeft())
                          .replace("%page%", String.valueOf(page - 1))
                          .get();
                } else {
                  left =
                      BackendUtil.getGuiItem(
                              PracticeConfig.guis()
                                  .getGuis()
                                  .getSetup()
                                  .getServer()
                                  .getMatches()
                                  .getIcons()
                                  .getBackTo())
                          .get();
                }
                inventory.setItem(45, left);

                // Right navigation
                ItemStack right;
                if (PageUtil.isPageValid(iconsList, page + 1, spaces)) {
                  right =
                      BackendUtil.getGuiItem(
                              PracticeConfig.guis()
                                  .getGuis()
                                  .getSetup()
                                  .getServer()
                                  .getMatches()
                                  .getIcons()
                                  .getGoPageRight())
                          .replace("%page%", String.valueOf(page + 1))
                          .get();
                } else {
                  right = GuiManager.getFILLER_ITEM();
                }
                inventory.setItem(53, right);

                inventory.setItem(
                    49,
                    BackendUtil.getGuiItem(
                            PracticeConfig.guis()
                                .getGuis()
                                .getSetup()
                                .getServer()
                                .getMatches()
                                .getIcons()
                                .getRefresh())
                        .get());

                page++;
              }

              if (gui.containsKey(page)) {
                final int finalPage = page;
                Bukkit.getScheduler()
                    .runTask(
                        AstralPractice.getInstance(),
                        () -> {
                          gui.remove(finalPage);

                          for (Player player : inGuiPlayers.keySet()) {
                            if (inGuiPlayers.get(player) == finalPage) {
                              open(player, finalPage - 1);
                            }
                          }
                        });
              }
            });
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    final Player player = (Player) e.getWhoClicked();
    int slot = e.getRawSlot();
    Inventory inventory = e.getClickedInventory();
    final ClickType click = e.getClick();
    final ItemStack item = e.getCurrentItem();

    e.setCancelled(true);

    if (inventory == null) {
      return;
    }
    if (inventory.getSize() <= slot) {
      return;
    }
    if (item == GuiManager.getFILLER_ITEM()) {
      return;
    }

    int page = inGuiPlayers.get(player);

    switch (slot) {
      case 45:
        if (page == 1) {
          backTo.open(player);
        } else {
          open(player, page - 1);
        }
        break;
      case 53:
        if (gui.containsKey(page + 1)) {
          open(player, page + 1);
        }
        break;
      case 49:
        if (PlayerCooldown.isActive(player, CooldownObject.SERVER_SETUP_MATCH_REFRESH)) {
          Common.sendMmMessage(
              player,
              PracticeConfig.language().getCommand().getSetup().getServer().getWaitBefore());
          return;
        }

        update();
        PlayerCooldown.addCooldown(player, CooldownObject.SERVER_SETUP_MATCH_REFRESH, 5);
        break;
      default:
        if (matchIcons.containsKey(item)) {
          Match match = matchIcons.get(item);

          if (MatchManager.getInstance().getLiveMatches().contains(match)) {
            if (click.isLeftClick()) {
              match.addSpectator(player, null, true, false);
            } else if (click.isRightClick()) {
              match.sendMessage(
                  PracticeConfig.language()
                      .getCommand()
                      .getSetup()
                      .getServer()
                      .getMatchEnded()
                      .replace("%player%", player.getName()),
                  true);
              match.endMatch();

              Common.sendMmMessage(
                  player,
                  PracticeConfig.language()
                      .getCommand()
                      .getSetup()
                      .getServer()
                      .getPlayerEndedMatch()
                      .replace("%matchId%", match.getId()));
              update();
            }
          } else {
            Common.sendMmMessage(
                player,
                PracticeConfig.language()
                    .getCommand()
                    .getSetup()
                    .getServer()
                    .getMatchAlreadyEnded());
            update();
          }
        }
        break;
    }
  }

  private ItemStack getMatchItem(Match match) {
    GuiItem guiItem =
        new GuiItem(
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getServer()
                .getMatches()
                .getIcons()
                .getMatchIcon()
                .getName(),
            match.getLadder().getIcon().getType(),
            Common.getItemDamage(match.getLadder().getIcon()),
            PracticeConfig.guis()
                .getGuis()
                .getSetup()
                .getServer()
                .getMatches()
                .getIcons()
                .getMatchIcon()
                .getLore());
    guiItem
        .replace("%matchType%", match.getType().getName(false))
        .replace("%matchId%", match.getId())
        .replace(
            "%weightClass%",
            (match instanceof Duel && ((Duel) match).isRanked())
                ? WeightClass.RANKED.getName()
                : WeightClass.UNRANKED.getName())
        .replace("%matchType%", match.getType().getName(false))
        .replace("%ladder%", match.getLadder().getDisplayName())
        .replace("%arena%", match.getArena().getDisplayName())
        .replace("%duration%", match.getCurrentRound().getFormattedTime())
        .replace("%roundDuration%", match.getCurrentRound().getFormattedTime())
        .replace("%matchDuration%", match.getFormattedTime())
        .replace("%spectators%", String.valueOf(match.getSpectators().size()));

    return guiItem.get();
  }
}

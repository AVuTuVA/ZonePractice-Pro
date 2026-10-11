package dev.lokspel.practice.manager.gui.guis.selectors;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.arena.arenas.Arena;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.duel.DuelManager;
import dev.lokspel.practice.manager.duel.DuelRequest;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.enums.MatchType;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.manager.party.matchrequest.PartyRequest;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DuelRoundSelectorGui extends MatchStarterGui {

  private static int MAX_ROUNDS =
      PracticeConfig.settings().getMatchSettings().getDuel().getRoundSelector().getMax();
  private static int MIN_ROUNDS =
      PracticeConfig.settings().getMatchSettings().getDuel().getRoundSelector().getMin();

  static {
    if (MAX_ROUNDS > 100) {
      MAX_ROUNDS = 100;
    }
    if (MIN_ROUNDS < 1) {
      MIN_ROUNDS = 1;
    }
  }

  private static final ItemStack BACK_TO_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getKitEditor()
                  .getDuelRoundSelector()
                  .getIcons()
                  .getBackTo())
          .get();
  private static final GuiItem ROUND_SELECTOR =
      BackendUtil.getGuiItem(
          PracticeConfig.guis()
              .getGuis()
              .getKitEditor()
              .getDuelRoundSelector()
              .getIcons()
              .getRoundSelector());
  private static final GuiItem SHOW_LADDER =
      BackendUtil.getGuiItem(
          PracticeConfig.guis()
              .getGuis()
              .getKitEditor()
              .getDuelRoundSelector()
              .getIcons()
              .getShowLadder());
  private static final GuiItem SHOW_ARENA =
      BackendUtil.getGuiItem(
          PracticeConfig.guis()
              .getGuis()
              .getKitEditor()
              .getDuelRoundSelector()
              .getIcons()
              .getShowArena());
  private static final ItemStack START_MATCH_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getKitEditor()
                  .getDuelRoundSelector()
                  .getIcons()
                  .getStartMatch())
          .get();
  private static final ItemStack TEAM_SETUP_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis().getGuis().getParty().getPartySplit().getIcons().getTeamSetup())
          .get();

  private final Party party;
  private Arena arena;
  private int rounds;

  public DuelRoundSelectorGui(
      MatchType matchType, Ladder ladder, Arena arena, Gui backTo, @Nullable Party party) {
    super(GuiType.DuelRound_Selector, matchType, ladder, backTo);

    this.arena = arena;
    this.party = party;
    this.rounds = ladder.getRounds();

    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getKitEditor().getDuelRoundSelector().getTitle(), 1));

    this.build();
  }

  @Override
  public void build() {
    Inventory inventory = gui.get(1);

    inventory.setItem(0, BACK_TO_ITEM);

    GuiItem ladderIconItem = SHOW_LADDER.cloneItem();
    if (ladder.getIcon() != null) {
      ladderIconItem.setBaseItem(ladder.getIcon());
    }

    inventory.setItem(5, ladderIconItem.replace("%ladder%", ladder.getDisplayName()).get());

    if (arena != null) {
      GuiItem arenaIconItem = SHOW_ARENA.cloneItem();
      if (arena.getIcon() != null) {
        arenaIconItem.setBaseItem(arena.getIcon());
      }

      inventory.setItem(6, arenaIconItem.replace("%arena%", arena.getDisplayName()).get());
    }

    inventory.setItem(
        8,
        matchType.equals(MatchType.PARTY_SPLIT) && party != null && party.getMembers().size() > 2
            ? TEAM_SETUP_ITEM
            : START_MATCH_ITEM);

    update();
  }

  @Override
  public void update() {
    gui.get(1)
        .setItem(
            3,
            ROUND_SELECTOR
                .cloneItem()
                .replace("%rounds%", String.valueOf(rounds))
                .replace("%recommended_rounds%", String.valueOf(ladder.getRounds()))
                .get());

    this.updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);

    Player player = (Player) e.getWhoClicked();
    Party party = PartyManager.getInstance().getParty(player);
    int slot = e.getRawSlot();

    if (slot == 0) {
      backTo.open(player);
    } else if (slot == 3) {
      if (e.isRightClick() && rounds < MAX_ROUNDS) {
        this.rounds++;
        this.update();
      } else if (e.isLeftClick() && rounds > MIN_ROUNDS) {
        this.rounds--;
        this.update();
      }
    } else if (slot == 8) {
      if (!ladder.isEnabled() || !ladder.getMatchTypes().contains(matchType)) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getDuelRoundSelector().getLadderNotAvailable());

        LadderSelectorGui ladderSelectorGui = getLadderBackToGui();
        if (ladderSelectorGui != null) {
          ladderSelectorGui.update();
          ladderSelectorGui.open(player);
          return;
        } else {
          player.closeInventory();
        }
      }

      if (ladder instanceof NormalLadder && ((NormalLadder) ladder).isFrozen()) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getDuelRoundSelector().getLadderFrozen());

        LadderSelectorGui ladderSelectorGui = getLadderBackToGui();
        if (ladderSelectorGui != null) {
          ladderSelectorGui.update();
          ladderSelectorGui.open(player);
          return;
        } else {
          player.closeInventory();
        }
      }

      if (arena != null && (!arena.isEnabled() || arena.isFrozen())) {
        Common.sendMmMessage(
            player, PracticeConfig.language().getDuelRoundSelector().getArenaNotAvailable());
        arena = null;
      }

      /*
       * Arena availability is checked in the Duel Request
       */

      /*
       * Duel games arena selector
       */
      if (party == null) {
        Player target = DuelManager.getInstance().getPendingRequestTarget().get(player);

        if (!target.isOnline()) {
          Common.sendMmMessage(
              player, PracticeConfig.language().getDuelRoundSelector().getEnemyOffline());
          player.closeInventory();
          return;
        }

        DuelManager.getInstance()
            .sendRequest(new DuelRequest(player, target, ladder, arena, rounds));
        player.closeInventory();
      }
      /*
       * Party games arena selector
       */
      else {
        /*
         * Own party game arena selector
         */
        if (!this.matchType.equals(MatchType.PARTY_VS_PARTY)) {
          if (party.getMembers().size() < 2) {
            player.closeInventory();
            Common.sendMmMessage(
                player,
                PracticeConfig.language().getDuelRoundSelector().getPartyNotEnoughPlayers());
            return;
          }

          if (openPartySplitGui(player, party, arena, rounds)) {
            return;
          }

          Match match = getMatch(party, arena, rounds);
          if (match == null) {
            Common.sendMmMessage(
                player, PracticeConfig.language().getParty().getNoAvailableArena());
            return;
          }

          party.setMatch(match);
          match.startMatch();
        }
        /*
         * Party vs party game arena selector
         */
        else {
          Party target =
              PartyManager.getInstance().getRequestManager().getPendingRequestTarget().get(party);

          if (!PartyManager.getInstance().getParties().contains(target)) {
            Common.sendMmMessage(
                player, PracticeConfig.language().getDuelRoundSelector().getEnemyPartyOffline());
            GuiManager.getInstance().searchGui(GuiType.Party_OtherParties).open(player);
            return;
          }

          PartyRequest partyRequest = new PartyRequest(party, target, ladder, arena, rounds);
          partyRequest.sendRequest();
        }
      }
    }
  }

  private LadderSelectorGui getLadderBackToGui() {
    if (backTo instanceof LadderSelectorGui) {
      return (LadderSelectorGui) backTo;
    } else if (backTo instanceof ArenaSelectorGui arenaSelectorGui) {
      if (arenaSelectorGui.backTo instanceof LadderSelectorGui) {
        return (LadderSelectorGui) arenaSelectorGui.backTo;
      }
    }
    return null;
  }
}

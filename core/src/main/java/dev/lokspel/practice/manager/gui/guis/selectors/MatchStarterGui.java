package dev.lokspel.practice.manager.gui.guis.selectors;

import dev.lokspel.practice.manager.arena.arenas.Arena;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.enums.MatchType;
import dev.lokspel.practice.manager.fight.match.type.duel.Duel;
import dev.lokspel.practice.manager.fight.match.type.partyffa.PartyFfa;
import dev.lokspel.practice.manager.fight.match.type.playersvsplayers.partysplit.PartySplit;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.guis.party.PartySplitGui;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.util.LadderUtil;
import dev.lokspel.practice.manager.party.Party;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public abstract class MatchStarterGui extends Gui {

  protected final MatchType matchType;
  protected final Ladder ladder;
  protected final Gui backTo;

  public MatchStarterGui(GuiType type, MatchType matchType, Ladder ladder, Gui backTo) {
    super(type);

    this.matchType = matchType;
    this.ladder = ladder;
    this.backTo = backTo;
  }

  protected boolean openPartySplitGui(Player player, Party party, Arena arena, int rounds) {
    if (matchType.equals(MatchType.PARTY_SPLIT) && party.getMembers().size() > 2) {
      new PartySplitGui(party, ladder, arena, rounds, this).open(player);
      return true;
    }
    return false;
  }

  @Nullable
  protected Match getMatch(Party party, Arena arena, int rounds) {
    Match match = null;
    List<Player> matchPlayers = new ArrayList<>(party.getMembers());

    Arena selectedArena = arena;
    if (selectedArena == null) {
      selectedArena = LadderUtil.getAvailableArena(ladder);
    }
    if (selectedArena == null || selectedArena.getAvailableArena() == null) {
      return null;
    }

    if (party.getMembers().size() == 2) {
      match = new Duel(ladder, selectedArena, matchPlayers, false, rounds);
    } else {
      if (matchType.equals(MatchType.PARTY_FFA)) {
        match = new PartyFfa(ladder, selectedArena, party, rounds);
      } else if (matchType.equals(MatchType.PARTY_SPLIT)) {
        match = new PartySplit(ladder, selectedArena, party, rounds);
      }
    }
    return match;
  }
}

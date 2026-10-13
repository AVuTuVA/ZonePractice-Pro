package dev.lokspel.practice.manager.ladder.type;

import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.ladder.abstraction.interfaces.LadderHandle;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.ladder.enums.KnockbackType;
import dev.lokspel.practice.manager.ladder.enums.LadderType;
import org.bukkit.event.Event;

public class Combo extends NormalLadder implements LadderHandle {

  public Combo(String name, LadderType type) {
    super(name, type);
    this.ladderKnockback.setKnockbackType(KnockbackType.COMBO);
  }

  @Override
  public boolean handleEvents(Event e, Match match) {
    return false;
  }
}

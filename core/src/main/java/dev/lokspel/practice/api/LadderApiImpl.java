package dev.lokspel.practice.api;

import dev.lokspel.api.ladder.LadderApi;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import java.util.List;

public class LadderApiImpl implements LadderApi {

  @Override
  public List<String> getLadderNames() {
    return LadderManager.getInstance().getLadders().stream().map(Ladder::getName).toList();
  }
}

package dev.lokspel.practice.manager.fight.match.enums;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.Common;

public enum WeightClass {
  UNRANKED(PracticeConfig.settings().getMatchSettings().getWeightClass().getUnranked()),
  RANKED(PracticeConfig.settings().getMatchSettings().getWeightClass().getRanked());

  private final String name;

  WeightClass(String name) {
    this.name = name;
  }

  public String getName() {
    return Common.mmToNormal(this.name);
  }

  public String getMmName() {
    return Common.serializeNormalToMmString(this.name);
  }
}

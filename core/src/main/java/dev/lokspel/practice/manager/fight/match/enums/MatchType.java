package dev.lokspel.practice.manager.fight.match.enums;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.language.MatchTypeMessages;
import dev.lokspel.practice.util.Common;
import lombok.Getter;

public enum MatchType {
  DUEL(PracticeConfig.settings().getMatchSettings().getMatchTypeNames().getDuel(), "DUEL"),
  PARTY_FFA(
      PracticeConfig.settings().getMatchSettings().getMatchTypeNames().getPartyFfa(), "PARTY-FFA"),
  PARTY_SPLIT(
      PracticeConfig.settings().getMatchSettings().getMatchTypeNames().getPartySplit(),
      "PARTY-SPLIT"),
  PARTY_VS_PARTY(
      PracticeConfig.settings().getMatchSettings().getMatchTypeNames().getPartyVsParty(),
      "PARTY-VS-PARTY");

  private final String name;
  @Getter private final String pathName;

  MatchType(String name, String pathName) {
    this.name = name;
    this.pathName = pathName;
  }

  /** The messages of this match type in language.yml, read anew so that a reload applies. */
  public MatchTypeMessages messages() {
    return switch (this) {
      case DUEL -> PracticeConfig.language().getMatch().getDuel();
      case PARTY_FFA -> PracticeConfig.language().getMatch().getPartyFfa();
      case PARTY_SPLIT -> PracticeConfig.language().getMatch().getPartySplit();
      case PARTY_VS_PARTY -> PracticeConfig.language().getMatch().getPartyVsParty();
    };
  }

  public String getName(boolean miniMessageFormat) {
    if (miniMessageFormat) {
      return Common.serializeNormalToMmString(this.name);
    }
    return this.name;
  }
}

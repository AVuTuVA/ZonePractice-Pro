package dev.lokspel.practice.config.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LadderSpecificMessages {
  @JsonProperty("RESPAWN")
  private String respawn;

  @JsonProperty("PLAYER-RESPAWNED")
  private String playerRespawned;

  @JsonProperty("BED-DESTROYED")
  private String bedDestroyed;

  @JsonProperty("PLAYER-DIED")
  private String playerDied;

  static LadderSpecificMessages duelBedWars() {
    LadderSpecificMessages value = new LadderSpecificMessages();
    value.respawn = "<yellow>You will respawn in <red>%seconds% <yellow>%secondName%.";
    value.playerRespawned = "%teamColor%%player% <yellow>has <green>respawned<yellow>.";
    value.bedDestroyed = "%team% team's <yellow>bed has been destroyed!";

    return value;
  }

  static LadderSpecificMessages duelBattleRush() {
    LadderSpecificMessages value = new LadderSpecificMessages();
    value.respawn = "<yellow>You will respawn in <red>%seconds% <yellow>%secondName%.";
    value.playerRespawned = "%teamColor%%player% <yellow>has <green>respawned<yellow>.";

    return value;
  }

  static LadderSpecificMessages partyVsPartyBedWars() {
    LadderSpecificMessages value = new LadderSpecificMessages();
    value.respawn = "<yellow>You will respawn in <red>%seconds% <yellow>%secondName%.";
    value.playerRespawned = "%teamColor%%player% <yellow>has <green>respawned<yellow>.";
    value.bedDestroyed = "%team% team's <yellow>bed has been destroyed!";
    value.playerDied = "%teamColor%%player% <yellow>has <red>died<yellow>.";

    return value;
  }

  static LadderSpecificMessages partyVsPartyBattleRush() {
    LadderSpecificMessages value = new LadderSpecificMessages();
    value.respawn = "<yellow>You will respawn in <red>%seconds% <yellow>%secondName%.";
    value.playerRespawned = "%teamColor%%player% <yellow>has <green>respawned<yellow>.";
    value.playerDied = "%teamColor%%player% <yellow>has <red>died<yellow>.";

    return value;
  }
}

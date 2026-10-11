package dev.lokspel.practice.config.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

/** Name and description of one ladder type (LADDER.LADDER-TYPES.&lt;TYPE&gt;). */
@Getter
public class LadderTypeMessages {
  @JsonProperty("NAME")
  private String name;

  @JsonProperty("DESCRIPTION")
  private List<String> description;

  static LadderTypeMessages basic() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Basic";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>In this ladder, everything follows",
            "<gray>the normal PVP rules. Build is prohibited",
            "<gray>,so with this type of ladder",
            "<gray>several matches can take place in the",
            "<gray>same arena at the same time.",
            "<gray>You can only adjust the ladder settings",
            "<gray>with the general ladder settings.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages build() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Build";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>This ladder works in the same way as BASIC, ",
            "<gray>except that build is allowed, so",
            "<gray>only one match can take place in ",
            "<gray>an arena at a time with this ladder type.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages sumo() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Sumo";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>With this ladder, players will die ",
            "<gray>if they come into contact with ",
            "<gray>water or void. In addition, build ",
            "<gray>is disabled and players life and ",
            "<gray>food levels do not go down.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages tntSumo() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "TNT Sumo";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>TNT explodes instantly on placement",
            "<gray>with custom knockback physics.",
            "<gray>Only player-placed blocks are destroyed",
            "<gray>and returned to their owner.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages boxing() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Boxing";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>With this ladder, players are not ",
            "<gray>damaged and their food level does ",
            "<gray>not go down. To win, the amount of ",
            "<gray>hits counts. Build is disabled.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages pearlFight() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Pearl Fight";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>With this ladder, players will get their ",
            "<gray>placed blocks back after a certain time. ",
            "<gray>If another player breaks a player block, ",
            "<gray>the player who originally placed it will ",
            "<gray>still get it back. Other than that, everything ",
            "<gray>works according to the normal pvp rules.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages spleef() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Spleef";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>With this ladder, players are given an ",
            "<gray>item to shoot the snow blocks underneath ",
            "<gray>them, which they can use to get the ",
            "<gray>snow and shoot each other. Life and ",
            "<gray>food levels do not go down and players ",
            "<gray>can die in water or void.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages skywars() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "SkyWars";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>In this ladder, players can open ",
            "<gray>chests during the game to access ",
            "<gray>useful items. Building is allowed.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages bedwars() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "BedWars";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>In this ladder, players cannot die ",
            "<gray>permanently until their bed is destroyed.",
            "<gray>Building is allowed. Can only be assigned",
            "<gray>to an arena of type 'BedWars'.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages fireballFight() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Fireball Fight";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>This ladder is similar to bedwars,",
            "<gray>except that players can use fireballs",
            "<gray>to jump between islands. The fireball",
            "<gray>and tnt do no damage to the players,",
            "<gray>but push them very far back.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages mlgRush() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "MLG Rush";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>Fast bed-rush gamemode where breaking",
            "<gray>the enemy bed scores one point instantly.",
            "<gray>Players respawn immediately with no",
            "<gray>health damage, only knockback.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages bridges() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Bridges";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>In order to win the rounds players have to",
            "<gray>jump in the other team's portal which is in",
            "<gray>the other side of the bridge. Build is enabled.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages battleRush() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Battle Rush";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>In this gamemode there is no damage. The",
            "<gray>goal is to jump in the other player's portal.",
            "<gray>Players can build and there is a respawn",
            "<gray>cooldown.",
            "<dark_gray><st>----------------------------------");

    return value;
  }

  static LadderTypeMessages creeper() {
    LadderTypeMessages value = new LadderTypeMessages();
    value.name = "Creeper";
    value.description =
        List.of(
            "<dark_gray><st>----------------------------------",
            "<gray>With this ladder, creepers ignite as soon as a",
            "<gray>player gets close to them, even when the player is",
            "<gray>out of sight. The delay before they explode can be",
            "<gray>changed in the ladder settings.",
            "<dark_gray><st>----------------------------------");

    return value;
  }
}

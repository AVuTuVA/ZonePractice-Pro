package dev.lokspel.practice.config.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class EventMessages {
  @JsonProperty("COMMAND-HELP")
  private List<String> commandHelp;

  @JsonProperty("EVENT-LIVE")
  private String eventLive;

  @JsonProperty("EVENT-ALREADY-ENABLED")
  private String eventAlreadyEnabled;

  @JsonProperty("EVENT-ALREADY-DISABLED")
  private String eventAlreadyDisabled;

  @JsonProperty("CANT-EDIT")
  private String cantEdit;

  @JsonProperty("KIT-SET")
  private String kitSet;

  @JsonProperty("BROADCAST")
  private String broadcast;

  @JsonProperty("WON-FIGHT")
  private String wonFight;

  @JsonProperty("SPECTATOR-FORWARDED")
  private String spectatorForwarded;

  @JsonProperty("ROUND-DRAWN")
  private String roundDrawn;

  @JsonProperty("PLAYER-OUT-OF-ROUND")
  private String playerOutOfRound;

  @JsonProperty("SPECTATOR-TARGET-NOT-PLAYING")
  private String spectatorTargetNotPlaying;

  @JsonProperty("ROUND-STARTING")
  private String roundStarting;

  @JsonProperty("ROUND-ENDING")
  private String roundEnding;

  @JsonProperty("WON-EVENT")
  private String wonEvent;

  @JsonProperty("NO-WINNER")
  private String noWinner;

  @JsonProperty("STARTED-SPECTATING")
  private String startedSpectating;

  @JsonProperty("PLAYER-OUT")
  private String playerOut;

  @JsonProperty("KIT-COMMAND-HELP")
  private String kitCommandHelp;

  @JsonProperty("JUGGERNAUT-KIT-SET")
  private String juggernautKitSet;

  @JsonProperty("PLAYERS-KIT-SET")
  private String playersKitSet;

  @JsonProperty("JUGGERNAUT-ANNOUNCE")
  private String juggernautAnnounce;

  @JsonProperty("GAME-STARTING")
  private String gameStarting;

  @JsonProperty("GAME-ENDING")
  private String gameEnding;

  @JsonProperty("JUGGERNAUT-WON")
  private String juggernautWon;

  @JsonProperty("ATTACKERS-WON")
  private String attackersWon;

  @JsonProperty("PLAYER-DIED")
  private String playerDied;

  @JsonProperty("JUGGERNAUT-DIED")
  private String juggernautDied;

  @JsonProperty("LIFE-LEFT")
  private String lifeLeft;

  @JsonProperty("POINTS-RECEIVED")
  private String pointsReceived;

  @JsonProperty("BLOWN-UP")
  private String blownUp;

  @JsonProperty("PLAYER-TAGGED-PLAYER")
  private String playerTaggedPlayer;

  @JsonProperty("START-TAGGER")
  private String startTagger;

  @JsonProperty("PLAYER-TAGGED")
  private String playerTagged;

  static EventMessages brackets() {
    EventMessages value = new EventMessages();
    value.commandHelp =
        List.of(
            "<red><st>---------------------------------------",
            " <red>Brackets Event Setup",
            "",
            "<gray>Setup Commands:",
            "<red> » <click:suggest_command:'/%label% brackets setkit'><hover:show_text:'<gray>Set"
                + " the kit for the event.'>/%label% brackets setkit",
            "<red> » <click:suggest_command:'/%label% brackets"
                + " enable'><hover:show_text:'<gray>Enable the event.'>/%label% brackets enable",
            "<red> » <click:suggest_command:'/%label% brackets"
                + " disable'><hover:show_text:'<gray>Disable the event.'>/%label% brackets disable",
            "",
            "<yellow>Note: <gray>Use <white>/setup event <gray>GUI to get the",
            "<gray>Event Wand for corner & spawn position setup.",
            "<red><st>---------------------------------------");
    value.eventLive = "<red>You cannot edit the Brackets Event while it is ongoing.";
    value.eventAlreadyEnabled = "<red>Brackets event is already enabled.";
    value.eventAlreadyDisabled = "<red>Brackets event is already disabled.";
    value.cantEdit = "<red>You cannot edit an enabled Event.";
    value.kitSet = "<green>You successfully set the kit for the brackets event.";
    value.broadcast =
        "<gold>Brackets <white>event is now <green>open<white>! <click:run_command:/event"
            + " join><hover:show_text:'<green>Click here <yellow>to join the event.'><green>Join"
            + " Event</hover></click>";
    value.wonFight = "<yellow>%player% <green>won <yellow>the fight!";
    value.spectatorForwarded =
        "<yellow>Since the previous fight ended, you have been forwarded to spectate another one.";
    value.roundDrawn =
        "<yellow>Round <gold>%round% <yellow>fights have been drawn! <green>Good luck!";
    value.playerOutOfRound =
        "<red>Unfortunately, the round started with an odd number of players, so %player% is out of"
            + " this round.";
    value.spectatorTargetNotPlaying =
        "<red>%target% isn't playing anymore. You are going to spectate a random match.";
    value.roundStarting = "<yellow>The round starts in <red>%seconds% <yellow>%secondName%";
    value.roundEnding = "<yellow>The round ends in <red>%seconds% <yellow>%secondName%";
    value.wonEvent = "<gold>%winner% <yellow>has won the event. <green>Congratulations!";
    value.noWinner = "<red>No one won the event in time.";
    value.startedSpectating = "<gray>%spectator% <yellow>started spectating the event.";
    value.playerOut = "<yellow>%player% <red>is out of the game.";

    return value;
  }

  static EventMessages juggernaut() {
    EventMessages value = new EventMessages();
    value.commandHelp =
        List.of(
            "<red><st>---------------------------------------",
            " <red>Juggernaut Event Setup",
            "",
            "<gray>Setup Commands:",
            "<red> » <click:suggest_command:'/%label% %label2% setkit"
                + " juggernaut'><hover:show_text:'<gray>Set the kit for the juggernaut.'>/%label%"
                + " %label2% setkit juggernaut",
            "<red> » <click:suggest_command:'/%label% %label2% setkit"
                + " players'><hover:show_text:'<gray>Set the kit for the players.'>/%label%"
                + " %label2% setkit players",
            "<red> » <click:suggest_command:'/%label% %label2%"
                + " enable'><hover:show_text:'<gray>Enable the event.'>/%label% %label2% enable",
            "<red> » <click:suggest_command:'/%label% %label2%"
                + " disable'><hover:show_text:'<gray>Disable the event.'>/%label% %label2% disable",
            "",
            "<yellow>Note: <gray>Use <white>/setup event <gray>GUI to get the",
            "<gray>Event Wand for corner & spawn position setup.",
            "<red><st>---------------------------------------");
    value.eventLive = "<red>You cannot edit the Juggernaut Event while it is ongoing.";
    value.eventAlreadyEnabled = "<red>Juggernaut event is already enabled.";
    value.eventAlreadyDisabled = "<red>Juggernaut event is already disabled.";
    value.cantEdit = "<red>You cannot edit an enabled Event.";
    value.broadcast =
        "<gold>Juggernaut <white>event is now <green>open<white>! <click:run_command:/event"
            + " join><hover:show_text:'<green>Click here <yellow>to join the event.'><green>Join"
            + " Event</hover></click>";
    value.noWinner = "<red>No one won the event in time.";
    value.startedSpectating = "<gray>%spectator% <yellow>started spectating the event.";
    value.kitCommandHelp = "<red>/%label% %label2% setkit <juggernaut/players>";
    value.juggernautKitSet =
        "<green>You successfully set the <yellow>Juggernaut <green>kit for the event.";
    value.playersKitSet =
        "<green>You successfully set the <yellow>Players <green>kit for the event.";
    value.juggernautAnnounce = "<gold>%player% <yellow>is the juggernaut!";
    value.gameStarting = "<yellow>The game starts in <red>%seconds% <yellow>%secondName%";
    value.gameEnding = "<yellow>The game ends in <red>%seconds% <yellow>%secondName%";
    value.juggernautWon =
        "<yellow>%player% <gray>as <red>Juggernaut <gray>won the event. <green>Congratulations!";
    value.attackersWon = "<gray>The <green>attackers <gray>won the event. <green>Congratulations!";
    value.playerDied =
        "<gray>%player% <red>has died"
            + " (<yellow>%startPlayerCount%<red>/<yellow>%playerCount%<red>)!";
    value.juggernautDied = "<yellow>%player% the <gold>Juggernaut <yellow>has died.";

    return value;
  }

  static EventMessages lms() {
    EventMessages value = new EventMessages();
    value.commandHelp =
        List.of(
            "<red><st>---------------------------------------",
            " <red>LMS Event Setup",
            "",
            "<gray>Setup Commands:",
            "<red> » <click:suggest_command:'/%label% lms setkit'><hover:show_text:'<gray>Set the"
                + " kit for the event.'>/%label% lms setkit",
            "<red> » <click:suggest_command:'/%label% lms enable'><hover:show_text:'<gray>Enable"
                + " the event.'>/%label% lms enable",
            "<red> » <click:suggest_command:'/%label% lms disable'><hover:show_text:'<gray>Disable"
                + " the event.'>/%label% lms disable",
            "",
            "<yellow>Note: <gray>Use <white>/setup event <gray>GUI to get the",
            "<gray>Event Wand for corner & spawn position setup.",
            "<red><st>---------------------------------------");
    value.eventLive = "<red>You cannot edit the LMS Event while it is ongoing.";
    value.eventAlreadyEnabled = "<red>LMS event is already enabled.";
    value.eventAlreadyDisabled = "<red>LMS event is already disabled.";
    value.cantEdit = "<red>You cannot edit an enabled Event.";
    value.kitSet = "<green>You successfully set the kit for the LMS event.";
    value.broadcast =
        "<gold>LMS <white>event is now <green>open<white>! <click:run_command:/event"
            + " join><hover:show_text:'<green>Click here <yellow>to join the event.'><green>Join"
            + " Event</hover></click>";
    value.wonEvent = "<gold>%winner% <yellow>has won the event. <green>Congratulations!";
    value.noWinner = "<red>No one won the event in time.";
    value.startedSpectating = "<gray>%spectator% <yellow>started spectating the event.";
    value.gameStarting = "<yellow>The game starts in <red>%seconds% <yellow>%secondName%";
    value.playerDied =
        "<gray>%player% <red>has died"
            + " (<yellow>%startPlayerCount%<red>/<yellow>%playerCount%<red>)!";

    return value;
  }

  static EventMessages oitc() {
    EventMessages value = new EventMessages();
    value.commandHelp =
        List.of(
            "<red><st>---------------------------------------",
            " <red>OITC Event Setup",
            "",
            "<gray>Setup Commands:",
            "<red> » <click:suggest_command:'/%label% oitc enable'><hover:show_text:'<gray>Enable"
                + " the event.'>/%label% oitc enable",
            "<red> » <click:suggest_command:'/%label% oitc disable'><hover:show_text:'<gray>Disable"
                + " the event.'>/%label% oitc disable",
            "",
            "<yellow>Note: <gray>Use <white>/setup event <gray>GUI to get the",
            "<gray>Event Wand for corner & spawn position setup.",
            "<red><st>---------------------------------------");
    value.eventLive = "<red>You cannot edit the OITC Event while it is ongoing.";
    value.eventAlreadyEnabled = "<red>OITC event is already enabled.";
    value.eventAlreadyDisabled = "<red>OITC event is already disabled.";
    value.broadcast =
        "<gold>OITC <white>event is now <green>open<white>! <click:run_command:/event"
            + " join><hover:show_text:'<green>Click here <yellow>to join the event.'><green>Join"
            + " Event</hover></click>";
    value.wonEvent =
        "<gold>%winner% <yellow>has won the event with <red>%points% <yellow>points."
            + " <green>Congratulations!";
    value.noWinner = "<red>No one won the event in time.";
    value.startedSpectating = "<gray>%spectator% <yellow>started spectating the event.";
    value.gameStarting = "<yellow>The game starts in <red>%seconds% <yellow>%secondName%";
    value.playerDied =
        "<gray>%player% <red>has died"
            + " (<yellow>%startPlayerCount%<red>/<yellow>%playerCount%<red>)!";
    value.lifeLeft = "<red>You have <yellow>%life% <red>life left.";
    value.pointsReceived = "<yellow>You received <gold>%point% <yellow>points.";

    return value;
  }

  static EventMessages splegg() {
    EventMessages value = new EventMessages();
    value.commandHelp =
        List.of(
            "<red><st>---------------------------------------",
            " <red>Splegg Event Setup",
            "",
            "<gray>Setup Commands:",
            "<red> » <click:suggest_command:'/%label% splegg enable'><hover:show_text:'<gray>Enable"
                + " the event.'>/%label% splegg enable",
            "<red> » <click:suggest_command:'/%label% splegg"
                + " disable'><hover:show_text:'<gray>Disable the event.'>/%label% splegg disable",
            "",
            "<yellow>Note: <gray>Use <white>/setup event <gray>GUI to get the",
            "<gray>Event Wand for corner & spawn position setup.",
            "<red><st>---------------------------------------");
    value.eventLive = "<red>You cannot edit the Splegg Event while it is ongoing.";
    value.eventAlreadyEnabled = "<red>Splegg event is already enabled.";
    value.eventAlreadyDisabled = "<red>Splegg event is already disabled.";
    value.broadcast =
        "<gold>Splegg <white>event is now <green>open<white>! <click:run_command:/event"
            + " join><hover:show_text:'<green>Click here <yellow>to join the event.'><green>Join"
            + " Event</hover></click>";
    value.wonEvent = "<gold>%winner% <yellow>has won the event. <green>Congratulations!";
    value.noWinner = "<red>No one won the event in time.";
    value.startedSpectating = "<gray>%spectator% <yellow>started spectating the event.";
    value.gameStarting = "<yellow>The game starts in <red>%seconds% <yellow>%secondName%";
    value.playerDied =
        "<gray>%player% <red>has died"
            + " (<yellow>%startPlayerCount%<red>/<yellow>%playerCount%<red>)!";

    return value;
  }

  static EventMessages sumo() {
    EventMessages value = new EventMessages();
    value.commandHelp =
        List.of(
            "<red><st>---------------------------------------",
            " <red>Sumo Event Setup",
            "",
            "<gray>Setup Commands:",
            "<red> » <click:suggest_command:'/%label% sumo enable'><hover:show_text:'<gray>Enable"
                + " the event.'>/%label% sumo enable",
            "<red> » <click:suggest_command:'/%label% sumo disable'><hover:show_text:'<gray>Disable"
                + " the event.'>/%label% sumo disable",
            "<red> » <click:suggest_command:'/%label% sumo setkit'><hover:show_text:'<gray>Set the"
                + " kit for the event.'>/%label% sumo setkit",
            "",
            "<yellow>Note: <gray>Use <white>/setup event <gray>GUI to get the",
            "<gray>Event Wand for corner & spawn position setup.",
            "<red><st>---------------------------------------");
    value.eventLive = "<red>You cannot edit the Sumo Event while it is ongoing.";
    value.eventAlreadyEnabled = "<red>Sumo event is already enabled.";
    value.eventAlreadyDisabled = "<red>Sumo event is already disabled.";
    value.cantEdit = "<red>You cannot edit an enabled Event.";
    value.kitSet = "<green>You successfully set the kit for the sumo event.";
    value.broadcast =
        "<gold>Sumo <white>event is now <green>open<white>! <click:run_command:/event"
            + " join><hover:show_text:'<green>Click here <yellow>to join the event.'><green>Join"
            + " Event</hover></click>";
    value.wonFight = "<yellow>%player% <green>won <yellow>the fight!";
    value.spectatorForwarded =
        "<yellow>Since the previous fight ended, you have been forwarded to spectate another one.";
    value.roundDrawn =
        "<yellow>Round <gold>%round% <yellow>fights have been drawn! <green>Good luck!";
    value.playerOutOfRound =
        "<red>Unfortunately, the round started with an odd number of players, so %player% is out of"
            + " this round.";
    value.spectatorTargetNotPlaying =
        "<red>%target% isn't playing anymore. You are going to spectate a random match.";
    value.roundStarting = "<yellow>The round starts in <red>%seconds% <yellow>%secondName%";
    value.roundEnding = "<yellow>The round ends in <red>%seconds% <yellow>%secondName%";
    value.wonEvent = "<gold>%winner% <yellow>has won the event. <green>Congratulations!";
    value.noWinner = "<red>No one won the event in time.";
    value.startedSpectating = "<gray>%spectator% <yellow>started spectating the event.";
    value.playerOut = "<yellow>%player% <red>is out of the game.";

    return value;
  }

  static EventMessages tnttag() {
    EventMessages value = new EventMessages();
    value.commandHelp =
        List.of(
            "<red><st>---------------------------------------",
            " <red>TntTag Event Setup",
            "",
            "<gray>Setup Commands:",
            "<red> » <click:suggest_command:'/%label% tnttag enable'><hover:show_text:'<gray>Enable"
                + " the event.'>/%label% tnttag enable",
            "<red> » <click:suggest_command:'/%label% tnttag"
                + " disable'><hover:show_text:'<gray>Disable the event.'>/%label% tnttag disable",
            "",
            "<yellow>Note: <gray>Use <white>/setup event <gray>GUI to get the",
            "<gray>Event Wand for corner & spawn position setup.",
            "<red><st>---------------------------------------");
    value.eventLive = "<red>You cannot edit the TntTag Event while it is ongoing.";
    value.eventAlreadyEnabled = "<red>TntTag event is already enabled.";
    value.eventAlreadyDisabled = "<red>TntTag event is already disabled.";
    value.broadcast =
        "<gold>Tnt Tag <white>event is now <green>open<white>! <click:run_command:/event"
            + " join><hover:show_text:'<green>Click here <yellow>to join the event.'><green>Join"
            + " Event</hover></click>";
    value.roundStarting = "<yellow>The IT(s) will be picked in <red>%seconds% <yellow>%secondName%";
    value.wonEvent = "<gold>%winner% <yellow>has won the event. <green>Congratulations!";
    value.noWinner = "<red>No one won the event in time.";
    value.startedSpectating = "<gray>%spectator% <yellow>started spectating the event.";
    value.blownUp = "<yellow>%player% <red>blown up!";
    value.playerTaggedPlayer = "<gold>%player% <yellow>tagged <red>%target%<yellow>!";
    value.startTagger = "<gold>%player% <yellow>has the tnt.";
    value.playerTagged = "<red>You're IT, tag someone!";

    return value;
  }
}

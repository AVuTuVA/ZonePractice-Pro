package dev.lokspel.practice.config.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class CommandEventArguments {
  @JsonProperty("HOST")
  private Host host = new Host();

  @JsonProperty("JOIN")
  private Join join = new Join();

  @JsonProperty("STOP")
  private Stop stop = new Stop();

  @JsonProperty("HELP")
  private Help help = new Help();

  @JsonProperty("SPAWN-POSITION")
  private SpawnPosition spawnPosition = new SpawnPosition();

  @JsonProperty("BRACKETS")
  private EventMessages brackets = EventMessages.brackets();

  @JsonProperty("JUGGERNAUT")
  private EventMessages juggernaut = EventMessages.juggernaut();

  @JsonProperty("LMS")
  private EventMessages lms = EventMessages.lms();

  @JsonProperty("OITC")
  private EventMessages oitc = EventMessages.oitc();

  @JsonProperty("SPLEGG")
  private EventMessages splegg = EventMessages.splegg();

  @JsonProperty("SUMO")
  private EventMessages sumo = EventMessages.sumo();

  @JsonProperty("TNTTAG")
  private EventMessages tnttag = EventMessages.tnttag();

  @Getter
  public static class Host {
    @JsonProperty("COMMAND-HELP")
    private String commandHelp = "<red>/%label% host";

    @JsonProperty("NO-PERMISSION")
    private String noPermission = "<red>You don't have permission.";

    @JsonProperty("CANT-HOST-NOW")
    private String cantHostNow = "<red>You can't start an event, until the current one starts.";

    @JsonProperty("CANT-HOST-MULTIPLE")
    private String cantHostMultiple = "<red>There can only be one event happening at a time.";
  }

  @Getter
  public static class Join {
    @JsonProperty("COMMAND-HELP")
    private String commandHelp = "<red>/%label% join";

    @JsonProperty("NO-PERMISSION")
    private String noPermission = "<red>You don't have permission.";

    @JsonProperty("CANT-JOIN")
    private String cantJoin =
        "<red>You can only join events while you are in the lobby and not a member of a party.";

    @JsonProperty("ALREADY-IN-EVENT")
    private String alreadyInEvent = "<red>You are already in an event.";

    @JsonProperty("NO-EVENT")
    private String noEvent = "<red>There are no events that you can currently join.";
  }

  @Getter
  public static class Stop {
    @JsonProperty("NO-PERMISSION")
    private String noPermission = "<red>You don't have permission.";

    @JsonProperty("NO-EVENT")
    private String noEvent = "<red>There is currently no event.";

    @JsonProperty("MORE-EVENTS")
    private String moreEvents =
        "<red>There is currently more than one live event. Please use the /event stop <event>"
            + " command.";

    @JsonProperty("NO-LIVE-EVENT")
    private String noLiveEvent = "<red>There is now live event with the name <yellow>%event%<red>.";

    @JsonProperty("END-SUCCESS")
    private String endSuccess =
        "<yellow>You <green>successfully <red>ended <yellow>the <gold>%event% <yellow>event.";
  }

  @Getter
  public static class Help {
    @JsonProperty("USER")
    private List<String> user =
        List.of(
            "<red><st>---------------------------------------",
            "<red> » /%label% host - Host an event.",
            "<red> » /%label% join - Join an event.",
            "<red><st>---------------------------------------");

    @JsonProperty("STAFF")
    private List<String> staff =
        List.of(
            "<red><st>---------------------------------------",
            "<red> » /%label% host - Host an event.",
            "<red> » /%label% join - Join an event.",
            "<red> » /%label% <event> - An events settings.",
            "<red> » /%label% stop <event> - Stop an ongoing event.",
            "<red><st>---------------------------------------");
  }

  @Getter
  public static class SpawnPosition {
    @JsonProperty("NO-REGION")
    private String noRegion = "<red>You must set the two corners of the arena first.";
  }
}

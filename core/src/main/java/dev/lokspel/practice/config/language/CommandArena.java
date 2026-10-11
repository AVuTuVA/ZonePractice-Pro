package dev.lokspel.practice.config.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommandArena {
  @JsonProperty("NO-PERMISSION")
  private String noPermission = "<red>You don't have permission!";

  @JsonProperty("ARGUMENTS")
  private CommandArenaArguments arguments = new CommandArenaArguments();
}

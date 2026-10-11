package dev.lokspel.practice.config.gui;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SetupLadderSettings {
  @JsonProperty("TITLE")
  private String title = "<dark_blue>%ladder% <dark_gray>- Settings";

  @JsonProperty("ICONS")
  private SetupLadderSettingsIcons icons = new SetupLadderSettingsIcons();
}

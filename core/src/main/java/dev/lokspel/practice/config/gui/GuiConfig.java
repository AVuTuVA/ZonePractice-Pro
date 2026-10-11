package dev.lokspel.practice.config.gui;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokspel.practice.config.ItemConfig;
import lombok.Getter;

@Getter
public class GuiConfig {
  @JsonProperty("GENERAL-FILLER-ITEM")
  private ItemConfig generalFillerItem = ItemConfig.of("BLACK_STAINED_GLASS_PANE").name(" ");

  @JsonProperty("GUIS")
  private Guis guis = new Guis();
}

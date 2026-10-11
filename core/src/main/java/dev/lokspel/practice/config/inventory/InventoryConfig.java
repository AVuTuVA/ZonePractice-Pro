package dev.lokspel.practice.config.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
public class InventoryConfig {
  @Comment({"Spawn inventories", "Set the slot to -1 if you don't want to display the item."})
  @JsonProperty("LOBBY-BASIC")
  private InventoryLayout lobbyBasic = InventoryLayout.lobbyBasic();

  @Comment("Party inventory items")
  @JsonProperty("PARTY")
  private InventoryLayout party = InventoryLayout.party();

  @Comment("Queue items")
  @JsonProperty("QUEUE")
  private Queue queue = new Queue();

  @Comment("Spectator items")
  @JsonProperty("SPECTATOR")
  private Spectator spectator = new Spectator();

  @Comment("Staff Mode items")
  @JsonProperty("STAFF-MODE")
  private InventoryLayout staffMode = InventoryLayout.staffMode();

  @Getter
  public static class Queue {
    @JsonProperty("MATCH")
    private InventoryLayout match = InventoryLayout.queueMatch();

    @JsonProperty("EVENT")
    private InventoryLayout event = InventoryLayout.queueEvent();
  }

  @Getter
  public static class Spectator {
    @JsonProperty("LOBBY")
    private InventoryLayout lobby = InventoryLayout.lobby();

    @JsonProperty("MATCH")
    private InventoryLayout match = InventoryLayout.spectatorMatch();

    @JsonProperty("EVENT")
    private InventoryLayout event = InventoryLayout.spectatorEvent();

    @JsonProperty("FFA")
    private InventoryLayout ffa = InventoryLayout.spectatorEvent();
  }
}

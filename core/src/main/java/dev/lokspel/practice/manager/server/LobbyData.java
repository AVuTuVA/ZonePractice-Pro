package dev.lokspel.practice.manager.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code lobby} section of {@code backend.yml}: world name and coordinates as plain values.
 *
 * <p>Older versions stored the lobby as a serialized {@link org.bukkit.Location}, which has the
 * same keys plus {@code ==}; {@link #getSerializedType()} is set for such a value.
 */
@Getter
@Setter
public class LobbyData {
  /** The {@code ==} key of a lobby stored as a Bukkit {@code Location}; never written. */
  @JsonProperty("==")
  private String serializedType;

  @JsonProperty("world")
  private String world;

  @JsonProperty("x")
  private Double posX;

  @JsonProperty("y")
  private Double posY;

  @JsonProperty("z")
  private Double posZ;

  @JsonProperty("yaw")
  private Double yaw;

  @JsonProperty("pitch")
  private Double pitch;
}

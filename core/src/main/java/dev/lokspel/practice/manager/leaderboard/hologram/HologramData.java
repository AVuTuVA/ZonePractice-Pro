package dev.lokspel.practice.manager.leaderboard.hologram;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

/** One entry under {@code holograms} in {@code backend.yml}. */
@Getter
@Setter
public class HologramData {
  @JsonProperty("enabled")
  private Boolean enabled;

  /** Name of the {@link HologramType}. */
  @JsonProperty("type")
  private String type;

  /** Name of the leaderboard's secondary type. */
  @JsonProperty("lb-type")
  private String lbType;

  @JsonProperty("showStat")
  private Integer showStat;

  @JsonProperty("location")
  private Location location;

  /** Ladder of a static ladder hologram. */
  @JsonProperty("ladder")
  private String ladder;

  /** Ladders of a dynamic ladder hologram. */
  @JsonProperty("ladders")
  private List<String> ladders;
}

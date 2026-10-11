package dev.lokspel.practice.manager.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramData;
import dev.lokspel.practice.manager.server.LobbyData;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/** Contents of {@code backend.yml}: lobby, inventory armour, holograms and telemetry. */
@Getter
@Setter
public class BackendData {
  @JsonProperty("lobby")
  private LobbyData lobby;

  /** Base64 armour contents by upper-case inventory type. */
  @JsonProperty("INV-ARMORS")
  private Map<String, String> invArmors;

  /** Holograms by name. */
  @JsonProperty("holograms")
  private Map<String, HologramData> holograms;

  @JsonProperty("TELEMETRY")
  private Telemetry telemetry;

  /** The {@code TELEMETRY} section. */
  @Getter
  @Setter
  public static class Telemetry {
    @JsonProperty("PRACTICE_STATS")
    private PracticeStats practiceStats;
  }

  /** The {@code TELEMETRY.PRACTICE_STATS} section. */
  @Getter
  @Setter
  public static class PracticeStats {
    @JsonProperty("SERVER_HASH")
    private String serverHash;
  }
}

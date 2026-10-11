package dev.lokspel.practice.config.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
public class KnockbackSettings {
  @JsonProperty("AIR-HORIZONTAL")
  private Double airHorizontal;

  @JsonProperty("AIR-VERTICAL")
  private Double airVertical;

  @JsonProperty("HORIZONTAL")
  private Double horizontal;

  @JsonProperty("VERTICAL")
  private Double vertical;

  @Comment("Set <= 0 to disable cap.")
  @JsonProperty("MAX-AIR-HORIZONTAL")
  private Double maxAirHorizontal;

  @Comment("Set <= 0 to disable cap.")
  @JsonProperty("MAX-AIR-VERTICAL")
  private Double maxAirVertical;

  @Comment("Set <= 0 to disable cap.")
  @JsonProperty("MAX-HORIZONTAL")
  private Double maxHorizontal;

  @Comment("Set <= 0 to disable cap.")
  @JsonProperty("MAX-VERTICAL")
  private Double maxVertical;

  static KnockbackSettings normal() {
    KnockbackSettings value = new KnockbackSettings();
    value.airHorizontal = 0.93;
    value.airVertical = 0.93;
    value.horizontal = 0.82;
    value.vertical = 0.88;
    value.maxAirHorizontal = -1.0;
    value.maxAirVertical = -1.0;
    value.maxHorizontal = -1.0;
    value.maxVertical = -1.0;

    return value;
  }

  static KnockbackSettings combo() {
    KnockbackSettings value = new KnockbackSettings();
    value.airHorizontal = 2.8;
    value.airVertical = 2.8;
    value.horizontal = 2.5;
    value.vertical = 2.5;
    value.maxAirHorizontal = 1.35;
    value.maxAirVertical = 0.62;
    value.maxHorizontal = 1.15;
    value.maxVertical = 0.52;

    return value;
  }
}

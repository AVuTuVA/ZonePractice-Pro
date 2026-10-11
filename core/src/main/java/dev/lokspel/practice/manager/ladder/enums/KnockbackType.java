package dev.lokspel.practice.manager.ladder.enums;

import dev.lokspel.practice.config.PracticeConfig;
import lombok.Getter;

public enum KnockbackType {
  DEFAULT(0, 0, 0, 0, -1, -1, -1, -1),
  NORMAL(
      PracticeConfig.settings().getKnockback().getNormal().getAirHorizontal(),
      PracticeConfig.settings().getKnockback().getNormal().getAirVertical(),
      PracticeConfig.settings().getKnockback().getNormal().getHorizontal(),
      PracticeConfig.settings().getKnockback().getNormal().getVertical(),
      readMax(PracticeConfig.settings().getKnockback().getNormal().getMaxAirHorizontal(), -1.0D),
      readMax(PracticeConfig.settings().getKnockback().getNormal().getMaxAirVertical(), -1.0D),
      readMax(PracticeConfig.settings().getKnockback().getNormal().getMaxHorizontal(), -1.0D),
      readMax(PracticeConfig.settings().getKnockback().getNormal().getMaxVertical(), -1.0D)),
  COMBO(
      PracticeConfig.settings().getKnockback().getCombo().getAirHorizontal(),
      PracticeConfig.settings().getKnockback().getCombo().getAirVertical(),
      PracticeConfig.settings().getKnockback().getCombo().getHorizontal(),
      PracticeConfig.settings().getKnockback().getCombo().getVertical(),
      readMax(PracticeConfig.settings().getKnockback().getCombo().getMaxAirHorizontal(), 1.35D),
      readMax(PracticeConfig.settings().getKnockback().getCombo().getMaxAirVertical(), 0.62D),
      readMax(PracticeConfig.settings().getKnockback().getCombo().getMaxHorizontal(), 1.15D),
      readMax(PracticeConfig.settings().getKnockback().getCombo().getMaxVertical(), 0.52D));

  @Getter private final double airhorizontal;
  @Getter private final double airvertical;
  @Getter private final double horizontal;
  @Getter private final double vertical;
  @Getter private final double maxAirhorizontal;
  @Getter private final double maxAirvertical;
  @Getter private final double maxHorizontal;
  @Getter private final double maxVertical;

  KnockbackType(
      double airhorizontal,
      double airvertical,
      double horizontal,
      double vertical,
      double maxAirhorizontal,
      double maxAirvertical,
      double maxHorizontal,
      double maxVertical) {
    this.airhorizontal = airhorizontal;
    this.airvertical = airvertical;
    this.horizontal = horizontal;
    this.vertical = vertical;
    this.maxAirhorizontal = maxAirhorizontal;
    this.maxAirvertical = maxAirvertical;
    this.maxHorizontal = maxHorizontal;
    this.maxVertical = maxVertical;
  }

  private static double readMax(Double value, double defaultValue) {
    double configured = value != null ? value : defaultValue;
    return configured > 0.0D ? configured : -1.0D;
  }
}

package dev.lokspel.practice.manager.division;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.division.DivisionConfig;
import dev.lokspel.practice.util.StringUtil;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

@Getter
public class Division {

  private final String configName;

  private final String fullName;
  private final String shortName;
  private final String color;

  private final Material iconMaterial;
  private short iconDamage;
  private final int weight;

  private final int experience;
  private final int win;
  private final int elo;

  public Division(final String configName, final DivisionConfig.Division section) {
    this.configName = configName;

    DivisionConfig.Division.Data data = section.getData();
    this.color = data.getColor();
    this.fullName = data.getName().getFull().replace("%color%", color);
    this.shortName = data.getName().getShortValue().replace("%color%", color);

    this.iconMaterial = Material.valueOf(data.getIconMaterial());

    if (data.getIconDamage() != null) {
      try {
        this.iconDamage = Short.parseShort(String.valueOf(data.getIconDamage()));
      } catch (NumberFormatException ignored) {
        // This best-effort operation may fail without interrupting its caller.

      }
    }

    this.weight = data.getWeight() != null ? data.getWeight() : -1;

    DivisionConfig.Division.Requirements requirements = section.getRequirements();
    this.experience = requirements != null ? intOrZero(requirements.getExperience()) : 0;
    this.win = requirements != null ? intOrZero(requirements.getWins()) : 0;
    this.elo = requirements != null ? intOrZero(requirements.getElo()) : 0;
  }

  private static int intOrZero(Integer value) {
    return value != null ? value : 0;
  }

  public boolean isValid() {
    return fullName != null
        && shortName != null
        && color != null
        && iconMaterial != null
        && experience >= 0
        && win >= 0;
  }

  public Component getComponentFullName() {
    return AstralPractice.getMiniMessage().deserialize(StringUtil.legacyToMiniMessage(fullName));
  }

  public Component getComponentShortName() {
    return AstralPractice.getMiniMessage().deserialize(StringUtil.legacyToMiniMessage(shortName));
  }
}

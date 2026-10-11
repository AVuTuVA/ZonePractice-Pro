package dev.lokspel.practice.manager.arena;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.Common;
import java.util.List;
import lombok.Getter;
import org.bukkit.Material;

public enum ArenaType {
  BASIC(
      PracticeConfig.language().getArena().getArenaTypes().getBasic().getName(),
      Material.DIAMOND_SWORD,
      false,
      PracticeConfig.language().getArena().getArenaTypes().getBasic().getDescription()),
  BUILD(
      PracticeConfig.language().getArena().getArenaTypes().getBuild().getName(),
      Material.IRON_PICKAXE,
      true,
      PracticeConfig.language().getArena().getArenaTypes().getBuild().getDescription()),
  FFA(
      PracticeConfig.language().getArena().getArenaTypes().getFfa().getName(),
      Material.GOLDEN_SWORD,
      false,
      PracticeConfig.language().getArena().getArenaTypes().getFfa().getDescription());

  private final String name;
  @Getter private final Material icon;
  @Getter private final boolean build;
  private final List<String> description;

  ArenaType(String name, Material icon, boolean build, List<String> description) {
    this.name = name;
    this.icon = icon;
    this.build = build;
    this.description = description;
  }

  public String getName() {
    return Common.mmToNormal(this.name);
  }

  public List<String> getDescription() {
    return Common.mmToNormal(this.description);
  }
}

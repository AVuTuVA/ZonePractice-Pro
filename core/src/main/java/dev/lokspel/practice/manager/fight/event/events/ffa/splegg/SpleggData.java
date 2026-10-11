package dev.lokspel.practice.manager.fight.event.events.ffa.splegg;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.event.enums.EventType;
import dev.lokspel.practice.manager.fight.event.interfaces.EventData;
import dev.lokspel.practice.manager.fight.event.interfaces.EventFileData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public class SpleggData extends EventData {

  private ItemStack eggLauncher;
  private List<String> breakableMaterials = Collections.singletonList("WOOL");

  public SpleggData() {
    super(EventType.SPLEGG);
  }

  @Override
  protected void setCustomData(EventFileData data) {}

  @Override
  protected void getCustomData(EventFileData data) {
    this.eggLauncher =
        BackendUtil.getGuiItem(
                PracticeConfig.settings().getEvent().getSplegg().getEggLauncherItem())
            .get();

    List<String> configuredMaterials =
        PracticeConfig.settings().getEvent().getSplegg().getBreakableMaterials();
    if (configuredMaterials.isEmpty()) {
      this.breakableMaterials = Collections.singletonList("WOOL");
      return;
    }

    List<String> normalizedMaterials = new ArrayList<>();
    for (String configuredMaterial : configuredMaterials) {
      if (configuredMaterial == null) {
        continue;
      }

      String normalized = configuredMaterial.trim().toUpperCase(Locale.ROOT);
      if (!normalized.isEmpty()) {
        normalizedMaterials.add(normalized);
      }
    }

    this.breakableMaterials =
        normalizedMaterials.isEmpty() ? Collections.singletonList("WOOL") : normalizedMaterials;
  }

  @Override
  protected void enable() throws IOException {
    if (eggLauncher == null || eggLauncher.getType().equals(Material.AIR)) {
      throw new IOException("Egg launcher item is not set. Set it in the config.");
    }
  }
}

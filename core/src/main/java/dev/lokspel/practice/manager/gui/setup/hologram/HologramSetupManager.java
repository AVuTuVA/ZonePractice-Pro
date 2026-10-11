package dev.lokspel.practice.manager.gui.setup.hologram;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.leaderboard.hologram.Hologram;
import dev.lokspel.practice.manager.leaderboard.hologram.HologramManager;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

@Getter
public class HologramSetupManager implements Listener {

  private static HologramSetupManager instance;

  public static HologramSetupManager getInstance() {
    if (instance == null) {
      instance = new HologramSetupManager();
    }
    return instance;
  }

  private final Map<Hologram, Map<GuiType, Gui>> hologramSetupGuis = new HashMap<>();

  public HologramSetupManager() {
    Bukkit.getPluginManager().registerEvents(this, AstralPractice.getInstance());
  }

  public void buildHologramSetupGuis(Hologram hologram) {
    Map<GuiType, Gui> guis = new HashMap<>();

    guis.put(GuiType.Hologram_Main, GuiManager.getInstance().addGui(new HologramMainGui(hologram)));
    guis.put(GuiType.Hologram_Ladder, GuiManager.getInstance().addGui(new LadderGui(hologram)));

    hologramSetupGuis.put(hologram, guis);
  }

  public void loadGuis() {
    GuiManager.getInstance().addGui(new HologramSummaryGui());

    for (Hologram hologram : HologramManager.getInstance().getHolograms()) {
      buildHologramSetupGuis(hologram);
    }
  }

  public void removeHologramGuis(Hologram hologram) {
    for (Gui gui : hologramSetupGuis.get(hologram).values()) {
      for (Player player : gui.getInGuiPlayers().keySet()) {
        GuiManager.getInstance().searchGui(GuiType.Setup_Hub).open(player);
      }
    }

    hologramSetupGuis.remove(hologram);
    GuiManager.getInstance().searchGui(GuiType.Hologram_Summary).update();
  }
}

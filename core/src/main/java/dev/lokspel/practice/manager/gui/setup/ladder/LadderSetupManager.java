package dev.lokspel.practice.manager.gui.setup.ladder;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.CustomKitGui;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.DestroyableBlocksGui;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.InventoryGui;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.LadderMainGui;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.MatchTypeGui;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.SkyWarsLootGui;
import dev.lokspel.practice.manager.gui.setup.ladder.laddersettings.settings.SettingsGui;
import dev.lokspel.practice.manager.ladder.LadderManager;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.ladder.type.SkyWars;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

@Getter
public class LadderSetupManager implements Listener {

  private static LadderSetupManager instance;

  public static LadderSetupManager getInstance() {
    if (instance == null) {
      instance = new LadderSetupManager();
    }
    return instance;
  }

  private final Map<Ladder, Map<GuiType, Gui>> ladderSetupGuis = new HashMap<>();

  public LadderSetupManager() {
    Bukkit.getPluginManager().registerEvents(this, AstralPractice.getInstance());
  }

  public void buildLadderSetupGuis(NormalLadder ladder) {
    Map<GuiType, Gui> guis = new EnumMap<>(GuiType.class);

    guis.put(GuiType.Ladder_Main, new LadderMainGui(ladder));
    guis.put(GuiType.Ladder_Inventory, new InventoryGui(ladder));
    guis.put(GuiType.Ladder_CustomKitExtra_unRanked, new CustomKitGui(ladder, false));
    guis.put(GuiType.Ladder_CustomKitExtra_Ranked, new CustomKitGui(ladder, true));
    guis.put(GuiType.Ladder_Settings, new SettingsGui(ladder));
    guis.put(GuiType.Ladder_MatchType, new MatchTypeGui(ladder));

    if (ladder.isBuild()) {
      guis.put(GuiType.Ladder_DestroyableBlock, new DestroyableBlocksGui(ladder));
    }

    if (ladder instanceof SkyWars) {
      guis.put(GuiType.Ladder_SkyWarsLoot, new SkyWarsLootGui((SkyWars) ladder));
    }

    ladderSetupGuis.put(ladder, guis);
  }

  public void loadGuis() {
    GuiManager.getInstance().addGui(new LadderSummaryGui());

    for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
      buildLadderSetupGuis(ladder);
    }
  }

  /** Re-creates the summary and every ladder's setup GUIs from the reloaded configuration. */
  public void reloadGuis() {
    ladderSetupGuis.clear();
    loadGuis();
  }

  public void removeLadderGuis(Ladder ladder) {
    for (Gui gui : ladderSetupGuis.get(ladder).values()) {
      for (Player player : gui.getInGuiPlayers().keySet()) {
        GuiManager.getInstance().searchGui(GuiType.Setup_Hub).open(player);
      }
    }

    ladderSetupGuis.remove(ladder);
    GuiManager.getInstance().searchGui(GuiType.Ladder_Summary).update();
  }
}

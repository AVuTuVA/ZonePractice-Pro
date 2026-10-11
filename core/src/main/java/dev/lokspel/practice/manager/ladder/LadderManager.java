package dev.lokspel.practice.manager.ladder;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.data.DataFiles;
import dev.lokspel.practice.manager.gui.GuiManager;
import dev.lokspel.practice.manager.gui.guis.customladder.EditorMenuGui;
import dev.lokspel.practice.manager.gui.guis.customladder.premadecustom.CustomLadderSelectorGui;
import dev.lokspel.practice.manager.gui.guis.ladder.LadderPreviewGui;
import dev.lokspel.practice.manager.gui.guis.queue.RankedGui;
import dev.lokspel.practice.manager.gui.guis.queue.UnrankedGui;
import dev.lokspel.practice.manager.gui.guis.queue.customkit.ChooseQueueTypeGui;
import dev.lokspel.practice.manager.gui.setup.ladder.LadderSetupManager;
import dev.lokspel.practice.manager.ladder.abstraction.normal.LadderData;
import dev.lokspel.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.lokspel.practice.manager.ladder.enums.LadderType;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.StartUpCallback;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;

public class LadderManager {

  private static LadderManager instance;

  public static LadderManager getInstance() {
    if (instance == null) {
      instance = new LadderManager();
    }
    return instance;
  }

  private LadderManager() {}

  @Getter private final List<NormalLadder> ladders = new ArrayList<>();

  @Getter
  private static final int DEFAULT_ELO =
      PracticeConfig.settings().getQueue().getRanked().getDefaultElo();

  public NormalLadder getLadder(String ladderName) {
    for (NormalLadder ladder : ladders) {
      if (ladder.getName().equalsIgnoreCase(ladderName)) {
        return ladder;
      }
    }
    return null;
  }

  public List<NormalLadder> getEnabledLadders() {
    List<NormalLadder> list = new ArrayList<>();
    for (NormalLadder ladder : ladders) {
      if (ladder.isEnabled()) {
        list.add(ladder);
      }
    }
    return list;
  }

  public void loadLadders(final StartUpCallback startUpCallback) {
    Bukkit.getScheduler()
        .runTaskAsynchronously(
            AstralPractice.getInstance(),
            () -> {
              for (String file : DataFiles.list("ladders")) {
                LadderData data = DataFiles.read(file, LadderData.class);

                if (data != null && data.getType() != null) {
                  String name = data.getName();

                  try {
                    LadderType type = LadderType.valueOf(data.getType());

                    ladders.add(
                        (NormalLadder)
                            type.getClassInstance()
                                .getConstructor(String.class, LadderType.class)
                                .newInstance(name, type));
                  } catch (IllegalArgumentException e) {
                    Common.sendConsoleMmMessage(
                        "<red>Invalid ladder format for ladder " + name + ".");
                  } catch (NoSuchMethodException
                      | InvocationTargetException
                      | InstantiationException
                      | IllegalAccessException e) {
                    throw new RuntimeException(e);
                  }
                }
              }

              Bukkit.getScheduler()
                  .runTask(AstralPractice.getInstance(), startUpCallback::onLoadingDone);
            });
  }

  public void loadGuis() {
    LadderSetupManager.getInstance().loadGuis();
    GuiManager.getInstance().addGui(new UnrankedGui());
    GuiManager.getInstance().addGui(new RankedGui());
    GuiManager.getInstance().addGui(new ChooseQueueTypeGui());
    GuiManager.getInstance().addGui(new CustomLadderSelectorGui());
    GuiManager.getInstance().addGui(new EditorMenuGui());
  }

  /**
   * Re-creates the queue, kit editor and ladder setup GUIs and every ladder's preview from the
   * reloaded configuration.
   */
  public void reloadGuis() {
    for (NormalLadder ladder : ladders) {
      ladder.setPreviewGui(new LadderPreviewGui(ladder));
    }

    LadderSetupManager.getInstance().reloadGuis();
    GuiManager.getInstance().addGui(new UnrankedGui());
    GuiManager.getInstance().addGui(new RankedGui());
    GuiManager.getInstance().addGui(new ChooseQueueTypeGui());
    GuiManager.getInstance().addGui(new CustomLadderSelectorGui());
    GuiManager.getInstance().addGui(new EditorMenuGui());
  }

  public void saveLadders() {
    for (NormalLadder ladder : ladders) {
      ladder.setData();
    }
  }
}

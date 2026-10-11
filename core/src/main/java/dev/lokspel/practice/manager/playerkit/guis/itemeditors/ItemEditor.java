package dev.lokspel.practice.manager.playerkit.guis.itemeditors;

import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.playerkit.items.EditorIcon;
import java.util.List;
import org.bukkit.inventory.Inventory;

public abstract class ItemEditor extends Gui {

  protected final List<EditorIcon> icons;

  public ItemEditor(GuiType type, List<EditorIcon> icons) {
    super(type);
    this.icons = icons;
  }

  protected EditorIcon getIcon(int slot) {
    for (EditorIcon icon : icons) {
      if (icon.getSlot() == slot) {
        return icon;
      }
    }
    return null;
  }

  @Override
  public void build() {
    this.update();
  }

  @Override
  public void update() {
    Inventory inventory = gui.get(1);

    for (EditorIcon editorIcon : this.icons) {
      if (inventory.getSize() <= editorIcon.getSlot() || editorIcon.getSlot() < 0) {
        continue;
      }

      inventory.setItem(editorIcon.getSlot(), editorIcon.get());
    }

    this.updatePlayers();
  }
}

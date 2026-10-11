package dev.lokspel.practice.manager.playerkit.items;

import dev.lokspel.practice.manager.gui.GuiItem;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditorIcon extends GuiItem {

  private int slot = -1;

  public boolean equals(EditorIcon editorIcon) {
    return this.getMaterial() == editorIcon.getMaterial() && this.getSlot() == editorIcon.getSlot();
  }
}

package dev.lokspel.practice.manager.inventory.inventoryitem;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Setter
@Getter
public class InvArmor {

  private ItemStack helmet;
  private ItemStack chestplate;
  private ItemStack leggings;
  private ItemStack boots;

  public ItemStack[] getArmorContent() {
    return new ItemStack[] {
      clean(this.boots), clean(this.leggings), clean(this.chestplate), clean(this.helmet)
    };
  }

  public void setArmorContent(ItemStack[] armorContent) {
    if (armorContent == null) {
      this.boots = null;
      this.leggings = null;
      this.chestplate = null;
      this.helmet = null;
      return;
    }
    this.boots = armorContent.length > 0 ? clean(armorContent[0]) : null;
    this.leggings = armorContent.length > 1 ? clean(armorContent[1]) : null;
    this.chestplate = armorContent.length > 2 ? clean(armorContent[2]) : null;
    this.helmet = armorContent.length > 3 ? clean(armorContent[3]) : null;
  }

  public boolean isNull() {
    return (this.helmet == null || this.helmet.getType().isAir())
        && (this.chestplate == null || this.chestplate.getType().isAir())
        && (this.leggings == null || this.leggings.getType().isAir())
        && (this.boots == null || this.boots.getType().isAir());
  }

  private static ItemStack clean(ItemStack item) {
    return item == null || item.getType().isAir() ? null : item;
  }
}

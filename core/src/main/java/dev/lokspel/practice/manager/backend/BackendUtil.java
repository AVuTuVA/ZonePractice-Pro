package dev.lokspel.practice.manager.backend;

import dev.lokspel.practice.config.ItemConfig;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.util.Common;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

public enum BackendUtil {
  ;

  public static GuiItem getGuiItem(ItemConfig item) {
    GuiItem guiItem = new GuiItem();

    if (item.getName() != null) {
      guiItem.setName(item.getName());
    }

    if (item.getMaterial() != null) {
      guiItem.setMaterial(Material.valueOf(item.getMaterial()));
    }

    if (item.getAmount() != null) {
      int amount = item.getAmount();
      if (amount <= 0 || amount > 64) {
        amount = 1;
      }
      guiItem.setAmount(amount);
    }

    if (item.getDurability() != null) {
      int durability = item.getDurability();
      if (durability < 0) {
        durability = 1;
      }
      guiItem.setDurability(durability);
    }

    if (item.getLore() != null) {
      guiItem.setLore(item.getLore());
    }

    if (item.getFlags() != null) {
      for (String flag : item.getFlags()) {
        try {
          ItemFlag itemFlag = ItemFlag.valueOf(flag);
          guiItem.addItemFlag(itemFlag);
        } catch (IllegalArgumentException e) {
          Common.sendConsoleMmMessage("<red>Invalid ItemFlag: " + flag);
        }
      }
    }

    // Always add flags to hide unbreakable and attributes for clean lobby items
    guiItem.addItemFlag(ItemFlag.HIDE_UNBREAKABLE);
    guiItem.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);

    // Set unbreakable to prevent durability bars from showing
    guiItem.setUnbreakable(true);

    if (item.getEnchantments() != null) {
      for (String s : item.getEnchantments()) {
        String[] enchantmentSplit = s.split(":");
        if (enchantmentSplit.length != 2) {
          continue;
        }

        try {
          Enchantment enchantment = Common.resolveEnchantment(enchantmentSplit[0]);
          int level = Integer.parseInt(enchantmentSplit[1]);

          if (enchantment == null) {
            throw new IllegalArgumentException("Enchantment not found: " + enchantmentSplit[0]);
          }
          if (enchantment.getStartLevel() > level) {
            level = enchantment.getStartLevel();
          }

          guiItem.addEnchantment(enchantment, level);
        } catch (Exception e) {
          Common.sendConsoleMmMessage("<red>Enchantment not found: " + e);
        }
      }
    }

    return guiItem;
  }
}

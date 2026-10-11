package dev.lokspel.practice.manager.gui.guis;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.util.InventoryUtil;
import dev.lokspel.practice.util.TimeUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerInvGui extends Gui {

  private final Player target;

  public PlayerInvGui(Player target) {
    super(GuiType.Player_Inventory);
    this.target = target;
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis()
                .getGuis()
                .getPlayerInventory()
                .getTitle()
                .replace("%player%", target.getName()),
            5));

    build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    Bukkit.getScheduler()
        .runTaskAsynchronously(
            AstralPractice.getInstance(),
            () -> {
              Inventory inventory = gui.get(1);

              List<ItemStack> inventoryContent = Arrays.asList(target.getInventory().getContents());
              List<ItemStack> firstLine = new ArrayList<>();
              for (int i = 0; i < 36; i++) {
                if (i < 9) {
                  firstLine.add(inventoryContent.get(i));
                }

                if (i < 27) {
                  inventory.setItem(i, inventoryContent.get(i + 9));
                } else {
                  inventory.setItem(i, firstLine.get(i - 27));
                }
              }

              List<ItemStack> armorContent =
                  Arrays.asList(target.getInventory().getArmorContents());
              for (int i = 36; i < 40; i++) {
                inventory.setItem(i, armorContent.get(i - 36));
              }

              if (!target.getActivePotionEffects().isEmpty()) {
                inventory.setItem(42, getEffectItem());
              }
              inventory.setItem(43, getHealthItem());
              inventory.setItem(44, getHungerItem());

              for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) == null) {
                  inventory.setItem(
                      i,
                      BackendUtil.getGuiItem(
                              PracticeConfig.guis()
                                  .getGuis()
                                  .getPlayerInventory()
                                  .getIcons()
                                  .getFillerItem())
                          .get());
                }
              }
            });
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    e.setCancelled(true);
  }

  private ItemStack getHealthItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis().getGuis().getPlayerInventory().getIcons().getHealth())
        .replace("%health%", String.valueOf(target.getHealth()))
        .get();
  }

  private ItemStack getHungerItem() {
    return BackendUtil.getGuiItem(
            PracticeConfig.guis().getGuis().getPlayerInventory().getIcons().getHunger())
        .replace("%hunger%", String.valueOf(target.getFoodLevel()))
        .get();
  }

  private ItemStack getEffectItem() {
    if (!target.getActivePotionEffects().isEmpty()) {
      List<String> effects = new ArrayList<>();
      for (PotionEffect potionEffect : target.getActivePotionEffects()) {
        effects.add(
            PracticeConfig.guis()
                .getGuis()
                .getPlayerInventory()
                .getIcons()
                .getEffect()
                .getFormat()
                .replace(
                    "%name%",
                    StringUtils.capitalize(
                        potionEffect.getType().getKey().getKey().replace("_", " ").toLowerCase()))
                .replace("%amplifier%", String.valueOf(potionEffect.getAmplifier() + 1))
                .replace(
                    "%time%", TimeUtil.formatDuration((potionEffect.getDuration() / 20) * 1000L)));
      }

      List<String> lore = new ArrayList<>();
      for (String line :
          PracticeConfig.guis()
              .getGuis()
              .getPlayerInventory()
              .getIcons()
              .getEffect()
              .getIcon()
              .getLore()) {
        if (line.contains("%effects%")) {
          lore.addAll(effects);
        } else {
          lore.add(line);
        }
      }

      GuiItem item =
          BackendUtil.getGuiItem(
              PracticeConfig.guis()
                  .getGuis()
                  .getPlayerInventory()
                  .getIcons()
                  .getEffect()
                  .getIcon());
      item.setLore(lore);

      return item.get();
    }

    return null;
  }
}

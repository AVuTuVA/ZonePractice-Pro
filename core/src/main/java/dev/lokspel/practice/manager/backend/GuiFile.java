package dev.lokspel.practice.manager.backend;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.manager.gui.GuiItem;
import dev.lokspel.practice.util.Common;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public enum GuiFile {
  ;

  private static File file;
  @Getter private static YamlConfiguration config;

  public static void createFile(AstralPractice practice) {
    file = new File(practice.getDataFolder(), "guis.yml");
    config = new YamlConfiguration();
    reload();
  }

  public static void save() {
    try {
      config.save(file);
    } catch (IOException e) {
      Common.sendConsoleMmMessage("<red>Error: " + e.getMessage());
    }
  }

  public static void reload() {
    try {
      config.load(file);
    } catch (IOException | InvalidConfigurationException e) {
      Common.sendConsoleMmMessage("<red>Error: " + e.getMessage());
    }
  }

  public static String getString(String loc) {
    return config.getString(loc.toUpperCase());
  }

  public static int getInt(String loc) {
    return config.getInt(loc.toUpperCase());
  }

  public static List<String> getStringList(String loc) {
    return config.getStringList(loc.toUpperCase());
  }

  public static GuiItem getGuiItem(String loc) {
    return BackendUtil.getGuiItem(config, loc);
  }
}

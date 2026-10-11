package dev.lokspel.practice.config;

import dev.lokspel.practice.config.division.DivisionConfig;
import dev.lokspel.practice.config.group.GroupConfig;
import dev.lokspel.practice.config.gui.GuiConfig;
import dev.lokspel.practice.config.inventory.InventoryConfig;
import dev.lokspel.practice.config.language.LanguageConfig;
import dev.lokspel.practice.config.playerkit.PlayerKitConfig;
import dev.lokspel.practice.config.settings.SettingsConfig;
import dev.lokspel.practice.config.sidebar.SidebarConfig;
import java.nio.file.Path;
import java.util.List;
import me.whereareiam.configura.Configura;
import me.whereareiam.configura.exception.ConfigException;
import me.whereareiam.configura.feature.comment.CommentFeature;
import me.whereareiam.strata.MigrationStream;
import me.whereareiam.strata.Strata;
import me.whereareiam.strata.adapter.configura.ConfigContext;
import me.whereareiam.strata.adapter.configura.ConfiguraTarget;
import me.whereareiam.strata.adapter.configura.StrataFeature;

/**
 * Loads the configuration files of the plugin and holds what they contain.
 *
 * <p>Each file is bound to its model with Configura: a missing file is created from the defaults
 * the models declare, settings a user's file lacks are added, and what the user wrote is kept.
 * Before that, Strata brings files written by older versions up to date.
 */
public enum PracticeConfig {
  ;

  private static final String VERSION_FILE = "config.yml";
  private static final List<String> FILES =
      List.of(
          "config.yml",
          "language.yml",
          "guis.yml",
          "sidebar.yml",
          "groups.yml",
          "divisions.yml",
          "inventories.yml",
          "playerkit.yml");

  private static Path directory;
  private static Documents documents;

  /**
   * Upgrades and loads every configuration file of the data folder.
   *
   * @param dataDirectory the plugin's data folder
   * @throws ConfigException if a file cannot be read or does not fit its model
   */
  public static void load(Path dataDirectory) {
    Configura configura = configura();
    migrate(configura, dataDirectory);

    documents = Documents.load(configura, dataDirectory);
    directory = dataDirectory;
  }

  /**
   * Loads every configuration file again. The previous values stay in place if a file cannot be
   * loaded.
   *
   * @throws ConfigException if a file cannot be read or does not fit its model
   */
  public static void reload() {
    documents = Documents.load(configura(), directory);
  }

  /**
   * Describes a failure to load the configuration with its causes, which name the file and the
   * setting that could not be read.
   *
   * @param failure what {@link #load(Path)} or {@link #reload()} threw
   * @return a single-line description for the console
   */
  public static String describeFailure(Throwable failure) {
    StringBuilder message = new StringBuilder(String.valueOf(failure.getMessage()));
    for (Throwable cause = failure.getCause(); cause != null; cause = cause.getCause()) {
      message.append(" <- ").append(cause.getMessage());
    }

    return message.toString();
  }

  public static SettingsConfig settings() {
    return documents.settings();
  }

  public static LanguageConfig language() {
    return documents.language();
  }

  public static GuiConfig guis() {
    return documents.guis();
  }

  public static SidebarConfig sidebar() {
    return documents.sidebar();
  }

  public static GroupConfig groups() {
    return documents.groups();
  }

  public static DivisionConfig divisions() {
    return documents.divisions();
  }

  public static InventoryConfig inventories() {
    return documents.inventories();
  }

  public static PlayerKitConfig playerKit() {
    return documents.playerKit();
  }

  private static Configura configura() {
    return Configura.builder()
        .feature(new StrataFeature())
        .feature(CommentFeature.defaults())
        .build();
  }

  private static void migrate(Configura configura, Path dataDirectory) {
    MigrationStream<ConfigContext> stream =
        MigrationStream.<ConfigContext>builder()
            .id("astralpractice/config")
            .target(new ConfiguraTarget(configura, dataDirectory, VERSION_FILE))
            .baseline((files, latest) -> files.exists(VERSION_FILE) ? 0 : latest)
            .migration(1, "drop-boosted-yaml-versions", PracticeConfig::dropBoostedYamlVersions)
            .build();

    new Strata(List.of(stream)).migrate();
  }

  /** Up to 7.9, boosted-yaml kept the version of each file in a VERSION key. */
  private static void dropBoostedYamlVersions(ConfigContext files) {
    for (String file : FILES) {
      if (files.exists(file)) {
        files.remove(file, "/VERSION");
      }
    }
  }

  private record Documents(
      SettingsConfig settings,
      LanguageConfig language,
      GuiConfig guis,
      SidebarConfig sidebar,
      GroupConfig groups,
      DivisionConfig divisions,
      InventoryConfig inventories,
      PlayerKitConfig playerKit) {

    static Documents load(Configura configura, Path directory) {
      return new Documents(
          configura.update(directory.resolve("config.yml"), SettingsConfig.class),
          configura.update(directory.resolve("language.yml"), LanguageConfig.class),
          configura.update(directory.resolve("guis.yml"), GuiConfig.class),
          configura.update(directory.resolve("sidebar.yml"), SidebarConfig.class),
          configura.update(directory.resolve("groups.yml"), GroupConfig.class),
          configura.update(directory.resolve("divisions.yml"), DivisionConfig.class),
          configura.update(directory.resolve("inventories.yml"), InventoryConfig.class),
          configura.update(directory.resolve("playerkit.yml"), PlayerKitConfig.class));
    }
  }
}

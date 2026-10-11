package dev.lokspel.practice.manager.backend;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.data.DataFiles;
import dev.lokspel.practice.util.Common;

/** Owns {@code backend.yml}, bound to {@link BackendData}. */
public enum BackendManager {
  ;

  private static final String FILE = "backend.yml";
  private static final String PRACTICE_STATS_SERVER_HASH_PREFIX = "APV1_";

  private static volatile BackendData data = new BackendData();

  /** Saving is refused until the file has been read, so an early save cannot empty it. */
  private static volatile boolean loaded;

  /**
   * Returns the loaded contents of {@code backend.yml}. The instance is replaced by {@link
   * #reload()}, so callers should not keep it.
   *
   * @return the current model, empty if the file couldn't be read yet
   */
  public static BackendData data() {
    return data;
  }

  public static void createFile(AstralPractice practice) {
    // Only try to load if file exists; locations in worlds that aren't loaded yet fail to read
    if (DataFiles.exists(FILE)) {
      BackendData read = DataFiles.readIfReady(FILE, BackendData.class);
      if (read != null) {
        data = read;
        loaded = true;
      } else {
        // Expected if worlds aren't loaded yet; the file is reloaded when loadLobby() is called
        Common.sendConsoleMmMessage(
            "<yellow>Backend file exists but couldn't be fully loaded yet (worlds may not be"
                + " ready). Will retry during initialization.");
      }
    } else {
      // Create new file if it doesn't exist
      loaded = true;
      save();
    }
  }

  public static void save() {
    if (!loaded) {
      Common.sendConsoleMmMessage("<yellow>Not saving backend.yml before it has been read.");
      return;
    }

    try {
      DataFiles.write(FILE, data);
    } catch (RuntimeException e) {
      // Location#serialize can throw for a location whose world reference was cleared. Log
      // clearly instead of letting the async task swallow the trace.
      Common.sendConsoleMmMessage(
          "<red>Error serializing backend.yml (possible null world in lobby location): "
              + e.getMessage());
    }
  }

  public static void reload() {
    if (!DataFiles.exists(FILE)) {
      loaded = true;
      return;
    }

    BackendData read = DataFiles.read(FILE, BackendData.class);
    if (read == null) {
      Common.sendConsoleMmMessage("<red>Error reloading backend: couldn't read " + FILE);
      return;
    }

    data = read;
    loaded = true;
  }

  public static String getPracticeStatsServerHash() {
    BackendData.Telemetry telemetry = data.getTelemetry();
    if (telemetry == null || telemetry.getPracticeStats() == null) {
      return null;
    }

    String configuredHash = telemetry.getPracticeStats().getServerHash();
    if (configuredHash == null) {
      return null;
    }

    String normalized = normalizePracticeStatsServerHash(configuredHash);
    if (normalized == null) {
      return null;
    }

    // Migrate legacy values so next restart keeps the same prefixed format.
    if (!normalized.equals(configuredHash)) {
      telemetry.getPracticeStats().setServerHash(normalized);
      save();
    }

    return normalized;
  }

  public static String getOrCreatePracticeStatsServerHash(String generatedHash) {
    String existing = getPracticeStatsServerHash();
    if (existing != null) {
      return existing;
    }

    String normalizedGenerated = normalizePracticeStatsServerHash(generatedHash);
    if (normalizedGenerated == null) {
      return null;
    }

    BackendData.Telemetry telemetry = data.getTelemetry();
    if (telemetry == null) {
      telemetry = new BackendData.Telemetry();
      data.setTelemetry(telemetry);
    }
    if (telemetry.getPracticeStats() == null) {
      telemetry.setPracticeStats(new BackendData.PracticeStats());
    }

    telemetry.getPracticeStats().setServerHash(normalizedGenerated);
    save();
    return normalizedGenerated;
  }

  private static String normalizePracticeStatsServerHash(String hash) {
    if (hash == null) {
      return null;
    }

    String trimmed = hash.trim();
    if (trimmed.isEmpty()) {
      return null;
    }

    return trimmed.startsWith(PRACTICE_STATS_SERVER_HASH_PREFIX)
        ? trimmed
        : PRACTICE_STATS_SERVER_HASH_PREFIX + trimmed;
  }
}

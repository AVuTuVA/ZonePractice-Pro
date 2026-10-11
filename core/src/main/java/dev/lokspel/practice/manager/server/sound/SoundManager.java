package dev.lokspel.practice.manager.server.sound;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.settings.SettingsConfig;
import dev.lokspel.practice.util.Common;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

  private static SoundManager instance;

  public static SoundManager getInstance() {
    if (instance == null) {
      instance = new SoundManager();
    }
    return instance;
  }

  private static final SoundEffect NONE = new SoundEffect(false);

  private final Map<SoundType, SoundEffect> sounds = new HashMap<>();

  private SoundManager() {
    this.loadSounds();
  }

  private void loadSounds() {
    Map<String, SettingsConfig.Sound> configured = PracticeConfig.settings().getSounds();
    if (configured != null) {
      for (Map.Entry<String, SettingsConfig.Sound> entry : configured.entrySet()) {
        String soundName = entry.getKey();
        try {
          SoundType type = SoundType.valueOf(soundName);
          sounds.put(type, new SoundEffect(entry.getValue()));
        } catch (IllegalArgumentException e) {
          Common.sendConsoleMmMessage("<red>Invalid sound type: " + soundName);
        }
      }
    }
  }

  public SoundEffect getSound(SoundType type) {
    SoundEffect sound = sounds.get(type);
    return sound == null ? NONE : sound;
  }
}

package dev.lokspel.practice.manager.fight.event;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.settings.SettingsConfig;
import dev.lokspel.practice.manager.fight.event.enums.EventType;
import dev.lokspel.practice.util.Common;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Reads AUTO-EVENTS time lists from config.yml (per event type) and fires
 * EventManager.startEvent(null, type) at the configured server-local times.
 *
 * <p>Config layout (under EVENT.<TYPE>): AUTO-EVENTS: - "18:30" - "21:00"
 *
 * <p>Global toggle: EVENT.AUTO-EVENTS: true/false
 */
public class AutoEventScheduler {

  private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("H:mm");

  // Parsed times per event type
  private final Map<EventType, List<LocalTime>> scheduledTimes = new EnumMap<>(EventType.class);

  // Tracks which (type -> HH:mm) already fired this minute to avoid double-firing
  private final Map<EventType, String> lastFired = new EnumMap<>(EventType.class);

  private BukkitRunnable task;
  private boolean taskRunning = false;

  public AutoEventScheduler() {
    reload();
  }

  /** Re-reads the config. Call this if the config is reloaded at runtime. */
  public void reload() {
    scheduledTimes.clear();

    if (!PracticeConfig.settings().getEvent().isAutoEvents()) {
      return;
    }

    SettingsConfig.Event settings = PracticeConfig.settings().getEvent();
    for (EventType type : EventType.values()) {
      List<String> raw = getAutoEvents(settings, type);
      if (raw == null || raw.isEmpty()) {
        continue;
      }

      List<LocalTime> times = new ArrayList<>();
      for (String s : raw) {
        // Strip legacy colour codes that may be present in the value,
        // then trim whitespace.
        String clean = s.replaceAll("(?i)§[0-9a-fk-or]", "").trim();
        if (clean.isEmpty()) {
          continue;
        }

        // SnakeYAML parses bare HH:MM values (e.g. 18:38) as sexagesimal
        // integers (18*60+38 = 1118) when they are not quoted in the yml.
        // Detect a plain integer and convert it back to HH:mm.
        if (clean.matches("\\d+")) {
          int total = Integer.parseInt(clean);
          clean = String.format("%d:%02d", total / 60, total % 60);
        }

        try {
          times.add(LocalTime.parse(clean, TIME_FORMAT));
        } catch (DateTimeParseException ex) {
          Common.sendConsoleMmMessage(
              "<yellow>[AutoEvent] Invalid time \""
                  + clean
                  + "\" for "
                  + type.name()
                  + " — expected HH:mm");
        }
      }

      if (!times.isEmpty()) {
        scheduledTimes.put(type, times);
      }
    }
  }

  private static List<String> getAutoEvents(SettingsConfig.Event settings, EventType type) {
    return switch (type) {
      case LMS -> settings.getLms().getAutoEvents();
      case OITC -> settings.getOitc().getAutoEvents();
      case TNTTAG -> settings.getTnttag().getAutoEvents();
      case BRACKETS -> settings.getBrackets().getAutoEvents();
      case SUMO -> settings.getSumo().getAutoEvents();
      case SPLEGG -> settings.getSplegg().getAutoEvents();
      case JUGGERNAUT -> settings.getJuggernaut().getAutoEvents();
    };
  }

  /** Starts the repeating check task. Called once after event data is loaded. */
  public void start() {
    if (scheduledTimes.isEmpty() || taskRunning) {
      return;
    }

    taskRunning = true;
    task =
        new BukkitRunnable() {
          @Override
          public void run() {
            if (!PracticeConfig.settings().getEvent().isAutoEvents()) {
              return;
            }

            // Truncate to HH:mm — same resolution as the config values
            LocalTime now = LocalTime.now().withSecond(0).withNano(0);
            String nowKey = now.format(TIME_FORMAT);

            for (Map.Entry<EventType, List<LocalTime>> entry : scheduledTimes.entrySet()) {
              EventType type = entry.getKey();

              // Already fired for this minute-window — skip
              if (nowKey.equals(lastFired.get(type))) {
                continue;
              }

              for (LocalTime scheduled : entry.getValue()) {
                if (scheduled.equals(now)) {
                  lastFired.put(type, nowKey);
                  Common.sendConsoleMmMessage(
                      "<green>[AutoEvent] Auto-starting event: <yellow>" + type.getName());
                  EventManager.getInstance().startEvent(null, type);
                  break;
                }
              }
            }
          }
        };

    // Poll every 20 seconds — fine-grained enough to never miss a minute
    task.runTaskTimer(AstralPractice.getInstance(), 20L, 20L * 20L);
  }

  /** Cancels the scheduler (called on plugin disable). */
  public void cancel() {
    if (task != null && taskRunning) {
      task.cancel();
      taskRunning = false;
    }
  }
}

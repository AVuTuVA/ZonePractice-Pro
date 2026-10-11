package dev.lokspel.practice.manager.fight.event.runnables.queue;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.event.interfaces.Event;
import dev.lokspel.practice.manager.server.sound.SoundEffect;
import dev.lokspel.practice.manager.server.sound.SoundManager;
import dev.lokspel.practice.manager.server.sound.SoundType;
import dev.lokspel.practice.util.interfaces.Runnable;

public class QueueStartRunnable extends Runnable {

  private final Event event;

  public QueueStartRunnable(final QueueRunnable queueRunnable) {
    super(0, 20, false);

    this.event = queueRunnable.getEvent();
    this.seconds = this.event.getEventData().getWaitBeforeStart();
  }

  @Override
  public void run() {
    if (this.seconds == this.event.getEventData().getWaitBeforeStart()
        || seconds == 10
        || seconds <= 5 && seconds != 0) {
      event.sendMessage(
          PracticeConfig.language()
              .getEvent()
              .getQueueStartCountdown()
              .replace("%seconds%", String.valueOf(seconds))
              .replace(
                  "%secondName%",
                  (seconds == 1
                      ? PracticeConfig.language().getSecondName().getOneSecond()
                      : PracticeConfig.language().getSecondName().getUnderOneSecond())),
          false);

      if (seconds <= 5) {
        SoundEffect sound = SoundManager.getInstance().getSound(SoundType.EVENT_QUEUE_COUNTDOWN);
        if (sound != null) {
          sound.play(event.getPlayers());
        }
      }
    }

    if (seconds == 0) {
      this.cancel();
      event.start();
      event.getQueueRunnable().cancel();
    }

    this.seconds--;
  }
}

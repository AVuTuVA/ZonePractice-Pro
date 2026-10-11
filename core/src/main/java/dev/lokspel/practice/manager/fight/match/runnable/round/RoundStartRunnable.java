package dev.lokspel.practice.manager.fight.match.runnable.round;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.language.MatchTypeMessages;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.Round;
import dev.lokspel.practice.manager.fight.match.enums.MatchStatus;
import dev.lokspel.practice.manager.fight.match.enums.RoundStatus;
import dev.lokspel.practice.manager.fight.match.util.TitleUtil;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.server.sound.SoundManager;
import dev.lokspel.practice.manager.server.sound.SoundType;
import dev.lokspel.practice.manager.spectator.SpectatorManager;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.StringUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class RoundStartRunnable extends BukkitRunnable {

  private final Match match;
  private final Round round;

  @Getter private int seconds;
  @Getter private boolean running = false;

  public RoundStartRunnable(Round round) {
    this.round = round;
    this.match = round.getMatch();

    Ladder ladder = match.getLadder();

    if (this.round.getRoundNumber() == 1 || ladder.isMultiRoundStartCountdown()) {
      this.seconds = ladder.getStartCountdown();
    } else {
      this.seconds = 0;
    }
  }

  @Override
  public void run() {
    MatchTypeMessages.Start startMessages = getStartMessages();
    String message;

    if (this.seconds == 0) {
      this.cancel();

      this.round.setRoundStatus(RoundStatus.LIVE);
      this.round.beginRunnable();

      // Show "FIGHT!" title when countdown finishes
      if (match.getLadder().isCountdownTitles()) {
        TitleUtil.sendTitleToAll(
            match.getPeople(),
            Common.deserializeMiniMessage(
                PracticeConfig.language().getMatch().getStartTitles().getFight()),
            100,
            1000,
            200);
      }

      if (this.round.getRoundNumber() == 1) {
        message = StringUtil.replaceSecondString(startMessages.getMatchStarted(), seconds);
        this.match.setStatus(MatchStatus.LIVE);
        SpectatorManager.getInstance().getSpectatorMenuGui().update();
      } else {
        message = StringUtil.replaceSecondString(startMessages.getRoundStarted(), seconds);
      }

      SoundManager.getInstance().getSound(SoundType.MATCH_STARTED).play(match.getPeople());
    } else {
      // Show countdown number as title
      if (match.getLadder().isCountdownTitles()
          && this.seconds
              <= PracticeConfig.settings().getMatchSettings().getTitle().getStartCountdownFrom()
          && this.seconds > 0) {
        TitleUtil.sendTitleToAll(
            match.getPeople(),
            Common.deserializeMiniMessage(
                PracticeConfig.language()
                    .getMatch()
                    .getStartTitles()
                    .getCountdown()
                    .replace("%remaining%", String.valueOf(seconds))),
            100,
            800,
            100);
      }

      if (round.getRoundNumber() == 1) {
        message = StringUtil.replaceSecondString(startMessages.getMatchStarting(), seconds);
      } else {
        message = StringUtil.replaceSecondString(startMessages.getRoundStarting(), seconds);
      }

      this.seconds--;

      SoundManager.getInstance().getSound(SoundType.MATCH_START_COUNTDOWN).play(match.getPeople());
    }

    this.match.sendMessage(message, true);
  }

  public RoundStartRunnable begin() {
    this.round.setRoundStatus(RoundStatus.START);

    running = true;
    this.runTaskTimer(
        AstralPractice.getInstance(),
        match.getLadder().isMultiRoundStartCountdown() || round.getRoundNumber() == 1 ? 20L : 0,
        20L);

    return this;
  }

  @Override
  public synchronized void cancel() {
    if (!running) {
      return;
    }

    Bukkit.getScheduler().cancelTask(this.getTaskId());
    running = false;
  }

  private MatchTypeMessages.Start getStartMessages() {
    MatchTypeMessages messages = match.getType().messages();
    return messages.getStart();
  }
}

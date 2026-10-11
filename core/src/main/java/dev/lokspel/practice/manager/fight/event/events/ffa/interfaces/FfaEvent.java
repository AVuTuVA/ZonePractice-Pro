package dev.lokspel.practice.manager.fight.event.events.ffa.interfaces;

import dev.lokspel.api.event.event.EventEndEvent;
import dev.lokspel.api.event.spectate.start.EventSpectateStartEvent;
import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.language.EventMessages;
import dev.lokspel.practice.manager.fight.event.enums.EventStatus;
import dev.lokspel.practice.manager.fight.event.interfaces.EventData;
import dev.lokspel.practice.manager.fight.event.interfaces.FullRunnableInterface;
import dev.lokspel.practice.manager.fight.event.runnables.DurationRunnable;
import dev.lokspel.practice.manager.fight.event.runnables.StartRunnable;
import dev.lokspel.practice.manager.fight.event.util.EventUtil;
import dev.lokspel.practice.manager.server.ServerManager;
import dev.lokspel.practice.manager.server.sound.SoundEffect;
import dev.lokspel.practice.manager.server.sound.SoundManager;
import dev.lokspel.practice.manager.server.sound.SoundType;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.entityhider.PlayerHider;
import dev.lokspel.practice.util.playerutil.PlayerUtil;
import java.util.Random;
import java.util.function.Supplier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class FfaEvent extends FullRunnableInterface {

  protected static final Random random = new Random();
  private final Supplier<EventMessages> messages;

  public FfaEvent(Object starter, EventData eventData, Supplier<EventMessages> messages) {
    super(starter, eventData);
    this.messages = messages;
  }

  /** Returns this event's language section, resolved on each call so reloads apply. */
  public EventMessages getMessages() {
    return this.messages.get();
  }

  public void teleport(final Player player) {
    // Safety check: ensure spawns list is not empty
    if (eventData.getSpawns() == null || eventData.getSpawns().isEmpty()) {
      Common.sendMmMessage(player, "<red>Error: No spawn points configured for this event!");
      return;
    }

    // Safety check: ensure player is online
    if (!player.isOnline()) {
      return;
    }

    int i = random.nextInt(eventData.getSpawns().size());
    Location spawnLocation = eventData.getSpawns().get(i);

    // Safety check: ensure spawn location is valid
    if (spawnLocation == null || spawnLocation.getWorld() == null) {
      Common.sendMmMessage(player, "<red>Error: Invalid spawn location!");
      return;
    }

    // Teleport the player
    player.teleport(spawnLocation);

    // Load inventory after teleport
    loadInventory(player);
  }

  @Override
  protected void customStart() {
    this.status = EventStatus.START;
    this.customCustomStart(); // It has to be here because of Juggernaut.

    // Teleport players FIRST, before starting the countdown runnable
    for (Player player : this.players) {
      for (Player target : this.players) {
        if (player != target) {
          PlayerHider.getInstance().showPlayer(player, target);
        }
      }

      this.teleport(player);
      // Disable flying to prevent players from using lobby flight settings during events
      PlayerUtil.setFightPlayer(player);
    }

    // Start the countdown runnable AFTER players are teleported
    this.getStartRunnable().begin();
  }

  protected abstract void customCustomStart();

  @Override
  public void handleStartRunnable(StartRunnable startRunnable) {
    int seconds = startRunnable.getSeconds();

    if (seconds == 0) {
      startRunnable.cancel();
      this.status = EventStatus.LIVE;
      this.getDurationRunnable().begin();
    } else if (seconds % 10 == 0 || seconds <= 3) {
      sendMessage(
          getMessages()
              .getGameStarting()
              .replace("%seconds%", String.valueOf(seconds))
              .replace(
                  "%secondName%",
                  (seconds == 1
                      ? PracticeConfig.language().getSecondName().getOneSecond()
                      : PracticeConfig.language().getSecondName().getUnderOneSecond())),
          true);

      SoundEffect sound = SoundManager.getInstance().getSound(SoundType.EVENT_START_COUNTDOWN);
      if (sound != null) {
        sound.play(this.getPlayers());
      }
    }

    startRunnable.decreaseTime();
  }

  @Override
  public void handleDurationRunnable(DurationRunnable durationRunnable) {
    if (durationRunnable.getSeconds() == 0) {
      if (!getStatus().equals(EventStatus.END)) {
        endEvent();
      } else {
        durationRunnable.cancel();
      }
    } else {
      durationRunnable.decreaseTime();
    }
  }

  @Override
  public void endEvent() {
    if (this.status.equals(EventStatus.END)) {
      return;
    }

    EventEndEvent event = new EventEndEvent(this);
    Bukkit.getPluginManager().callEvent(event);

    this.cancelAllRunnable();
    this.status = EventStatus.END;
    if (AstralPractice.getInstance().isEnabled()) {
      this.getEndRunnable().begin();
    } else {
      this.getEndRunnable().end();
    }

    if (winner != null) {
      this.sendMessage(getMessages().getWonEvent().replace("%winner%", winner.getName()), true);

      for (String cmd : eventData.getType().getWinnerCmd()) {
        ServerManager.runConsoleCommand(cmd.replace("%player%", winner.getName()));
      }
    } else {
      this.sendMessage(getMessages().getNoWinner(), true);
    }
  }

  @Override
  public void killPlayer(Player player, boolean teleport) {
    if (!this.players.contains(player)) {
      return;
    }

    this.sendMessage(
        getMessages()
            .getPlayerDied()
            .replace("%player%", player.getName())
            .replace("%startPlayerCount%", String.valueOf(this.getStartPlayerCount()))
            .replace("%playerCount%", String.valueOf(players.size() - 1)),
        true);

    this.players.remove(player);
    if (player.isOnline()) {
      this.addSpectator(player, null, teleport, false);
    }

    this.checkIfEnd();
  }

  @Override
  public void addSpectator(Player spectator, Player target, boolean teleport, boolean message) {
    EventSpectateStartEvent event = new EventSpectateStartEvent(spectator, this);
    Bukkit.getPluginManager().callEvent(event);

    if (target == null && !this.players.isEmpty()) {
      target = this.players.get(random.nextInt(this.players.size()));
    }

    if (teleport) {
      if (target != null) {
        spectator.teleport(target);
      } else {
        spectator.teleport(this.getEventData().getCuboid().getCenter());
      }
    }

    this.addSpectator(spectator);
    EventUtil.setEventSpectatorInventory(spectator);

    if (message && !this.status.equals(EventStatus.END)) {
      sendMessage(
          getMessages().getStartedSpectating().replace("%spectator%", spectator.getName()), true);
    }

    for (Player eventPlayer : players) {
      PlayerHider.getInstance().hidePlayer(eventPlayer, spectator);
    }

    for (Player eventSpectator : this.getSpectators()) {
      if (!eventSpectator.equals(spectator)) {
        PlayerHider.getInstance().hidePlayer(eventSpectator, spectator);
        PlayerHider.getInstance().hidePlayer(spectator, eventSpectator);
      }
    }
  }

  protected abstract void loadInventory(Player player);
}

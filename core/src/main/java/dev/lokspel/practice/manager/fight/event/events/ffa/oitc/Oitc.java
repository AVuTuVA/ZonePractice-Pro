package dev.lokspel.practice.manager.fight.event.events.ffa.oitc;

import dev.lokspel.api.event.event.EventEndEvent;
import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.event.EventManager;
import dev.lokspel.practice.manager.fight.event.enums.EventStatus;
import dev.lokspel.practice.manager.fight.event.events.ffa.interfaces.FfaEvent;
import dev.lokspel.practice.manager.server.ServerManager;
import dev.lokspel.practice.util.Common;
import dev.lokspel.practice.util.playerutil.PlayerUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class Oitc extends FfaEvent {

  private static final int DEFAULT_PLAYER_LIFE =
      PracticeConfig.settings().getEvent().getOitc().getSettings().getPlayerLife();

  private final Map<Player, Integer> playerPoints = new HashMap<>();
  private final Map<Player, Integer> playerLives = new HashMap<>();

  public Oitc(Object starter, OitcData eventData) {
    super(
        starter,
        eventData,
        () -> PracticeConfig.language().getCommand().getEvent().getArguments().getOitc());
  }

  @Override
  protected void customCustomStart() {
    for (Player player : this.players) {
      playerPoints.put(player, 0);
      playerLives.put(player, DEFAULT_PLAYER_LIFE);
    }
  }

  @Override
  protected void loadInventory(Player player) {
    PlayerUtil.clearPlayer(player, true, false, true);
    player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
    player.getInventory().addItem(new ItemStack(Material.BOW));
    player.getInventory().addItem(EventManager.PLAYER_TRACKER);
    player.getInventory().setItem(8, new ItemStack(Material.ARROW));
    player.updateInventory();
  }

  @Override
  public OitcData getEventData() {
    return (OitcData) this.eventData;
  }

  @Override
  public void killPlayer(Player player, boolean teleport) {
    if (!this.players.contains(player)) {
      return;
    }

    int newLife = playerLives.get(player) - 1;

    if (playerLives.get(player) == 1) {
      players.remove(player);

      for (Player eventPlayer : players) {
        this.givePoints(eventPlayer, 5);
      }

      this.sendMessage(
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getOitc()
              .getPlayerDied()
              .replace("%player%", player.getName())
              .replace("%startPlayerCount%", String.valueOf(playerLives.size()))
              .replace("%playerCount%", String.valueOf(players.size())),
          true);

      this.addSpectator(player, null, teleport, false);
      this.checkIfEnd();
    } else {
      this.teleport(player);
      Common.sendMmMessage(
          player,
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getOitc()
              .getLifeLeft()
              .replace("%life%", String.valueOf(newLife)));
    }

    playerLives.replace(player, newLife);
  }

  public void givePoints(Player player, int point) {
    if (playerPoints.containsKey(player)) {
      playerPoints.replace(player, playerPoints.get(player) + point);
    }

    Common.sendMmMessage(
        player,
        PracticeConfig.language()
            .getCommand()
            .getEvent()
            .getArguments()
            .getOitc()
            .getPointsReceived()
            .replace("%point%", String.valueOf(point)));
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
      this.sendMessage(
          PracticeConfig.language()
              .getCommand()
              .getEvent()
              .getArguments()
              .getOitc()
              .getWonEvent()
              .replace("%winner%", winner.getName())
              .replace("%points%", String.valueOf(playerPoints.get(winner))),
          true);

      for (String cmd : eventData.getType().getWinnerCmd()) {
        ServerManager.runConsoleCommand(cmd.replace("%player%", winner.getName()));
      }
    } else {
      this.sendMessage(
          PracticeConfig.language().getCommand().getEvent().getArguments().getOitc().getNoWinner(),
          true);
    }
  }

  public Player getHighestPointPlayer() {
    Map<Player, Integer> points = getOrder(playerPoints);

    for (Player player : points.keySet()) {
      return player;
    }

    return null;
  }

  public Map<Player, Integer> getOrder(Map<Player, Integer> map) {
    return PlayerUtil.sortByValue(map);
  }
}

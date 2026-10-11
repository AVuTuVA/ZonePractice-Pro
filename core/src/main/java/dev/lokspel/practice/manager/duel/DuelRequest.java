package dev.lokspel.practice.manager.duel;

import dev.lokspel.practice.manager.arena.arenas.Arena;
import dev.lokspel.practice.manager.backend.LanguageManager;
import dev.lokspel.practice.manager.fight.match.type.duel.Duel;
import dev.lokspel.practice.manager.fight.util.PlayerUtil;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.util.LadderUtil;
import dev.lokspel.practice.util.Common;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class DuelRequest {

  private Player sender;
  private Player target;
  private Ladder ladder;
  private Arena arena;
  private int rounds;
  private final Runnable expireHandler;

  public DuelRequest(Player sender, Player target, Ladder ladder, Arena arena, int rounds) {
    this(sender, target, ladder, arena, rounds, null);
  }

  public DuelRequest(
      Player sender,
      Player target,
      Ladder ladder,
      Arena arena,
      int rounds,
      Runnable expireHandler) {
    this.sender = sender;
    this.target = target;
    this.ladder = ladder;
    this.arena = arena;
    this.rounds = rounds;
    this.expireHandler = expireHandler;
  }

  public void sendRequest() {
    String arenaName;
    if (arena != null) {
      arenaName = arena.getDisplayName();
    } else {
      arenaName = LanguageManager.getString("COMMAND.DUEL.REQUEST-MESSAGE.RANDOM-ARENA-NAME");
    }

    for (String line : LanguageManager.getList("COMMAND.DUEL.REQUEST-MESSAGE.SENDER")) {
      Common.sendMmMessage(
          sender,
          line.replace("%ladder%", ladder.getDisplayName())
              .replace("%arena%", arenaName)
              .replace("%rounds%", String.valueOf(rounds))
              .replace("%target%", target.getName())
              .replace("%targetPing%", String.valueOf(PlayerUtil.getPing(target))));
    }

    for (String line : LanguageManager.getList("COMMAND.DUEL.REQUEST-MESSAGE.TARGET")) {
      Common.sendMmMessage(
          target,
          line.replace("%ladder%", ladder.getDisplayName())
              .replace("%arena%", arenaName)
              .replace("%rounds%", String.valueOf(rounds))
              .replace("%sender%", sender.getName())
              .replace("%senderPing%", String.valueOf(PlayerUtil.getPing(sender))));
    }
  }

  public void acceptRequest() {
    DuelManager.getInstance().getRequests().get(target).remove(this);

    boolean playerSelected = this.getArena() != null;
    Arena arena;
    if (this.getArena() != null) {
      arena = this.getArena();

      if (arena.getAvailableArena() == null) {
        Common.sendMmMessage(
            sender,
            LanguageManager.getString("COMMAND.DUEL.ARENA-BUSY")
                .replace("%arena%", this.getArena().getDisplayName()));
        arena = LadderUtil.getAvailableArena(ladder);
        playerSelected = false;
      }
    } else {
      arena = LadderUtil.getAvailableArena(ladder);
    }

    if (arena != null) {
      Duel duel = new Duel(ladder, arena, Arrays.asList(sender, target), false, rounds);
      duel.setPlayerSelectedArena(playerSelected);
      duel.startMatch();
    } else {
      Common.sendMmMessage(sender, LanguageManager.getString("COMMAND.DUEL.NO-AVAILABLE-ARENA"));
      Common.sendMmMessage(target, LanguageManager.getString("COMMAND.DUEL.NO-AVAILABLE-ARENA"));
    }
  }

  public void handleExpiry() {
    if (expireHandler != null) {
      expireHandler.run();
    }
  }
}

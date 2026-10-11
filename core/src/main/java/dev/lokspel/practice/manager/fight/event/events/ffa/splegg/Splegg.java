package dev.lokspel.practice.manager.fight.event.events.ffa.splegg;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.event.events.ffa.interfaces.FfaEvent;
import dev.lokspel.practice.manager.fight.util.PlayerUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class Splegg extends FfaEvent {

  private final Map<Player, Integer> shotEggs = new HashMap<>();
  private final Map<Player, Integer> shotBlocks = new HashMap<>();

  public Splegg(Object starter, SpleggData eventData) {
    super(
        starter,
        eventData,
        () -> PracticeConfig.language().getCommand().getEvent().getArguments().getSplegg());
  }

  @Override
  protected void customCustomStart() {
    for (Player player : this.players) {
      this.shotEggs.put(player, 0);
      this.shotBlocks.put(player, 0);
    }
  }

  @Override
  protected void loadInventory(Player player) {
    PlayerUtil.clearInventory(player);
    player.getInventory().addItem(this.getEventData().getEggLauncher());
    player.updateInventory();
  }

  @Override
  public SpleggData getEventData() {
    return (SpleggData) this.eventData;
  }
}

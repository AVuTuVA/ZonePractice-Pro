package dev.lokspel.practice.manager.fight.event.events.duel.sumo;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.event.events.duel.interfaces.DuelEvent;
import dev.lokspel.practice.manager.fight.util.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Sumo extends DuelEvent {

  public Sumo(Object starter, SumoData sumoData) {
    super(
        starter,
        sumoData,
        () -> PracticeConfig.language().getCommand().getEvent().getArguments().getSumo());
  }

  @Override
  public SumoData getEventData() {
    return (SumoData) eventData;
  }

  @Override
  public void teleport(Player player, Location location) {
    PlayerUtil.clearInventory(player);
    dev.lokspel.practice.util.playerutil.PlayerUtil.setFightPlayer(player);

    player.teleport(location);

    this.getEventData().getKitData().loadKitData(player, true);
  }
}

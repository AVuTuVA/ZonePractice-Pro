package dev.nandi0813.api.Event.Spectate.End;

import dev.nandi0813.api.Interface.FFA;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class FFASpectateEndEvent extends SpectateEndEvent {

    private static final HandlerList handlers = new HandlerList();

    private final FFA ffa;

    public FFASpectateEndEvent(Player player, FFA ffa) {
        super(player, ffa);
        this.ffa = ffa;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

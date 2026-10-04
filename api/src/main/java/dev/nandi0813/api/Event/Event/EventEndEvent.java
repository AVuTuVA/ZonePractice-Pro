package dev.nandi0813.api.Event.Event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class EventEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final dev.nandi0813.api.Interface.Event event;

    public EventEndEvent(dev.nandi0813.api.Interface.Event event) {
        this.event = event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
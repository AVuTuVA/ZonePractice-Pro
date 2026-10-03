package dev.nandi0813.practice.manager.ladder.type;

import dev.nandi0813.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.nandi0813.practice.manager.ladder.enums.LadderType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Creeper extends NormalLadder {

    private double creeperExplosionDelay;

    public Creeper(String name, LadderType type) {
        super(name, type);
        this.creeperExplosionDelay = 1.0;
    }

    public int getCreeperExplosionDelayTicks() {
        return (int) Math.round(creeperExplosionDelay * 20);
    }

}
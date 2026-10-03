package dev.nandi0813.practice.manager.ladder.type;

import dev.nandi0813.practice.manager.ladder.abstraction.interfaces.CustomConfig;
import dev.nandi0813.practice.manager.ladder.abstraction.normal.NormalLadder;
import dev.nandi0813.practice.manager.ladder.enums.LadderType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

@Setter
@Getter
public class Creeper extends NormalLadder implements CustomConfig {

    private double creeperExplosionDelay;
    private static final String CREEPER_EXPLOSION_DELAY_PATH = "creeper-explosion-delay";

    public Creeper(String name, LadderType type) {
        super(name, type);
        this.creeperExplosionDelay = 1.0;
    }

    public int getCreeperExplosionDelayTicks() {
        return (int) Math.round(creeperExplosionDelay * 20);
    }

    @Override
    public void setCustomConfig(YamlConfiguration config) {
        config.set(CREEPER_EXPLOSION_DELAY_PATH, this.creeperExplosionDelay);
    }

    @Override
    public void getCustomConfig(YamlConfiguration config) {
        if (config.isDouble(CREEPER_EXPLOSION_DELAY_PATH)) {
            this.creeperExplosionDelay = config.getDouble(CREEPER_EXPLOSION_DELAY_PATH);
            if (this.creeperExplosionDelay < 0.5 || this.creeperExplosionDelay > 10.0)
                this.creeperExplosionDelay = 1.0;
        } else
            this.creeperExplosionDelay = 1.0;
    }

}
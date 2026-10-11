package dev.lokspel.practice.manager.fight.util;

import dev.lokspel.practice.config.PracticeConfig;
import lombok.Getter;

@Getter
public enum DeathCause {
  VOID(PracticeConfig.language().getFight().getDeathMessages().getVoidValue()),
  VOID_BY_PLAYER(PracticeConfig.language().getFight().getDeathMessages().getVoidByPlayer()),
  WATER(PracticeConfig.language().getFight().getDeathMessages().getWater()),
  LAVA(PracticeConfig.language().getFight().getDeathMessages().getLava()),
  FIRE(PracticeConfig.language().getFight().getDeathMessages().getFire()),
  FALL(PracticeConfig.language().getFight().getDeathMessages().getFall()),
  EXPLOSION(PracticeConfig.language().getFight().getDeathMessages().getExplosion()),
  EXPLOSION_BY_PLAYER(
      PracticeConfig.language().getFight().getDeathMessages().getExplosionByPlayer()),
  PLAYER_ATTACK(PracticeConfig.language().getFight().getDeathMessages().getPlayer()),
  PLAYER_PROJECTILE(PracticeConfig.language().getFight().getDeathMessages().getProjectile()),
  SUMO(PracticeConfig.language().getFight().getDeathMessages().getSumoFall()),
  SPLEEF(PracticeConfig.language().getFight().getDeathMessages().getSpleefFall()),
  PORTAL_OWN_JUMP(PracticeConfig.language().getFight().getDeathMessages().getOwnPortalJump()),
  DEFAULT(PracticeConfig.language().getFight().getDeathMessages().getDefaultValue());

  private final String message;

  DeathCause(final String message) {
    this.message = message;
  }
}

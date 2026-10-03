package dev.nandi0813.practice.manager.fight.match.listener;

import dev.nandi0813.practice.ZonePractice;
import dev.nandi0813.practice.manager.fight.match.Match;
import dev.nandi0813.practice.manager.fight.match.MatchManager;
import dev.nandi0813.practice.manager.fight.match.enums.RoundStatus;
import dev.nandi0813.practice.manager.ladder.enums.LadderType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CreeperListener implements Listener {

    private static final long SCAN_DELAY_TICKS = 20L;
    private static final long SCAN_PERIOD_TICKS = 1L;
    private static final double TRIGGER_DISTANCE = 16.0;

    public CreeperListener() {
        startCreeperScanner();
    }

    private void startCreeperScanner() {
        new BukkitRunnable() {
            @Override
            public void run() {
                scanMatches();
            }
        }.runTaskTimer(
                ZonePractice.getInstance(),
                SCAN_DELAY_TICKS,
                SCAN_PERIOD_TICKS
        );
    }

    private static void scanMatches() {
        for (Match match : MatchManager.getInstance().getLiveMatches()) {
            if (isCreeperLiveMatch(match)) {
                igniteCreepers(match);
            }
        }
    }

    private static boolean isCreeperLiveMatch(Match match) {
        return match.getLadder().getType() == LadderType.CREEPER
                && match.getCurrentRound().getRoundStatus() == RoundStatus.LIVE;
    }

    private static void igniteCreepers(Match match) {
        int fuseTicks = getFuseTicks(match);

        for (Player player : match.getPlayers()) {
            if (!isAlive(player)) {
                continue;
            }

            for (Entity entity : player.getNearbyEntities(
                    TRIGGER_DISTANCE,
                    TRIGGER_DISTANCE,
                    TRIGGER_DISTANCE
            )) {
                if (entity instanceof Creeper creeper) {
                    ignite(creeper, fuseTicks);
                }
            }
        }
    }

    private static int getFuseTicks(Match match) {
        dev.nandi0813.practice.manager.ladder.type.Creeper ladder =
                (dev.nandi0813.practice.manager.ladder.type.Creeper) match.getLadder();

        return ladder.getCreeperExplosionDelayTicks();
    }

    private static boolean isAlive(Player player) {
        return player.isValid() && !player.isDead();
    }

    private static void ignite(Creeper creeper, int fuseTicks) {
        if (!isValid(creeper)) {
            return;
        }

        if (!creeper.isIgnited()) {
            creeper.setMaxFuseTicks(fuseTicks);
            creeper.ignite();
            creeper.setFuseTicks(fuseTicks);
            return;
        }

        // Never extend an already running fuse.
        if (creeper.getFuseTicks() > fuseTicks) {
            creeper.setFuseTicks(fuseTicks);
        }
    }

    private static boolean isValid(Creeper creeper) {
        return creeper.isValid() && !creeper.isDead();
    }

    @EventHandler
    public void onCreeperDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Creeper victim)) {
            return;
        }

        if (!(event.getDamager() instanceof Creeper)) {
            return;
        }

        if (!isInsideCreeperMatch(victim)) {
            return;
        }

        event.setCancelled(true);
    }

    private static boolean isInsideCreeperMatch(Creeper creeper) {
        for (Match match : MatchManager.getInstance().getLiveMatches()) {
            boolean insideArena = match.getCuboid() != null
                    && match.getCuboid().contains(creeper.getLocation());

            if (isCreeperLiveMatch(match) && insideArena) {
                return true;
            }
        }

        return false;
    }
}
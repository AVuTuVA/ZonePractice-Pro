package dev.lokspel.practice.manager.fight.match.util;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.MatchManager;
import dev.lokspel.practice.manager.fight.match.type.partyffa.PartyFfa;
import dev.lokspel.practice.manager.fight.util.stats.Statistic;
import dev.lokspel.practice.manager.ladder.abstraction.Ladder;
import dev.lokspel.practice.manager.ladder.enums.LadderType;
import dev.lokspel.practice.manager.ladder.type.SkyWars;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.manager.profile.enums.ProfileStatus;
import dev.lokspel.practice.util.Cuboid;
import dev.lokspel.practice.util.NumberUtil;
import dev.lokspel.practice.util.Pair;
import dev.lokspel.practice.util.playerutil.PlayerUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class MatchUtil {
  private MatchUtil() {}

  public static String getMatchId() {
    return "match-" + System.currentTimeMillis() + NumberUtil.getRandomNumber(100, 999);
  }

  @Nullable
  public static Match getMatchIfInMatch(Player player) {
    Profile profile = ProfileManager.getInstance().getProfile(player);
    if (profile == null || profile.getStatus() != ProfileStatus.MATCH) {
      return null;
    }
    return MatchManager.getInstance().getLiveMatchByPlayer(player);
  }

  public static boolean isLadderBedRelated(Ladder ladder) {
    LadderType ladderType = ladder.getType();
    return ladderType.equals(LadderType.BEDWARS)
        || ladderType.equals(LadderType.FIREBALL_FIGHT)
        || ladderType.equals(LadderType.MLG_RUSH);
  }

  public static Cuboid getSideBuildLimitCube(Cuboid baseCube, int limit) {
    baseCube = baseCube.expand(Cuboid.CuboidDirection.North, -limit);
    baseCube = baseCube.expand(Cuboid.CuboidDirection.South, -limit);
    baseCube = baseCube.expand(Cuboid.CuboidDirection.West, -limit);
    return baseCube.expand(Cuboid.CuboidDirection.East, -limit);
  }

  public static List<ItemStack> getRandomSkyWarsLoot(SkyWars ladder) {
    if (ladder.getSkyWarsLoot() == null) {
      return Collections.emptyList();
    }

    List<ItemStack> allLoot = new ArrayList<>(Arrays.asList(ladder.getSkyWarsLoot().clone()));
    Collections.shuffle(allLoot);
    int random = (int) ((Math.random() * (10 - 4)) + 4);

    List<ItemStack> actualLoot = new ArrayList<>();
    for (int i = 0; i < random; i++) {
      if (allLoot.get(i) != null) {
        actualLoot.add(allLoot.get(i));
      }
    }

    return actualLoot;
  }

  /**
   * Computes the Elo change for both players of a ranked duel.
   *
   * <p>In {@code RANDOM} mode both players change by the same magnitude (winner gains, loser loses)
   * using the random {@code QUEUE.RANKED.ELO-CHANGE} interval. In {@code SKILL} mode (default) the
   * standard Elo formula is used: {@code newElo = oldElo + K * (result - expected)}, where {@code
   * expected} is derived from the rating difference between the two players.
   *
   * <p>The absolute change of each player is capped by {@code QUEUE.RANKED.ELO-SYSTEM.MAX-CHANGE}
   * (if greater than 0).
   *
   * @param winnerElo the winner's Elo before the match
   * @param loserElo the loser's Elo before the match
   * @return the winner's Elo change and the loser's Elo change (loser change is negative or zero)
   */
  public static Pair<Integer, Integer> getEloChange(int winnerElo, int loserElo) {
    int winnerChange;
    int loserChange;

    if (EloMode.getActiveMode() == EloMode.SKILL) {
      final int eloFactor =
          Math.max(1, PracticeConfig.settings().getQueue().getRanked().getEloSystem().getKfactor());
      double expectedWinner = expectedScore(winnerElo, loserElo);

      winnerChange = (int) Math.round(eloFactor * (1 - expectedWinner));
      loserChange = (int) Math.round(eloFactor * (0 - expectedScore(loserElo, winnerElo)));
    } else {
      winnerChange = getRandomElo();
      loserChange = -winnerChange;
    }

    int maxChange = PracticeConfig.settings().getQueue().getRanked().getEloSystem().getMaxChange();
    if (maxChange > 0) {
      winnerChange = Math.clamp(winnerChange, -maxChange, maxChange);
      loserChange = Math.clamp(loserChange, -maxChange, maxChange);
    }

    return new Pair<>(winnerChange, loserChange);
  }

  /** Standard Elo expected score for a player against an opponent. */
  private static double expectedScore(int rating, int opponentRating) {
    return 1.0 / (1.0 + Math.pow(10, (opponentRating - rating) / 400.0));
  }

  /** Random Elo change from the configured {@code QUEUE.RANKED.ELO-CHANGE} interval. */
  private static int getRandomElo() {
    String[] changeInterval =
        PracticeConfig.settings().getQueue().getRanked().getEloChange().split("-");
    int min = Integer.parseInt(changeInterval[0]);
    int max = Integer.parseInt(changeInterval[1]);

    return (int) ((Math.random() * (max - min)) + min);
  }

  public static void safePlayerTeleportBlock(Block block) {
    if (block == null) {
      return;
    }
    if (!block.getWorld().isChunkLoaded(block.getX() >> 4, block.getZ() >> 4)) {
      return;
    }
    if (block.getType().equals(Material.AIR)) {
      block.setBlockData(Material.BEDROCK.createBlockData());
    }
  }

  public static Player getBoxingTopPlayer(PartyFfa partyFfa, int rank) {
    if (partyFfa.getPlayers().size() < rank) {
      return null;
    }

    Map<Player, Integer> boxingHits = new HashMap<>();
    for (Player player : partyFfa.getPlayers()) {
      Statistic roundStatistic = partyFfa.getCurrentStat(player);
      if (roundStatistic != null) {
        boxingHits.put(player, roundStatistic.getHit());
      }
    }

    if (boxingHits.size() < rank) {
      return null;
    }

    return new ArrayList<>(PlayerUtil.sortByValue(boxingHits).keySet()).get(rank - 1);
  }
}

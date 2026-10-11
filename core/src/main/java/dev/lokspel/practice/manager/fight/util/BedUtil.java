package dev.lokspel.practice.manager.fight.util;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.config.language.LadderSpecificMessages;
import dev.lokspel.practice.config.language.MatchTypeMessages;
import dev.lokspel.practice.manager.arena.util.BedLocation;
import dev.lokspel.practice.manager.fight.match.Match;
import dev.lokspel.practice.manager.fight.match.enums.TeamEnum;
import dev.lokspel.practice.manager.fight.match.interfaces.Team;
import dev.lokspel.practice.util.Common;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BedUtil {

  public static BedLocation getBedLocation(Block block) {
    if (block == null) {
      return null;
    }

    Location bedLoc = block.getLocation();
    Bed bed = (Bed) block.getState().getBlockData();

    if (bed.getPart().equals(Bed.Part.HEAD)) {
      bedLoc = block.getRelative(bed.getFacing().getOppositeFace()).getLocation();
    }

    return new BedLocation(
        bedLoc.getWorld(), bedLoc.getX(), bedLoc.getY(), bedLoc.getZ(), bed.getFacing());
  }

  public static void placeBed(Location loc, BlockFace face) {
    Block bedFoot = loc.getBlock();
    bedFoot.setBlockData(Material.RED_BED.createBlockData());
    Bed bedFootData = (Bed) Bukkit.createBlockData(Material.RED_BED);
    bedFootData.setPart(Bed.Part.FOOT);
    bedFootData.setFacing(face);

    Block bedHead = bedFoot.getRelative(face);
    bedHead.setBlockData(Material.RED_BED.createBlockData());
    Bed bedHeadData = (Bed) Bukkit.createBlockData(Material.RED_BED);
    bedHeadData.setPart(Bed.Part.HEAD);
    bedHeadData.setFacing(face);

    bedFoot.setBlockData(bedFootData, false);
    bedHead.setBlockData(bedHeadData, false);
  }

  public static boolean onBedBreak(final @NotNull BlockBreakEvent e, final @NotNull Match match) {
    Player player = e.getPlayer();

    if (match.getCurrentStat(player).isSet()) {
      return false;
    }

    final Map<TeamEnum, Boolean> bedStatus = match.getCurrentRound().getBedStatus();
    if (!bedStatus.get(TeamEnum.TEAM1) && !bedStatus.get(TeamEnum.TEAM2)) {
      return false;
    }

    Block bedBlock = e.getBlock();
    if (!BlockUtil.isBedMaterial(bedBlock.getType())) {
      return false;
    }

    TeamEnum team = ((Team) match).getTeam(player);
    Location bedLoc = bedBlock.getLocation();

    boolean destroy = false;
    if (match.getArena().getBedLoc1().getLocation().equals(bedLoc)
        || match
            .getArena()
            .getBedLoc1()
            .getLocation()
            .getBlock()
            .getRelative(match.getArena().getBedLoc1().getFacing())
            .getLocation()
            .equals(bedLoc)) {
      e.setCancelled(true);

      if (team.equals(TeamEnum.TEAM2)) {
        destroy = true;

        if (bedStatus.get(TeamEnum.TEAM1)) {
          bedStatus.replace(TeamEnum.TEAM1, false);
          sendBedDestroyMessage(match, TeamEnum.TEAM1);
        }
      } else {
        Common.sendMmMessage(player, PracticeConfig.language().getMatch().getCantBreakOwnBed());
      }
    } else if (match.getArena().getBedLoc2().getLocation().equals(bedLoc)
        || match
            .getArena()
            .getBedLoc2()
            .getLocation()
            .getBlock()
            .getRelative(match.getArena().getBedLoc2().getFacing())
            .getLocation()
            .equals(bedLoc)) {
      e.setCancelled(true);

      if (team.equals(TeamEnum.TEAM1)) {
        destroy = true;

        if (bedStatus.get(TeamEnum.TEAM2)) {
          bedStatus.replace(TeamEnum.TEAM2, false);
          sendBedDestroyMessage(match, TeamEnum.TEAM2);
        }
      } else {
        Common.sendMmMessage(player, PracticeConfig.language().getMatch().getCantBreakOwnBed());
      }
    }

    if (destroy) {
      BedLocation bedLocation = getBedLocation(e.getBlock());
      Block head = bedLocation.getBlock().getRelative(bedLocation.getFacing());

      match.addBlockChange(new ChangedBlock(bedLocation.getBlock()));

      head.setBlockData(Material.AIR.createBlockData());
      bedLocation.getBlock().setBlockData(Material.AIR.createBlockData());
    }

    return destroy;
  }

  private static void sendBedDestroyMessage(Match match, TeamEnum team) {
    MatchTypeMessages matchTypeMessages = match.getType().messages();
    MatchTypeMessages.LadderSpecific ladderSpecific = matchTypeMessages.getLadderSpecific();
    if (ladderSpecific == null) {
      return;
    }

    LadderSpecificMessages messages =
        switch (match.getLadder().getType()) {
          case BEDWARS -> ladderSpecific.getBedWars();
          case FIREBALL_FIGHT -> ladderSpecific.getFireballFight();
          case MLG_RUSH -> ladderSpecific.getMlgRush();
          default -> null;
        };

    if (messages == null) {
      return;
    }

    match.sendMessage(
        messages
            .getBedDestroyed()
            .replace("%team%", team.getNameMm())
            .replace("%teamColor%", team.getColorMm()),
        true);
  }
}

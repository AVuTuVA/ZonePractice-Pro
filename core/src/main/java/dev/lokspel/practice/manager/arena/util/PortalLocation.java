package dev.lokspel.practice.manager.arena.util;

import dev.lokspel.practice.util.Cuboid;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PortalLocation {

  private final Location center;
  @Getter private final Cuboid cuboid;

  public PortalLocation(World world, double x, double y, double z) {
    center = new Location(world, x, y, z); // Center
    cuboid = new Cuboid(center.clone().add(1, 0, 1), center.clone().add(-1, 0, -1));
  }

  public PortalLocation(Location location) {
    this(location.getWorld(), location.getX(), location.getY(), location.getZ());
  }

  public Location getCenter() {
    return this.center.clone();
  }

  public void setPortal() {
    var portalData = Material.END_PORTAL.createBlockData();
    for (Block block : cuboid) {
      block.setBlockData(portalData);
    }
  }

  public boolean isOverlap(PortalLocation pl) {
    Cuboid other = pl.getCuboid();
    return this.cuboid.getLowerX() <= other.getUpperX()
        && this.cuboid.getUpperX() >= other.getLowerX()
        && this.cuboid.getLowerZ() <= other.getUpperZ()
        && this.cuboid.getUpperZ() >= other.getLowerZ();
  }

  public boolean isIn(Player player) {
    return cuboid.contains(player.getLocation());
  }

  public boolean isInsidePortalProtection(Block block, int maxDistance) {
    return (center.distance(block.getLocation()) + 1) <= maxDistance;
  }
}

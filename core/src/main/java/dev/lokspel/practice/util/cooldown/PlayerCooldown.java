package dev.lokspel.practice.util.cooldown;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import org.bukkit.entity.Player;

public final class PlayerCooldown {

  private PlayerCooldown() {}

  @Getter
  private static final Map<Player, Map<CooldownObject, Long>> cooldowns = new ConcurrentHashMap<>();

  public static void addCooldown(Player player, CooldownObject object, int time) {
    addCooldown(player, object, (double) time);
  }

  public static void addCooldown(Player player, CooldownObject object, double time) {
    cooldowns
        .computeIfAbsent(player, _ -> new EnumMap<>(CooldownObject.class))
        .put(object, (long) (System.currentTimeMillis() + time * 1000L));
  }

  public static void removeCooldown(Player player, CooldownObject object) {
    Map<CooldownObject, Long> playerCooldowns = cooldowns.get(player);
    if (playerCooldowns != null) {
      playerCooldowns.remove(object);
    }
  }

  public static void clearPlayer(Player player) {
    cooldowns.remove(player);
  }

  public static boolean isActive(Player player, CooldownObject object) {
    Map<CooldownObject, Long> playerCooldowns = cooldowns.get(player);
    if (playerCooldowns != null) {
      Long expiry = playerCooldowns.get(object);
      return expiry != null && System.currentTimeMillis() < expiry;
    }
    return false;
  }

  public static long getLeft(Player player, CooldownObject object) {
    Map<CooldownObject, Long> playerCooldowns = cooldowns.get(player);
    if (playerCooldowns != null) {
      Long expiry = playerCooldowns.get(object);
      if (expiry != null && System.currentTimeMillis() < expiry) {
        return expiry - System.currentTimeMillis();
      }
    }
    return 0L;
  }

  public static double getLeftInDouble(Player player, CooldownObject object) {
    long left = getLeft(player, object);
    return Math.round(left / 100.0) / 10.0;
  }
}

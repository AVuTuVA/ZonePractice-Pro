package dev.lokspel.practice.manager.profile;

import dev.lokspel.practice.AstralPractice;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

@Getter
@Setter
public class RankedBan {

  private Profile banner;
  private boolean banned;
  private String reason;
  private long time;

  public boolean ban(Profile banner, String reason) {
    if (banned) {
      return false;
    }

    this.banned = true;
    this.banner = banner;
    this.reason = reason;
    this.time = System.currentTimeMillis();
    return true;
  }

  public boolean unban() {
    if (!banned) {
      return false;
    }

    this.banned = false;
    this.reason = null;
    return true;
  }

  /**
   * Writes the ban into its section of the profile file.
   *
   * @param data the section the file holds, or {@code null}
   * @return the updated section, or {@code null} to remove it when the player is not banned
   */
  public ProfileData.RankedBan setData(final ProfileData.RankedBan data) {
    if (!isBanned()) {
      return null;
    }

    ProfileData.RankedBan section = data != null ? data : new ProfileData.RankedBan();
    if (banner != null) {
      section.setBanner(banner.getUuid().toString());
    }

    section.setBanned(true);
    section.setReason(reason);
    section.setBannedAt(time);
    return section;
  }

  /**
   * Reads the ban from its section of the profile file. The banner's profile is looked up a few
   * seconds later, once the profiles are loaded.
   *
   * @param data the section, or {@code null} if the file has none
   */
  public void getData(final ProfileData.RankedBan data) {
    if (data == null || data.getBanned() == null) {
      return;
    }

    banned = data.getBanned();

    if (!isBanned()) {
      return;
    }

    String bannerUuid = data.getBanner();
    Bukkit.getScheduler()
        .runTaskLater(
            AstralPractice.getInstance(),
            () -> {
              if (bannerUuid != null) {
                banner = ProfileManager.getInstance().getProfile(UUID.fromString(bannerUuid));
              }
            },
            20L * 3);

    if (data.getReason() != null) {
      reason = data.getReason();
    }

    if (data.getBannedAt() != null) {
      time = data.getBannedAt();
    }
  }
}

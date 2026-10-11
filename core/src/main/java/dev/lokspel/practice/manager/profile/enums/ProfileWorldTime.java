package dev.lokspel.practice.manager.profile.enums;

import dev.lokspel.practice.config.PracticeConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum ProfileWorldTime {
  SUNRISE(PracticeConfig.language().getProfile().getWorldTimeNames().getSunrise(), 24000),
  DAY(PracticeConfig.language().getProfile().getWorldTimeNames().getDay(), 6000),
  SUNSET(PracticeConfig.language().getProfile().getWorldTimeNames().getSunset(), 12000),
  NIGHT(PracticeConfig.language().getProfile().getWorldTimeNames().getNight(), 18000);

  @Getter private final String name;
  @Getter private final long time;

  ProfileWorldTime(String name, long time) {
    this.name = name;
    this.time = time;
  }

  public static ProfileWorldTime getNextWorldTime(ProfileWorldTime profileWorldTime) {
    List<ProfileWorldTime> list = new ArrayList<>(Arrays.asList(ProfileWorldTime.values()));

    if (profileWorldTime != null) {
      int c = list.indexOf(profileWorldTime);

      if (list.size() - 1 == c) {
        return list.getFirst();
      } else {
        return list.get(c + 1);
      }
    } else {
      return list.getFirst();
    }
  }
}

package dev.lokspel.practice.manager.profile.enums;

import dev.lokspel.practice.config.PracticeConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum ProfilePrefixVisibility {
  NONE(PracticeConfig.language().getProfile().getPrefixVisibilityNames().getNone(), false, false),
  PREFIX(
      PracticeConfig.language().getProfile().getPrefixVisibilityNames().getPrefix(), true, false),
  SUFFIX(
      PracticeConfig.language().getProfile().getPrefixVisibilityNames().getSuffix(), false, true),
  PREFIX_AND_SUFFIX(
      PracticeConfig.language().getProfile().getPrefixVisibilityNames().getPrefixAndSuffix(),
      true,
      true);

  @Getter private final String name;
  @Getter private final boolean showPrefix;
  @Getter private final boolean showSuffix;

  ProfilePrefixVisibility(String name, boolean showPrefix, boolean showSuffix) {
    this.name = name;
    this.showPrefix = showPrefix;
    this.showSuffix = showSuffix;
  }

  public static ProfilePrefixVisibility getNext(ProfilePrefixVisibility current) {
    List<ProfilePrefixVisibility> list =
        new ArrayList<>(Arrays.asList(ProfilePrefixVisibility.values()));

    if (current != null) {
      int index = list.indexOf(current);
      if (index == list.size() - 1) {
        return list.getFirst();
      }
      return list.get(index + 1);
    }

    return list.getFirst();
  }
}

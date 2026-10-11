package dev.lokspel.practice.manager.fight.match.enums;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.util.StringUtil;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public enum TeamEnum {
  TEAM1(
      AstralPractice.getMiniMessage()
          .deserialize(
              StringUtil.legacyToMiniMessage(
                  PracticeConfig.settings().getMatchSettings().getTeams().getTeam1().getName())),
      PracticeConfig.settings().getMatchSettings().getTeams().getTeam1().getColor(),
      AstralPractice.getMiniMessage()
          .deserialize(
              StringUtil.legacyToMiniMessage(
                  PracticeConfig.settings()
                      .getMatchSettings()
                      .getTeams()
                      .getTeam1()
                      .getNametag()
                      .getPrefix())),
      NamedTextColor.NAMES.valueOr(
          PracticeConfig.settings()
              .getMatchSettings()
              .getTeams()
              .getTeam1()
              .getNametag()
              .getNameColor()
              .toLowerCase(),
          NamedTextColor.WHITE),
      AstralPractice.getMiniMessage()
          .deserialize(
              StringUtil.legacyToMiniMessage(
                  PracticeConfig.settings()
                      .getMatchSettings()
                      .getTeams()
                      .getTeam1()
                      .getNametag()
                      .getSuffix()))),
  TEAM2(
      AstralPractice.getMiniMessage()
          .deserialize(
              StringUtil.legacyToMiniMessage(
                  PracticeConfig.settings().getMatchSettings().getTeams().getTeam2().getName())),
      PracticeConfig.settings().getMatchSettings().getTeams().getTeam2().getColor(),
      AstralPractice.getMiniMessage()
          .deserialize(
              StringUtil.legacyToMiniMessage(
                  PracticeConfig.settings()
                      .getMatchSettings()
                      .getTeams()
                      .getTeam2()
                      .getNametag()
                      .getPrefix())),
      NamedTextColor.NAMES.valueOr(
          PracticeConfig.settings()
              .getMatchSettings()
              .getTeams()
              .getTeam2()
              .getNametag()
              .getNameColor()
              .toLowerCase(),
          NamedTextColor.WHITE),
      AstralPractice.getMiniMessage()
          .deserialize(
              StringUtil.legacyToMiniMessage(
                  PracticeConfig.settings()
                      .getMatchSettings()
                      .getTeams()
                      .getTeam2()
                      .getNametag()
                      .getSuffix()))),
  FFA(
      Component.empty(),
      PracticeConfig.settings().getMatchSettings().getTeams().getFfa().getColor(),
      Component.empty(),
      NamedTextColor.WHITE,
      Component.empty());

  private final Component name;
  private final String color;

  @Getter private final Component prefix;
  @Getter private final NamedTextColor nameColor;
  @Getter private final Component suffix;

  TeamEnum(
      Component name, String color, Component prefix, NamedTextColor nameColor, Component suffix) {
    this.name =
        AstralPractice.getMiniMessage()
            .deserialize(StringUtil.legacyToMiniMessage(color))
            .append(name);
    this.color = color;

    this.prefix = prefix;
    this.nameColor = nameColor;
    this.suffix = suffix;
  }

  public Component getNameComponent() {
    return name;
  }

  public Component getColor() {
    return AstralPractice.getMiniMessage().deserialize(StringUtil.legacyToMiniMessage(color));
  }

  public String getNameMm() {
    return AstralPractice.getMiniMessage().serialize(name);
  }

  public String getColorMm() {
    return color;
  }
}

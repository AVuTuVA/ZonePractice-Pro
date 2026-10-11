package dev.lokspel.practice.config.playerkit;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
public class PlayerKitConfig {
  @JsonProperty("GUI")
  private Gui gui = new Gui();

  @JsonProperty("DISABLED-ENCHANTS")
  private Map<String, List<String>> disabledEnchants = defaultDisabledEnchants();

  @JsonProperty("DEFAULT-SETTINGS")
  private DefaultSettings defaultSettings = new DefaultSettings();

  @Comment("Options: DUEL, PARTY_FFA, PARTY_SPLIT, PARTY_VS_PARTY")
  @JsonProperty("MATCH-TYPES")
  private List<String> matchTypes = List.of("DUEL", "PARTY_FFA", "PARTY_SPLIT", "PARTY_VS_PARTY");

  private static Map<String, List<String>> defaultDisabledEnchants() {
    Map<String, List<String>> values = new LinkedHashMap<>();
    values.put("_CHESTPLATE", List.of("THORNS", "BINDING_CURSE", "VANISHING_CURSE"));
    values.put("IRON_SWORD", List.of("SHARPNESS"));

    return values;
  }

  @Getter
  public static class DefaultSettings {
    @JsonProperty("NAME")
    private String name = "Kit %id%";

    @JsonProperty("REGENERATION")
    private boolean regeneration = true;

    @JsonProperty("HUNGER")
    private boolean hunger = true;

    @JsonProperty("BUILD")
    private boolean build = true;

    @JsonProperty("ROUNDS")
    private int rounds = 1;

    @JsonProperty("HITDELAY")
    private int hitdelay = 20;

    @JsonProperty("EP_COOLDOWN")
    private int epCooldown = 13;

    @JsonProperty("GA_COOLDOWN")
    private int gaCooldown = 5;

    @JsonProperty("WIND_CHARGE_COOLDOWN")
    private int windChargeCooldown = 0;

    @JsonProperty("HEALTH_BELOW_NAME")
    private boolean healthBelowName = false;
  }
}

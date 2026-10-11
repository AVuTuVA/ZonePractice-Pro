package dev.lokspel.practice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

/**
 * An item as the configuration files describe it. Every key is optional; a missing key keeps the
 * item's default. Sections with more keys than an item extend this class.
 *
 * <p>Defaults are written fluently: {@code ItemConfig.of("ARROW").name("<gray>Back")}.
 */
@Getter
public class ItemConfig {
  @JsonProperty("NAME")
  private String name;

  @JsonProperty("MATERIAL")
  private String material;

  @JsonProperty("AMOUNT")
  private Integer amount;

  @JsonProperty("DURABILITY")
  private Integer durability;

  @JsonProperty("LORE")
  private List<String> lore;

  @JsonProperty("FLAGS")
  private List<String> flags;

  @JsonProperty("ENCHANTMENTS")
  private List<String> enchantments;

  public static ItemConfig of(String material) {
    return new ItemConfig().material(material);
  }

  public ItemConfig name(String name) {
    this.name = name;
    return this;
  }

  public ItemConfig material(String material) {
    this.material = material;
    return this;
  }

  public ItemConfig amount(int amount) {
    this.amount = amount;
    return this;
  }

  public ItemConfig durability(int durability) {
    this.durability = durability;
    return this;
  }

  public ItemConfig lore(String... lore) {
    this.lore = List.of(lore);
    return this;
  }

  public ItemConfig flags(String... flags) {
    this.flags = List.of(flags);
    return this;
  }

  public ItemConfig enchantments(String... enchantments) {
    this.enchantments = List.of(enchantments);
    return this;
  }
}

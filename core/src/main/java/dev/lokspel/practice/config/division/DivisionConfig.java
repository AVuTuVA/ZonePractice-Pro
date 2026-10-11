package dev.lokspel.practice.config.division;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import me.whereareiam.configura.annotation.merge.Merge;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;
import me.whereareiam.configura.type.merge.MissingEntries;

@Getter
public class DivisionConfig {
  @Comment({
    "You can only create maximum 28 divisions. Anything above that the server is going to ignore.",
    "Placeholders: %division%, %division_short%, %division_weight%",
    "if both are false wins will be set to true in the backend"
  })
  @JsonProperty("REQUIRE")
  private Require require = new Require();

  @Merge(missingEntries = MissingEntries.OMIT)
  @JsonProperty("DIVISIONS")
  private Map<String, Division> divisions = defaultDivisions();

  private static Map<String, Division> defaultDivisions() {
    Map<String, Division> values = new LinkedHashMap<>();
    values.put("RECRUIT", Division.recruit());
    values.put("SILVER1", Division.silver1());
    values.put("SILVER2", Division.silver2());
    values.put("SILVER3", Division.silver3());
    values.put("SILVER4", Division.silver4());
    values.put("SILVER5", Division.silver5());
    values.put("GOLD1", Division.gold1());
    values.put("GOLD2", Division.gold2());
    values.put("GOLD3", Division.gold3());
    values.put("GOLD4", Division.gold4());
    values.put("GOLD5", Division.gold5());
    values.put("PLATINUM1", Division.platinum1());
    values.put("PLATINUM2", Division.platinum2());
    values.put("PLATINUM3", Division.platinum3());
    values.put("EMERALD1", Division.emerald1());
    values.put("EMERALD2", Division.emerald2());
    values.put("EMERALD3", Division.emerald3());
    values.put("DIAMOND1", Division.diamond1());
    values.put("DIAMOND2", Division.diamond2());
    values.put("DIAMOND3", Division.diamond3());
    values.put("SAPPHIRE1", Division.sapphire1());
    values.put("SAPPHIRE2", Division.sapphire2());
    values.put("SAPPHIRE3", Division.sapphire3());
    values.put("MASTER", Division.master());

    return values;
  }

  @Getter
  public static class Require {
    @JsonProperty("WINS")
    private boolean wins = true;

    @Comment("depends on the global elo")
    @JsonProperty("ELO")
    private boolean elo = true;
  }

  @Getter
  public static class Division {
    @JsonProperty("DATA")
    private Data data;

    @JsonProperty("REQUIREMENTS")
    private Requirements requirements;

    static Division recruit() {
      Division value = new Division();
      value.data = Data.recruit();
      value.requirements = Requirements.recruit();

      return value;
    }

    static Division silver1() {
      Division value = new Division();
      value.data = Data.silver1();
      value.requirements = Requirements.silver1();

      return value;
    }

    static Division silver2() {
      Division value = new Division();
      value.data = Data.silver2();
      value.requirements = Requirements.silver2();

      return value;
    }

    static Division silver3() {
      Division value = new Division();
      value.data = Data.silver3();
      value.requirements = Requirements.silver3();

      return value;
    }

    static Division silver4() {
      Division value = new Division();
      value.data = Data.silver4();
      value.requirements = Requirements.silver4();

      return value;
    }

    static Division silver5() {
      Division value = new Division();
      value.data = Data.silver5();
      value.requirements = Requirements.silver5();

      return value;
    }

    static Division gold1() {
      Division value = new Division();
      value.data = Data.gold1();
      value.requirements = Requirements.gold1();

      return value;
    }

    static Division gold2() {
      Division value = new Division();
      value.data = Data.gold2();
      value.requirements = Requirements.gold2();

      return value;
    }

    static Division gold3() {
      Division value = new Division();
      value.data = Data.gold3();
      value.requirements = Requirements.gold3();

      return value;
    }

    static Division gold4() {
      Division value = new Division();
      value.data = Data.gold4();
      value.requirements = Requirements.gold4();

      return value;
    }

    static Division gold5() {
      Division value = new Division();
      value.data = Data.gold5();
      value.requirements = Requirements.gold5();

      return value;
    }

    static Division platinum1() {
      Division value = new Division();
      value.data = Data.platinum1();
      value.requirements = Requirements.platinum1();

      return value;
    }

    static Division platinum2() {
      Division value = new Division();
      value.data = Data.platinum2();
      value.requirements = Requirements.platinum2();

      return value;
    }

    static Division platinum3() {
      Division value = new Division();
      value.data = Data.platinum3();
      value.requirements = Requirements.platinum3();

      return value;
    }

    static Division emerald1() {
      Division value = new Division();
      value.data = Data.emerald1();
      value.requirements = Requirements.emerald1();

      return value;
    }

    static Division emerald2() {
      Division value = new Division();
      value.data = Data.emerald2();
      value.requirements = Requirements.emerald2();

      return value;
    }

    static Division emerald3() {
      Division value = new Division();
      value.data = Data.emerald3();
      value.requirements = Requirements.emerald3();

      return value;
    }

    static Division diamond1() {
      Division value = new Division();
      value.data = Data.diamond1();
      value.requirements = Requirements.diamond1();

      return value;
    }

    static Division diamond2() {
      Division value = new Division();
      value.data = Data.diamond2();
      value.requirements = Requirements.diamond2();

      return value;
    }

    static Division diamond3() {
      Division value = new Division();
      value.data = Data.diamond3();
      value.requirements = Requirements.diamond3();

      return value;
    }

    static Division sapphire1() {
      Division value = new Division();
      value.data = Data.sapphire1();
      value.requirements = Requirements.sapphire1();

      return value;
    }

    static Division sapphire2() {
      Division value = new Division();
      value.data = Data.sapphire2();
      value.requirements = Requirements.sapphire2();

      return value;
    }

    static Division sapphire3() {
      Division value = new Division();
      value.data = Data.sapphire3();
      value.requirements = Requirements.sapphire3();

      return value;
    }

    static Division master() {
      Division value = new Division();
      value.data = Data.master();
      value.requirements = Requirements.master();

      return value;
    }

    @Getter
    public static class Data {
      @JsonProperty("NAME")
      private Name name;

      @JsonProperty("COLOR")
      private String color;

      @JsonProperty("ICON-MATERIAL")
      private String iconMaterial;

      @JsonProperty("WEIGHT")
      private Integer weight;

      @Comment("Optional damage value of the icon item.")
      @JsonProperty("ICON-DAMAGE")
      private Integer iconDamage;

      static Data recruit() {
        Data value = new Data();
        value.name = Name.recruit();
        value.color = "<gray>";
        value.iconMaterial = "IRON_INGOT";
        value.weight = 1;
        value.iconDamage = 0;

        return value;
      }

      static Data silver1() {
        Data value = new Data();
        value.name = Name.silver1();
        value.color = "<gray>";
        value.iconMaterial = "IRON_INGOT";

        return value;
      }

      static Data silver2() {
        Data value = new Data();
        value.name = Name.silver2();
        value.color = "<gray>";
        value.iconMaterial = "IRON_INGOT";

        return value;
      }

      static Data silver3() {
        Data value = new Data();
        value.name = Name.silver3();
        value.color = "<gray>";
        value.iconMaterial = "IRON_INGOT";

        return value;
      }

      static Data silver4() {
        Data value = new Data();
        value.name = Name.silver4();
        value.color = "<gray>";
        value.iconMaterial = "IRON_INGOT";

        return value;
      }

      static Data silver5() {
        Data value = new Data();
        value.name = Name.silver5();
        value.color = "<gray>";
        value.iconMaterial = "IRON_INGOT";

        return value;
      }

      static Data gold1() {
        Data value = new Data();
        value.name = Name.gold1();
        value.color = "<gold>";
        value.iconMaterial = "GOLD_INGOT";

        return value;
      }

      static Data gold2() {
        Data value = new Data();
        value.name = Name.gold2();
        value.color = "<gold>";
        value.iconMaterial = "GOLD_INGOT";

        return value;
      }

      static Data gold3() {
        Data value = new Data();
        value.name = Name.gold3();
        value.color = "<gold>";
        value.iconMaterial = "GOLD_INGOT";

        return value;
      }

      static Data gold4() {
        Data value = new Data();
        value.name = Name.gold4();
        value.color = "<gold>";
        value.iconMaterial = "GOLD_INGOT";

        return value;
      }

      static Data gold5() {
        Data value = new Data();
        value.name = Name.gold5();
        value.color = "<gold>";
        value.iconMaterial = "GOLD_INGOT";

        return value;
      }

      static Data platinum1() {
        Data value = new Data();
        value.name = Name.platinum1();
        value.color = "<dark_aqua>";
        value.iconMaterial = "QUARTZ";

        return value;
      }

      static Data platinum2() {
        Data value = new Data();
        value.name = Name.platinum2();
        value.color = "<dark_aqua>";
        value.iconMaterial = "QUARTZ";

        return value;
      }

      static Data platinum3() {
        Data value = new Data();
        value.name = Name.platinum3();
        value.color = "<dark_aqua>";
        value.iconMaterial = "QUARTZ";

        return value;
      }

      static Data emerald1() {
        Data value = new Data();
        value.name = Name.emerald1();
        value.color = "<green>";
        value.iconMaterial = "EMERALD";

        return value;
      }

      static Data emerald2() {
        Data value = new Data();
        value.name = Name.emerald2();
        value.color = "<green>";
        value.iconMaterial = "EMERALD";

        return value;
      }

      static Data emerald3() {
        Data value = new Data();
        value.name = Name.emerald3();
        value.color = "<green>";
        value.iconMaterial = "EMERALD";

        return value;
      }

      static Data diamond1() {
        Data value = new Data();
        value.name = Name.diamond1();
        value.color = "<aqua>";
        value.iconMaterial = "DIAMOND";

        return value;
      }

      static Data diamond2() {
        Data value = new Data();
        value.name = Name.diamond2();
        value.color = "<aqua>";
        value.iconMaterial = "DIAMOND";

        return value;
      }

      static Data diamond3() {
        Data value = new Data();
        value.name = Name.diamond3();
        value.color = "<aqua>";
        value.iconMaterial = "DIAMOND";

        return value;
      }

      static Data sapphire1() {
        Data value = new Data();
        value.name = Name.sapphire1();
        value.color = "<blue>";
        value.iconMaterial = "LAPIS_LAZULI";

        return value;
      }

      static Data sapphire2() {
        Data value = new Data();
        value.name = Name.sapphire2();
        value.color = "<blue>";
        value.iconMaterial = "LAPIS_LAZULI";

        return value;
      }

      static Data sapphire3() {
        Data value = new Data();
        value.name = Name.sapphire3();
        value.color = "<blue>";
        value.iconMaterial = "LAPIS_LAZULI";

        return value;
      }

      static Data master() {
        Data value = new Data();
        value.name = Name.master();
        value.color = "<red>";
        value.iconMaterial = "BLAZE_POWDER";

        return value;
      }

      @Getter
      public static class Name {
        @JsonProperty("FULL")
        private String full;

        @JsonProperty("SHORT")
        private String shortValue;

        static Name recruit() {
          Name value = new Name();
          value.full = "%color%Recruit";
          value.shortValue = "%color%R";

          return value;
        }

        static Name silver1() {
          Name value = new Name();
          value.full = "%color%Silver I";
          value.shortValue = "%color%I";

          return value;
        }

        static Name silver2() {
          Name value = new Name();
          value.full = "%color%Silver II";
          value.shortValue = "%color%II";

          return value;
        }

        static Name silver3() {
          Name value = new Name();
          value.full = "%color%Silver III";
          value.shortValue = "%color%III";

          return value;
        }

        static Name silver4() {
          Name value = new Name();
          value.full = "%color%Silver IV";
          value.shortValue = "%color%IV";

          return value;
        }

        static Name silver5() {
          Name value = new Name();
          value.full = "%color%Silver V";
          value.shortValue = "%color%V";

          return value;
        }

        static Name gold1() {
          Name value = new Name();
          value.full = "%color%Gold I";
          value.shortValue = "%color%I";

          return value;
        }

        static Name gold2() {
          Name value = new Name();
          value.full = "%color%Gold II";
          value.shortValue = "%color%II";

          return value;
        }

        static Name gold3() {
          Name value = new Name();
          value.full = "%color%Gold III";
          value.shortValue = "%color%III";

          return value;
        }

        static Name gold4() {
          Name value = new Name();
          value.full = "%color%Gold IV";
          value.shortValue = "%color%IV";

          return value;
        }

        static Name gold5() {
          Name value = new Name();
          value.full = "%color%Gold V";
          value.shortValue = "%color%V";

          return value;
        }

        static Name platinum1() {
          Name value = new Name();
          value.full = "%color%Platinum I";
          value.shortValue = "%color%I";

          return value;
        }

        static Name platinum2() {
          Name value = new Name();
          value.full = "%color%Platinum II";
          value.shortValue = "%color%II";

          return value;
        }

        static Name platinum3() {
          Name value = new Name();
          value.full = "%color%Platinum III";
          value.shortValue = "%color%III";

          return value;
        }

        static Name emerald1() {
          Name value = new Name();
          value.full = "%color%Emerald I";
          value.shortValue = "%color%I";

          return value;
        }

        static Name emerald2() {
          Name value = new Name();
          value.full = "%color%Emerald II";
          value.shortValue = "%color%II";

          return value;
        }

        static Name emerald3() {
          Name value = new Name();
          value.full = "%color%Emerald III";
          value.shortValue = "%color%III";

          return value;
        }

        static Name diamond1() {
          Name value = new Name();
          value.full = "%color%Diamond I";
          value.shortValue = "%color%I";

          return value;
        }

        static Name diamond2() {
          Name value = new Name();
          value.full = "%color%Diamond II";
          value.shortValue = "%color%II";

          return value;
        }

        static Name diamond3() {
          Name value = new Name();
          value.full = "%color%Diamond III";
          value.shortValue = "%color%III";

          return value;
        }

        static Name sapphire1() {
          Name value = new Name();
          value.full = "%color%Sapphire I";
          value.shortValue = "%color%I";

          return value;
        }

        static Name sapphire2() {
          Name value = new Name();
          value.full = "%color%Sapphire II";
          value.shortValue = "%color%II";

          return value;
        }

        static Name sapphire3() {
          Name value = new Name();
          value.full = "%color%Sapphire III";
          value.shortValue = "%color%III";

          return value;
        }

        static Name master() {
          Name value = new Name();
          value.full = "%color%Master";
          value.shortValue = "%color%M";

          return value;
        }
      }
    }

    @Getter
    public static class Requirements {
      @JsonProperty("EXPERIENCE")
      private Integer experience;

      @JsonProperty("WINS")
      private Integer wins;

      @JsonProperty("ELO")
      private Integer elo;

      static Requirements recruit() {
        Requirements value = new Requirements();
        value.experience = 0;
        value.wins = 0;
        value.elo = 0;

        return value;
      }

      static Requirements silver1() {
        Requirements value = new Requirements();
        value.experience = 1000;
        value.wins = 10;
        value.elo = 1050;

        return value;
      }

      static Requirements silver2() {
        Requirements value = new Requirements();
        value.experience = 2000;
        value.wins = 20;
        value.elo = 1100;

        return value;
      }

      static Requirements silver3() {
        Requirements value = new Requirements();
        value.experience = 3000;
        value.wins = 30;
        value.elo = 1150;

        return value;
      }

      static Requirements silver4() {
        Requirements value = new Requirements();
        value.experience = 4000;
        value.wins = 40;
        value.elo = 1200;

        return value;
      }

      static Requirements silver5() {
        Requirements value = new Requirements();
        value.experience = 5000;
        value.wins = 50;
        value.elo = 1250;

        return value;
      }

      static Requirements gold1() {
        Requirements value = new Requirements();
        value.experience = 7000;
        value.wins = 70;
        value.elo = 1300;

        return value;
      }

      static Requirements gold2() {
        Requirements value = new Requirements();
        value.experience = 9000;
        value.wins = 90;
        value.elo = 1350;

        return value;
      }

      static Requirements gold3() {
        Requirements value = new Requirements();
        value.experience = 11000;
        value.wins = 110;
        value.elo = 1400;

        return value;
      }

      static Requirements gold4() {
        Requirements value = new Requirements();
        value.experience = 13000;
        value.wins = 130;
        value.elo = 1450;

        return value;
      }

      static Requirements gold5() {
        Requirements value = new Requirements();
        value.experience = 15000;
        value.wins = 150;
        value.elo = 1500;

        return value;
      }

      static Requirements platinum1() {
        Requirements value = new Requirements();
        value.experience = 18000;
        value.wins = 180;
        value.elo = 1550;

        return value;
      }

      static Requirements platinum2() {
        Requirements value = new Requirements();
        value.experience = 21000;
        value.wins = 210;
        value.elo = 1600;

        return value;
      }

      static Requirements platinum3() {
        Requirements value = new Requirements();
        value.experience = 24000;
        value.wins = 240;
        value.elo = 1650;

        return value;
      }

      static Requirements emerald1() {
        Requirements value = new Requirements();
        value.experience = 28000;
        value.wins = 280;
        value.elo = 1700;

        return value;
      }

      static Requirements emerald2() {
        Requirements value = new Requirements();
        value.experience = 32000;
        value.wins = 320;
        value.elo = 1750;

        return value;
      }

      static Requirements emerald3() {
        Requirements value = new Requirements();
        value.experience = 36000;
        value.wins = 360;
        value.elo = 1800;

        return value;
      }

      static Requirements diamond1() {
        Requirements value = new Requirements();
        value.experience = 41000;
        value.wins = 410;
        value.elo = 1850;

        return value;
      }

      static Requirements diamond2() {
        Requirements value = new Requirements();
        value.experience = 46000;
        value.wins = 460;
        value.elo = 1900;

        return value;
      }

      static Requirements diamond3() {
        Requirements value = new Requirements();
        value.experience = 51000;
        value.wins = 510;
        value.elo = 1950;

        return value;
      }

      static Requirements sapphire1() {
        Requirements value = new Requirements();
        value.experience = 57000;
        value.wins = 570;
        value.elo = 2000;

        return value;
      }

      static Requirements sapphire2() {
        Requirements value = new Requirements();
        value.experience = 63000;
        value.wins = 630;
        value.elo = 2050;

        return value;
      }

      static Requirements sapphire3() {
        Requirements value = new Requirements();
        value.experience = 69000;
        value.wins = 690;
        value.elo = 2100;

        return value;
      }

      static Requirements master() {
        Requirements value = new Requirements();
        value.experience = 100000;
        value.wins = 1000;
        value.elo = 2500;

        return value;
      }
    }
  }
}

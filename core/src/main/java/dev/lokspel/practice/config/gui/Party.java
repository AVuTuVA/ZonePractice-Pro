package dev.lokspel.practice.config.gui;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokspel.practice.config.ItemConfig;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class Party {
  @JsonProperty("PARTY-GAMES")
  private PartyGames partyGames = new PartyGames();

  @JsonProperty("PARTY-SETTINGS")
  private PartySettings partySettings = new PartySettings();

  @JsonProperty("OTHER-PARTIES")
  private OtherParties otherParties = new OtherParties();

  @JsonProperty("PUBLIC-PARTIES")
  private PublicParties publicParties = new PublicParties();

  @JsonProperty("PARTY-SPLIT")
  private PartySplit partySplit = new PartySplit();

  @Getter
  public static class PartyGames {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Party Games";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("FILLER-ITEM")
      private ItemConfig fillerItem = ItemConfig.of("BLACK_STAINED_GLASS_PANE");

      @JsonProperty("PARTY_SPLIT")
      private ItemConfig partySplit = ItemConfig.of("FIREWORK_STAR").name("<aqua>Split Fight");

      @JsonProperty("PARTY_FFA")
      private ItemConfig partyFfa = ItemConfig.of("SLIME_BALL").name("<green>Party FFA");
    }
  }

  @Getter
  public static class PartySettings {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Party Settings";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("FILLER-ITEM")
      private ItemConfig fillerItem = ItemConfig.of("BLACK_STAINED_GLASS_PANE");

      @JsonProperty("PLAYER-LIMIT")
      private ItemConfig playerLimit =
          ItemConfig.of("PLAYER_HEAD")
              .name("<yellow>Player Limit: <red>%limit%")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>That many player can join",
                  "<gray>your party maximum.",
                  "",
                  "<green><bold>LEFT-CLICK <green>to decrease the limit.",
                  "<aqua><bold>RIGHT-CLICK <aqua>to increase the limit.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("ACCESS-MODIFIERS")
      private AccessModifiers accessModifiers = new AccessModifiers();

      @JsonProperty("BROADCAST")
      private Broadcast broadcast = new Broadcast();

      @JsonProperty("ALL-INVITE")
      private AllInvite allInvite = new AllInvite();

      @JsonProperty("DUEL-REQUESTS")
      private DuelRequests duelRequests = new DuelRequests();

      @JsonProperty("PARTY-CHAT")
      private PartyChat partyChat = new PartyChat();

      @Getter
      public static class AccessModifiers {
        @JsonProperty("PUBLIC")
        private ItemConfig publicValue =
            ItemConfig.of("CHEST")
                .name("<yellow>Public: <green>True")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>The party is <green>public",
                    "<gray>anyone can join.",
                    "",
                    "<yellow><bold>Click here <gray>to change the status.",
                    "<dark_gray><st>------------------------");

        @JsonProperty("PRIVATE")
        private ItemConfig privateValue =
            ItemConfig.of("CHEST")
                .name("<yellow>Public: <red>False")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>The party is <red>private <gray>only",
                    "<gray>players with invite can join.",
                    "",
                    "<yellow><bold>Click here <gray>to change the status.",
                    "<dark_gray><st>------------------------");
      }

      @Getter
      public static class Broadcast {
        @JsonProperty("ENABLED")
        private ItemConfig enabled =
            ItemConfig.of("PAPER")
                .name("<yellow>Broadcast Party: <green>On")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>The party is broadcasting",
                    "<gray>to the players of the server.",
                    "",
                    "<yellow><bold>Click here <gray>to <red>turn off <gray>broadcasting.",
                    "<dark_gray><st>------------------------");

        @JsonProperty("DISABLED")
        private ItemConfig disabled =
            ItemConfig.of("PAPER")
                .name("<yellow>Broadcast Party: <red>Off")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>The party isn't broadcasting.",
                    "",
                    "<yellow><bold>Click here <gray>to <green>turn on <gray>broadcasting.",
                    "<dark_gray><st>------------------------");
      }

      @Getter
      public static class AllInvite {
        @JsonProperty("ENABLED")
        private ItemConfig enabled =
            ItemConfig.of("NAME_TAG")
                .name("<yellow>All Invite: <green>True")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Everyone can invite players",
                    "<gray>to the party.",
                    "",
                    "<yellow><bold>Click here <gray>to change the status.",
                    "<dark_gray><st>------------------------");

        @JsonProperty("DISABLED")
        private ItemConfig disabled =
            ItemConfig.of("NAME_TAG")
                .name("<yellow>All Invite: <red>False")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Only the party leader can invite",
                    "<gray>players to the party.",
                    "",
                    "<yellow><bold>Click here <gray>to change the status.",
                    "<dark_gray><st>------------------------");
      }

      @Getter
      public static class DuelRequests {
        @JsonProperty("ENABLED")
        private ItemConfig enabled =
            ItemConfig.of("DIAMOND_SWORD")
                .name("<yellow>Duel Requests: <green>True")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Players can send duel requests",
                    "<gray>to the party.",
                    "",
                    "<yellow><bold>Click here <gray>to change the status.",
                    "<dark_gray><st>------------------------");

        @JsonProperty("DISABLED")
        private ItemConfig disabled =
            ItemConfig.of("DIAMOND_SWORD")
                .name("<yellow>Duel Requests: <red>False")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Players can't send duel requests",
                    "<gray>to the party.",
                    "",
                    "<yellow><bold>Click here <gray>to change the status.",
                    "<dark_gray><st>------------------------");
      }

      @Getter
      public static class PartyChat {
        @JsonProperty("ENABLED")
        private ItemConfig enabled =
            ItemConfig.of("NOTE_BLOCK")
                .name("<yellow>Party Chat: <green>On")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Party members can use",
                    "<gray>the party chat.",
                    "",
                    "<yellow><bold>Click here <gray>to <red>turn off <gray>party chat.",
                    "<dark_gray><st>------------------------");

        @JsonProperty("DISABLED")
        private ItemConfig disabled =
            ItemConfig.of("NOTE_BLOCK")
                .name("<yellow>Party Chat: <red>Off")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Party members can't use",
                    "<gray>the party chat.",
                    "",
                    "<yellow><bold>Click here <gray>to <green>turn on <gray>party chat.",
                    "<dark_gray><st>------------------------");
      }
    }
  }

  @Getter
  public static class OtherParties {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Other Parties";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("FILLER-ITEM")
      private ItemConfig fillerItem = ItemConfig.of("BLACK_STAINED_GLASS_PANE");

      @JsonProperty("PARTY-ITEM")
      private PartyItem partyItem = new PartyItem();

      @Getter
      public static class PartyItem {
        @JsonProperty("NAME")
        private String name = "<aqua>%leader%'s Party <gray>(<white>%partySize%<gray>)";

        @JsonProperty("LORE")
        private Lore lore = new Lore();

        @Getter
        public static class Lore {
          @JsonProperty("MEMBER-FORMAT")
          private String memberFormat = " <gray>- <aqua>%player%";

          @JsonProperty("LORE")
          private List<String> lore =
              List.of(
                  "<gray><st>------------------------",
                  "%members%",
                  "",
                  "<white><bold>Click here <white>to duel this party.",
                  "<gray><st>------------------------");
        }
      }
    }
  }

  @Getter
  public static class PublicParties {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Public Parties - Page %page%";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("FILLER-ITEM")
      private ItemConfig fillerItem = ItemConfig.of("BLACK_STAINED_GLASS_PANE");

      @JsonProperty("PAGE-LEFT")
      private ItemConfig pageLeft = ItemConfig.of("ARROW").name("<gold>Go to Page %page%");

      @JsonProperty("PAGE-RIGHT")
      private ItemConfig pageRight = ItemConfig.of("ARROW").name("<gold>Go to Page %page%");

      @JsonProperty("PARTY-ITEM")
      private PartyItem partyItem = new PartyItem();

      @Getter
      public static class PartyItem {
        @JsonProperty("NAME")
        private String name =
            "<green>%leader%'s Party <gray>(<white>%partySize%<gray>/<white>%maxPartySize%<gray>)";

        @JsonProperty("LORE")
        private Lore lore = new Lore();

        @Getter
        public static class Lore {
          @JsonProperty("MEMBER-FORMAT")
          private String memberFormat = " <gray>- <aqua>%player%";

          @JsonProperty("LORE")
          private List<String> lore =
              List.of(
                  "<gray><st>------------------------",
                  "%members%",
                  "",
                  "<gray>Players: <white>%size%<gray>/<white>%maxsize%",
                  "",
                  "<green><bold>Click here <green>to join this party.",
                  "<gray><st>------------------------");
        }
      }
    }
  }

  @Getter
  public static class PartySplit {
    @JsonProperty("TITLE")
    private String title = "<dark_gray>Party Split";

    @JsonProperty("ICONS")
    private Icons icons = new Icons();

    @Getter
    public static class Icons {
      @JsonProperty("FILLER-ITEM")
      private ItemConfig fillerItem = ItemConfig.of("BLACK_STAINED_GLASS_PANE");

      @JsonProperty("PLAYER-ITEM")
      private Map<String, ItemConfig> playerItem = defaultPlayerItem();

      @JsonProperty("RANDOM-SPLIT")
      private ItemConfig randomSplit =
          ItemConfig.of("NAME_TAG")
              .name("<yellow>Random Split")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Randomly assigns every player",
                  "<gray>to a team.",
                  "",
                  "<yellow><bold>Click here <gray>to randomize.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("START-MATCH")
      private ItemConfig startMatch =
          ItemConfig.of("EMERALD")
              .name("<green><bold>Start Match")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Starts the match with the",
                  "<gray>current team assignments.",
                  "",
                  "<green><bold>Click here <green>to start.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("TEAM-SETUP")
      private ItemConfig teamSetup =
          ItemConfig.of("WRITABLE_BOOK")
              .name("<aqua><bold>Setup Teams")
              .lore(
                  "<dark_gray><st>------------------------",
                  "<gray>Opens the team setup to",
                  "<gray>assign players to the",
                  "<blue>Blue <gray>and <red>Red <gray>teams.",
                  "",
                  "<aqua><bold>Click here <gray>to continue.",
                  "<dark_gray><st>------------------------");

      @JsonProperty("BACK-TO-SELECTOR")
      private ItemConfig backToSelector = ItemConfig.of("ARROW").name("<red><bold>Back");

      private static Map<String, ItemConfig> defaultPlayerItem() {
        Map<String, ItemConfig> values = new LinkedHashMap<>();
        values.put(
            "UNASSIGNED",
            new ItemConfig()
                .name("<white>%player%")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Team: <white>Not selected",
                    "<blue>L <gray>Blue     <red>R <gray>Red",
                    "<dark_gray>Shift+L <gray>Spec     Shift+R <gray>None",
                    "<dark_gray><st>------------------------"));
        values.put(
            "BLUE",
            new ItemConfig()
                .name("<aqua>%player%")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Team: <blue>Blue",
                    "<blue>L <gray>Blue     <red>R <gray>Red",
                    "<dark_gray>Shift+L <gray>Spec     Shift+R <gray>None",
                    "<dark_gray><st>------------------------"));
        values.put(
            "RED",
            new ItemConfig()
                .name("<red>%player%")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Team: <red>Red",
                    "<blue>L <gray>Blue     <red>R <gray>Red",
                    "<dark_gray>Shift+L <gray>Spec     Shift+R <gray>None",
                    "<dark_gray><st>------------------------"));
        values.put(
            "SPECTATOR",
            new ItemConfig()
                .name("<gray>%player%")
                .lore(
                    "<dark_gray><st>------------------------",
                    "<gray>Team: <dark_gray>Spectator",
                    "<blue>L <gray>Blue     <red>R <gray>Red",
                    "<dark_gray>Shift+L <gray>Spec     Shift+R <gray>None",
                    "<dark_gray><st>------------------------"));

        return values;
      }
    }
  }
}

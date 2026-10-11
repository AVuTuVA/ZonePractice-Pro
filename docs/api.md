<img width="2000" height="1000" alt="image(6)" src="https://i.imgur.com/ydTpj5d.png" />

---

# API

AstralPractice provides an API module (`AstralPractice-Api`) allowing other plugins to query player states, manage matches, inspect statistics, and listen to custom events.

## Dependency Setup

### Gradle (Groovy)

```groovy
repositories {
    mavenCentral()
    maven { url = uri('https://repo.papermc.io/repository/maven-public/') }
    maven { url = uri('https://jitpack.io') }
}

dependencies {
    compileOnly 'com.github.sylveya.AstralPractice:AstralPractice-Api:2.4.0'
}
```

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.sylveya.AstralPractice:AstralPractice-Api:2.4.0")
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.sylveya.AstralPractice</groupId>
        <artifactId>AstralPractice-Api</artifactId>
        <version>2.4.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### plugin.yml

Add AstralPractice as a dependency or soft-dependency:

```yaml
depend: [AstralPractice]
# or
softdepend: [AstralPractice]
```

---

## Accessing the API

The API instance can be accessed statically or retrieved from the Bukkit `ServicesManager`:

```java
// Static access
if (AstralPracticeApi.isAvailable()) {
    AstralPracticeApi api = AstralPracticeApi.getInstance();
}

// Or via Bukkit ServicesManager
AstralPracticeApi api = Bukkit.getServicesManager().load(AstralPracticeApi.class);
```

> [!TIP]
> If your plugin soft-depends on AstralPractice or loads alongside it, listen to `AstralPracticeReadyEvent` to ensure all ladders, arenas, and managers are fully initialized before making API calls.

---

## Player State & Session Queries

Query what a player is currently participating in or spectating:

```java
AstralPracticeApi api = AstralPracticeApi.getInstance();

// Current active match (returns null if not in a match)
Match match = api.getMatch(player);

// Spectated match (returns null if not spectating)
Match spectating = api.getSpectatingMatch(player);

// FFA game (returns null if not in FFA)
Ffa ffa = api.getFfa(player);

// Event instance (returns null if not in an event)
Event event = api.getEvent(player);

// Party instance (returns null if not in a party)
Party party = api.getParty(player);

// Waiting queue (returns null if not in a queue)
Queue queue = api.getQueue(player);
```

---

## Ladders & Statistics

```java
AstralPracticeApi api = AstralPracticeApi.getInstance();

// List all registered ladder names
List<String> ladders = api.getLadderNames();

// Player ladder kills and deaths (-1 if unavailable)
int kills = api.getLadderKills(player, "Nodebuff");
int deaths = api.getLadderDeaths(player, "Nodebuff");

// Elo rating for a ladder
int elo = api.getElo(player, "Nodebuff");

// Ladder wins and losses by weight class
int rankedWins = api.getLadderWins(player, "Nodebuff", WeightClass.RANKED);
int unrankedLosses = api.getLadderLosses(player, "Nodebuff", WeightClass.UNRANKED);

// Global statistics
int globalWins = api.getGlobalWins(player);
int exp = api.getExperience(player);
```

---

## Match Management & Daily Limits

```java
AstralPracticeApi api = AstralPracticeApi.getInstance();

// Check remaining daily matches
int rankedLeft = api.getPlayerRankedLeft(player);
int unrankedLeft = api.getPlayerUnRankedLeft(player);

// Add daily matches
api.addPlayerRanked(player, 5);
api.addPlayerUnRanked(player, 5);

// Reset daily matches
api.resetPlayerRanked(player);
api.resetPlayerUnRanked(player);

// Force-end a player's match with a MiniMessage-formatted reason
api.endMatch(player, "<red>Match ended by an admin");
```

---

## Divisions & Nametags

```java
AstralPracticeApi api = AstralPracticeApi.getInstance();

// Player division name (SHORT or FULL)
String divisionShort = api.getPlayerDivision(player, DivisionName.SHORT);
String divisionFull = api.getPlayerDivision(player, DivisionName.FULL);

// Nametag components (Adventure Component prefix/suffix, color, sort priority)
PlayerNametag nametag = api.getPlayerNametag(player);
Component prefix = nametag.getPrefix();
NamedTextColor nameColor = nametag.getColorOfName();
Component suffix = nametag.getSuffix();
int priority = nametag.getSortPriority();
```

---

## Interfaces

### `Match`

The `Match` interface (`dev.lokspel.api.interfaces.Match`) represents an ongoing duel or match:

| Method | Return Type | Description |
|---|---|---|
| `getMatchId()` | `String` | Unique match identifier |
| `getArenaName()` | `String` | Name of the arena (or `null`) |
| `isRanked()` | `boolean` | Whether the match is a ranked duel |
| `getDurationSeconds()` | `int` | Elapsed match duration in seconds |
| `getCurrentRoundNumber()` | `int` | Current round number |
| `getLadderName()` | `String` | Identifier of the match ladder |
| `getLadderDisplayName()` | `String` | Display name of the ladder |
| `getWinsNeeded()` | `int` | Number of round wins required to win the match |
| `getPlayers()` | `List<Player>` | Active players in the match |
| `getSpectators()` | `List<Player>` | Spectators watching the match |
| `addSpectator(...)` | `void` | Add a spectator to the match |
| `removeSpectator(Player)` | `void` | Remove a spectator from the match |
| `sendMessage(String, boolean)` | `void` | Broadcast a MiniMessage string to match players (and spectators if flag is true) |

### `Queue`

The `Queue` interface (`dev.lokspel.api.interfaces.Queue`) represents a player waiting in matchmaking:

| Method | Return Type | Description |
|---|---|---|
| `getPlayer()` | `Player` | Player in the queue |
| `getLadderName()` | `String` | Ladder the player queued for |
| `isRanked()` | `boolean` | Whether this is a ranked queue |
| `getDuration()` | `int` | Elapsed time waiting in queue (in seconds) |

### `Party`

The `Party` interface (`dev.lokspel.api.interfaces.Party`) represents a player group:

| Method | Return Type | Description |
|---|---|---|
| `getLeader()` | `Player` | Party leader |
| `getMembers()` | `List<Player>` | All party members (including leader) |

### `Event`

The `Event` interface (`dev.lokspel.api.interfaces.Event`) represents an active server event:

| Method | Return Type | Description |
|---|---|---|
| `getStarter()` | `Object` | Host or starter of the event |
| `getPlayers()` | `List<Player>` | Remaining participants |
| `getSpectators()` | `List<Player>` | Spectators watching the event |
| `getStartPlayerCount()` | `int` | Initial player count when the event began |
| `getWinner()` | `Player` | Winner of the event (or `null`) |

---

## Events

AstralPractice provides Bukkit events under `dev.lokspel.api.event.*` that your plugin can register and handle:

| Event | Package | Description | Cancellable |
|---|---|---|---|
| `AstralPracticeReadyEvent` | `dev.lokspel.api.event` | Fired when AstralPractice has finished initializing and the API is ready | No |
| `EloChangeEvent` | `dev.lokspel.api.event` | Fired when a player's Elo rating is modified (in a duel or by command) | No |
| `MatchStartEvent` | `dev.lokspel.api.event.match` | Fired when a match starts | Yes |
| `MatchEndEvent` | `dev.lokspel.api.event.match` | Fired when a match concludes | No |
| `MatchRoundStartEvent` | `dev.lokspel.api.event.match` | Fired at the beginning of a round in a match | No |
| `MatchRoundEndEvent` | `dev.lokspel.api.event.match` | Fired at the end of a round in a match | No |
| `QueueStartEvent` | `dev.lokspel.api.event.queue` | Fired when a player enters matchmaking queue | Yes |
| `QueueEndEvent` | `dev.lokspel.api.event.queue` | Fired when a player leaves or matches from a queue | No |
| `PartyCreateEvent` | `dev.lokspel.api.event` | Fired when a new party is created | No |
| `FfaRemovePlayerEvent` | `dev.lokspel.api.event` | Fired when a player leaves an FFA arena | No |
| `SpectateStartEvent` | `dev.lokspel.api.event.spectate.start` | Fired when a player starts spectating | Yes |
| `SpectateEndEvent` | `dev.lokspel.api.event.spectate.end` | Fired when a player stops spectating | No |

### Listening to `EloChangeEvent`

```java
import dev.lokspel.api.event.EloChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EloListener implements Listener {

    @EventHandler
    public void onEloChange(EloChangeEvent event) {
        Player player = event.getPlayer();
        String ladder = event.getLadderName();
        int oldElo = event.getOldElo();
        int newElo = event.getNewElo();
        int delta = event.getDelta(); // positive or negative change

        // Match may be null if Elo was modified outside a duel (e.g. by admin command)
        if (event.getMatch() != null) {
            String matchId = event.getMatch().getMatchId();
        }
    }
}
```

### Listening to `AstralPracticeReadyEvent`

```java
import dev.lokspel.api.AstralPracticeApi;
import dev.lokspel.api.event.AstralPracticeReadyEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReadyListener implements Listener {

    @EventHandler
    public void onReady(AstralPracticeReadyEvent event) {
        AstralPracticeApi api = AstralPracticeApi.getInstance();
        // Safe to query ladders, arenas, and stats
    }
}
```

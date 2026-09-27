# ZonePractice Pro

Minecraft PvP practice plugin for Paper

This project is a fork of [ZonePractice-Pro](https://github.com/ZoneDevelopement/ZonePractice-Pro)

## Features

- **Ladders** – 13 types (BASIC, BUILD, SUMO, TNT_SUMO, BOXING, PEARL_FIGHT, SPLEEF, SKYWARS, BEDWARS, FIREBALL_FIGHT, MLG_RUSH, BRIDGES, BATTLE_RUSH) with ranked/unranked queues, divisions and ELO
- **Arenas** – build/non-build logic, async arena copies (faster with FastAsyncWorldEdit)
- **Events** – LMS, OITC, TNT Tag, Brackets, Sumo, Splegg, Juggernaut, plus auto-scheduled events
- **FFA arenas** and **duels**
- **Parties** – Party FFA, Party Split, Party vs Party
- **Custom player kits** – full inventory editor (`/editor`) and kit sharing codes (`/copykit`)
- **Cosmetics** – armor trims, death effects, shield banner layouts, lobby movement items
- **Leaderboard holograms** – 8 leaderboard types (ELO, wins, kills, deaths, win/lose streaks)
- **Profiles** – match history, per-ladder and global statistics, optional MySQL/MariaDB storage
- **Presentation** – sidebars, holograms, nametags (TAB compatible), player hider
- **Staff tools** – setup GUI (`/setup`), staff mode (`/staff`), ELO/ranked management
- **Integration** – PlaceholderAPI, TAB, Multiverse-Core, My_Worlds, FastAsyncWorldEdit

## Requirements

- **Java 25**
- **Paper** on Minecraft **1.21.11** or **26.1 / 26.2 / 26.3** (the plugin refuses to enable on other versions)
- **PacketEvents 2.x** installed as a separate plugin — it is not shaded into the release jar. Get it from
  [retrooper/packetevents releases](https://github.com/retrooper/packetevents/releases) and place it in `plugins/`
  alongside ZonePractice Pro, then start the server (no hot-loading).

Optional: PlaceholderAPI, TAB, Multiverse-Core, My_Worlds, FastAsyncWorldEdit, LuckPerms.

## Building from source

Requires JDK 25 and Maven.

```bash
mvn clean package
```

The shaded, release-ready jar is written to `distribution/target/ZonePracticePro-<version>.jar`.

The `-Pauto-deploy` profile additionally copies the jar into the local `test_servers/` folders.

## Configuration

Default files are generated on first start in `plugins/ZonePracticePro/`:

`config.yml`, `language.yml`, `guis.yml`, `inventories.yml`, `sidebar.yml`, `groups.yml`, `divisions.yml`,
`playerkit.yml`, `backend.yml`, `ladders/*.yml`

- Templates live in `core/src/main/resources/`. Compare them against your server folder when upgrading — they are
  only written if missing, so new keys in a template will not overwrite your existing file.
- `config.yml` has a `VERSION` key used for the config migration pass. Review the diff before upgrading.
- MySQL/MariaDB is opt-in via the `MYSQL-DATABASE` section; profiles are saved on a configurable interval.
  The MariaDB JDBC driver and HikariCP are already shaded into the release jar.

## Commands & Permissions

The canonical command and permission list is `core/src/main/resources/plugin.yml`.

Commands: `/practice` (aliases `/prac`, `/zonepractice`, `/zoneprac`, `/zonep`), `/arena`, `/ladder`, `/ffa`,
`/event`, `/duel`, `/party`, `/spectate`, `/setup`, `/editor`, `/cosmetics`, `/settings`, `/divisions`,
`/statistics`, `/matchhistory`, `/matchinv`, `/copykit`, `/nick`, `/ignorequeue`, `/customqueue`, `/preview`,
`/unranked`, `/ranked`, `/staff`.

Permissions use the `zpp.` namespace, e.g. `zpp.admin`, `zpp.setup`, `zpp.staff`, `zpp.practice.*`, `zpp.staffmode.*`,
`zpp.bypass.*`.

### Player groups

Groups are defined in `core/src/main/resources/groups.yml` and selected by permission, defaulting to
`zpp.group.<group name lowercase>`:

`zpp.group.default`, `zpp.group.premium`, `zpp.group.supreme`, `zpp.group.staff`, `zpp.group.admin`

The active group controls match limits (ranked/unranked/event per day), party size and custom kit slots. Attach
cosmetics permissions per group with your permission plugin.

### Cosmetics permissions

Some cosmetics permissions are registered dynamically at startup by `CosmeticsPermissionManager`, so they are not fully listed in `plugin.yml`

#### Entry permission

1. `zpp.cosmetics.main`
   Required to run `/cosmetics` (`CosmeticsCommand`)

#### Armor trim permissions

1. Tier access:
  1. `zpp.cosmetics.armortrim.base.leather`
  2. `zpp.cosmetics.armortrim.base.gold`
  3. `zpp.cosmetics.armortrim.base.iron`
  4. `zpp.cosmetics.armortrim.base.diamond`
  5. `zpp.cosmetics.armortrim.base.netherite`
  6. wildcard: `zpp.cosmetics.armortrim.base.*`
2. Pattern access:
  1. `zpp.cosmetics.armortrim.pattern.<id>`
  2. wildcard: `zpp.cosmetics.armortrim.pattern.*`
3. Material access:
  1. `zpp.cosmetics.armortrim.material.<id>`
  2. wildcard: `zpp.cosmetics.armortrim.material.*`
4. Apply trim to all armor (copy from base):
  1. `zpp.cosmetics.armortrim.apply-global`

`<id>` values come from Mojang/Paper trim registries and are sanitized to lowercase alphanumeric/underscore (for example: sentry, vex, amethyst, netherite)

#### Death effect permissions

1. Per effect:
  1. `zpp.cosmetics.deatheffect.none`
  2. `zpp.cosmetics.deatheffect.flame`
  3. `zpp.cosmetics.deatheffect.lightning`
  4. `zpp.cosmetics.deatheffect.firework`
  5. `zpp.cosmetics.deatheffect.explosion`
  6. `zpp.cosmetics.deatheffect.blood`
  7. `zpp.cosmetics.deatheffect.enchant`
  8. `zpp.cosmetics.deatheffect.ender`
  9. `zpp.cosmetics.deatheffect.hearts`
  10. `zpp.cosmetics.deatheffect.ice`
2. Wildcard:
  1. `zpp.cosmetics.deatheffect.*`

#### Shield layout permissions

1. Open/use shield cosmetics:
  1. `zpp.cosmetics.shield.use`
  2. wildcard: `zpp.cosmetics.shield.*`
2. Layout count limits:
  1. `zpp.cosmetics.shield.layouts.1` to `zpp.cosmetics.shield.layouts.21`
  2. wildcard: `zpp.cosmetics.shield.layouts.*`
  3. unlimited alias: `zpp.cosmetics.shield.layouts.unlimited`

If none of the layout count permissions are set, the code falls back to 1 max layout

#### Lobby movement permissions

1. Per item:
  1. `zpp.cosmetics.lobby.none`
  2. `zpp.cosmetics.lobby.wind_charge`
  3. `zpp.cosmetics.lobby.trident`
  4. `zpp.cosmetics.lobby.spear`
2. Wildcard:
  1. `zpp.cosmetics.lobby.*`

### Groups and cosmetics permissions

Player groups are configured in `core/src/main/resources/groups.yml` and selected by group permission:

- `zpp.group.default`
- `zpp.group.premium`
- `zpp.group.supreme`
- `zpp.group.staff`
- `zpp.group.admin`

The active group controls limits like custom kits and party capacity. You can also use your permission plugin (LuckPerms, etc.) to attach cosmetics permissions per group.

Example bundle strategy:

- `DEFAULT`: `zpp.cosmetics.main`, `zpp.cosmetics.armortrim.base.leather`, `zpp.cosmetics.deatheffect.none`, `zpp.cosmetics.shield.use`, `zpp.cosmetics.shield.layouts.1`
- `PREMIUM`: add `zpp.cosmetics.armortrim.base.gold`, selected trim/material nodes, `zpp.cosmetics.deatheffect.flame`, `zpp.cosmetics.shield.layouts.3`
- `SUPREME+`: grant broader wildcards (`zpp.cosmetics.armortrim.pattern.*`, `zpp.cosmetics.armortrim.material.*`, `zpp.cosmetics.deatheffect.*`, `zpp.cosmetics.shield.layouts.unlimited`)

## Developer API

Published on JitPack as `com.github.sylveya:ZonePracticePro-Api`. Interfaces and events live in
`dev.nandi0813.api`; the implementation stays internal.

Maven:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.sylveya</groupId>
        <artifactId>ZonePracticePro-Api</artifactId>
        <version>2.4.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

Gradle:

```groovy
repositories { maven { url 'https://jitpack.io' } }

dependencies { compileOnly 'com.github.sylveya:ZonePracticePro-Api:2.4.0' }
```

Load after ZonePractice Pro:

```yaml
name: YourPlugin
version: '1.0'
main: your.package.Main
api-version: '1.21'
depend: [ZonePracticePro]
```

### Usage

```java
public final class Example extends JavaPlugin implements Listener {

    private ZonePracticeApi api;

    @Override
    public void onEnable() {
        api = ZonePracticeApi.getInstance();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        int wins = api.getLadderWins(player, "Nodebuff", WeightClass.RANKED);
        player.sendMessage(api.getPlayerDivision(player, DivisionName.FULL) + " (" + wins + " ranked wins)");
    }

    @EventHandler
    public void onMatchStart(MatchStartEvent e) {
        Match match = e.getMatch();
        // MiniMessage string, second arg = also send to spectators
        match.sendMessage("<red>Match starting in <bold>" + match.getLadderDisplayName(), true);
    }
}
```

### What the API exposes

`ZonePracticeApi` — divisions, per-ladder ELO, wins/losses (per weight class), global wins, experience, daily
ranked/unranked match limits, forced match end, and player nametags (`PlayerNametag`).

Interfaces: `Match`, `Event`, `FFA`, `Party`, `Queue`, `Spectatable` — all expose `getSpectators()` and, through
`Spectatable`, `addSpectator()`, `removeSpectator()` and `sendMessage()`.

Events (all in `dev.nandi0813.api.Event`):

| Event | Cancellable |
| --- | --- |
| `MatchStartEvent`, `MatchEndEvent`, `MatchRoundStartEvent`, `MatchRoundEndEvent` | start only |
| `EventStartEvent`, `EventEndEvent` | start only |
| `QueueStartEvent`, `QueueEndEvent` | start only |
| `PartyCreateEvent` | yes |
| `NewPlayerJoin`, `FFARemovePlayerEvent` | no |
| `MatchSpectateStartEvent` / `MatchSpectateEndEvent`, `EventSpectateStartEvent` / `EventSpectateEndEvent`, `FFASpectateStartEvent` / `FFASpectateEndEvent` | no |

## Troubleshooting

**Plugin disables itself on startup** – unsupported server version. Check the console message; you need 1.21.11
or 26.1 / 26.2 / 26.3.

**PacketEvents not found / not loaded** – it must be a separate plugin in `plugins/`, loaded before ZonePractice Pro.
Full restart, no hot-loading.

**`/setup` or `/practice` GUI does not open** – you need `zpp.setup` or `zpp.admin`, and the lobby must be set with
`/practice lobby set`.

**Players have no access to features** – they need `zpp.group.default` and the lobby set.

**MySQL connection errors** – verify host, port, database, user and password under `MYSQL-DATABASE` in `config.yml`,
and that the server accepts external connections. The driver is MariaDB, so the target must be reachable over the
MariaDB/MySQL protocol.

## License

MIT — see [LICENSE](https://github.com/sylveya/ZonePractice-Pro/blob/dev/LICENSE). Copyright © ZonePractice
contributors.

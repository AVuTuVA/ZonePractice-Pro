# Configuration files

AstralPractice keeps its settings in eight files in the plugin folder: `config.yml`, `language.yml`,
`guis.yml`, `sidebar.yml`, `groups.yml`, `divisions.yml`, `inventories.yml` and `playerkit.yml`. They
are loaded with [Configura](https://github.com/whereareiam/Configura) into typed models, and
[Strata](https://github.com/whereareiam/strata) upgrades files written by older versions.

## What happens on start and on `/practice reload`

1. Strata runs the migrations of `PracticeConfig` the installation has not seen yet. It records the
   version as `_version` at the top of `config.yml` and keeps a copy of every file it changes in
   `.strata/backup/`.
2. Each file is read, settings it lacks are added with their defaults, and the file is written back.
   What a server owner wrote is kept. Keys the plugin does not know are dropped.
3. If a file cannot be read, the plugin does not start; a reload keeps the previous values and logs
   which file and setting failed.

## Where things are

| What | Where |
| --- | --- |
| Loading, reloading, migrations | `core/src/main/java/dev/lokspel/practice/config/PracticeConfig.java` |
| Models and their default values, one package per file | `core/src/main/java/dev/lokspel/practice/config/<file>/` |
| Explanations shown in the files | `@Comment` on the model fields |

Code reads settings through the models, for example
`PracticeConfig.settings().getChat().getPartyChat().isEnabled()` or
`PracticeConfig.language().getMatch().getDuel().getPlayerLeft()`. Read them when needed rather than
keeping them in fields, so that a reload applies.

## Add a setting

Add a field to the model, with the key as it appears in the file and its default value:

```java
@Comment("Seconds a player is protected after joining.")
@JsonProperty("SPAWN-PROTECTION")
private int spawnProtection = 5;
```

A section is a nested class whose fields carry their own defaults (`private Lobby lobby = new
Lobby();`), and an item is `ItemConfig.of("ARROW").name("<gray>Back")`. Classes that appear more than
once with different values (the queue settings of ranked and unranked, groups, divisions, sounds, ...)
have no initializers; each default instance is a named factory in the class, such as
`QueueSettings.ranked()` or `Group.premium()`, and the field or map that holds it calls the factory.

Existing servers get the new key with its default on their next start. Sections whose keys are chosen
by the server owner (groups, divisions, sounds, per-ladder sidebars, ...) are `Map`s; entries an owner
deleted are not added back where the field has `@Merge(missingEntries = MissingEntries.OMIT)`.

## Rename, move or remove a setting

Adding a key needs no migration. To rename or move one without losing what server owners set, add a
migration at the end of the stream in `PracticeConfig.migrate`, for example:

```java
.migration(2, "rename-spawn-protection", files ->
    files.rename("config.yml", "/PLAYER/PROTECTION", "/PLAYER/SPAWN-PROTECTION"))
```

Paths are JSON pointers. Never change or renumber a migration that has been released.

## Test the files on a server

`distribution/src/anvil` holds Anvil tests that start Paper
with PacketEvents and the shaded plugin, join with a simulated player and check what the files do: a
fresh install, and an upgrade of a 7.9 plugin folder (`src/anvil/resources/upgrade-from-7.9`) followed
by `/practice reload` with an edited and then a broken `language.yml`. Run them with:

```sh
./gradlew :distribution:anvilTest
```

Add `--rerun` to run them again without changes. They download Paper on the first run and start real
servers, so `./gradlew build` does not run them. They use Paper 26.1.2, the newest version Anvil
supports. The Anvil version is `anvilVersion` in `gradle.properties`.

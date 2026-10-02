<img width="2000" height="1000" alt="image(6)" src="https://i.imgur.com/5NX2JVG.png" />

---

# Permissions

The full list is in [`plugin.yml`](../core/src/main/resources/plugin.yml). Below are only the nodes it does not declare.

## Death effects

| Permission                           | Usage             |
|--------------------------------------|-------------------|
| `zpp.cosmetics.deatheffect.none`      | No death effect   |
| `zpp.cosmetics.deatheffect.flame`     | Flame effect      |
| `zpp.cosmetics.deatheffect.lightning` | Lightning effect  |
| `zpp.cosmetics.deatheffect.firework`  | Firework effect   |
| `zpp.cosmetics.deatheffect.explosion` | Explosion effect  |
| `zpp.cosmetics.deatheffect.blood`     | Blood effect      |
| `zpp.cosmetics.deatheffect.enchant`   | Enchant effect    |
| `zpp.cosmetics.deatheffect.ender`     | Ender effect      |
| `zpp.cosmetics.deatheffect.hearts`    | Hearts effect     |
| `zpp.cosmetics.deatheffect.ice`       | Ice effect        |
| `zpp.cosmetics.deatheffect.supernova` | Supernova effect  |
| `zpp.cosmetics.deatheffect.voidstorm` | Voidstorm effect  |
| `zpp.cosmetics.deatheffect.phoenix`   | Phoenix effect    |
| `zpp.cosmetics.deatheffect.comet`     | Comet effect      |
| `zpp.cosmetics.deatheffect.*`         | All death effects |

## Armor trims

| Permission                            | Usage                   |
|---------------------------------------|-------------------------|
| `zpp.cosmetics.armortrim.pattern.<id>`  | Use an armor trim pattern  |
| `zpp.cosmetics.armortrim.material.<id>` | Use an armor trim material |

`<id>` is the trim name from your Minecraft version, for example `sentry`, `vex`, `amethyst` or `netherite`.

## Shield layouts

| Permission                               | Usage                              |
|------------------------------------------|------------------------------------|
| `zpp.cosmetics.shield.use`               | Open the shield cosmetics          |
| `zpp.cosmetics.shield.layouts.<1-21>`    | Use up to that many shield layouts |
| `zpp.cosmetics.shield.layouts.unlimited` | Use unlimited shield layouts       |

## Groups

| Permission        | Usage                                          |
|-------------------|------------------------------------------------|
| `zpp.group.<name>` | Player group — see [`groups.yml`](../core/src/main/resources/groups.yml) |

## Staff mode

| Permission             | Usage                                       |
|------------------------|---------------------------------------------|
| `zpp.staffmode.follow` | Follow other staff members (not registered) |
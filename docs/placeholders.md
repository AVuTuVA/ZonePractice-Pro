<img width="2000" height="1000" alt="image(6)" src="https://i.imgur.com/5NX2JVG.png" />

---

# Placeholders

> [!NOTE]
> [PlaceholderAPI](https://placeholderapi.com) is required for placeholders.
>
> The expansion identifier is `zpp` (`zppro` also works). All placeholders use the format `%zpp_<placeholder>%`.

---

## Global

| PlaceholderAPI    | Description                            |
|-------------------|----------------------------------------|
| %zpp_in_queue%    | Number of queues                       |
| %zpp_in_fight%    | Number of players in matches           |
| %zpp_ping%        | The ping of the player in milliseconds |

## Player Stats

| PlaceholderAPI         | Description                            |
|------------------------|----------------------------------------|
| %zpp_wins_global%      | Total wins (ranked + unranked)         |
| %zpp_wins_global_r%    | Total ranked wins                      |
| %zpp_wins_global_u%    | Total unranked wins                    |
| %zpp_losses_global%    | Total losses (ranked + unranked)       |
| %zpp_losses_global_r%  | Total ranked losses                    |
| %zpp_losses_global_u%  | Total unranked losses                  |
| %zpp_elo_global%       | Global elo                             |
| %zpp_division_short%   | Global division short name             |
| %zpp_division_full%    | Global division full name              |
| %zpp_division_weight%  | Global division weight                 |
| %zpp_nametag_color%    | Player's nametag color                 |
| %zpp_group_name%       | Player group name                      |
| %zpp_group_prefix%     | Player group prefix                    |
| %zpp_group_suffix%     | Player group suffix                    |
| %zpp_group_limit_r%    | Daily ranked match limit of the group  |
| %zpp_group_limit_u%    | Daily unranked match limit of the group|

## Ladder Stats

Ladder placeholders use the ladder name instead of the type:

| PlaceholderAPI                  | Description            |
|---------------------------------|------------------------|
| %zpp_wins_ladder_<ladder>_u%    | Unranked wins in kit   |
| %zpp_wins_ladder_<ladder>_r%    | Ranked wins in kit     |
| %zpp_losses_ladder_<ladder>_u%  | Unranked losses in kit |
| %zpp_losses_ladder_<ladder>_r%  | Ranked losses in kit   |
| %zpp_elo_ladder_<ladder>%       | Elo in kit             |

**Specific ladder example:** `%zpp_wins_ladder_Boxing_r%` — returns the player's ranked wins in the Boxing ladder.

## Queue

| PlaceholderAPI                | Description                     |
|-------------------------------|---------------------------------|
| %zpp_in_queue_<ladder>%       | Number of queues for a ladder   |
| %zpp_in_fight_<ladder>%       | Number of players in a ladder   |

## FFA Arena

| PlaceholderAPI                     | Description                  |
|------------------------------------|------------------------------|
| %zpp_ffa_<arena>_players%          | Players in the FFA arena     |
| %zpp_ffa_<arena>_spectators%       | Spectators in the FFA arena  |

## Leaderboards

Format: `%zpp_lb_global_<type>_<position>_<name|value>%`

**Available types:** `wins`, `elo`

**Position:** `1` to `10`

| PlaceholderAPI           | Description                              |
|--------------------------|------------------------------------------|
| %zpp_lb_global_wins_1_k% | Player name with the most wins (rank 1) |
| %zpp_lb_global_wins_1_v% | Win count of the #1 player              |
| %zpp_lb_global_elo_3_k%  | Player name ranked #3 in elo            |
| %zpp_lb_global_elo_3_v%  | Elo of the #3 player                    |

Format: `%zpp_lb_ladder_<ladder>_<type>_<position>_<name|value>%`

| PlaceholderAPI                       | Description                                |
|--------------------------------------|--------------------------------------------|
| %zpp_lb_ladder_Boxing_wins_1_k%      | Player name with the most wins in Boxing  |
| %zpp_lb_ladder_Boxing_wins_1_v%      | Win count of the #1 player in Boxing      |
| %zpp_lb_ladder_Axe_elo_3_k%          | Player name ranked #3 in elo for Axe      |
| %zpp_lb_ladder_Axe_elo_3_v%          | Elo of the #3 player in Axe               |
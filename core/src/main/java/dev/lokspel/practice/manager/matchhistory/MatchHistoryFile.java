package dev.lokspel.practice.manager.matchhistory;

import dev.lokspel.practice.data.DataFiles;
import dev.lokspel.practice.util.Common;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * YAML-backed per-player match history stored at {@code
 * plugins/AstralPractice/match-history/<uuid>.yml}.
 *
 * <p>Stores up to 5 recent match entries per player. The file is read once when the history is
 * created and kept in memory; each saved match rewrites it.
 */
public class MatchHistoryFile {

  private static final int MAX_HISTORY = 5;
  private static final String NO_OPPONENT = "00000000-0000-0000-0000-000000000000";
  private static final String UNKNOWN = "Unknown";

  private final MatchHistory matchHistory;
  private final String path;
  private final MatchHistoryData data;

  public MatchHistoryFile(MatchHistory matchHistory) {
    this.matchHistory = matchHistory;
    this.path = "match-history/" + matchHistory.getUuid().toString().toLowerCase() + ".yml";
    this.data = read();
  }

  /** Writes the matches held in memory to the file. */
  public synchronized void setData() {
    DataFiles.write(path, data);
  }

  /** Saves a new match and prunes entries beyond the cap. Returns the assigned id. */
  public synchronized int saveMatch(MatchHistoryEntry entry) {
    int nextId = getNextId();
    data.getMatches().put(String.valueOf(nextId), toMatch(entry));
    pruneOldMatches();
    DataFiles.write(path, data);
    return nextId;
  }

  /** Fills the match history's cache with the matches of the file, newest first. */
  public synchronized void getData() {
    matchHistory.getMatches().clear();

    for (Map.Entry<String, MatchHistoryData.Match> match : data.getMatches().entrySet()) {
      MatchHistoryEntry entry = toEntry(match.getKey(), match.getValue());
      if (entry != null) {
        matchHistory.getMatches().add(entry);
      }
    }

    matchHistory.getMatches().sort((a, b) -> Integer.compare(b.getMatchId(), a.getMatchId()));
  }

  private MatchHistoryData read() {
    MatchHistoryData read =
        DataFiles.readOrCreate(path, MatchHistoryData.class, MatchHistoryData::new);
    if (read.getMatches() == null) {
      read.setMatches(new LinkedHashMap<>());
    }
    return read;
  }

  private static MatchHistoryData.Match toMatch(MatchHistoryEntry entry) {
    MatchHistoryData.Match match = new MatchHistoryData.Match();
    match.setUuid(entry.getPlayerUuid().toString());
    match.setOpponentUuid(entry.getOpponentUuid().toString());
    match.setUsername(entry.getPlayerName());
    match.setOpponentName(entry.getOpponentName());
    match.setKitName(entry.getKitName());
    match.setArenaName(entry.getArenaName());
    match.setScore(entry.getPlayerScore());
    match.setOpponentScore(entry.getOpponentScore());
    match.setFinalHealth(entry.getPlayerFinalHealth());
    match.setOpponentFinalHealth(entry.getOpponentFinalHealth());
    match.setWinnerUuid(entry.getWinnerUuid() != null ? entry.getWinnerUuid().toString() : "");
    match.setMatchDuration(entry.getMatchDuration());
    match.setPlayedAt(entry.getPlayedAt());
    return match;
  }

  private MatchHistoryEntry toEntry(String matchId, MatchHistoryData.Match match) {
    try {
      return new MatchHistoryEntry(
          Integer.parseInt(matchId),
          UUID.fromString(or(match.getUuid(), matchHistory.getUuid().toString())),
          UUID.fromString(or(match.getOpponentUuid(), NO_OPPONENT)),
          or(match.getUsername(), UNKNOWN),
          or(match.getOpponentName(), UNKNOWN),
          or(match.getKitName(), UNKNOWN),
          or(match.getArenaName(), UNKNOWN),
          or(match.getScore(), 0),
          or(match.getOpponentScore(), 0),
          or(match.getFinalHealth(), 0.0),
          or(match.getOpponentFinalHealth(), 0.0),
          getWinnerUuid(match),
          or(match.getMatchDuration(), 0),
          or(match.getPlayedAt(), System.currentTimeMillis()));
    } catch (Exception e) {
      Common.sendConsoleMmMessage(
          "<yellow>[MatchHistory] Skipping corrupt entry "
              + matchId
              + " for "
              + matchHistory.getUuid()
              + ": "
              + e.getMessage());
      return null;
    }
  }

  private static <T> T or(T value, T fallback) {
    return value != null ? value : fallback;
  }

  private static UUID getWinnerUuid(MatchHistoryData.Match match) {
    String winnerUuid = match.getWinnerUuid();
    return (winnerUuid != null && !winnerUuid.isEmpty()) ? UUID.fromString(winnerUuid) : null;
  }

  private int getNextId() {
    int max = 0;
    for (int id : getIds()) {
      max = Math.max(max, id);
    }
    return max + 1;
  }

  private void pruneOldMatches() {
    List<Integer> ids = getIds();
    if (ids.size() <= MAX_HISTORY) {
      return;
    }

    ids.sort((a, b) -> Integer.compare(b, a)); // newest first
    for (int id : ids.subList(MAX_HISTORY, ids.size())) {
      data.getMatches().remove(String.valueOf(id));
    }
  }

  private List<Integer> getIds() {
    List<Integer> ids = new ArrayList<>();
    for (String key : data.getMatches().keySet()) {
      try {
        ids.add(Integer.parseInt(key));
      } catch (NumberFormatException ignored) {
        // Keys that are not match ids are left alone.
      }
    }
    return ids;
  }
}

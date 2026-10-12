package dev.lokspel.api.ladder;

import java.util.List;

/** API section for practice ladders. */
public interface LadderApi {

  /**
   * Get the names of all available ladders.
   *
   * @return List of ladder names
   */
  List<String> getLadderNames();
}

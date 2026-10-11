package dev.lokspel.practice.manager.ladder.abstraction.interfaces;

import dev.lokspel.practice.manager.ladder.abstraction.normal.LadderData;

/** A ladder type with settings of its own in its ladder file. */
public interface CustomConfig {

  /**
   * Puts the type's settings into the ladder file's model before it is written.
   *
   * @param data the model of the ladder file
   */
  void writeCustomConfig(LadderData data);

  /**
   * Takes the type's settings from the ladder file's model, falling back to defaults for absent or
   * invalid values.
   *
   * @param data the model of the ladder file
   */
  void readCustomConfig(LadderData data);
}

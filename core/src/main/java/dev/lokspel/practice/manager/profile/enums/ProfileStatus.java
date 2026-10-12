package dev.lokspel.practice.manager.profile.enums;

import dev.lokspel.api.enums.PlayerState;

public enum ProfileStatus {
  OFFLINE, // Player is offline.
  LOBBY, // Player is in the lobby with the normal lobby inventory.
  EDITOR, // Player is in the kit editor GUI.
  CUSTOM_EDITOR, // Player is in the custom kit editor GUI.
  QUEUE, // Player is waiting in queue.
  MATCH, // Player is in a match.
  FFA,
  EVENT, // Player is in an event.
  SPECTATE, // Player is spectating a match.
  STAFF_MODE; // Player is in the lobby and has staff-mode on.

  public PlayerState toPlayerState() {
    return switch (this) {
      case OFFLINE -> PlayerState.OFFLINE;
      case LOBBY -> PlayerState.LOBBY;
      case EDITOR -> PlayerState.EDITOR;
      case CUSTOM_EDITOR -> PlayerState.CUSTOM_EDITOR;
      case QUEUE -> PlayerState.QUEUE;
      case MATCH -> PlayerState.MATCH;
      case FFA -> PlayerState.FFA;
      case EVENT -> PlayerState.EVENT;
      case SPECTATE -> PlayerState.SPECTATE;
      case STAFF_MODE -> PlayerState.STAFF_MODE;
    };
  }
}

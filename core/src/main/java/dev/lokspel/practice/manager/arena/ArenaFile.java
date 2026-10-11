package dev.lokspel.practice.manager.arena;

import dev.lokspel.practice.AstralPractice;
import dev.lokspel.practice.data.DataFiles;
import dev.lokspel.practice.manager.arena.arenas.interfaces.DisplayArena;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

/**
 * The file {@code arenas/<name>.yml} of an arena. The read model stays in memory; the arena updates
 * it and {@link #save()} writes it back.
 */
@Getter
public class ArenaFile {

  private final String path;
  private final ArenaData data;

  public ArenaFile(DisplayArena arena) {
    this(arena, null);
  }

  /**
   * Opens the file of an arena.
   *
   * @param arena the arena
   * @param data the file's content if it was already read, otherwise {@code null}
   */
  public ArenaFile(DisplayArena arena, @Nullable ArenaData data) {
    this.path = path(arena.getName());
    this.data = data != null ? data : DataFiles.readOrCreate(path, ArenaData.class, ArenaData::new);
  }

  /** Path of an arena's file relative to the data folder. */
  public static String path(String arenaName) {
    return "arenas/" + arenaName.toLowerCase() + ".yml";
  }

  public void save() {
    DataFiles.write(path, data);
  }

  /**
   * Deletes the file.
   *
   * @return whether the file existed and is gone now
   */
  public boolean delete() {
    Path file = AstralPractice.getInstance().getDataFolder().toPath().resolve(path);
    if (!Files.exists(file)) {
      return false;
    }

    DataFiles.delete(path);
    return !Files.exists(file);
  }
}

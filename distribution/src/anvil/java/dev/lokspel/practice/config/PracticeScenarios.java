package dev.lokspel.practice.config;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import me.whereareiam.anvil.api.model.process.Distribution;
import me.whereareiam.anvil.api.model.process.MinecraftServer;
import me.whereareiam.anvil.api.model.scenario.AnvilScenario;
import me.whereareiam.anvil.api.model.workspace.AssetSource;
import me.whereareiam.anvil.api.model.workspace.WorkspaceAsset;
import me.whereareiam.anvil.api.model.workspace.WorkspacePlan;
import me.whereareiam.anvil.api.scenario.AnvilScenarioDefinition;
import me.whereareiam.anvil.api.type.Platforms;
import org.jetbrains.annotations.NotNull;

/**
 * Paper 26.1.2 servers with PacketEvents and the shaded plugin installed.
 *
 * <p>Build 74 of 26.1.2 is the newest Paper Anvil supports; the plugin itself targets 26.2.
 */
final class PracticeScenarios {
  static final String SERVER = "server";

  private PracticeScenarios() {}

  private static AnvilScenario paper(String name, Path dataFolder) {
    WorkspacePlan.WorkspacePlanBuilder workspace =
        WorkspacePlan.builder()
            .asset(
                WorkspaceAsset.builder()
                    .source(AssetSource.artifact("packetevents"))
                    .target(Path.of("plugins", "packetevents.jar"))
                    .build())
            .asset(
                WorkspaceAsset.builder()
                    .source(AssetSource.artifact("plugin-under-test"))
                    .target(Path.of("plugins", "AstralPractice.jar"))
                    .build());
    if (dataFolder != null) {
      workspace.asset(
          WorkspaceAsset.builder()
              .source(AssetSource.path(dataFolder))
              .target(Path.of("plugins", "AstralPractice"))
              .build());
    }

    MinecraftServer server =
        MinecraftServer.builder()
            .name(SERVER)
            .platform(Platforms.PAPER)
            .distribution(Distribution.remote("26.1.2", "74"))
            .setting("view-distance", "4")
            .setting("simulation-distance", "4")
            .workspace(workspace.build())
            .memoryMegabytes(2048)
            .build();

    return AnvilScenario.builder().name(name).entrypoint(SERVER).server(server).build();
  }

  private static Path fixture(String name) {
    URL url = PracticeScenarios.class.getResource("/" + name);
    if (url == null) {
      throw new IllegalStateException("Missing fixture: " + name);
    }

    try {
      return Path.of(url.toURI());
    } catch (URISyntaxException e) {
      throw new IllegalStateException(e);
    }
  }

  /** No plugin folder yet. */
  public static final class FreshInstall implements AnvilScenarioDefinition {
    @Override
    public @NotNull AnvilScenario define() {
      return paper("astralpractice-fresh-install", null);
    }
  }

  /** A plugin folder as 7.9 left it, with a few settings changed by the owner. */
  public static final class UpgradeFrom79 implements AnvilScenarioDefinition {
    @Override
    public @NotNull AnvilScenario define() {
      return paper("astralpractice-upgrade-from-7.9", fixture("upgrade-from-7.9"));
    }
  }
}

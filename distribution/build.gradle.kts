plugins {
    id("packaging-shaded-jar")
    alias(libs.plugins.anvil)
    alias(libs.plugins.anvil.junit)
    alias(libs.plugins.anvil.capability.default)
    alias(libs.plugins.anvil.platform.paper)
}

dependencies {
    implementation(projects.core)

    anvilImplementation(libs.junit.jupiter)
    anvilRuntimeOnly(libs.junit.platform.launcher)
    anvilRuntimeOnly(libs.anvil.protocol.mcprotocol)
}

anvil {
    acceptEula()
    engine {
        protocolLibrary("mcprotocol")
        processors.set(4)
        if (!System.getProperty("os.name").lowercase().contains("win")) {
            processPriority.set("low")
        }
    }
    artifact("plugin-under-test", tasks.named("shadowJar"))
    artifact("packetevents", "maven.modrinth:packetevents:2.14.0+spigot")
}

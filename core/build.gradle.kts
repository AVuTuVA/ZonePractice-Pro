plugins {
    id("module-java")
}

dependencies {
    compileOnly(libs.packetevents.spigot)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.tab.api)

    compileOnly(libs.fawe.core)
    compileOnly(libs.fawe.bukkit) {
        exclude(module = "FastAsyncWorldEdit-Core")
    }

    implementation(libs.bundles.configura)
    implementation(libs.bundles.strata)
    implementation(libs.anvilgui)
    implementation(libs.bstats.bukkit)
    implementation(libs.faststats.bukkit)
    implementation(libs.gson)
    implementation(libs.hikaricp)
    implementation(libs.mariadb)
    implementation(libs.scoreboard.library.api)
    runtimeOnly(libs.scoreboard.library.implementation)
    implementation(projects.api)
}

tasks.named<ProcessResources>("processResources") {
    val pluginVersion = project.version.toString()
    inputs.property("version", pluginVersion)
    filesMatching("plugin.yml") {
        expand("version" to pluginVersion)
    }
}

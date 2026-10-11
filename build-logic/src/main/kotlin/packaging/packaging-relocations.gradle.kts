import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow")
}

tasks.withType<ShadowJar>().configureEach {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    filesMatching("META-INF/services/**") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
    mergeServiceFiles()

    exclude("META-INF/maven/**", "META-INF/proguard/**")
    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    exclude("**/module-info.class", "org/intellij/**", "org/jetbrains/**", "org/jspecify/**")
    exclude("**/.DS_Store")

    relocate("net.megavex.scoreboardlibrary", "dev.lokspel.practice.dependencies.sidebar")
    relocate("me.whereareiam.configura", "dev.lokspel.practice.dependencies.configura")
    relocate("me.whereareiam.strata", "dev.lokspel.practice.dependencies.strata")
    relocate("com.fasterxml.jackson", "dev.lokspel.practice.dependencies.jackson")
    relocate("org.yaml.snakeyaml", "dev.lokspel.practice.dependencies.snakeyaml")
    relocate("net.wesjd.anvilgui", "dev.lokspel.practice.dependencies.anvilgui")
    relocate("org.bstats", "dev.lokspel.practice.dependencies.bstats")
    relocate("dev.faststats", "dev.lokspel.practice.dependencies.faststats")
    relocate("com.google.gson", "dev.lokspel.practice.dependencies.gson")
    relocate("com.zaxxer.hikari", "dev.lokspel.practice.dependencies.hikari")
    relocate("org.mariadb.jdbc", "dev.lokspel.practice.dependencies.mariadb")
}

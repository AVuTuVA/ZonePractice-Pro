import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.jvm.tasks.Jar

plugins {
    id("module-java")
    id("packaging-relocations")
}

tasks.withType<ShadowJar>().configureEach {
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")

    if (providers.gradleProperty("output").isPresent) {
        destinationDirectory.set(file(providers.gradleProperty("output").get()))
    }
}

tasks.named<Jar>("jar").configure {
    dependsOn(tasks.named("shadowJar"))
}

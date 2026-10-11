import org.gradle.api.plugins.quality.CheckstyleExtension
import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    `java-library`
    checkstyle
    id("com.diffplug.spotless")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

group = rootProject.group

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(25)
}

tasks.withType<ProcessResources>().configureEach {
    filteringCharset = "UTF-8"
}

configure<CheckstyleExtension> {
    toolVersion = libs.findVersion("checkstyle").get().requiredVersion
    configFile = rootProject.file("config/checkstyle/google_checks.xml")
    configProperties = mapOf(
        "org.checkstyle.google.severity" to "error",
        "org.checkstyle.google.suppressionfilter.config" to rootProject.file("config/checkstyle/suppressions.xml").absolutePath
    )
    maxWarnings = 0
}

tasks.withType<Checkstyle>().configureEach {
    // Checkstyle needs source files only; avoid compiling the plugin to lint it.
    classpath = files()
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

configure<SpotlessExtension> {
    java {
        googleJavaFormat(libs.findVersion("google-java-format").get().requiredVersion).reflowLongStrings()
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    ignoreFailures = providers.gradleProperty("ignoreTestFailures")
        .map(String::toBoolean)
        .orElse(false)
        .get()
}

plugins {
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.shadow) apply false
}

allprojects {
    group = "dev.loskpel"
    version = rootProject.version
}

defaultTasks("build")

tasks.register("pluginJar") {
    group = "build"
    description = "Builds the distribution shadow jar."
    dependsOn(":distribution:shadowJar")
}

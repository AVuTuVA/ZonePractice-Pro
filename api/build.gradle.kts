plugins {
    id("module-api")
}

version = "2.5.0"

dependencies {
    compileOnlyApi(libs.paper.api)
    compileOnlyApi(libs.lombok)
    annotationProcessor(libs.lombok)
}

publishing {
    publications {
        named<MavenPublication>("mavenJava") {
            artifactId = "AstralPractice-Api"
        }
    }
}

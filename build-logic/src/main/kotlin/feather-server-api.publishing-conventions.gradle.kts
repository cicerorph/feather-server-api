plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "Artifactory"
            url = if (version.toString().endsWith("SNAPSHOT")) {
                uri("https://repo.feathermc.net/artifactory/maven-snapshots/")
            } else {
                uri("https://repo.feathermc.net/artifactory/maven-releases/")
            }
            credentials {
                username =
                    project.findProperty("artifactory.username") as? String ?: System.getenv("ARTIFACTORY_USERNAME")
                password =
                    project.findProperty("artifactory.password") as? String ?: System.getenv("ARTIFACTORY_PASSWORD")
            }
        }
    }
}

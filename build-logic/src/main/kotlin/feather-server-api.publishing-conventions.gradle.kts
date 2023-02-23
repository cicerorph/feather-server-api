plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "featherPublic"
            credentials(PasswordCredentials::class)
            url = uri("https://repo.feathermc.net/artifactory/feather-public")
        }
    }
}
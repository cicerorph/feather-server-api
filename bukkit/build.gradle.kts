plugins {
    id("feather-server-api.java-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    maven {
        name = "spigot-snapshots"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")

    implementation(project(":api"))
    implementation(project(":common"))
    implementation(project(":messaging"))
}

tasks.withType<Jar> {
    archiveBaseName.set("feather-server-api")
    archiveClassifier.set("bukkit")
}
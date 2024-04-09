plugins {
    id("feather-server-api.java-conventions")
}

repositories {
    maven {
        name = "spigot-snapshots"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

val externalResources: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    compileOnly("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")

    compileOnly(project(":api"))

    externalResources(project(":examples:static", "demoHtml"))
}

tasks.withType<ProcessResources> {
    dependsOn(externalResources)
    from(externalResources.files)
    files("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.withType<Jar> {
    archiveBaseName.set("feather-server-api-example")
    archiveClassifier.set("bukkit")
}
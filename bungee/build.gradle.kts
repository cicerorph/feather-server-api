plugins {
    id("feather-server-api.java-conventions")
}

repositories {
    maven {
        name = "spigot-public"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/public/")
    }
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.8-SNAPSHOT")
}
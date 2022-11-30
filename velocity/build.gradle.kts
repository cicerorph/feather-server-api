plugins {
    id("feather-server-api.java-conventions")
}

repositories {
    maven {
        name = "velocity-snapshots"
        url = uri("https://repo.velocitypowered.com/snapshots/")
    }
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:1.1.5")
    annotationProcessor("com.velocitypowered:velocity-api:1.1.5")
}
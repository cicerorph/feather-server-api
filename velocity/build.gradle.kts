plugins {
    id("feather-server-api.java-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    maven {
        name = "velocity-snapshots"
        url = uri("https://repo.velocitypowered.com/snapshots/")
    }
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    implementation(project(":api"))
    implementation(project(":common"))
    implementation(project(":messaging"))
}
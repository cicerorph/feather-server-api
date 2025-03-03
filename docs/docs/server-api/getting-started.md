---
id: getting-started
title: Getting Started with Feather Server API
sidebar_label: Getting Started
description: Learn how to set up and integrate Feather Server API in your Minecraft server plugins.
sidebar_position: 1
slug: /
---


# Getting Started with Feather Server API

The Feather Server API allows server owners and plugin developers to interact with features of Feather from their Minecraft servers. This guide will help you integrate the API into your server plugins.

## Prerequisites

Before you begin, ensure you have:

- A Bukkit/Spigot/Paper Minecraft server
- Basic knowledge of Java and Bukkit plugin development
- A development environment set up for creating Minecraft plugins

## Adding the Dependency

To use the Feather Server API in your project, you need to add it as a dependency. The examples below show how to include it in your project using different build systems.

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<Tabs>
  <TabItem value="maven" label="Maven" default>

```xml
<repositories>
    <repository>
        <id>feather-repo</id>
        <url>https://repo.feathermc.net/artifactory/maven-releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>net.digitalingot.feather-server-api</groupId>
        <artifactId>api</artifactId>
        <version>0.0.5</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

  </TabItem>
  <TabItem value="gradle" label="Gradle (Groovy)">

```groovy
repositories {
    maven {
        name = 'feather-repo'
        url = 'https://repo.feathermc.net/artifactory/maven-releases'
    }
}

dependencies {
    compileOnly 'net.digitalingot.feather-server-api:api:0.0.5'
}
```

  </TabItem>
  <TabItem value="kotlin" label="Gradle (Kotlin DSL)">

```kotlin
repositories {
    maven {
        name = "feather-repo"
        url = uri("https://repo.feathermc.net/artifactory/maven-releases")
    }
}

dependencies {
    compileOnly("net.digitalingot.feather-server-api:api:0.0.5")
}
```

  </TabItem>
</Tabs>

:::caution Important Note
Always use `provided` (Maven) or `compileOnly` (Gradle) scope for the Feather Server API dependency, as it will be provided by the Feather Server API plugin at runtime.
:::

## Installing the Server Plugin

The Feather Server API requires a plugin to be installed on your server. Download the latest version from [GitHub](https://github.com/FeatherMC/feather-server-api/releases) and place it in your server's `plugins` directory.

After restarting your server, the API will be available for your plugins to use.

## Basic Usage

Here's a simple example of a plugin that detects when a player with Feather joins the server:

```java
public class MyFeatherPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // Register for Feather events
        FeatherAPI.getEventService().subscribe(
            PlayerHelloEvent.class,
            event -> {
                FeatherPlayer player = event.getPlayer();
                getLogger().info(player.getName() + " is using Feather!");
                getLogger().info("Platform: " + event.getPlatform());
                getLogger().info("Enabled mods: " + event.getFeatherMods().size());
            }
        );
    }
}
```

This simple example logs information when a player using Feather joins your server.

## Next Steps

Now that you've set up the Feather Server API in your project, you can explore its various features:

- [Events](./events.md) - Subscribe to Feather events
- [Mods](./mods.md) - Control which mods players can use
- [UI](./ui/overview.md) - Create custom UI overlays for Feather users
- [Waypoints](./waypoints.md) - Create and manage waypoints for players
- [Meta Services](/category/meta) - Customize server appearance and functionality
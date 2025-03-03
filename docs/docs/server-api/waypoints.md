---
id: waypoints
title: Waypoints
sidebar_label: Waypoints
description: Learn how to create and manage waypoints for players using the Feather Server API
---

import Image from '@theme/IdealImage';

# Waypoints

## Overview

Waypoints allows server developers to create custom waypoints for players using Feather. Waypoints appear as beacons in the game world, helping players navigate to important locations. Waypoints can be customized with different colors, names, and durations.

<Image
  img={require('./assets/waypoints.png')}
  alt="A Minecraft world with several waypoints visible, displaying different colors and labels."
  priority={true}
/>
*The image above shows a Minecraft world with several waypoints visible, displaying different colors and labels.*


## Getting Started with Waypoints

The `WaypointService` is the main interface for creating and managing waypoints. You can access it through the `FeatherAPI`:

```java
WaypointService waypointService = FeatherAPI.getWaypointService();
```

### Creating a Simple Waypoint

To create a basic waypoint, you need:
1. A reference to a `FeatherPlayer`
2. The coordinates where you want to place the waypoint
3. A `WaypointBuilder` to configure the waypoint

```java
// Get the player
FeatherPlayer player = FeatherAPI.getPlayerService().getPlayer(playerUuid);

// Create a waypoint at coordinates (100, 65, -200)
WaypointBuilder builder = waypointService.createWaypointBuilder(100, 65, -200);

// Configure the waypoint with a name and color
builder.withName("Resource Area")
       .withColor(WaypointColor.fromRgb(255, 0, 0)); // Red color

// Create the waypoint for the player
UUID waypointId = waypointService.createWaypoint(player, builder);
```

The `createWaypoint` method returns a UUID that can be used to reference or remove the waypoint later.

## Customizing Waypoints

### Setting Waypoint Colors

Waypoints can be customized with different colors to help players distinguish between them. The `WaypointColor` class provides several ways to define colors:

```java
// RGB values (0-255)
WaypointColor red = WaypointColor.fromRgb(255, 0, 0);

// RGB with alpha for transparency (0-255)
WaypointColor semiTransparentBlue = WaypointColor.fromRgba(0, 0, 255, 128);

// RGB normalized values (0.0-1.0)
WaypointColor green = WaypointColor.fromRgb(0.0f, 1.0f, 0.0f);

// RGB with alpha normalized values (0.0-1.0)
WaypointColor semiTransparentYellow = WaypointColor.fromRgba(1.0f, 1.0f, 0.0f, 0.5f);

// Using the special chroma color (shifting rainbow effect)
WaypointColor rainbowEffect = WaypointColor.chroma();
```

### Setting Waypoint Duration

Waypoints can be temporary or permanent. Use the `WaypointDuration` class to specify how long a waypoint should last:

```java
// Create a permanent waypoint
builder.withDuration(WaypointDuration.none());

// Create a waypoint that lasts for 60 seconds
builder.withDuration(WaypointDuration.of(60));
```

### Creating Waypoints in Different Worlds

If your server has multiple worlds, you can specify the world ID when creating a waypoint:

```java
// Get the world UUID from a Bukkit World object
UUID worldId = bukkitWorld.getUID();

// Set the world ID for the waypoint
builder.withWorldId(worldId);
```

If no world ID is provided, the waypoint will be created in the player's current world.

## Managing Waypoints

### Removing a Single Waypoint

To remove a single waypoint, use the `destroyWaypoint` method with the waypoint ID:

```java
waypointService.destroyWaypoint(player, waypointId);
```

### Removing Multiple Waypoints

To remove multiple waypoints at once:

```java
// Create a collection of waypoint IDs to remove
List<UUID> waypointIds = Arrays.asList(waypointId1, waypointId2, waypointId3);

// Remove all specified waypoints
waypointService.destroyWaypoints(player, waypointIds);
```

### Removing All Waypoints

To remove all waypoints for a player:

```java
waypointService.destroyAllWaypoints(player);
```

### Updating Waypoints

The API doesn't provide direct methods to update existing waypoints. If you need to update a waypoint's properties, you should:

1. Keep track of the waypoint's UUID
2. Destroy the existing waypoint
3. Create a new waypoint with the updated properties

```java
// Destroy the existing waypoint
waypointService.destroyWaypoint(player, existingWaypointId);

// Create a new waypoint with updated properties
WaypointBuilder updatedBuilder = waypointService.createWaypointBuilder(newX, newY, newZ)
    .withName(updatedName)
    .withColor(updatedColor);

UUID newWaypointId = waypointService.createWaypoint(player, updatedBuilder);
```

### Syncing Waypoints Across Players

If you want multiple players to see the same waypoints, you'll need to create identical waypoints for each player:

```java
// Create the same waypoint for a group of players
WaypointBuilder builder = waypointService.createWaypointBuilder(x, y, z)
    .withName("Group Objective")
    .withColor(WaypointColor.fromRgb(255, 215, 0)); // Gold color

for (FeatherPlayer groupMember : groupMembers) {
    waypointService.createWaypoint(groupMember, builder);
}
```

Remember that waypoints are client-side and player-specific, so each player needs their own instance of a waypoint.

## Common Use Cases

### Creating a Spawn Waypoint

Create a waypoint at the server spawn location when a player joins:

```java
// Get the spawn location from the Bukkit world
Location spawnLocation = world.getSpawnLocation();

// Create a waypoint builder with spawn coordinates
WaypointBuilder builder = waypointService.createWaypointBuilder(
    spawnLocation.getBlockX(), 
    spawnLocation.getBlockY(), 
    spawnLocation.getBlockZ()
);

// Configure the waypoint as a bright green marker named "Spawn"
builder.withName("Spawn")
       .withColor(WaypointColor.fromRgb(0, 255, 0))
       .withDuration(WaypointDuration.none());

// Create the waypoint for the player
waypointService.createWaypoint(player, builder);
```

### Highlighting Event Locations

Create temporary waypoints that indicate where special events are occurring:

```java
// Create a temporary event waypoint that lasts for 5 minutes
WaypointBuilder builder = waypointService.createWaypointBuilder(eventX, eventY, eventZ)
    .withName("Treasure Hunt")
    .withColor(WaypointColor.chroma()) // Use the special chroma effect for important events
    .withDuration(WaypointDuration.of(300)); // 5 minutes in seconds

// Create for all online players
for (FeatherPlayer player : FeatherAPI.getPlayerService().getPlayers()) {
    waypointService.createWaypoint(player, builder);
}
```
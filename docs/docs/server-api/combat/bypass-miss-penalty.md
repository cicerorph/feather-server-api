---
id: bypass-miss-penalty
title: Bypassing Miss Penalty
sidebar_label: Bypassing Miss Penalty
description: Control whether players can bypass the attack cooldown after missing in combat
---

# Bypassing Miss Penalty

## Overview

In vanilla Minecraft survival, players experience a brief attack cooldown (often called "miss penalty") when they miss an attack with a weapon. This mechanic prevents players from continuously swinging their weapons without penalty.

The Feather Server API allows server operators to control whether players can bypass this miss penalty.

## Getting Started

To control a player's miss penalty state, you'll use the `bypassMissPenalty()` method on a `FeatherPlayer` instance. When enabled, the player can attack continuously without experiencing the normal cooldown after missing.

Here's a basic implementation:

```java
// Get a Feather player
FeatherPlayer featherPlayer = FeatherAPI.getPlayerService().getPlayer(player.getUniqueId());

// Allow the player to bypass miss penalty
featherPlayer.bypassMissPenalty(true);

// Later, to restore vanilla behavior:
featherPlayer.bypassMissPenalty(false);
```

The method takes a single boolean parameter:
- `true` - Allows the player to bypass the miss penalty (no cooldown after missing)
- `false` - Restores vanilla behavior (standard cooldown after missing)

## Common Use Cases

### Toggling Miss Penalty for All Players

You might want to enable or disable miss penalty bypass for all players on your server:

```java
// Enable miss penalty bypass for all players
for (FeatherPlayer player : FeatherAPI.getPlayerService().getPlayers()) {
    player.bypassMissPenalty(true);
}
```

### Player-Based Combat Modes

You might want different player groups to have different combat experiences:

```java
// When a player joins a specific arena or game mode
public void onPlayerJoinArena(Player player, ArenaType arenaType) {
    FeatherPlayer featherPlayer = FeatherAPI.getPlayerService().getPlayer(player.getUniqueId());
    if (featherPlayer != null) {
        // Different arenas have different combat rules
        switch (arenaType) {
            case LEGACY_PVP:
                featherPlayer.bypassMissPenalty(true);
                break;
            case VANILLA_PVP:
                featherPlayer.bypassMissPenalty(false);
                break;
        }
    }
}
```

### Permission-Based Combat Mechanics

You can also tie miss penalty bypass to player permissions:

```java
// When a player joins with Feather
public void onFeatherPlayerJoin(PlayerHelloEvent event) {
    Player player = Bukkit.getPlayer(event.getPlayer().getUniqueId());
    if (player.hasPermission("myserver.combat.bypassmisspenalty")) {
        event.getPlayer().bypassMissPenalty(true);
    }
}
```

## Integration with Event System

A common pattern is to set the miss penalty state when a player joins the server with Feather:

```java
// Subscribe to the PlayerHelloEvent to catch when Feather users join
FeatherAPI.getEventService().subscribe(PlayerHelloEvent.class, event -> {
    // Set miss penalty bypass based on game rules, permissions, or other factors
    event.getPlayer().bypassMissPenalty(myServerConfig.isMissPenaltyBypassed());
});
```
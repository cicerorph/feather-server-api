---
id: mods
title: Mods
sidebar_label: Mods
description: Learn how to manage Feather mods, including blocking, enabling, and disabling mods.
sidebar_position: 3
---

# Mod Management

The Feather Server API allows you to manage which mods players can use on your server. This includes blocking mods entirely, enabling or disabling specific mods for individual players, and retrieving information about which mods are blocked or enabled.

## Overview

Mod management is handled through the `FeatherPlayer` interface, which provides methods for:

- Blocking mods (preventing players from using them)
- Unblocking mods (allowing players to use previously blocked mods)
- Getting a list of blocked mods
- Enabling specific mods for players
- Disabling specific mods for players
- Getting a list of enabled mods

These operations allow server owners to create a consistent gameplay experience by controlling which client-side mods can be used on their server.

## Basic Usage

### Accessing the API

To manage mods, you'll first need to get a reference to a `FeatherPlayer` object:

```java
// Get a player by UUID
FeatherPlayer player = FeatherAPI.getPlayerService().getPlayer(playerUUID);

// Or get all Feather players
Collection<FeatherPlayer> allPlayers = FeatherAPI.getPlayerService().getPlayers();
```

### Working with FeatherMod Objects

Most mod management operations use `FeatherMod` objects. A `FeatherMod` represents a built-in mod in Feather and is identified by its name:

```java
// Create a FeatherMod object
FeatherMod perspectiveMod = new FeatherMod("perspective");
```

## Blocking and Unblocking Mods

### Blocking Mods

Blocking mods prevents players from enabling them, even manually. When a mod is blocked:
- If it was enabled, it will be automatically disabled
- The player cannot re-enable it until it is unblocked

```java
// Block a single mod
Collection<FeatherMod> modsToBlock = Collections.singletonList(new FeatherMod("perspective"));
player.blockMods(modsToBlock);

// Block multiple mods
List<FeatherMod> multipleModsToBlock = Arrays.asList(
    new FeatherMod("motion_blur"),
    new FeatherMod("zoom"),
    new FeatherMod("timer")
);
player.blockMods(multipleModsToBlock);
```

:::info
Blocking a mod is more restrictive than disabling it. Blocked mods cannot be enabled by the player at all, while disabled mods can still be enabled through the client's mod menu.
:::

### Unblocking Mods

Unblocking mods allows players to enable the mods again through their client mod menu:

```java
// Unblock a single mod
Collection<FeatherMod> modsToUnblock = Collections.singletonList(new FeatherMod("perspective"));
player.unblockMods(modsToUnblock);

// Unblock multiple mods
List<FeatherMod> multipleModsToUnblock = Arrays.asList(
    new FeatherMod("motion_blur"),
    new FeatherMod("zoom"),
    new FeatherMod("timer")
);
player.unblockMods(multipleModsToUnblock);
```

### Getting Blocked Mods

You can retrieve a list of all mods that are currently blocked for a player:

```java
CompletableFuture<Collection<FeatherMod>> blockedModsFuture = player.getBlockedMods();

blockedModsFuture.thenAccept(blockedMods -> {
    for (FeatherMod mod : blockedMods) {
        // Process each blocked mod
        System.out.println("Blocked mod: " + mod.getName());
    }
});
```

## Enabling and Disabling Mods

### Enabling Mods

Enabling a mod activates it for the player. Note that this only works if the mod is not blocked:

```java
// Enable a single mod
Collection<FeatherMod> modsToEnable = Collections.singletonList(new FeatherMod("coordinates"));
player.enableMods(modsToEnable);

// Enable multiple mods
List<FeatherMod> multipleModsToEnable = Arrays.asList(
    new FeatherMod("item_physics"),
    new FeatherMod("saturation"),
    new FeatherMod("fps")
);
player.enableMods(multipleModsToEnable);
```

### Disabling Mods

Disabling a mod deactivates it for the player:

```java
// Disable a single mod
Collection<FeatherMod> modsToDisable = Collections.singletonList(new FeatherMod("perspective"));
player.disableMods(modsToDisable);

// Disable multiple mods
List<FeatherMod> multipleModsToDisable = Arrays.asList(
    new FeatherMod("motion_blur"),
    new FeatherMod("zoom"),
    new FeatherMod("timer")
);
player.disableMods(multipleModsToDisable);
```

### Getting Enabled Mods

You can retrieve a list of all mods that are currently enabled for a player:

```java
CompletableFuture<Collection<FeatherMod>> enabledModsFuture = player.getEnabledMods();

enabledModsFuture.thenAccept(enabledMods -> {
    for (FeatherMod mod : enabledMods) {
        // Process each enabled mod
        System.out.println("Enabled mod: " + mod.getName());
    }
});
```

## Reference

### Common Feather Mods

Here are some common Feather mods you might want to manage:

| Name                        | Slug                          | Description                                                   |
|-----------------------------|-------------------------------|---------------------------------------------------------------|
| Team Tracker                | `teamtracker`                 | Add indicator to see teammate location                        |
| Animations                  | `animations`                  | Modify player animations                                      |
| Armor Bar                   | `armorBar`                    | Extra armor bar features (Credits: RedLime)                   |
| Armor Status                | `armorStatus`                 | Display your armor durability                                 |
| Attack Indicator            | `attackIndicator`             | Add extra actions to your attack indicator                    |
| Autohide HUD                | `autohidehud`                 | Automatically hide parts of the HUD                           |
| Auto Perspective            | `autoperspective`             | Automatically switch perspectives on events                   |
| Auto Text                   | `autoText`                    | Keybind macros for commands and text                          |
| Backups                     | `backups`                     | Backups for your singleplayer world                           |
| Block Indicator             | `blockIndicator`              | Show info about the block you're looking at                   |
| Block Overlay               | `blockOverlay`                | Add overlay and outline to blocks                             |
| Boss Bar                    | `bossBar`                     | Customize your bossbar                                        |
| Brightness                  | `brightness`                  | Adjust your Brightness Settings & Night Vision on demand      |
| Camera                      | `camera`                      | Adds useful camera utilities                                  |
| Color Saturation            | `colorSaturation`             | Adjust your display color saturation settings                 |
| Combo Display               | `comboDisplay`                | Display number of consecutive hits                            |
| Coordinates                 | `coordinates`                 | Display your location and world info                          |
| CPS                         | `cps`                         | Show your clicks per second                                   |
| Custom Crosshair            | `crosshair`                   | Customize your crosshair                                      |
| Cull Logs                   | `culllogs`                    | Automatically remove old log files                            |
| Custom Advancements Screen  | `customadvancementsscreen`    | Show more information about advancement requirements          |
| Custom Chat                 | `customChat`                  | Tweak your chat display                                       |
| Custom F3                   | `customf3`                    | Customize your debug screen                                   |
| Custom Fog                  | `customfog`                   | Custom fog settings and clear water                           |
| Damage Indicator            | `damageIndicator`             | Show mob health information                                   |
| Dark Mode                   | `darkmode`                    | Dark mode for Minecraft GUI                                   |
| Death Info                  | `deathInfo`                   | Show information on death                                     |
| Direction                   | `direction`                   | Display your cardinal direction                               |
| Discord                     | `discordRP`                   | Share your current status on Discord                          |
| Drop Prevention             | `dropprevention`              | Lock items in your hotbar                                     |
| Elytras                     | `elytras`                     | Extra elytra features and chestplate swapping                 |
| FOV Changer                 | `fovChanger`                  | Customize field of view                                       |
| FPS                         | `fps`                         | Display your frames per second                                |
| Glint                       | `glint`                       | Customize color of enchants and other settings                |
| Hearts                      | `hearts`                      | Replaces multiple vanilla heart rows with a single row        |
| Hitbox                      | `hitbox`                      | Adds hitboxes around entities                                 |
| Hit Indicator               | `hitindicator`                | Add indicators where you receive damage                       |
| Horses                      | `horses`                      | Add extra features and stats to horses                        |
| Hypixel                     | `hypixel`                     | Useful mods for Hypixel                                       |
| Inventory                   | `inventory`                   | Useful mods for inventory management                          |
| Item Counter                | `itemCounter`                 | Count items in your inventory                                 |
| Item Despawn                | `itemdespawn`                 | Make items flash when they're about to despawn                |
| Item Info                   | `itemInfo1`                   | Display enchants when picking up an item                      |
| Item Physic                 | `itemPhysic`                  | Cool item dropping physics                                    |
| Jump Reset                  | `jumpreset`                   | Notifies the player about how close they were to achieving a successful jump reset |
| Keystrokes                  | `keystrokes`                  | Display your key presses                                      |
| Light Level Overlay         | `lightleveloverlay`           | Displays overlay indicating light levels                      |
| Loot Beams                  | `lootBeams`                   | Add beam to dropped items                                     |
| Mob Overlay                 | `mobOverlay`                  | Add overlay and outline to mobs                               |
| Motion Blur                 | `motionBlur`                  | Blur your vision, make sure it's not too high                 |
| Mousestrokes                | `mousestrokes`                | Displays mouse movement                                       |
| Nametags                    | `nametags`                    | Modify nametag mechanics                                      |
| Nick Hider                  | `nickHider`                   | Hide your nick and skin                                       |
| Pack Display                | `packdisplay`                 | Displays your current texture pack                            |
| Pack Organizer              | `packOrganizer`               | Improved resource pack page w/ folder support and extra features |
| Perspective                 | `perspective`                 | Freelook                                                      |
| Ping                        | `ping`                        | Display your ping to the server                               |
| Player Model                | `playerModel`                 | Render your player model in HUD                               |
| Playtime                    | `playtime`                    | Show time game open or in-game                                |
| Potion Effects              | `potionEffects`               | Display potion effects                                        |
| Reach Display               | `reachDisplay`                | Shows the distance when hitting a player                      |
| Reconnect                   | `reconnect`                   | Automatically reconnect to servers                            |
| Saturation                  | `saturation`                  | Adds saturation visualization food tooltips                   |
| Scoreboard                  | `scoreboard`                  | Customize scoreboard                                          |
| Screenshot                  | `screenshot`                  | Extra screenshot features                                     |
| Keybind Search              | `searchkeybind`               | Adds a keybind search popup overlay                           |
| Server Address              | `serverAddress`               | Shows the server address                                      |
| Shulker Tooltips            | `shulkertooltips`             | See the contents of shulker boxes and other containers        |
| Snaplook                    | `snaplook`                    | Snap to a particular perspective                              |
| Sound Filters               | `soundfilters`                | Adds sound enhancing features / reverb                        |
| Speed Meter                 | `speedMeter`                  | Show your current velocity                                    |
| Stopwatch                   | `stopwatch1`                  | Start and stop a stopwatch with a keybind                     |
| Subtitles                   | `subtitles`                   | Add color and other features to subtitles                     |
| System Resources            | `systemresources`             | Show system resources                                         |
| Tablist                     | `tablist`                     | Tweak tablist settings                                        |
| Tier Tagger                 | `tiertagger`                  | Displays tier from Vanilla PvP Tierlist                       |
| Time                        | `time`                        | Display the current time                                      |
| Time Changer                | `timeChanger`                 | Change the time locally                                       |
| Title Tweaker               | `titletweaker`                | Make adjustments to the Title Display                         |
| TNT Timer                   | `tnttimer`                    | Adds a timer to your TNT explosions                           |
| Toast Control               | `toastcontrol`                | Modify notification toast mechanics                           |
| Toggle Sprint               | `toggleSprint`                | Toggle your sprint and sneak key                              |
| Tooltips                    | `tooltips`                    | Tooltip Enhancements                                          |
| Totem                       | `totem`                       | Extra totem features                                          |
| TPS                         | `tps`                         | Estimate server TPS                                           |
| UHC Overlay                 | `uhcoverlay`                  | Resize popular UHC items to make them easier to find          |
| UI Scaling                  | `uiScaling`                   | Can potentially affect other mods, USE WITH CAUTION!          |
| ViewModel                   | `viewModel`                   | Adjust player viewmodel                                       |
| Voice                       | `voice`                       | Speak with other Feather players                              |
| Waypoints                   | `waypoints`                   | Render waypoints on a map                                     |
| Weather Changer             | `weatherchanger`              | Change the weather locally                                    |
| Zoom                        | `zoom`                        | Zoom in and out of objects                                    |

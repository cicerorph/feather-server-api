---
id: overview
title: overview
sidebar_label: Overview
description: Create rich, interactive in-game UI overlays using web technologies
sidebar_position: 1
---

import ReactPlayer from 'react-player'
import OverlayDemoVideoUrl from './assets/overlay-demo.mp4';

# UI System

Feather UI's allows you to create rich, interactive in-game overlays using web technologies (HTML, CSS, and JavaScript). This powerful feature lets server developers build complex interfaces that seamlessly integrate with Minecraft gameplay.

<ReactPlayer playing loop muted url={OverlayDemoVideoUrl} />
*Visual representation of how the UI overlay appears in-game, showing a transparent overlay with interactive elements on top of the Minecraft world.*

## Key Features

- Create full-screen UI overlays using HTML, CSS, and JavaScript
- Two-way communication between the server and client UI
- Support for modern web frameworks like React or Angular
- UI lifecycle management with visibility and focus controls
- RPC system for handling client-server interactions

## Basic Concepts

The UI System is built around a few core concepts:

- **UI Pages**: Full-screen overlays rendered on top of the game
- **UI Lifecycle**: Events for managing the creation, display, and destruction of UI pages
- **RPC Communication**: Method for handling data exchange between client and server
- **UI Handlers**: Callbacks for reacting to UI state changes

## Getting Started

### Creating Your First UI Page

To create a basic UI page, you'll need to:

1. Register a UI page with a URL
2. Set up handlers for lifecycle events
3. Create the page for a player
4. Display the page to the player

Here's a simple example:

```java
// Register a UI page
UIPage page = FeatherAPI.getUIService().registerPage(this, "https://example.com/ui.html");

// Set up lifecycle handler
page.setLifecycleHandler(new UILifecycleHandlerAdapter() {
    @Override
    public void onCreated(FeatherPlayer player) {
        // The page has been created for the player
        // You can send initial data here
    }
    
    @Override
    public void onDestroyed(FeatherPlayer player) {
        // The page has been destroyed for the player
        // Clean up any resources here
    }
});

// Create the page for a player
FeatherPlayer player = FeatherAPI.getPlayerService().getPlayer(playerUUID);
FeatherAPI.getUIService().createPageForPlayer(player, page);

// Show the page to the player
FeatherAPI.getUIService().showOverlayForPlayer(player, page);

// Give the page input focus (for keyboard and mouse events)
FeatherAPI.getUIService().openPageForPlayer(player, page);
```

### Using Data URLs

Instead of hosting HTML files, you can use data URLs to embed your HTML content directly:

```java
String htmlContent = "<html><body><h1>Hello, Feather!</h1></body></html>";
String dataUrl = "data:text/html;base64," + Base64.getEncoder().encodeToString(htmlContent.getBytes());
UIPage page = FeatherAPI.getUIService().registerPage(this, dataUrl);
```

## UI Lifecycle Management

The UI system provides several handlers to manage the lifecycle of your UI:

### Lifecycle Handler

Monitors creation and destruction of the UI page:

```java
page.setLifecycleHandler(new UILifecycleHandlerAdapter() {
    @Override
    public void onCreated(FeatherPlayer player) {
        // UI page created
    }
    
    @Override
    public void onDestroyed(FeatherPlayer player) {
        // UI page destroyed
    }
});
```

### Visibility Handler

Tracks when the UI is shown or hidden:

```java
page.setVisibilityHandler(new UIVisibilityHandlerAdapter() {
    @Override
    public void onShow(FeatherPlayer player) {
        // UI page became visible
    }
    
    @Override
    public void onHide(FeatherPlayer player) {
        // UI page became hidden
    }
});
```

### Focus Handler

Monitors input focus changes:

```java
page.setFocusHandler(new UIFocusHandlerAdapter() {
    @Override
    public void onFocusGained(FeatherPlayer player) {
        // UI page gained input focus
    }
    
    @Override
    public void onFocusLost(FeatherPlayer player) {
        // UI page lost input focus
    }
});
```

### Load Handler

Provides error notifications if the UI fails to load:

```java
page.setLoadHandler(new UILoadHandlerAdapter() {
    @Override
    public void onLoadError(FeatherPlayer player, String errorText) {
        // UI page failed to load
        player.getPlayer().sendMessage("UI failed to load: " + errorText);
    }
});
```

## UI State Management

You can control the state of UI pages using the UIService:

```java
// Show the UI without giving it input focus
FeatherAPI.getUIService().showOverlayForPlayer(player, page);

// Hide the UI
FeatherAPI.getUIService().hideOverlayFromPlayer(player, page);

// Show the UI and give the UI input focus
FeatherAPI.getUIService().openPageForPlayer(player, page);

// Remove input focus from UI (also hides if not shown via `showOverlayForPlayer`)
FeatherAPI.getUIService().closePageForPlayer(player, page);

// Remove the UI completely
FeatherAPI.getUIService().destroyPageForPlayer(player, page);
```

## Server-to-Client Communication

You can send data from your server to the UI using JSON:

```java
// Create a JSON message
JsonObject message = new JsonObject();
message.addProperty("type", "updateInventory");
JsonArray items = new JsonArray();
// Add items to the array
message.add("items", items);

// Send the message
String jsonString = new Gson().toJson(message);
FeatherAPI.getUIService().sendPageMessage(player, page, jsonString);
```

In the client-side HTML/JavaScript:

```javascript
window.addEventListener('message', function(event) {
    const data = event.data;
    if (data.type === 'updateInventory') {
        // Update the UI with the inventory items
        updateInventoryDisplay(data.items);
    }
});
```

## Client-to-Server Communication (RPC)

To handle requests from the client UI to your server, use the RPC system:

### 1. Create an RPC controller

```java
public class InventoryController implements RpcController {
    @RpcHandler("getItem")
    public void getItem(RpcRequest request, RpcResponse response) {
        // Parse the item ID from the request body
        int itemId = Integer.parseInt(request.getBody());
        
        // Get the player's inventory
        FeatherPlayer player = request.getSource();
        
        // Create a response with the item data
        JsonObject item = getItemData(player, itemId);
        String jsonResponse = new Gson().toJson(item);
        
        // Send the response
        response.respond(jsonResponse);
    }
}
```

### 2. Register the controller

```java
FeatherAPI.getUIService().registerCallbacks(page, new InventoryController());
```

### 3. Call the RPC function from the client

```javascript
async function getItemDetails(itemId) {
    const response = await fetch(`https://${window.resourceName}/getItem`, {
        method: 'POST',
        body: itemId.toString()
    });
    
    const data = await response.json();
    updateItemDisplay(data);
}
```

## Best Practices

### 1. Use a Single Page Application Approach

Design your UI as a single-page application where you update content dynamically rather than loading multiple pages.

# Ore Compass Mod

A Minecraft Forge mod that adds three tiers of specialized compass items to detect and locate ores within configurable ranges. Compasses can be tuned to track specific ore types for strategic mining.

## Overview
The Ore Compass is a Minecraft Forge mod that adds specialized compass items capable of detecting and locating ores within a configurable range. This mod provides players with a valuable tool for mining and ore exploration, making resource gathering more efficient and strategic.

## Features

### Three Tier System
The mod implements three tiers of ore compasses, each with increasing capabilities:

1. **Basic Ore Compass (Tier 1)**
   - Detection range: 16 blocks
   - Detects basic ores: Coal, Iron, and Copper
   - Entry-level tool for early-game mining

2. **Advanced Ore Compass (Tier 2)**
   - Detection range: 32 blocks
   - Detects all Tier 1 ores plus: Gold, Diamond, Lapis Lazuli, Redstone, and Emerald
   - Mid-game upgrade for enhanced ore detection

3. **Master Ore Compass (Tier 3)**
   - Detection range: 64 blocks
   - Detects all previous ores plus: Ancient Debris and Nether Quartz
   - Ultimate ore detection tool for late-game mining

### Ore Detection System
The mod can detect the following ore types across both Overworld and Nether dimensions:

**Tier 1 Ores:**
- Coal (including Deepslate Coal Ore)
- Iron (including Deepslate Iron Ore)
- Copper (including Deepslate Copper Ore)

**Tier 2 Ores:**
- Gold (including Deepslate and Nether Gold Ore)
- Diamond (including Deepslate Diamond Ore)
- Lapis Lazuli (including Deepslate Lapis Ore)
- Redstone (including Deepslate Redstone Ore)
- Emerald (including Deepslate Emerald Ore)

**Tier 3 Ores:**
- Ancient Debris (Nether exclusive)
- Nether Quartz

### Compass Tuning System
Players can "tune" their ore compasses to detect specific ore types:
- Use crafting recipes to tune a compass to a specific ore
- Tuned compasses will only detect the specified ore type
- Tuned compasses display an enchanted glowing effect
- This allows for focused mining operations when searching for specific resources

### Functionality

**Real-time Detection:**
- Compasses automatically scan for ores every second (20 ticks)
- Detection occurs in a cubic area around the player
- The compass tracks the nearest ore within range

**User Interaction:**
- Right-click/use the compass to force an immediate scan
- Displays the detected ore type and distance in chat
- Shows "No ores detected in range" if nothing is found
- Action bar messages provide quick feedback

**Smart Tracking:**
- Compasses remember the last detected ore position
- Automatically validates if the tracked ore still exists
- Updates to new targets when previous ores are mined
- Efficient performance with periodic updates

**Visual Indicators:**
- Tooltip displays tier, range, and detection capabilities
- Shows which ore type the compass is tuned to (if applicable)
- Tuned compasses have an enchanted glowing effect

## Technical Implementation

### Core Components
- **OreCompass.java**: Main mod class handling registration and initialization
- **OreCompassItem.java**: Implements compass behavior, NBT storage, and user interaction
- **OreDetector.java**: Contains ore detection algorithms and scanning logic
- **OreType.java**: Enum defining all detectable ore types and their properties
- **TuneCompassRecipe.java**: Custom recipe system for tuning compasses
- **Config.java**: Configuration options for the mod

### Recipe System
The mod includes:
- Crafting recipes for each tier of compass
- Tuning recipes for each ore type (10 different ores)
- Custom recipe serializer for the tuning mechanic

### NBT Data Storage
Compasses store data using NBT tags:
- Tuned ore type
- Target ore position (X, Y, Z coordinates)
- Persistent across game sessions

## Use Cases
- **Early Mining**: Use Basic compass to find essential ores like coal and iron
- **Resource Farming**: Tune compass to specific ores for efficient gathering
- **Nether Exploration**: Master compass helps locate Ancient Debris and Quartz
- **Cave Exploration**: Quickly identify nearby ore deposits in large cave systems
- **Strip Mining Optimization**: Detect ores beyond visible range to maximize efficiency

## Mod Information
- **Mod ID**: ore_compass
- **Platform**: Minecraft Forge
- **Creative Tab**: Dedicated "Ore Compass" tab for all compass items
- **Item Properties**: Compasses are non-stackable (maximum stack size: 1)

## Benefits
- Reduces time spent searching for specific ores
- Makes mining more strategic and less random
- Provides clear progression through three tiers
- Compatible with both Overworld and Nether mining
- Supports all vanilla ore variants (regular and deepslate)

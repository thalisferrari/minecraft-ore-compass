package com.orecompass;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Base class for Ore Compass items
 */
public class OreCompassItem extends Item {
    private static final String NBT_TUNED_ORE = "TunedOre";
    private static final String NBT_TARGET_X = "TargetX";
    private static final String NBT_TARGET_Y = "TargetY";
    private static final String NBT_TARGET_Z = "TargetZ";

    private final int tier;
    private final int range;

    public OreCompassItem(int tier, int range, Properties properties) {
        super(properties.stacksTo(1));
        this.tier = tier;
        this.range = range;
    }

    public int getTier() {
        return tier;
    }

    public int getRange() {
        return range;
    }

    /**
     * Set the tuned ore type for this compass
     */
    public static void setTunedOre(ItemStack stack, OreType oreType) {
        stack.getOrCreateTag().putString(NBT_TUNED_ORE, oreType.getName());
    }

    /**
     * Get the tuned ore type from this compass
     */
    @Nullable
    public static OreType getTunedOre(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(NBT_TUNED_ORE)) {
            return OreType.fromName(tag.getString(NBT_TUNED_ORE));
        }
        return null;
    }

    /**
     * Check if this compass is tuned to a specific ore
     */
    public static boolean isTuned(ItemStack stack) {
        return getTunedOre(stack) != null;
    }

    /**
     * Store the target position in NBT
     */
    private static void setTargetPos(ItemStack stack, BlockPos pos) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(NBT_TARGET_X, pos.getX());
        tag.putInt(NBT_TARGET_Y, pos.getY());
        tag.putInt(NBT_TARGET_Z, pos.getZ());
    }

    /**
     * Get the stored target position
     */
    @Nullable
    private static BlockPos getTargetPos(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(NBT_TARGET_X)) {
            return new BlockPos(
                    tag.getInt(NBT_TARGET_X),
                    tag.getInt(NBT_TARGET_Y),
                    tag.getInt(NBT_TARGET_Z)
            );
        }
        return null;
    }

    /**
     * Clear the stored target position
     */
    private static void clearTargetPos(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            tag.remove(NBT_TARGET_X);
            tag.remove(NBT_TARGET_Y);
            tag.remove(NBT_TARGET_Z);
        }
    }

    /**
     * Check if the block at the given position is still a valid ore of the specified type
     */
    private boolean isValidOreAt(Level level, BlockPos pos, @Nullable OreType oreType) {
        if (pos == null || oreType == null) {
            return false;
        }
        return oreType.matches(level.getBlockState(pos).getBlock());
    }

    /**
     * Check if the block at the given position matches any ore in the list
     */
    private boolean isValidOreAt(Level level, BlockPos pos, List<OreType> oreTypes) {
        if (pos == null || oreTypes == null || oreTypes.isEmpty()) {
            return false;
        }
        for (OreType oreType : oreTypes) {
            if (oreType.matches(level.getBlockState(pos).getBlock())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && entity instanceof Player player) {
            // Update every 20 ticks (1 second)
            if (level.getGameTime() % 20 == 0) {
                updateCompass(stack, level, player);
            }
        }
    }

    /**
     * Update the compass to point to the nearest ore
     */
    private void updateCompass(ItemStack stack, Level level, Player player) {
        BlockPos playerPos = player.blockPosition();
        BlockPos storedPos = getTargetPos(stack);

        OreType tunedOre = getTunedOre(stack);

        // First, validate the stored position is still valid
        if (storedPos != null) {
            boolean stillValid = false;
            if (tunedOre != null) {
                stillValid = isValidOreAt(level, storedPos, tunedOre);
            } else {
                List<OreType> detectableOres = OreType.getOresForTier(tier);
                stillValid = isValidOreAt(level, storedPos, detectableOres);
            }

            // If stored position is no longer valid, clear it
            if (!stillValid) {
                clearTargetPos(stack);
                storedPos = null;
            }
        }

        // Find nearest ore
        BlockPos targetPos = null;
        if (tunedOre != null) {
            // Tuned to specific ore
            targetPos = OreDetector.findNearestOre(level, playerPos, range, tunedOre);
        } else {
            // Detect all ores for this tier
            List<OreType> detectableOres = OreType.getOresForTier(tier);
            targetPos = OreDetector.findNearestOre(level, playerPos, range, detectableOres);
        }

        // Update or clear position
        if (targetPos != null) {
            setTargetPos(stack, targetPos);
        } else {
            // No ore found, clear any stored position
            clearTargetPos(stack);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // Force update when used
            updateCompass(stack, level, player);

            BlockPos target = getTargetPos(stack);
            if (target != null) {
                // Identify which ore type is at the target position
                String oreTypeName = "Unknown";
                OreType tunedOre = getTunedOre(stack);

                if (tunedOre != null) {
                    // For tuned compass, we know it's the tuned ore
                    oreTypeName = tunedOre.getName().toUpperCase().replace("_", " ");
                } else {
                    // For non-tuned compass, check which ore it is
                    List<OreType> detectableOres = OreType.getOresForTier(tier);
                    for (OreType oreType : detectableOres) {
                        if (oreType.matches(level.getBlockState(target).getBlock())) {
                            oreTypeName = oreType.getName().toUpperCase().replace("_", " ");
                            break;
                        }
                    }
                }

                double distance = Math.sqrt(player.blockPosition().distSqr(target));
                player.displayClientMessage(
                        Component.literal(String.format("%s detected! Distance: %.1f blocks", oreTypeName, distance))
                                .withStyle(ChatFormatting.GREEN),
                        true
                );
            } else {
                String message;
                OreType tunedOre = getTunedOre(stack);
                if (tunedOre != null) {
                    message = String.format("No %s detected in range", tunedOre.getName().toUpperCase().replace("_", " "));
                } else {
                    message = "No ores detected in range";
                }

                player.displayClientMessage(
                        Component.literal(message)
                                .withStyle(ChatFormatting.RED),
                        true
                );
            }
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        // Show tier
        tooltip.add(Component.literal("Tier: " + tier)
                .withStyle(ChatFormatting.GRAY));

        // Show range
        tooltip.add(Component.literal("Range: " + range + " blocks")
                .withStyle(ChatFormatting.GRAY));

        // Show tuned ore if applicable
        OreType tunedOre = getTunedOre(stack);
        if (tunedOre != null) {
            tooltip.add(Component.literal("Tuned to: " + tunedOre.getName().toUpperCase())
                    .withStyle(ChatFormatting.GOLD));
        } else {
            List<OreType> detectableOres = OreType.getOresForTier(tier);
            tooltip.add(Component.literal("Detects: All Tier " + tier + " ores")
                    .withStyle(ChatFormatting.AQUA));
        }

        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        // Enchanted glow if tuned
        return isTuned(stack);
    }
}

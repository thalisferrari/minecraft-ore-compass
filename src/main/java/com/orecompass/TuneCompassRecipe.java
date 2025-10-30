package com.orecompass;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

/**
 * Custom recipe for tuning an Ore Compass to a specific ore type
 * Requires: 1 Ore Compass + 4 of the target ore
 */
public class TuneCompassRecipe implements CraftingRecipe {
    private final ResourceLocation id;
    private final Ingredient compass;
    private final Ingredient ore;
    private final String oreType;
    private final int oreCount;

    public TuneCompassRecipe(ResourceLocation id, Ingredient compass, Ingredient ore, String oreType, int oreCount) {
        this.id = id;
        this.compass = compass;
        this.ore = ore;
        this.oreType = oreType;
        this.oreCount = oreCount;
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        boolean hasCompass = false;
        int oreMatches = 0;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }

            if (compass.test(stack)) {
                if (hasCompass) {
                    return false; // Only one compass allowed
                }
                hasCompass = true;
            } else if (ore.test(stack)) {
                oreMatches++;
            } else {
                return false; // Invalid item in crafting grid
            }
        }

        return hasCompass && oreMatches == oreCount;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack compassStack = ItemStack.EMPTY;

        // Find the compass in the crafting grid
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (compass.test(stack)) {
                compassStack = stack.copy();
                break;
            }
        }

        if (compassStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        // Set the tuned ore type
        OreType targetOre = OreType.fromName(oreType);
        if (targetOre != null) {
            OreCompassItem.setTunedOre(compassStack, targetOre);
        }

        return compassStack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 5; // Need at least 5 slots (1 compass + 4 ore)
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        // Return a generic compass as display (actual result is dynamic)
        ItemStack result = new ItemStack(OreCompass.BASIC_ORE_COMPASS.get());
        OreType targetOre = OreType.fromName(oreType);
        if (targetOre != null) {
            OreCompassItem.setTunedOre(result, targetOre);
        }
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return OreCompass.TUNE_COMPASS_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.EQUIPMENT;
    }

    public static class Serializer implements RecipeSerializer<TuneCompassRecipe> {
        @Override
        public TuneCompassRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient compass = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "compass"));
            Ingredient ore = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ore"));
            String oreType = GsonHelper.getAsString(json, "ore_type");
            int oreCount = GsonHelper.getAsInt(json, "ore_count", 4);

            return new TuneCompassRecipe(recipeId, compass, ore, oreType, oreCount);
        }

        @Override
        public TuneCompassRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient compass = Ingredient.fromNetwork(buffer);
            Ingredient ore = Ingredient.fromNetwork(buffer);
            String oreType = buffer.readUtf();
            int oreCount = buffer.readInt();

            return new TuneCompassRecipe(recipeId, compass, ore, oreType, oreCount);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TuneCompassRecipe recipe) {
            recipe.compass.toNetwork(buffer);
            recipe.ore.toNetwork(buffer);
            buffer.writeUtf(recipe.oreType);
            buffer.writeInt(recipe.oreCount);
        }
    }
}

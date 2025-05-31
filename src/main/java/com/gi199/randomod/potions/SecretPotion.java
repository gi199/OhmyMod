package com.gi199.randomod.potions;

import com.gi199.randomod.RandoMod;
import com.gi199.randomod.item.ModItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SecretPotion implements ModInitializer {
    private static final Potion SECRET_POTION = Registry.register(Registries.POTION, Identifier.of(RandoMod.MOD_ID, "tater"), new Potion("tater", new StatusEffectInstance(RandoMod.SECRET_EFFECT, 3600, 0)));

    public static void initialize() {

    }

    @Override
    public void onInitialize() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> builder.registerPotionRecipe(
                // Input potion.
                Potions.WATER,
                // Ingredient
                ModItem.SECRET_FOOD,
                // Output potion.
                Registries.POTION.getEntry(SECRET_POTION)
        ));
    }
}
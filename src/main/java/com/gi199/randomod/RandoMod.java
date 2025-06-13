package com.gi199.randomod;

import com.gi199.randomod.armormaterial.SecretArmorMaterial;
import com.gi199.randomod.block.BlockClasses;
import com.gi199.randomod.effects.SecretEffect;
import com.gi199.randomod.item.ModItem;
import com.gi199.randomod.item.RandoModItemGroup;
import com.gi199.randomod.potions.SecretPotion;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandoMod implements ModInitializer {
    public static final String MOD_ID = "randomod";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final RegistryEntry<StatusEffect> SECRET_EFFECT =
            Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(RandoMod.MOD_ID, "secret_effects"), new SecretEffect());

    @Override
    public final void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");
        ModItem.initialize();
        BlockClasses.initialize();
        SecretPotion.initialize();
        //
        //
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("crash").executes(context -> {
            context.getSource().sendFeedback(() -> Text.literal("Called /crash."), false);
            return 1;
        })));
    //register items in item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(ModItem.SECRET_ITEM));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItem.SECRET_FOOD));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(ModItem.SECRET_SWORD));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(SecretArmorMaterial.SECRET_BOOTS));
    SecretArmorMaterial.initialize();
    RandoModItemGroup.initialize();
    }
}
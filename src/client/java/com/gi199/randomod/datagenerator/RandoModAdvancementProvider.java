package com.gi199.randomod.datagenerator;

import com.gi199.randomod.RandoMod;
import com.gi199.randomod.item.ModItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RandoModAdvancementProvider extends FabricAdvancementProvider {
    protected RandoModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry getSecretFood = Advancement.Builder.create()
                .display(
                        ModItem.SECRET_FOOD, // The display icon
                        Text.literal("Get Secret Food"), // The title
                        Text.literal("End?"), // The description
                        Identifier.ofVanilla("textures/gui/advancements/backgrounds/adventure.png"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
                        AdvancementFrame.TASK, // TASK, CHALLENGE, or GOAL
                        true, // Show the toast when completing it
                        true, // Announce it to chat
                        false // Hide it in the advancement tab until it's achieved
                )
                // "got_secret_food" is the name referenced by other advancements when they want to have "requirements."
                .criterion("got_secret_food", InventoryChangedCriterion.Conditions.items(ModItem.SECRET_FOOD))
                // Give the advancement an id
                .build(consumer, RandoMod.MOD_ID + ":get_secret_food");
        /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final RegistryWrapper.Impl<Item> itemLookup = wrapperLookup.getOrThrow(RegistryKeys.ITEM);
        AdvancementEntry appleAndBeef = Advancement.Builder.create()
                .parent(getSecretFood)
                .display(
                        ModItem.SECRET_ITEM,
                        Text.literal("?"),
                        Text.literal("R\na\nn\nd\no\nM\no\nd"),
                        null, // Children don't need a background, the root advancement takes care of that
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("?", ConsumeItemCriterion.Conditions.item(itemLookup, Items.APPLE))
                .criterion("?", ConsumeItemCriterion.Conditions.item(itemLookup, Items.COOKED_BEEF))
                .build(consumer, RandoMod.MOD_ID + ":?");
    }
}

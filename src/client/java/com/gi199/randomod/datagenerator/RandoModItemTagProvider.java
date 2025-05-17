package com.gi199.randomod.datagenerator;

import com.gi199.randomod.RandoMod;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class RandoModItemTagProvider extends FabricTagProvider<Item> {
    public static final TagKey<Item> X_ITEM = TagKey.of(RegistryKeys.ITEM, Identifier.of(RandoMod.MOD_ID, "x_item"));

    public RandoModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
    }
}

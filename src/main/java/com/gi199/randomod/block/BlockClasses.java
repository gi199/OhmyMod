package com.gi199.randomod.block;


import com.gi199.randomod.RandoMod;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class BlockClasses {
    public static final Block WOW_DIRT = register(Block::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS));
    public static final Block COUNTER_BLOCK = register("counter_block", CounterBlock::new, AbstractBlock.Settings.create(), true);
    private static Block register(Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
        // Create a registry key for the block
        RegistryKey<Block> blockKey = keyOfBlock();
        // Create the block instance
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        // Items need to be registered with a different type of registry key, but the ID
        // can be the same.
        RegistryKey<Item> itemKey = keyOfItem();

        BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, blockItem);

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock() {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(RandoMod.MOD_ID, "wow_dirt"));
    }

    private static RegistryKey<Item> keyOfItem() {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RandoMod.MOD_ID, "wow_dirt"));
    }

    public static void initialize() {
    }
}

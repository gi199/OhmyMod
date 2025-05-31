package com.gi199.randomod.block;


import com.gi199.randomod.RandoMod;
import com.gi199.randomod.editmcc.BoomTNTBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class BlockClasses {
    public static final Block WOW_DIRT = register(Block::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS));
    public static Block register(Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
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
        RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(RandoMod.MOD_ID, "wow_dirt"));
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(RandoMod.MOD_ID, "boom_tnt"));
    }

    private static RegistryKey<Item> keyOfItem() {
        RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RandoMod.MOD_ID, "wow_dirt"));
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RandoMod.MOD_ID, "boom_tnt"));
    }
    public static final Block BOOM_TNT = register(
            BoomTNTBlock::new, // 使用你的自定义 TNT 方块类
            AbstractBlock.Settings.create().strength(0.0F).sounds(BlockSoundGroup.GRASS)
    );
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
                .register((itemGroup) -> itemGroup.add(BlockClasses.WOW_DIRT));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE)
                .register(itemGroup -> itemGroup.add(BOOM_TNT));
    }
}

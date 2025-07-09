package com.gi199.randomod.item;

import com.gi199.randomod.RandoMod;
import com.gi199.randomod.armormaterial.SecretArmorMaterial;
import com.gi199.randomod.block.BlockClasses;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RandoModItemGroup {
    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(RandoMod.MOD_ID, "randomod_item_group"));
    public static final ItemGroup RANDOMOD_CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItem.SECRET_ITEM))
            .displayName(Text.translatable("itemGroup.randomod"))
            .build();
public static void initialize() {
    // Register the group.
    Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, RANDOMOD_CUSTOM_ITEM_GROUP);

    // Register items to the custom item group.
    ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
        itemGroup.add(ModItem.SECRET_ITEM);
        itemGroup.add(ModItem.SECRET_FOOD);
        itemGroup.add(ModItem.SECRET_SWORD);
        itemGroup.add(SecretArmorMaterial.SECRET_HELMET);
        itemGroup.add(SecretArmorMaterial.SECRET_BOOTS);
        itemGroup.add(SecretArmorMaterial.SECRET_LEGGINGS);
        itemGroup.add(SecretArmorMaterial.SECRET_CHESTPLATE);
        itemGroup.add(BlockClasses.WOW_DIRT);
    });
    }
}

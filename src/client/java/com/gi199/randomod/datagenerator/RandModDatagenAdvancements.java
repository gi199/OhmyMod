package com.gi199.randomod.datagenerator;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;

public class RandModDatagenAdvancements implements ModInitializer {
    @Override
    public void onInitialize() {
        HashMap<Item, Integer> tools = new HashMap<>();

        PlayerBlockBreakEvents.AFTER.register(((world, player, blockPos, blockState, blockEntity) -> {
            if (player instanceof ServerPlayerEntity serverPlayer) { // Only triggers on the server side
                Item item = player.getMainHandStack().getItem();

                Integer usedCount = tools.getOrDefault(item, 0);
                usedCount++;
                tools.put(item, usedCount);

                serverPlayer.sendMessage(Text.of("You've used \"" + item + "\" as a tool " + usedCount + " times!"));
            }
        }));
    }
}

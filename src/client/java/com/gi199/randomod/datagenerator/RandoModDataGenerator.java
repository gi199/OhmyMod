package com.gi199.randomod.datagenerator;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.NotNull;

class RandoModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public final void onInitializeDataGenerator(@NotNull FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(RandoModItemTagProvider::new);
        //pack.addProvider(RandoModAdvancementProvider::new);
    }
}

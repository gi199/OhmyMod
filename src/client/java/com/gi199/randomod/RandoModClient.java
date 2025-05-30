package com.gi199.randomod;

import com.gi199.randomod.optimiz.BlockRenderOptimiz;
import net.fabricmc.api.ClientModInitializer;

public class RandoModClient implements ClientModInitializer {
    /**
     *
     */
    @Override
    public final void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.

        // For this example, we will use the end rod particle behaviour.
        BlockRenderOptimiz.initialize();
    }
        
}
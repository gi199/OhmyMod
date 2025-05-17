package com.gi199.randomod;

import com.gi199.randomod.criterion.ModCriteria;
import com.gi199.randomod.render.RandoModClientRenderer;
import net.fabricmc.api.ClientModInitializer;

public class RandoModClient implements ClientModInitializer {
    /**
     *
     */
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        RandoModClientRenderer.main();
        ModCriteria.init();
    }
}
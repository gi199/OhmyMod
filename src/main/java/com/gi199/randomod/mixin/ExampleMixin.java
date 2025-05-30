package com.gi199.randomod.mixin;

import com.gi199.randomod.RandoMod;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
abstract class ExampleMixin {
    @Shadow
    @Final
    private static Logger LOGGER;


    @Inject(at = @At("HEAD"), method = "loadWorld")
    private void init(CallbackInfo info) {
        LOGGER.info(RandoMod.MOD_ID);
        // This code is injected into the start of MinecraftServer.loadWorld()V

    }
}
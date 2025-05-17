package com.gi199.randomod.mixin.client;

import net.minecraft.client.render.Tessellator;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Tessellator.class)
public class ExampleClientMixin {
    @Inject(at = @At("HEAD"), method = "initialize")
    private static void init(CallbackInfo info) {
		/*
		  Woew
		 */
        LoggerFactory.getLogger("");
    }
}
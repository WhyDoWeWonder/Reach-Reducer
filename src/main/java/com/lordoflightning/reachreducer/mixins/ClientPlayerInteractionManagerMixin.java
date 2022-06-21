package com.lordoflightning.reachreducer.mixins;

import com.lordoflightning.reachreducer.config.ModConfig;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.network.ClientPlayerInteractionManager;


@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin

{
    @Shadow
    private GameMode gameMode;

    /**
     * blockHitDelay
     */

    @Inject(at = {@At("HEAD")},
            method = {"getReachDistance()F"},
            cancellable = true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> ci)
    {
        ci.setReturnValue(gameMode.isCreative() ? ModConfig.INSTANCE.getCreativeBlockReachDistance() :
                ModConfig.INSTANCE.getBlockReachDistance());
    }
}


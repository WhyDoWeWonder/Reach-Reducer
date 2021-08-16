package com.lordoflightning.reachreducer;

import com.lordoflightning.reachreducer.config.ConfigInstance;
import com.lordoflightning.reachreducer.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class ReachReducerMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModConfig.init();
    }
}

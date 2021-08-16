package com.lordoflightning.reachreducer.mixins;

import com.lordoflightning.reachreducer.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow @Final
    private MinecraftClient client;

    @ModifyConstant(
            method = "updateTargetedEntity(F)V",
            require = 1, allow = 1, constant = @Constant(doubleValue = 6.0))
    private double getActualReachDistance(final double reachDistance) {
        if (this.client.player != null) {
            return ModConfig.INSTANCE.getCreativeAttackReachDistance();
        }
        return reachDistance;
    }

    @ModifyConstant(method = "updateTargetedEntity(F)V", constant = @Constant(doubleValue = 3.0))
    private double getActualAttackRange0(final double attackRange) {
        if (this.client.player != null) {
            return ModConfig.INSTANCE.getAttackReachDistance();
        }
        return attackRange;
    }

    @ModifyConstant(method = "updateTargetedEntity(F)V", constant = @Constant(doubleValue = 9.0))
    private double getActualAttackRange1(final double attackRange) {
        if (this.client.player != null) {
            float reach;
            if (this.client.player.abilities.creativeMode) {
                reach = ModConfig.INSTANCE.getCreativeAttackReachDistance();
            } else {
                reach = ModConfig.INSTANCE.getAttackReachDistance();
            }
            return reach * reach;
        }
        return attackRange;
    }

//    /* Overwrites the updateTargetedEntity Function to change the reach distance variables within the function
//    because Mojank™ code
//    There's probably a bug or unintended side effect from changing e and d (very helpful variable names, thanks Fabric!)
//    But honestly it's good enough and I don't care enough to do the extensive testing required
//     */
//    // d = Block Reach Distance
//    // e = Attack/Entity Reach Distance (is later set to itself squared)
//    @Overwrite
//    public void updateTargetedEntity(float tickDelta) {
//        Entity entity = ReachReducerConstants.client.getCameraEntity();
//        if (entity != null) {
//            if (ReachReducerConstants.client.world != null) {
//                double nonCreativeAttackReach = ModConfig.INSTANCE.getAttackReachDistance();
//                double creativeAttackReach = ModConfig.INSTANCE.getCreativeAttackReachDistance();
//
//                /* This variable is used for the "interacting with a block behind an entity check" below described in
//                 far more detail.
//
//                 It is set to either the creative mode or non-creative mode reach depending on
//                 which gamemode the player is in and while it's true that this variable wouldn't be necessary if the
//                 structure of the code was modified slightly, it would be better to keep the code structure as close
//                 to vanilla as possible in case it changes in the feature and for comparability.
//                */
//                double attackReach;
//
//                ReachReducerConstants.client.getProfiler().push("pick");
//                ReachReducerConstants.client.targetedEntity = null;
//                double d = (double)ReachReducerConstants.client.interactionManager.getReachDistance();
//                ReachReducerConstants.client.crosshairTarget = entity.raycast(d, tickDelta, false);
//                Vec3d vec3d = entity.getCameraPosVec(tickDelta);
//                boolean bl = false;
//                double e = d;
//                if (ReachReducerConstants.client.interactionManager.hasExtendedReach()) {
//                    e = creativeAttackReach;
//                    d = e;
//                    attackReach = creativeAttackReach;
//                } else {
//                    e = nonCreativeAttackReach;
//                    attackReach = nonCreativeAttackReach;
//                }
//
//                /* In vanilla this check is only done if the player doesn't have extended reach (IE is not in creative)
//                Since attack reach is 5.0 and hit reach is 6.0, but with reach reducer it's possible to set the attack
//                reach in creative mode below that of the block reach, necessitating the movement of this check here.
//                */
//                if (d > nonCreativeAttackReach) {
//                    bl = true;
//                }
//
//                // Changes e from entity distance to itself squared
//                e *= e;
//
//                if (ReachReducerConstants.client.crosshairTarget != null) {
//                    e = ReachReducerConstants.client.crosshairTarget.getPos().squaredDistanceTo(vec3d);
//                }
//
//                Vec3d vec3d2 = entity.getRotationVec(1.0F);
//                Vec3d vec3d3 = vec3d.add(vec3d2.x * d, vec3d2.y * d, vec3d2.z * d);
//                float f = 1.0F;
//                Box box = entity.getBoundingBox().stretch(vec3d2.multiply(d)).expand(1.0D, 1.0D, 1.0D);
//                EntityHitResult entityHitResult = ProjectileUtil.raycast(entity, vec3d, vec3d3, box, (entityx) -> {
//                    return !entityx.isSpectator() && entityx.collides();
//                }, e);
//                if (entityHitResult != null) {
////                    System.out.println("Hit");
//                    Entity entity2 = entityHitResult.getEntity();
//                    Vec3d vec3d4 = entityHitResult.getPos();
//                    double g = vec3d.squaredDistanceTo(vec3d4);
//
//                    /* The Purpose of the if statement below is to prevent players from interacting with the world
//                    (IE mining blocks) behind an entity out of reach distance. For example, consider a player that is
//                    looking at a zombie 3.1 blocks away with a block behind them, if the player attacks the player
//                    should but since block reach in survival is 4.5 blocks without this check the player would mine the
//                    block behind the husk, effectively mining through entity targets.
//
//                    This check prevents that by checking if bl is true (which is only true if the block reach is greater
//                    than the attack reach) then checks if the distance to the entity being targeted is more than the
//                    attack reach distance (but for some reason uses these values squared, a convention I have kept to
//                    using for worry of breaking vanilla behaviour).
//                    */
//                    if (bl && g > attackReach * attackReach) {
//                        ReachReducerConstants.client.crosshairTarget = BlockHitResult.createMissed(vec3d4, Direction.getFacing(vec3d2.x, vec3d2.y, vec3d2.z), new BlockPos(vec3d4));
//                    } else if (g < e || ReachReducerConstants.client.crosshairTarget == null) {
//                        ReachReducerConstants.client.crosshairTarget = entityHitResult;
//                        if (entity2 instanceof LivingEntity || entity2 instanceof ItemFrameEntity) {
//                            ReachReducerConstants.client.targetedEntity = entity2;
//                        }
//                    }
//                }
//
//                ReachReducerConstants.client.getProfiler().pop();
//            }
//        }
//    }
}
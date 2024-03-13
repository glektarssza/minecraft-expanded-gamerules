package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * Mixins relating to mobs.
 */
@Mixin(MobEntity.class)
public abstract class MobMixins extends LivingEntity {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    public MobMixins(EntityType<? extends LivingEntity> entityType, World level) {
        super(entityType, level);
    }

    /**
     * Set the target of this entity.
     *
     * @param entity The entity to set as the target.
     * @param ci     The callback information.
     */
    @Inject(at = @At("HEAD"), method = "setTarget(Lnet/minecraft/entity/LivingEntity;)V", cancellable = true)
    public void setTarget(LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof PlayerEntity
                && GameruleUtilities.getBooleanGamerule(this.level, "disableTargetingPlayers")) {
            ci.cancel();
            return;
        }
    }
}

package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * Mixins relating to living entities.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixins extends Entity {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    public LivingEntityMixins(EntityType<?> entityType, World level) {
        super(entityType, level);
    }

    /**
     * Check whether a living entity can attack another living entity.
     *
     * @param entity The entity to check if this living entity can attack.
     * @param ci     The callback information.
     */
    @Inject(at = @At("HEAD"), method = "canAttack(Lnet/minecraft/entity/LivingEntity;)Z", cancellable = true)
    public void canAttack(LivingEntity entity, CallbackInfoReturnable<Boolean> ci) {
        if (entity instanceof PlayerEntity
                && GameruleUtilities.getBooleanGamerule(this.level, "disableTargetingPlayers")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

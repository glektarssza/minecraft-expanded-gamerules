package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Mixins relating to living entities.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixins extends LivingEntity {
    /**
     * Required to make Java be quiet.
     *
     * @param entityType The entity type.
     * @param level      The level.
     */
    protected LivingEntityMixins(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Check whether a living entity can attack another living entity.
     *
     * @param entity The entity to check if this living entity can attack.
     * @param ci     The callback information.
     */
    @Inject(at = @At("HEAD"), method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", cancellable = true)
    public void canAttack(LivingEntity entity, CallbackInfoReturnable<Boolean> ci) {
        if (entity instanceof Player && GameruleUtilities.getBooleanGamerule(this.level(), "disableTargetingPlayers")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

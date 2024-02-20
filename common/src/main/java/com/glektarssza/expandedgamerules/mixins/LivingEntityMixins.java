package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Mixins relating to living entities.
 */
@Mixin(LivingEntity.class)
public class LivingEntityMixins {
    /**
     * Check whether a living entity can attack another living entity.
     *
     * @param entity The entity to check if this living entity can attack.
     * @param ci     The callback information.
     */
    @Inject(at = @At("HEAD"), method = "", cancellable = true)
    public static void canAttack(LivingEntity entity, CallbackInfoReturnable<Boolean> ci) {
        if (entity instanceof Player && GameruleUtilities.getBooleanGamerule(null, "disableTargetingPlayers")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

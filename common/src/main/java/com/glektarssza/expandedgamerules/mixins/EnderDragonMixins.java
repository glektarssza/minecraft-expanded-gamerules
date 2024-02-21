package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;

/**
 * Mixins relating to Ender Dragons.
 */
@Mixin(EnderDragon.class)
public abstract class EnderDragonMixins {
    /**
     * Check whether a living entity can attack another living entity.
     *
     * @param entity The entity to check if this living entity can attack.
     * @param ci     The callback information.
     */
    @Inject(at = @At("HEAD"), method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", cancellable = true)
    public void canAttack(LivingEntity entity, CallbackInfoReturnable<Boolean> ci) {
        var self = (EnderDragon) (Object) this;
        if (entity instanceof Player && GameruleUtilities.getBooleanGamerule(self.level(), "disableTargetingPlayers")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

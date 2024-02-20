package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.monster.EnderMan;

/**
 * Mixins relating to Endermen.
 */
@Mixin(EnderMan.class)
public final class EnderManMixins {
    /**
     * Attempt to teleport the Enderman.
     *
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "teleport(DDD)Z", cancellable = true)
    public static void teleport(double x, double y, double z, CallbackInfoReturnable<Boolean> ci) {
        if (GameruleUtilities.getBooleanGamerule(null, "disableEndermanTeleport")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

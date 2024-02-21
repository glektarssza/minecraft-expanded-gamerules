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
public abstract class EndermanMixins {
    /**
     * Attempt to teleport the Enderman.
     *
     * @param x  The x coordinate.
     * @param y  The y coordinate.
     * @param z  The z coordinate.
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "teleport(DDD)Z", cancellable = true)
    public void teleport(double x, double y, double z, CallbackInfoReturnable<Boolean> ci) {
        var self = (EnderMan) (Object) this;
        if (GameruleUtilities.getBooleanGamerule(self.level(), "disableShulkerTeleport")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

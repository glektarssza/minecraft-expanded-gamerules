package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.monster.Shulker;

/**
 * Mixins relating to Shulkers.
 */
@Mixin(Shulker.class)
public class ShulkerMixins {
    /**
     * Teleport the Shulker.
     *
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "teleportSomewhere()Z", cancellable = true)
    public void teleportSomewhere(CallbackInfoReturnable<Boolean> ci) {
        var self = (Shulker) (Object) this;
        if (GameruleUtilities.getBooleanGamerule(self.level(), "disableEndermanTeleport")) {
            ci.setReturnValue(false);
        }
    }
}

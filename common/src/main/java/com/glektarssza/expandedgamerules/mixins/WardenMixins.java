package com.glektarssza.expandedgamerules.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;

@Mixin(Warden.class)
public abstract class WardenMixins {
    @Inject(at = @At("HEAD"), method = "canTargetEntity(Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    public void canTargetEntity(@Nullable Entity entity, CallbackInfoReturnable<Boolean> ci) {
        var self = (Warden) (Object) this;
        if (entity instanceof Player && GameruleUtilities.getBooleanGamerule(self.level(), "disableTargetingPlayers")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

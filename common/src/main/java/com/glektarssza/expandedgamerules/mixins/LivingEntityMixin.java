package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.api.IBooleanGamerule;
import com.glektarssza.expandedgamerules.platform.Services;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("HEAD"), method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", cancellable = true)
    public void canAttack(LivingEntity entity, CallbackInfoReturnable<Boolean> ci) {
        if (!Services.PLATFORM.hasGamerule(new ResourceLocation("expandedgamerules", "disableTargetingPlayers"))) {
            return;
        }
        var rule = Services.PLATFORM.getGamerule(new ResourceLocation("expandedgamerules", "disableTargetingPlayers"));
        if (!(rule instanceof IBooleanGamerule)) {
            return;
        }
        if (!((IBooleanGamerule) rule).getValue()) {
            return;
        }
        ci.setReturnValue(false);
    }
}

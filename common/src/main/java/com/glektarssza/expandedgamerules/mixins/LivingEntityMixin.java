package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.api.IBooleanGamerule;
import com.glektarssza.expandedgamerules.platform.Services;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;;

/**
 * Mixins for living entities.
 */
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    /**
     * Mixin for the `canAttack` method.
     *
     * @param entity The entity being checked.
     * @param ci     The callback information.
     */
    @Inject(at = @At("HEAD"), method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", cancellable = true)
    public void canAttack(LivingEntity entity, CallbackInfoReturnable<Boolean> ci) {
        Services.REGISTRY.getGamerule(new ResourceLocation("expandedgamerules", "disableTargetingPlayers"))
                .ifPresent((rule) -> {
                    if (!(rule instanceof IBooleanGamerule)) {
                        return;
                    }
                    if (!((IBooleanGamerule) rule).getValue()) {
                        return;
                    }
                    ci.setReturnValue(false);
                });
    }
}

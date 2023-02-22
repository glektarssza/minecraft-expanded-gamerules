package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.events.MobChangeTargetCallback;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.ActionResult;

@Mixin(MobEntity.class)
public class MobChangeTargetMixin {
    @Inject(at = @At("INVOKE"), cancellable = true)
    private void setTarget(final LivingEntity target, CallbackInfo info) {
        MobEntity self = (MobEntity) ((Object) this);
        ActionResult result = MobChangeTargetCallback.EVENT.invoker().changeTarget(self, target);
        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}

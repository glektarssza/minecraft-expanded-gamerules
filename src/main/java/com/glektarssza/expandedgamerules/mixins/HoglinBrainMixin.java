package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.mob.HoglinBrain;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.util.ActionResult;

import com.glektarssza.expandedgamerules.events.MobChangeTargetCallback;
import com.google.common.collect.ImmutableList;;

@Mixin(HoglinBrain.class)
public class HoglinBrainMixin {
    @Inject(method = "refreshActivities", at = @At("HEAD"), cancellable = true)
    private static void refreshActivities(HoglinEntity entity) {
        Brain<HoglinEntity> brain = entity.getBrain();
        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
        brain.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.AVOID, Activity.IDLE));
        Activity activity2 = brain.getFirstPossibleNonCoreActivity().orElse(null);
    }
}

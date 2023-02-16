package com.glektarssza.expandedgamerules.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.ExpandedGamerules;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.HoglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.FakePlayer;

@Mixin(HoglinTasks.class)
public class HoglinTasksMixin {
    @Inject(method = "getSoundForCurrentActivity", at = @At("HEAD"), cancellable = true)
    private static void getSoundForCurrentActivity(HoglinEntity entity,
            CallbackInfoReturnable<Optional<SoundEvent>> info) {
        // -- Check if gamerule is enabled
        if (!ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, "disableTargetingPlayers")
                .orElse(false)) {
            return;
        }
        Brain<HoglinEntity> brain = entity.getBrain();
        Optional<LivingEntity> target = brain.getMemory(MemoryModuleType.ATTACK_TARGET);
        if (!target.isPresent()) {
            return;
        }
        if (!(target.get() instanceof PlayerEntity) || target.get() instanceof FakePlayer) {
            return;
        }
        Optional<Activity> activity = brain.getActiveNonCoreActivity();
        if (!activity.isPresent() || activity.get() != Activity.FIGHT) {
            return;
        }
        // -- Hoglin is targeting a player and trying to fight them!
        // -- Don't play the sound effect!
        info.setReturnValue(Optional.empty());
    }

    @Inject(method = "updateActivity", at = @At("HEAD"), cancellable = false)
    private static void updateActivity(HoglinEntity entity, CallbackInfo info) {
        // -- Check if gamerule is enabled
        if (!ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, "disableTargetingPlayers")
                .orElse(false)) {
            return;
        }
        Brain<HoglinEntity> brain = entity.getBrain();
        Optional<LivingEntity> target = brain.getMemory(MemoryModuleType.ATTACK_TARGET);
        if (!target.isPresent()) {
            return;
        }
        if (!(target.get() instanceof PlayerEntity) || target.get() instanceof FakePlayer) {
            return;
        }
        Optional<Activity> activity = brain.getActiveNonCoreActivity();
        if (!activity.isPresent() || activity.get() != Activity.FIGHT) {
            return;
        }
        // -- Hoglin is targeting a player and trying to fight them!
        // -- Don't play the animation!
        entity.setAggressive(false);
        // -- Also force them back to being idle!
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.AVOID, Activity.IDLE));
    }
}
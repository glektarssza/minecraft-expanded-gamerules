package com.glektarssza.expandedgamerules.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.ExpandedGamerules;

import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.monster.piglin.PiglinBruteBrain;

@Mixin(PiglinBruteBrain.class)
public class PiglinBruteBrainMixin {
    @Inject(method = "playActivitySound", at = @At("HEAD"), cancellable = true)
    private static void playActivitySound(PiglinBruteEntity entity, CallbackInfo info) {
        // -- Check if gamerule is enabled
        if (!ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, "disableTargetingPlayers")
                .orElse(false)) {
            return;
        }
        Brain<PiglinBruteEntity> brain = entity.getBrain();
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
        // -- PiglinBrute is targeting a player and trying to fight them!
        // -- Don't play the sound effect!
        info.cancel();
    }

    @Inject(method = "updateActivity", at = @At("TAIL"), cancellable = true)
    private static void updateActivity(PiglinBruteEntity entity, CallbackInfo info) {
        // -- Check if gamerule is enabled
        if (!ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, "disableTargetingPlayers")
                .orElse(false)) {
            return;
        }
        Brain<PiglinBruteEntity> brain = entity.getBrain();
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
        // -- Piglin Brute is targeting a player and trying to fight them!
        // -- Don't play the animation!
        entity.setAggressive(false);
    }
}

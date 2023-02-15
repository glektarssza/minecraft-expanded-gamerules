package com.glektarssza.expandedgamerules.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.glektarssza.expandedgamerules.ExpandedGamerules;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.HoglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.FakePlayer;

@Mixin(HoglinTasks.class)
public class HoglinTasksMixin {
    @Inject(method = "updateActivity", at = @At("HEAD"), cancellable = false)
    public static void updateActivity(HoglinEntity entity) {
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
        // -- Check if target is a player
        if (!(target.get() instanceof PlayerEntity) || target.get() instanceof FakePlayer) {
            return;
        }
        // -- Hoglin is targeting a player, don't do that!
        if (brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
        }
        if (brain.hasMemoryValue(MemoryModuleType.ANGRY_AT)) {
            brain.eraseMemory(MemoryModuleType.ANGRY_AT);
        }
        if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
            brain.eraseMemory(MemoryModuleType.UNIVERSAL_ANGER);
        }
        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD)) {
            brain.eraseMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
        }
        entity.setTarget(null);
        Optional<Activity> activity = brain.getActiveNonCoreActivity();
        // -- If the Hoglin is fighting, change it back to idling.
        // -- This stops the spamming of the aggression sound effect.
        if (activity.isPresent() && activity.get() == Activity.FIGHT) {
            brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
        }
    }
}

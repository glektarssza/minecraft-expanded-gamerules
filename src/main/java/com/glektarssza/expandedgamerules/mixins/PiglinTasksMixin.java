package com.glektarssza.expandedgamerules.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.ExpandedGamerules;
import com.glektarssza.expandedgamerules.utils.MobUtils;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.FakePlayer;

@Mixin(PiglinTasks.class)
public class PiglinTasksMixin {
    @Inject(at = @At("HEAD"), method = "updateActivity", cancellable = true)
    private static void updateActivity(PiglinEntity entity, CallbackInfo info) {
        Brain<PiglinEntity> brain = entity.getBrain();
        Activity activityBefore = brain.getActiveNonCoreActivity().orElse(null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        Activity activityAfter = brain.getActiveNonCoreActivity().orElse(null);
        Optional<LivingEntity> target = brain.getMemory(MemoryModuleType.ATTACK_TARGET);
        boolean hasResetTarget = false;
        if (activityAfter == Activity.FIGHT && target.isPresent() && target.get() instanceof PlayerEntity
                && !(target.get() instanceof FakePlayer) && ExpandedGamerules.GAMERULE_REGISTRY
                        .isGameruleEnabled(entity.level, "disableTargetingPlayers").orElse(false)) {
            // -- Trying to fight the player? Not allowed, lol
            MobUtils.resetTarget(entity);
            hasResetTarget = true;
        }
        if (activityBefore != activityAfter && !hasResetTarget) {
            PiglinTasks.getSoundForCurrentActivity(entity).ifPresent(entity::playSound);
        }
    }
}

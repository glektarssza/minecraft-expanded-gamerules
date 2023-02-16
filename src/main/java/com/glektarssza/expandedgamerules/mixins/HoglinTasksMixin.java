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
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.HoglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.FakePlayer;

@Mixin(HoglinTasks.class)
public class HoglinTasksMixin {
    @Inject(method = "updateActivity", at = @At("HEAD"), cancellable = false)
    private static void updateActivity(HoglinEntity entity, CallbackInfo info) {
        Brain<HoglinEntity> brain = entity.getBrain();
        Activity activityBefore = brain.getActiveNonCoreActivity().orElse((Activity) null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.AVOID, Activity.IDLE));
        Activity activityAfter = brain.getActiveNonCoreActivity().orElse((Activity) null);
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
            HoglinTasks.getSoundForCurrentActivity(entity).ifPresent(entity::playSound);
        }
    }
}

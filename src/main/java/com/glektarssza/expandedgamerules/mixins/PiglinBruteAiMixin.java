package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

import com.glektarssza.expandedgamerules.ExpandedGamerules;
import com.glektarssza.expandedgamerules.utils.MobUtils;
import com.google.common.collect.ImmutableList;

import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.monster.piglin.PiglinBruteAi;

@Mixin(PiglinBruteAi.class)
public class PiglinBruteAiMixin {
    @Inject(at = @At("HEAD"), method = "updateActivity", cancellable = true)
    private static void updateActivity(PiglinBrute entity, CallbackInfo info) {
        Brain<PiglinBrute> brain = entity.getBrain();
        Activity activityBefore = brain.getActiveNonCoreActivity().orElse(null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        Activity activityAfter = brain.getActiveNonCoreActivity().orElse(null);
        Optional<LivingEntity> target = brain.getMemory(MemoryModuleType.ATTACK_TARGET);
        boolean hasResetTarget = false;
        if (activityAfter == Activity.FIGHT && target.isPresent() && target.get() instanceof Player
                && !(target.get() instanceof FakePlayer) && ExpandedGamerules.GAMERULE_REGISTRY
                        .isGameruleEnabled(entity.level, "disableTargetingPlayers").orElse(false)) {
            // -- Trying to fight the player? Not allowed, lol
            MobUtils.resetTarget(entity);
            hasResetTarget = true;
        }
        if (activityBefore != activityAfter && !hasResetTarget) {
            PiglinBruteAi.playActivitySound(entity);
        }
    }
}

package com.glektarssza.expandedgamerules.utils;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;

public class MobUtils {
    /**
     * Reset the target of the given mob.
     *
     * @param entity The mob to reset the target of.
     */
    public static void resetTarget(MobEntity entity) {
        Brain<?> brain = entity.getBrain();
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
        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)) {
            brain.eraseMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
        }
        entity.setTarget(null);
        entity.setAggressive(false);
    }
}

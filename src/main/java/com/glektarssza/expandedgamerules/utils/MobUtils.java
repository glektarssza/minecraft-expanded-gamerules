package com.glektarssza.expandedgamerules.utils;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class MobUtils {
    /**
     * Reset the target of the given mob.
     *
     * @param entity The mob to reset the target of.
     */
    public static void resetTarget(Mob entity) {
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
        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)) {
            brain.eraseMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
        }
        entity.setTarget(null);
        entity.setAggressive(false);
    }
}

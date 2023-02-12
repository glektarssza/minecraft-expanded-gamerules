package com.glektarssza.expandedgamerules;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.GameRules.Category;
import net.minecraft.world.GameRules.IntegerValue;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraft.world.GameRules.RuleType;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class GameruleRegistry {
    /**
     * A map of all registered gamerules to their IDs.
     */
    private final Map<String, RuleKey<?>> gamerules;

    /**
     * Create a new instance.
     */
    public GameruleRegistry() {
        gamerules = new HashMap<>();
    }

    /**
     * Register a new gamerule.
     *
     * @param id           The ID of the gamerule to register.
     * @param defaultValue Whether the gamerule is enabled by default.
     */
    public void registerGamerule(String id, Category category, boolean defaultValue) {
        RuleKey<BooleanValue> rule = GameRules.register(id, category, BooleanValue.create(defaultValue));
        gamerules.put(id, rule);
    }

    /**
     * Register a new gamerule.
     *
     * @param id           The ID of the gamerule to register.
     * @param defaultValue The default value of the gamerule.
     */
    public void registerGamerule(String id, Category category, int defaultValue) {
        RuleKey<IntegerValue> rule = GameRules.register(id, category, IntegerValue.create(defaultValue));
        gamerules.put(id, rule);
    }

    /**
     * Check if the registry contains a gamerule with the given ID.
     *
     * @param id The ID to check for.
     *
     * @return `true` if the registry contains a gamerule with the given ID, `false`
     *         otherwise.
     */
    public boolean hasGamerule(String id) {
        return gamerules.containsKey(id);
    }

    /**
     * Check whether the gamerule with the given ID is enabled.
     *
     * @param world The world to check the gamerule in.
     * @param id    The ID of the gamerule to check.
     *
     * @return `true` if the gamerule is enabled, `false` otherwise.
     */
    public boolean isGameruleEnabled(World world, String id) {
        if (!hasGamerule(id)) {
            return false;
        }
        RuleKey<?> ruleKey = gamerules.get(id);
        if (!(ruleKey.getClass().getTypeParameters()[0] instanceof BooleanValue)) {
            return false;
        }
        return world.getGameRules().getBoolean((RuleKey<BooleanValue>) gamerules.get(id));
    }

    /**
     * Check whether the gamerule with the given ID is enabled.
     *
     * @param world The world to check the gamerule in.
     * @param id    The ID of the gamerule to check.
     *
     * @return `true` if the gamerule is enabled, `false` otherwise.
     */
    public boolean isGameruleEnabled(IWorld world, String id) {
        if (!hasGamerule(id)) {
            return false;
        }
        RuleKey<?> ruleKey = gamerules.get(id);
        if (!(ruleKey.getClass().getTypeParameters()[0] instanceof BooleanValue)) {
            return false;
        }
        return ((World) world).getGameRules().getBoolean((RuleKey<BooleanValue>) gamerules.get(id));
    }

    public int getGameruleValue(World world, String id) {
        if (!hasGamerule(id)) {
            return 0;
        }
        RuleKey<?> ruleKey = gamerules.get(id);
        if (!(ruleKey.getClass().getTypeParameters()[0] instanceof IntegerValue)) {
            return 0;
        }
        return world.getGameRules().getInt((RuleKey<IntegerValue>) gamerules.get(id));
    }

    public int getGameruleValue(IWorld world, String id) {
        if (!hasGamerule(id)) {
            return 0;
        }
        RuleKey<?> ruleKey = gamerules.get(id);
        if (!(ruleKey.getClass().getTypeParameters()[0] instanceof IntegerValue)) {
            return 0;
        }
        return ((World) world).getGameRules().getInt((RuleKey<IntegerValue>) gamerules.get(id));
    }
}

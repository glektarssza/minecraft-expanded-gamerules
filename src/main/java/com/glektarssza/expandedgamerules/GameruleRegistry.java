package com.glektarssza.expandedgamerules;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Key;
import net.minecraftforge.common.extensions.IForgeLevel;
import net.minecraft.world.level.Level;

public class GameruleRegistry {
    /**
     * A map of all registered gamerules to their IDs.
     */
    private final Map<String, Key<?>> gamerules;

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
        Key<BooleanValue> rule = GameRules.register(id, category, BooleanValue.create(defaultValue));
        gamerules.put(id, rule);
    }

    /**
     * Register a new gamerule.
     *
     * @param id           The ID of the gamerule to register.
     * @param defaultValue The default value of the gamerule.
     */
    public void registerGamerule(String id, Category category, int defaultValue) {
        Key<IntegerValue> rule = GameRules.register(id, category, IntegerValue.create(defaultValue));
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
     * @param level The world to check the gamerule in.
     * @param id    The ID of the gamerule to check.
     *
     * @return The enabled state of the gamerule if it exists, an empty optional
     *         otherwise.
     */
    @SuppressWarnings("unchecked")
    public Optional<Boolean> isGameruleEnabled(Level level, String id) {
        if (!hasGamerule(id)) {
            return Optional.empty();
        }
        Key<?> ruleKey = gamerules.get(id);
        if (!(ruleKey.getClass().getTypeParameters()[0] instanceof BooleanValue)) {
            return Optional.empty();
        }
        return Optional.of(level.getGameRules().getBoolean((Key<BooleanValue>) gamerules.get(id)));
    }

    /**
     * Check whether the gamerule with the given ID is enabled.
     *
     * @param level The world to check the gamerule in.
     * @param id    The ID of the gamerule to check.
     *
     * @return The enabled state of the gamerule if it exists, an empty optional
     *         otherwise.
     */
    @SuppressWarnings("unchecked")
    public Optional<Boolean> isGameruleEnabled(IForgeLevel level, String id) {
        if (!hasGamerule(id)) {
            return Optional.empty();
        }
        Key<?> ruleKey = gamerules.get(id);
        if (!(ruleKey.getClass().getTypeParameters()[0] instanceof BooleanValue)) {
            return Optional.empty();
        }
        return Optional.of(((Level) level).getGameRules().getBoolean((Key<BooleanValue>) gamerules.get(id)));
    }

    /**
     * Get the value of the gamerule with the given ID.
     *
     * @param level The world to check the gamerule in.
     * @param id    The ID of the gamerule to get the value for.
     *
     * @return The value of the gamerule if it exists, an empty optional
     *         otherwise.
     */
    @SuppressWarnings("unchecked")
    public Optional<Integer> getGameruleValue(Level level, String id) {
        if (!hasGamerule(id)) {
            return Optional.empty();
        }
        Key<?> ruleKey = gamerules.get(id);
        if (!(ruleKey.getClass().getTypeParameters()[0] instanceof IntegerValue)) {
            return Optional.empty();
        }
        return Optional.of(level.getGameRules().getInt((Key<IntegerValue>) gamerules.get(id)));
    }

    /**
     * Get the value of the gamerule with the given ID.
     *
     * @param level The world to check the gamerule in.
     * @param id    The ID of the gamerule to get the value for.
     *
     * @return The value of the gamerule if it exists, an empty optional
     *         otherwise.
     */
    @SuppressWarnings("unchecked")
    public Optional<Integer> getGameruleValue(IForgeLevel level, String id) {
        if (!hasGamerule(id)) {
            return Optional.empty();
        }
        Key<?> ruleKey = gamerules.get(id);
        if (!(ruleKey.getClass().getTypeParameters()[0] instanceof IntegerValue)) {
            return Optional.empty();
        }
        return Optional.of(((Level) level).getGameRules().getInt((Key<IntegerValue>) gamerules.get(id)));
    }
}

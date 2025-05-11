package com.glektarssza.expandedgamerules.api.v1.gamerule;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;
import net.minecraft.world.level.GameRules.Value;

import com.glektarssza.expandedgamerules.mixins.vanilla.gamerules.GamerulesMixins;

/**
 * An interface which defines an object for registering and accessing gamerules.
 */
public interface IGameruleRegistry {
    /**
     * Check if a gamerule exists.
     *
     * @param id The ID of the gamerule to look for.
     *
     * @return {@code true} if the gamerule exists; {@code false} otherwise.
     */
    public default boolean hasGamerule(String id) {
        return GamerulesMixins.accessGameruleTypes().keySet().stream()
            .anyMatch((item) -> item.getId().equals(id.toString()));
    }

    /**
     * Get the current value of a boolean gamerule.
     *
     * @param id The resource location of the gamerule to get the boolean value
     *        of.
     *
     * @return The boolean value of the gamerule.
     */
    @SuppressWarnings("unchecked")
    public default boolean getBooleanGamerule(Level level, String id) {
        var key = GamerulesMixins.accessGameruleTypes().keySet().stream()
            .filter((item) -> item.getId().equals(id.toString())).findAny()
            .orElseThrow();
        return level.getGameRules().getBoolean((Key<BooleanValue>) key);
    }

    /**
     * Get the current value of an integer gamerule.
     *
     * @param id The resource location of the gamerule to get the integer value
     *        of.
     *
     * @return The integer value of the gamerule.
     */
    @SuppressWarnings("unchecked")
    public default int getIntegerGamerule(Level level, String id) {
        var key = GamerulesMixins.accessGameruleTypes().keySet().stream()
            .filter((item) -> item.getId().equals(id.toString())).findAny()
            .orElseThrow();
        return level.getGameRules().getInt((Key<IntegerValue>) key);
    }

    /**
     * Register a new gamerule.
     *
     * @param id The resource location to register the new gamerule at. Must be
     *        unqiue.
     * @param category The category to register the new gamerule under.
     * @param type The type of gamerule to register.
     *
     * @return The key of the newly registered gamerule.
     *
     * @throws IllegalStateException Thrown if the resource location given is
     *         already in use.
     */
    public default <T extends Value<T>> Key<T> register(String id,
        Category category, Type<T> type) throws IllegalStateException {
        return GamerulesMixins.invokeRegister(id, category,
            type);
    }

    /**
     * Register a new gamerule with a custom category.
     *
     * @param id The resource location to register the new gamerule at. Must be
     *        unqiue.
     * @param category The category to register the new gamerule under.
     * @param type The type of gamerule to register.
     *
     * @return The key of the newly registered gamerule.
     *
     * @throws IllegalStateException Thrown if the resource location given is
     *         already in use.
     */
    public default <T extends Value<T>> Key<T> register(String id,
        CustomCategory category, Type<T> type) throws IllegalStateException {
        var ret = GamerulesMixins.invokeRegister(id, Category.MISC, type);
        ((IGameruleKeyExtensions) (Object) ret).setCustomCategory(category);
        return ret;
    }
}

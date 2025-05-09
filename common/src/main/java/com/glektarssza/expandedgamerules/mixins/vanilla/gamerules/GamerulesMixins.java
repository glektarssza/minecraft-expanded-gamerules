package com.glektarssza.expandedgamerules.mixins.vanilla.gamerules;

import java.util.Map;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;
import net.minecraft.world.level.GameRules.Value;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * A mixin into the {@link net.minecraft.world.level.GameRules} class which
 * provides access to some of its internal methods.
 */
@Mixin(GameRules.class)
public interface GamerulesMixins {
    /**
     * Invoke the {@code register} method of the
     * {@link net.minecraft.world.level.GameRules} class.
     *
     * @param key The key to register the new gamerule under. This must be
     *        unique.
     * @param category The category to register the new gamerule under.
     * @param type The type of value to register the new gamerule as.
     *
     * @return The key the new gamerule was registered under.
     */
    @Invoker("register")
    static <T extends Value<T>> Key<T> invokeRegister(String key,
        Category category, Type<T> type) {
        throw new AssertionError(String.format(
            "Failed to apply mixin to invoke method 'register' of class '%s'!",
            GameRules.class.getName()));
    }

    /**
     * Get the {@code GAME_RULE_TYPES} field off the
     * {@link net.minecraft.world.level.GameRules} class.
     *
     * @return The {@code GAME_RULE_TYPES} field.
     */
    @Accessor("GAME_RULE_TYPES")
    static Map<Key<?>, Type<?>> accessGameruleTypes() {
        throw new AssertionError(String.format(
            "Failed to apply mixin to access field 'GAME_RULE_TYPES' of class '%s'!",
            GameRules.class.getName()));
    }
}

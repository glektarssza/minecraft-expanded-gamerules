package com.glektarssza.expandedgamerules.mixins.vanilla.gamerules;

import java.util.function.BiConsumer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Type;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * A mixin into the {@link net.minecraft.world.level.GameRules.IntegerValue}
 * class which provides access to some of its internal methods.
 */
@Mixin(IntegerValue.class)
public interface IntegerValueMixins {
    /**
     * Invoke the {@code create} method of the
     * {@link net.minecraft.world.level.GameRules.IntegerValue} class.
     *
     * @param initialValue The initial value to create the new gamerule with.
     * @param changeListener The callback to trigger when the gamerule changes.
     *
     * @return The newly created gamerule.
     */
    @Invoker("create")
    static Type<IntegerValue> invokeCreate(int initialValue,
        BiConsumer<MinecraftServer, IntegerValue> changeListener) {
        throw new AssertionError(String.format(
            "Failed to apply mixin to invoke method 'create' of class '%s'!",
            IntegerValue.class.getName()));
    }
}

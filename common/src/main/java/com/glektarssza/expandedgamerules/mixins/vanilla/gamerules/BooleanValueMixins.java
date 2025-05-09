package com.glektarssza.expandedgamerules.mixins.vanilla.gamerules;

import java.util.function.BiConsumer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Type;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * A mixin into the {@link net.minecraft.world.level.GameRules.BooleanValue}
 * class which provides access to some of its internal methods.
 */
@Mixin(BooleanValue.class)
public interface BooleanValueMixins {
    @Invoker("create")
    static Type<BooleanValue> invokeCreate(boolean initialValue,
        BiConsumer<MinecraftServer, BooleanValue> changeListener) {
        throw new AssertionError(String.format(
            "Failed to apply mixin to invoke method 'create' of class '%s'!",
            BooleanValue.class.getName()));
    }
}

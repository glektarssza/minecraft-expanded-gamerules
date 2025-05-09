package com.glektarssza.expandedgamerules.mixins.vanilla.gamerules;

import javax.annotation.Nullable;

import net.minecraft.world.level.GameRules.Key;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.glektarssza.expandedgamerules.api.v1.gamerule.CustomCategory;
import com.glektarssza.expandedgamerules.api.v1.gamerule.IGameruleKeyExtensions;

/**
 * A mixin into the {@link net.minecraft.world.level.GameRules.Key} class which
 * extends it which the functionality defined in the
 * {@link com.glektarssza.expandedgamerules.api.v1.gamerule.IGameruleKeyExtensions}
 * interface.
 */
@Mixin(Key.class)
public abstract class KeyMixins implements IGameruleKeyExtensions {
    /**
     * The custom category this instance is registered to.
     */
    @Unique
    @Nullable
    private CustomCategory customCategory;

    /**
     * Get the custom category this instance is registered to.
     *
     * @return If this instance is registered to a custom category then the
     *         custom category this instance is registered to; {@code null}
     *         otherwise.
     */
    @Override
    @Nullable
    public CustomCategory getCustomCategory() {
        return this.customCategory;
    }

    /**
     * Set the custom category this instance is registered to.
     *
     * @param customCategory The custom category this instance should be
     *        registered to, if any, or {@code null} to clear the custom
     *        category registration.
     */
    @Override
    public void setCustomCategory(@Nullable CustomCategory customCategory) {
        this.customCategory = customCategory;
    }
}

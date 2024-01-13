package com.glektarssza.expandedgamerules.api;

import net.minecraft.resources.ResourceLocation;

/**
 * A registry for gamerules.
 */
public interface IGameruleRegistry {
    /**
     * Register a gamerule.
     *
     * The gamerule will be registered under the resource location from calling
     * {@link IGamerule#getResourceLocation()}.
     *
     * @param gamerule The gamerule to register.
     *
     * @throws IllegalArgumentException If a gamerule is already registered
     *                                  under the given key.
     */
    public void registerGamerule(IGamerule<?> gamerule);

    /**
     * Register a gamerule.
     *
     * @param key      The key to register the gamerule under.
     * @param gamerule The gamerule to register.
     *
     * @throws IllegalArgumentException If a gamerule is already registered
     *                                  under the given key.
     */
    public void registerGamerule(ResourceLocation key, IGamerule<?> gamerule);

    /**
     * Get whether a gamerule is registered.
     *
     * The gamerule will be checked based on the resource location from calling
     * {@link IGamerule#getResourceLocation()}.
     *
     * @param gamerule The gamerule to check.
     *
     * @returns Whether the gamerule is registered.
     */
    public boolean isGameruleRegistered(IGamerule<?> gamerule);

    /**
     * Get whether a gamerule is registered.
     *
     * @param key The key to check.
     *
     * @returns Whether the gamerule is registered.
     */
    public boolean isGameruleRegistered(ResourceLocation key);

    /**
     * Get the gamerule registered under the given key.
     *
     * @param key The key to get the gamerule for.
     *
     * @returns The gamerule registered under the given key.
     */
    public <T> IGamerule<T> getGamerule(ResourceLocation key);
}

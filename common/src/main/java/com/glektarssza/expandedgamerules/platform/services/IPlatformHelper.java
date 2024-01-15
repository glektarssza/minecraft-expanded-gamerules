package com.glektarssza.expandedgamerules.platform.services;

import com.glektarssza.expandedgamerules.api.IGamerule;

import javax.annotation.Nonnull;

import com.glektarssza.expandedgamerules.api.ICallback;

import net.minecraft.resources.ResourceLocation;

/**
 * An interface for platform-specific helper service classes.
 */
public interface IPlatformHelper {
    /**
     * Get the name of the platform.
     *
     * @return The name of the platform.
     */
    public String getPlatformName();

    /**
     * Check whether the named mod is loaded.
     *
     * @param modId The ID of the mod to check.
     *
     * @return Whether the named mod is loaded.
     */
    public boolean isModLoaded(@Nonnull String modId);

    /**
     * Check whether the current environment is a development environment.
     *
     * @return Whether the current environment is a development environment.
     */
    public boolean isDevelopmentEnvironment();

    /**
     * Get the name of the current environment.
     *
     * @return The name of the current environment.
     */
    public default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Initialize the gamerule registry.
     *
     * @param callback The method to call once the registry is initialized.
     */
    public void initializeGameruleRegistry(@Nonnull ICallback callback);

    /**
     * Register a gamerule into the gamerule registry.
     *
     * @param id       The ID of the gamerule to register.
     * @param gamerule The gamerule to register.
     *
     * @throws IllegalArgumentException If a gamerule is already registered with
     *                                  the given ID.
     */
    public void registerGamerule(@Nonnull ResourceLocation id, @Nonnull IGamerule gamerule)
            throws IllegalArgumentException;

    /**
     * Check if a gamerule is registered with the given ID.
     *
     * @param id The ID of the gamerule to check.
     *
     * @return Whether a gamerule is registered with the given ID.
     */
    public boolean hasGamerule(@Nonnull ResourceLocation id);

    /**
     * Get the gamerule registered with the given ID.
     *
     * @param id The ID of the gamerule to get.
     *
     * @return The gamerule registered with the given ID.
     */
    public IGamerule getGamerule(@Nonnull ResourceLocation id);
}

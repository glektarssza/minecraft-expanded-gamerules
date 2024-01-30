package com.glektarssza.expandedgamerules.platform.services;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.management.openmbean.KeyAlreadyExistsException;

import com.glektarssza.expandedgamerules.api.IGamerule;

import net.minecraft.resources.ResourceLocation;

/**
 * An interface which defines the shape of a service that provides various
 * methods for interacting with registries.
 */
public interface IRegistryHelper {
    /**
     * A simple callback that is triggered when the gamerule registry is
     * initialized.
     */
    public interface IRegistryInitializedCallback {
        /**
         * Call the callback.
         */
        public void apply();
    }

    /**
     * Check whether the gamerule registry is initialized.
     *
     * @return Whether the gamerule registry is initialized.
     */
    public boolean isGameruleRegistryInitialized();

    /**
     * Initialize the gamerule registry.
     *
     * @param callback The callback to call when the registry has been
     *                 initialized.
     */
    public void initializeGameruleRegistry(@Nonnull IRegistryInitializedCallback callback);

    /**
     * Register a new gamerule.
     *
     * @param key      The identifier to register the gamerule to. This parameter
     *                 will be prefixed with the mod ID.
     * @param gamerule The gamerule to register.
     */
    public void registerGamerule(@Nonnull String key, @Nonnull IGamerule gamerule)
            throws NullPointerException, KeyAlreadyExistsException;

    /**
     * Register a new gamerule.
     *
     * @param key      The identifier to register the gamerule to.
     * @param gamerule The gamerule to register.
     */
    public void registerGamerule(@Nonnull ResourceLocation key, @Nonnull IGamerule gamerule)
            throws NullPointerException, KeyAlreadyExistsException;

    /**
     * Get a gamerule from the registry with the given identifier.
     *
     * @param key The identifier to get a gamerule for. This parameter will be
     *            prefixed with the mod ID.
     *
     * @return The gamerule that has the given identifier.
     */
    @Nullable
    public IGamerule getGamerule(@Nonnull String key) throws NullPointerException;

    /**
     * Get a gamerule from the registry with the given identifier.
     *
     * @param key The identifier to get a gamerule for.
     *
     * @return The gamerule that has the given identifier.
     */
    @Nullable
    public IGamerule getGamerule(@Nonnull ResourceLocation key) throws NullPointerException;
}

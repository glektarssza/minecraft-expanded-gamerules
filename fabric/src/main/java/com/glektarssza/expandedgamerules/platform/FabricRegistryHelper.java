package com.glektarssza.expandedgamerules.platform;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.management.openmbean.KeyAlreadyExistsException;

import com.glektarssza.expandedgamerules.Constants;
import com.glektarssza.expandedgamerules.api.IGamerule;
import com.glektarssza.expandedgamerules.platform.services.IRegistryHelper;
import com.mojang.serialization.Lifecycle;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FabricRegistryHelper implements IRegistryHelper {
    /**
     * The gamerule registry.
     */
    @Nullable
    private MappedRegistry<IGamerule> registry;

    /**
     * Check whether the gamerule registry is initialized.
     *
     * @return Whether the gamerule registry is initialized.
     */
    public boolean isGameruleRegistryInitialized() {
        return this.registry != null;
    }

    /**
     * Initialize the gamerule registry.
     *
     * @param callback The callback to call when the registry has been
     *                 initialized.
     */
    public void initializeGameruleRegistry(@Nonnull IRegistryInitializedCallback callback) {
        this.registry = FabricRegistryBuilder
                .<IGamerule>createSimple(ResourceKey.createRegistryKey(new ResourceLocation(Constants.MOD_ID)))
                .buildAndRegister();
        callback.apply();
    }

    /**
     * Register a new gamerule.
     *
     * @param key      The identifier to register the gamerule to. This parameter
     *                 will be prefixed with the mod ID.
     * @param gamerule The gamerule to register.
     */
    public void registerGamerule(@Nonnull String key, @Nonnull IGamerule gamerule)
            throws NullPointerException, KeyAlreadyExistsException {
        this.registerGamerule(new ResourceLocation(Constants.MOD_ID, key), gamerule);
    }

    /**
     * Register a new gamerule.
     *
     * @param key      The identifier to register the gamerule to.
     * @param gamerule The gamerule to register.
     */
    public void registerGamerule(@Nonnull ResourceLocation key, @Nonnull IGamerule gamerule)
            throws NullPointerException, KeyAlreadyExistsException {
        if (this.registry == null) {
            throw new NullPointerException();
        }
        var resourceKey = ResourceKey.create(this.registry.key(), key);
        this.registry.register(resourceKey, gamerule, Lifecycle.stable());
    }

    /**
     * Get a gamerule from the registry with the given identifier.
     *
     * @param key The identifier to get a gamerule for. This parameter will be
     *            prefixed with the mod ID.
     *
     * @return The gamerule that has the given identifier.
     */
    @Nullable
    public IGamerule getGamerule(@Nonnull String key) throws NullPointerException {
        return this.getGamerule(new ResourceLocation(Constants.MOD_ID, key));
    }

    /**
     * Get a gamerule from the registry with the given identifier.
     *
     * @param key The identifier to get a gamerule for.
     *
     * @return The gamerule that has the given identifier.
     */
    @Nullable
    public IGamerule getGamerule(@Nonnull ResourceLocation key) throws NullPointerException {
        if (this.registry == null) {
            throw new NullPointerException();
        }
        return this.registry.get(key);
    }
}

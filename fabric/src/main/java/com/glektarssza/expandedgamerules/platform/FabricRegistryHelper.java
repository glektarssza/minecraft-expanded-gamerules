package com.glektarssza.expandedgamerules.platform;

import java.util.Optional;

import javax.annotation.Nonnull;

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
     * @param key      The identifier to register the gamerule to.
     * @param gamerule The gamerule to register.
     */
    public void registerGamerule(@Nonnull ResourceLocation key, @Nonnull IGamerule gamerule)
            throws IllegalStateException, IllegalArgumentException {
        if (this.registry == null) {
            throw new IllegalStateException("Gamerule registry is not initialized");
        }
        if (this.registry.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Gamerule \"%1$s\" already exists", key));
        }
        this.registry.register(ResourceKey.create(this.registry.key(), key), gamerule, Lifecycle.stable());
    }

    /**
     * Get a gamerule from the registry with the given identifier.
     *
     * @param key The identifier to get a gamerule for.
     *
     * @return The gamerule that has the given identifier.
     */
    @Override
    public Optional<IGamerule> getGamerule(@Nonnull ResourceLocation key) throws IllegalStateException {
        if (this.registry == null) {
            throw new IllegalStateException("Gamerule registry is not initialized");
        }
        return this.registry.getOptional(key);
    }
}

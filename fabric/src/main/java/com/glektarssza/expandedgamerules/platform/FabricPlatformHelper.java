package com.glektarssza.expandedgamerules.platform;

import javax.annotation.Nonnull;

import com.glektarssza.expandedgamerules.Constants;
import com.glektarssza.expandedgamerules.api.ICallback;
import com.glektarssza.expandedgamerules.api.IGamerule;
import com.glektarssza.expandedgamerules.platform.services.IPlatformHelper;
import com.mojang.serialization.Lifecycle;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * The Fabric-specific platform helper.
 */
public class FabricPlatformHelper implements IPlatformHelper {
    /**
     * The gamerule registry.
     */
    private MappedRegistry<IGamerule> gameruleRegistry;

    /**
     * Get the name of the platform.
     *
     * @return The name of the platform.
     */
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    /**
     * Check whether the named mod is loaded.
     *
     * @param modId The ID of the mod to check.
     *
     * @return Whether the named mod is loaded.
     */
    @Override
    public boolean isModLoaded(@Nonnull String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    /**
     * Check whether the current environment is a development environment.
     *
     * @return Whether the current environment is a development environment.
     */
    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    /**
     * Initialize the gamerule registry.
     */
    @Override
    public void initializeGameruleRegistry(@Nonnull ICallback callback) {
        Constants.LOG.info("Initializing expanded gamerule registry...");
        gameruleRegistry = FabricRegistryBuilder
                .<IGamerule>createSimple(
                        ResourceKey.createRegistryKey(new ResourceLocation("expandedgamerules", "gamerules")))
                .attribute(RegistryAttribute.SYNCED)
                .buildAndRegister();
        Constants.LOG.info("Expanded gamerule registry created and registered");
        callback.apply();
    }

    @Override
    public void registerGamerule(@Nonnull ResourceLocation id,
            @Nonnull IGamerule gamerule) throws IllegalArgumentException {
        Constants.LOG.debug("Registering new expanded gamerule with ID {}", id);
        if (gameruleRegistry.containsKey(id)) {
            throw new IllegalArgumentException("A gamerule is already registered with the given ID");
        }
        var lc = Lifecycle.experimental();
        if (lc == null) {
            throw new NullPointerException();
        }
        var regKey = gameruleRegistry.key();
        if (regKey == null) {
            throw new NullPointerException();
        }
        var key = ResourceKey.create(regKey, id);
        if (key == null) {
            throw new NullPointerException();
        }
        gameruleRegistry.register(key, gamerule, lc);
        Constants.LOG.debug("New expanded gamerule registered", id);
    }

    @Override
    public boolean hasGamerule(@Nonnull ResourceLocation id) {
        return gameruleRegistry.containsKey(id);
    }

    @Override
    public IGamerule getGamerule(@Nonnull ResourceLocation id) {
        return gameruleRegistry.get(id);
    }
}

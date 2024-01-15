package com.glektarssza.expandedgamerules.platform;

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
    public boolean isModLoaded(String modId) {
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
    public void initializeGameruleRegistry(ICallback callback) {
        gameruleRegistry = FabricRegistryBuilder
                .<IGamerule>createSimple(
                        ResourceKey.createRegistryKey(new ResourceLocation("expandedgamerules", "gamerules")))
                .attribute(RegistryAttribute.SYNCED)
                .buildAndRegister();
        callback.apply();
    }

    @Override
    public void registerGamerule(ResourceLocation id, IGamerule gamerule) throws IllegalArgumentException {
        if (gameruleRegistry.containsKey(id)) {
            throw new IllegalArgumentException("A gamerule is already registered with the given ID");
        }
        gameruleRegistry.register(ResourceKey.create(gameruleRegistry.key(), id), gamerule, Lifecycle.experimental());
    }

    @Override
    public boolean hasGamerule(ResourceLocation id) {
        return gameruleRegistry.containsKey(id);
    }

    @Override
    public IGamerule getGamerule(ResourceLocation id) {
        return gameruleRegistry.get(id);
    }
}

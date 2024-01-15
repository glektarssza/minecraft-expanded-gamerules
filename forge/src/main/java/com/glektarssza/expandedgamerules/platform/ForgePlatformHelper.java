package com.glektarssza.expandedgamerules.platform;

import com.glektarssza.expandedgamerules.api.ICallback;
import com.glektarssza.expandedgamerules.api.IGamerule;
import com.glektarssza.expandedgamerules.platform.services.IPlatformHelper;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * The Forge-specific platform helper.
 */
public class ForgePlatformHelper implements IPlatformHelper {
    /**
     * The gamerule registry.
     */
    private IForgeRegistry<IGamerule> gameruleRegistry;

    /**
     * Get the name of the platform.
     *
     * @return The name of the platform.
     */
    @Override
    public String getPlatformName() {
        return "Forge";
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
        return ModList.get().isLoaded(modId);
    }

    /**
     * Check whether the current environment is a development environment.
     *
     * @return Whether the current environment is a development environment.
     */
    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    /**
     * Initialize the gamerule registry.
     */
    @Override
    public void initializeGameruleRegistry(ICallback callback) {
        var builder = RegistryBuilder.<IGamerule>of(new ResourceLocation("expandedgamerules", "gamerules"));
        MinecraftForge.EVENT_BUS.addListener((NewRegistryEvent event) -> {
            event.create(builder, (registry) -> {
                gameruleRegistry = registry;
                callback.apply();
            });
        });
    }

    @Override
    public void registerGamerule(ResourceLocation id, IGamerule gamerule) throws IllegalArgumentException {
        if (gameruleRegistry.containsKey(id)) {
            throw new IllegalArgumentException("A gamerule is already registered with the given ID");
        }
        gameruleRegistry.register(id, gamerule);
    }

    @Override
    public boolean hasGamerule(ResourceLocation id) {
        return gameruleRegistry.containsKey(id);
    }

    @Override
    public IGamerule getGamerule(ResourceLocation id) {
        return gameruleRegistry.getValue(id);
    }
}

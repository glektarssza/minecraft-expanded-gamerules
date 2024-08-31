package com.glektarssza.expandedgamerules.platform;

import javax.annotation.Nonnull;

import com.glektarssza.expandedgamerules.platform.services.IPlatformHelper;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

/**
 * The Forge-specific platform helper.
 */
public class NeoForgePlatformHelper implements IPlatformHelper {
    /**
     * Get the name of the platform.
     *
     * @return The name of the platform.
     */
    @Override
    @Nonnull
    public String getPlatformName() {
        return "NeoForge";
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
}

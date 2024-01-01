package com.glektarssza.expandedgamerules.platform;

import com.glektarssza.expandedgamerules.platform.services.IPlatformHelper;

import net.fabricmc.loader.api.FabricLoader;

/**
 * The Fabric-specific platform helper.
 */
public class FabricPlatformHelper implements IPlatformHelper {
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
}

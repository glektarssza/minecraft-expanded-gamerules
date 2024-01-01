package com.glektarssza.expandedgamerules.platform;

import com.glektarssza.expandedgamerules.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

/**
 * The Forge-specific platform helper.
 */
public class ForgePlatformHelper implements IPlatformHelper {
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
}

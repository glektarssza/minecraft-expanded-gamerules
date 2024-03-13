package com.glektarssza.expandedgamerules.platform.services;

import javax.annotation.Nonnull;

/**
 * An interface for platform-specific helper service classes.
 */
public interface IPlatformHelper {
    /**
     * Get the name of the platform.
     *
     * @return The name of the platform.
     */
    @Nonnull
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
    @Nonnull
    public default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }
}

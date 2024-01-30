package com.glektarssza.expandedgamerules.platform;

import com.glektarssza.expandedgamerules.Constants;
import com.glektarssza.expandedgamerules.platform.services.IPlatformHelper;
import com.glektarssza.expandedgamerules.platform.services.IRegistryHelper;

import java.util.ServiceLoader;

/**
 * Common mod service loader.
 */
public class Services {
    /**
     * The platform helper.
     */
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    /**
     * The registry helper.
     */
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);

    /**
     * Load a platform-specific service implementation.
     *
     * @param <T>   The type of service to load.
     * @param clazz The service class to load the platform-specific
     *              implementation for.
     *
     * @return The loaded service implementation.
     */
    public static <T> T load(Class<T> clazz) {
        Constants.LOG.debug("Loading platform-specific service {}...", clazz);
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for platform-specific service {}", loadedService, clazz);
        return loadedService;
    }
}

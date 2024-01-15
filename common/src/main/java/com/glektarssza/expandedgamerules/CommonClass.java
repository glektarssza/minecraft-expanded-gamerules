package com.glektarssza.expandedgamerules;

import javax.annotation.Nonnull;

import com.glektarssza.expandedgamerules.api.IGamerule;
import com.glektarssza.expandedgamerules.platform.Services;

import net.minecraft.resources.ResourceLocation;

/**
 * Common mod code root class.
 */
public class CommonClass {
    /**
     * Initialize the common mod code.
     */
    public static void init() {
        Constants.LOG.info("Initializing common mod code for {}...", Constants.MOD_ID);
        Constants.LOG.info("Currently running on {} in a {} environment",
                Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Services.PLATFORM.initializeGameruleRegistry(() -> {
            Constants.LOG.info("Adding built-in rules...");
            // TODO: Add built-in rules
            Constants.LOG.info("Common mod code for {} has been initialized", Constants.MOD_ID);
        });
    }

    /**
     * Register a gamerule into the gamerule registry.
     *
     * @param id       The ID of the gamerule to register.
     * @param gamerule The gamerule to register.
     *
     * @throws IllegalArgumentException If a gamerule is already registered with
     *                                  the given ID.
     */
    public static void registerGamerule(@Nonnull ResourceLocation id,
            @Nonnull IGamerule gamerule) throws IllegalArgumentException {
        Services.PLATFORM.registerGamerule(id, gamerule);
    }

    /**
     * Check if a gamerule is registered with the given ID.
     *
     * @param id The ID of the gamerule to check.
     *
     * @returns Whether a gamerule is registered with the given ID.
     */
    public static boolean hasGamerule(@Nonnull ResourceLocation id) {
        return Services.PLATFORM.hasGamerule(id);
    }

    /**
     * Get the gamerule registered with the given ID.
     *
     * @param id The ID of the gamerule to get.
     *
     * @returns The gamerule registered with the given ID.
     */
    public static IGamerule getGamerule(@Nonnull ResourceLocation id) {
        return Services.PLATFORM.getGamerule(id);
    }
}

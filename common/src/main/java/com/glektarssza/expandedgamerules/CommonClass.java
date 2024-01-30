package com.glektarssza.expandedgamerules;

import java.util.Optional;

import javax.annotation.Nonnull;

import com.glektarssza.expandedgamerules.api.IGamerule;
import com.glektarssza.expandedgamerules.platform.Services;
import com.glektarssza.expandedgamerules.rules.DisableTargetingPlayersRule;

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
        Services.REGISTRY.initializeGameruleRegistry(() -> {
            Constants.LOG.info("Adding built-in rules...");
            registerBuiltinRules();
            Constants.LOG.info("Common mod code for {} has been initialized", Constants.MOD_ID);
        });
    }

    /**
     * Register the built-in gamerules.
     */
    public static void registerBuiltinRules() {
        registerGamerule(new ResourceLocation("expandedgamerules", "disableTargetingPlayers"),
                new DisableTargetingPlayersRule());
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
            @Nonnull IGamerule gamerule) throws IllegalStateException, IllegalArgumentException {
        Services.REGISTRY.registerGamerule(id, gamerule);
    }

    /**
     * Get the gamerule registered with the given ID.
     *
     * @param id The ID of the gamerule to get.
     *
     * @return The gamerule registered with the given ID.
     */
    public static Optional<IGamerule> getGamerule(@Nonnull ResourceLocation id) throws IllegalStateException {
        return Services.REGISTRY.getGamerule(id);
    }
}

package com.glektarssza.expandedgamerules;

import com.glektarssza.expandedgamerules.platform.Services;

import net.minecraft.world.level.GameRules.Category;

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
        registerBuiltinRules();
    }

    /**
     * Register the built-in gamerules.
     */
    public static void registerBuiltinRules() {
        GameruleUtilities.register("disableTargetingPlayers", Category.MOBS, false);
        GameruleUtilities.register("disableShulkerTeleport", Category.MOBS, false);
        GameruleUtilities.register("disableEndermanTeleport", Category.MOBS, false);
    }
}

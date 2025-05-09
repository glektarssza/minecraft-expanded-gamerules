package com.glektarssza.expandedgamerules;

import com.glektarssza.expandedgamerules.api.v1.gamerule.CustomCategory;
import com.glektarssza.expandedgamerules.platform.Services;

import net.minecraft.world.level.GameRules.Category;

/**
 * Common mod code root class.
 */
public final class CommonClass {
    /**
     * The custom category for environmental-related gamerules.
     */
    public static final CustomCategory CATEGORY_ENVIRONMENT = new CustomCategory(
        String.format("%s:envrion", Constants.MOD_ID),
        String.format("%s.gamerules.category.envrion", Constants.MOD_ID));

    /**
     * Initialize the common mod code.
     */
    public static void init() {
        Constants.LOG.info("Initializing common mod code for {}...",
            Constants.MOD_ID);
        Constants.LOG.info("Currently running on {} in a {} environment",
            Services.PLATFORM.getPlatformName(),
            Services.PLATFORM.getEnvironmentName());
        registerBuiltinRules();
    }

    /**
     * Register the built-in gamerules.
     */
    public static void registerBuiltinRules() {
        GameruleUtilities.register("disableTargetingPlayers", Category.MOBS,
            false);
        GameruleUtilities.register("disableShulkerTeleport", Category.MOBS,
            false);
        GameruleUtilities.register("disableEndermanTeleport", Category.MOBS,
            false);
        GameruleUtilities.register("doColdBiomesFreezeWater",
            CATEGORY_ENVIRONMENT, true);
    }
}

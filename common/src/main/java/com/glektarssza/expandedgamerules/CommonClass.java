package com.glektarssza.expandedgamerules;

import com.glektarssza.expandedgamerules.platform.Services;

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
    }
}

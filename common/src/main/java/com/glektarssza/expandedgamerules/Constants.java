package com.glektarssza.expandedgamerules;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common mod constants.
 */
public class Constants {
    /**
     * The ID of the mod.
     */
    @Nonnull
    public static final String MOD_ID = "expandedgamerules";

    /**
     * The name of the mod.
     */
    @Nonnull
    public static final String MOD_NAME = "Expanded Gamerules";

    /**
     * The mod logger.
     */
    @Nonnull
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
}

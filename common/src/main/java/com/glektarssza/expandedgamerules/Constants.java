package com.glektarssza.expandedgamerules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glektarssza.expandedgamerules.api.v1.gamerule.IGameruleRegistry;
import com.glektarssza.expandedgamerules.impl.GameruleRegistry;

/**
 * Common mod constants.
 */
public final class Constants {
    /**
     * The ID of the mod.
     */
    public static final String MOD_ID = "expandedgamerules";

    /**
     * The name of the mod.
     */
    public static final String MOD_NAME = "Expanded Gamerules";

    /**
     * The mod logger.
     */
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    /**
     * The gamerule registry provided by the mod.
     */
    public static final IGameruleRegistry GAMERULE_REGISTRY = new GameruleRegistry();
}

package com.glektarssza.expandedgamerules;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The root mod class.
 */
@Mod(ExpandedGamerules.MOD_ID)
public class ExpandedGamerules {
    /**
     * The ID of the mod.
     */
    public static final String MOD_ID = "expandedgamerules";

    /**
     * The logger to use for the mod.
     */
    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * Create a new instance.
     */
    public ExpandedGamerules() {
        // TODO: Load config
        // TODO: Load gamerule registry
        // TODO: Set up connections to event bus
    }
}

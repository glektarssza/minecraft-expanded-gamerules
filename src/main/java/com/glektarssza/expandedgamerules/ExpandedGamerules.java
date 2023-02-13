package com.glektarssza.expandedgamerules;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glektarssza.expandedgamerules.gamerules.DisableTargetingPlayersGamerule;

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
     * The registry of gamerules.
     */
    public static final GameruleRegistry GAMERULE_REGISTRY = new GameruleRegistry();

    /**
     * Create a new instance.
     */
    public ExpandedGamerules() {
        registerGamerules();
    }

    /**
     * Register this mod's gamerules.
     */
    public void registerGamerules() {
        new DisableTargetingPlayersGamerule().register(GAMERULE_REGISTRY);
    }
}

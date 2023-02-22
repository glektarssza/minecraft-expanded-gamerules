package com.glektarssza.expandedgamerules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glektarssza.expandedgamerules.gamerules.DisableTargetingPlayers;

import net.fabricmc.api.ModInitializer;;

public class ExpandedGamerules implements ModInitializer {
    public static final String MOD_ID = "expandedgamerules";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        new DisableTargetingPlayers().setup();
    }
}

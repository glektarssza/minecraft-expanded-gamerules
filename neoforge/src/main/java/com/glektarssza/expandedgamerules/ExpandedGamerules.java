package com.glektarssza.expandedgamerules;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * The main mod class for the Forge mod loader.
 */
@Mod(Constants.MOD_ID)
public class ExpandedGamerules {
    /**
     * Create a new instance.
     */
    public ExpandedGamerules(IEventBus eventBus) {
        CommonClass.init();
    }
}

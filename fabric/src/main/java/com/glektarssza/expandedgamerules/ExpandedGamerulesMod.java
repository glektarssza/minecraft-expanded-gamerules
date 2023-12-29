package com.glektarssza.expandedgamerules;

import net.fabricmc.api.ModInitializer;

public class ExpandedGamerulesMod implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }
}

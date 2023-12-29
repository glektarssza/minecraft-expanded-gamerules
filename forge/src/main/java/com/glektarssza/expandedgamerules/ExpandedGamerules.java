package com.glektarssza.expandedgamerules;

import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ExpandedGamerules {
    public ExpandedGamerules() {
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();
    }
}

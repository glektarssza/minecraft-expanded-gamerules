package com.glektarssza.expandedgamerules;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ExpandedGamerules {
    public ExpandedGamerules() {
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();
        MinecraftForge.EVENT_BUS.addListener(this::onItemTooltip);
    }

    private void onItemTooltip(ItemTooltipEvent event) {
        CommonClass.onItemTooltip(event.getItemStack(), event.getFlags(), event.getToolTip());
    }
}

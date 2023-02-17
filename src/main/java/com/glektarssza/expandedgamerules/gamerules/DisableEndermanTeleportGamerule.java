package com.glektarssza.expandedgamerules.gamerules;

import com.glektarssza.expandedgamerules.ExpandedGamerules;
import com.glektarssza.expandedgamerules.GameruleRegistry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.world.GameRules.Category;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DisableEndermanTeleportGamerule {
    /**
     * The ID of the gamerule.
     */
    public static final String ID = "disableEndermanTeleport";

    /**
     * The default value of the gamerule.
     */
    public static final boolean DEFAULT_VALUE = false;

    /**
     * The category of the gamerule.
     */
    public static final Category CATEGORY = Category.MOBS;

    /**
     * Register this gamerule.
     *
     * @param registry The registry to register this gamerule to.
     */
    public void register(GameruleRegistry registry) {
        registry.registerGamerule(ID, CATEGORY, DEFAULT_VALUE);
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Handle the event that is fired when an Ender entity is about to teleport.
     */
    @SubscribeEvent
    public void onEnderTeleport(EntityTeleportEvent.EnderEntity event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof EndermanEntity
                && ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, ID).orElse(false)) {
            event.setCanceled(true);
        }
    }
}

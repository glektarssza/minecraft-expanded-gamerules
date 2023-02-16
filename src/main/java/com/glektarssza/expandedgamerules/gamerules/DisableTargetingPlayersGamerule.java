package com.glektarssza.expandedgamerules.gamerules;

import com.glektarssza.expandedgamerules.ExpandedGamerules;
import com.glektarssza.expandedgamerules.GameruleRegistry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules.Category;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * A gamerule that disables targeting players.
 */
public class DisableTargetingPlayersGamerule {
    /**
     * The ID of the gamerule.
     */
    public static final String ID = "disableTargetingPlayers";

    /**
     * The default value of the gamerule.
     */
    public static final boolean DEFAULT_VALUE = false;

    /**
     * The category of the gamerule.
     */
    public static final Category CATEGORY = Category.MOBS;

    /**
     * Create a new instance.
     */
    public DisableTargetingPlayersGamerule() {
        // -- Does nothing
    }

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
     * Handle the event that is fired when a living entity sets its attack
     * target.
     */
    @SubscribeEvent
    public void onLivingChangeTargetEvent(LivingChangeTargetEvent event) {
        Entity entity = event.getEntity();
        // -- Gamerule is not enabled, do nothing
        if (!ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, ID).orElse(false)) {
            return;
        }
        // -- Entity is not a mob
        if (!(entity instanceof Mob)) {
            return;
        }
        LivingEntity target = event.getNewTarget();
        // -- Target is not a player or is a fake player
        if (!(target instanceof Player) || target instanceof FakePlayer) {
            return;
        }
        event.setCanceled(true);
    }

    /**
     * Handle the event that is fired when a living entity updates.
     */
    @SubscribeEvent
    public void OnLivingUpdateEvent(LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        // -- Gamerule is not enabled, do nothing
        if (!ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, ID).orElse(false)) {
            return;
        }
        // -- Entity is not a mob
        if (!(entity instanceof Hoglin)) {
            return;
        }
        Hoglin hoglin = (Hoglin) entity;
        Brain<Hoglin> brain = hoglin.getBrain();
        LivingEntity target = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        // -- Target is not a player or is a fake player
        if (!(target instanceof Player) || target instanceof FakePlayer) {
            return;
        }
        brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
    }
}

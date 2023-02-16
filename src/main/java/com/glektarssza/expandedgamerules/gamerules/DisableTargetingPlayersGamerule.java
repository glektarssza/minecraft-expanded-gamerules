package com.glektarssza.expandedgamerules.gamerules;

import com.glektarssza.expandedgamerules.ExpandedGamerules;
import com.glektarssza.expandedgamerules.GameruleRegistry;
import com.glektarssza.expandedgamerules.utils.MobUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules.Category;
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
        if (!(entity instanceof MobEntity)) {
            return;
        }
        MobEntity mob = (MobEntity) entity;
        Brain<?> brain = mob.getBrain();
        LivingEntity target = event.getTarget();
        LivingEntity attackTarget = null;
        if (brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            attackTarget = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        }
        // -- Target is not a player or is a fake player
        if (!(target instanceof PlayerEntity) || target instanceof FakePlayer
                && (!(attackTarget instanceof PlayerEntity) || attackTarget instanceof FakePlayer)) {
            return;
        }
        // -- Reset the mob's target
        MobUtils.resetTarget(mob);
    }

    /**
     * Handle the event that is fired when a living entity updates.
     */
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        // -- Gamerule is not enabled, do nothing
        if (!ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, ID).orElse(false)) {
            return;
        }
        // -- Entity is not a mob
        if (!(entity instanceof MobEntity)) {
            return;
        }
        MobEntity mob = (MobEntity) entity;
        Brain<?> brain = mob.getBrain();
        LivingEntity target = mob.getTarget();
        LivingEntity attackTarget = null;
        if (brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            attackTarget = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        }
        // -- Target is not a player or is a fake player
        if ((!(target instanceof PlayerEntity) || target instanceof FakePlayer)
                && (!(attackTarget instanceof PlayerEntity) || attackTarget instanceof FakePlayer)) {
            return;
        }
        // -- Reset the mob's target
        MobUtils.resetTarget(mob);
    }
}

package com.glektarssza.expandedgamerules.gamerules;

import java.util.Optional;

import com.glektarssza.expandedgamerules.ExpandedGamerules;
import com.glektarssza.expandedgamerules.GameruleRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules.Category;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
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
    public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        Entity entity = event.getEntity();
        // -- Gamerule is not enabled, do nothing
        if (ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.world, ID).orElse(false)) {
            return;
        }
        // -- Entity is not a mob
        if (!(entity instanceof MobEntity)) {
            return;
        }
        LivingEntity target = event.getTarget();
        // -- Target is not a player or is a fake player
        if (!(target instanceof PlayerEntity) || target instanceof FakePlayer) {
            return;
        }
        MobEntity mob = (MobEntity) entity;
        mob.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, Optional.empty());
        mob.getBrain().setMemory(MemoryModuleType.ANGRY_AT, Optional.empty());
        mob.getBrain().setMemory(MemoryModuleType.UNIVERSAL_ANGER, Optional.empty());
        if (mob.getBrain().hasMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD)) {
            mob.getBrain().setMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, Optional.empty());
        }
        mob.setAttackTarget(null);
    }

    /**
     * Handle the event that is fired when a living entity updates.
     */
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        // -- Gamerule is not enabled, do nothing
        if (ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.world, ID).orElse(false)) {
            return;
        }
        // -- Entity is not a mob
        if (!(entity instanceof MobEntity)) {
            return;
        }
        MobEntity mob = (MobEntity) entity;
        LivingEntity target = mob.getAttackTarget();
        // -- Target is not a player or is a fake player
        if (!(target instanceof PlayerEntity) || target instanceof FakePlayer) {
            return;
        }
        mob.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, Optional.empty());
        mob.getBrain().setMemory(MemoryModuleType.ANGRY_AT, Optional.empty());
        mob.getBrain().setMemory(MemoryModuleType.UNIVERSAL_ANGER, Optional.empty());
        if (mob.getBrain().hasMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD)) {
            mob.getBrain().setMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, Optional.empty());
        }
        mob.setAttackTarget(null);
        mob.setRevengeTarget(null);
    }
}

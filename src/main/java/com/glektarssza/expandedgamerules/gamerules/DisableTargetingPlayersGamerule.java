package com.glektarssza.expandedgamerules.gamerules;

import com.glektarssza.expandedgamerules.ExpandedGamerules;
import com.glektarssza.expandedgamerules.GameruleRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
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
        if (ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, ID).orElse(false)) {
            return;
        }
        // -- Entity is not a mob
        if (!(entity instanceof MobEntity)) {
            return;
        }
        // -- Don't handle Hoglins/Piglin Brutes here, we do that in mixins
        if (entity instanceof HoglinEntity || entity instanceof PiglinBruteEntity) {
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
        if (brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
        }
        if (brain.hasMemoryValue(MemoryModuleType.ANGRY_AT)) {
            brain.eraseMemory(MemoryModuleType.ANGRY_AT);
        }
        if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
            brain.eraseMemory(MemoryModuleType.UNIVERSAL_ANGER);
        }
        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD)) {
            brain.eraseMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
        }
        mob.setTarget(null);
    }

    /**
     * Handle the event that is fired when a living entity updates.
     */
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        // -- Gamerule is not enabled, do nothing
        if (ExpandedGamerules.GAMERULE_REGISTRY.isGameruleEnabled(entity.level, ID).orElse(false)) {
            return;
        }
        // -- Entity is not a mob
        if (!(entity instanceof MobEntity)) {
            return;
        }
        // -- Don't handle Hoglins/Piglin Brutes here, we do that in mixins
        if (entity instanceof HoglinEntity || entity instanceof PiglinBruteEntity) {
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
        if (brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
        }
        if (brain.hasMemoryValue(MemoryModuleType.ANGRY_AT)) {
            brain.eraseMemory(MemoryModuleType.ANGRY_AT);
        }
        if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
            brain.eraseMemory(MemoryModuleType.UNIVERSAL_ANGER);
        }
        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD)) {
            brain.eraseMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
        }
        mob.setTarget(null);
        // -- Hoglins and Piglin Brutes behave differently. Hoglins will
        // -- randomly target players as part of their idle behavior. This
        // -- causes them to spam the aggression sound as the targeting and
        // -- sound effect happen this code runs. To prevent this we use a mixin
        // -- to prevent the targeting from happening. See HoglinTasksMixin.java
        // -- for the implementation.
        // -- Piglin Brutes also target the player as part of their AI routines.
        // -- This causes them to spam the aggression sound/animation as well.
        // -- To prevent this we also use a mixin to prevent the targeting from
        // -- happening. See PiglinBruteTasksMixin.java for the implementation.
    }
}

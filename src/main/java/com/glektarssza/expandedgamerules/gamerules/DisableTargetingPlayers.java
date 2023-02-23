package com.glektarssza.expandedgamerules.gamerules;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.GameRules.BooleanRule;
import net.minecraft.world.GameRules.Category;
import net.minecraft.world.GameRules.Key;

import com.glektarssza.expandedgamerules.events.MobChangeTargetCallback;
import com.google.common.collect.ImmutableList;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;

public class DisableTargetingPlayers {
    public static final String ID = "disableTargetingPlayers";

    public static final Category CATEGORY = Category.MOBS;

    public static final boolean DEFAULT = false;

    public static final String DESCRIPTION = "Whether mobs can target players.";

    public Key<BooleanRule> key;

    public void setup() {
        key = GameRuleRegistry.register(ID, CATEGORY, GameRuleFactory.createBooleanRule(DEFAULT));
        MobChangeTargetCallback.EVENT.register(this::onMobChangeTarget);
    }

    public ActionResult onMobChangeTarget(MobEntity mob, LivingEntity target) {
        if (!mob.world.getGameRules().getBoolean(key) || !(target instanceof PlayerEntity)) {
            return ActionResult.PASS;
        }
        return ActionResult.FAIL;
    }
}

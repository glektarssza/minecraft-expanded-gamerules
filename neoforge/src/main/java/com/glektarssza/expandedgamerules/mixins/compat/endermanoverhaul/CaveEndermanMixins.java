package com.glektarssza.expandedgamerules.mixins.compat.endermanoverhaul;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;

import tech.alexnijjar.endermanoverhaul.common.entities.CaveEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;

/**
 * Mixins for the Cave Enderman class from Enderman Overhaul.
 */
@Mixin(CaveEnderman.class)
public abstract class CaveEndermanMixins extends BaseEnderman {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    public CaveEndermanMixins(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Teleport the Enderman under a block.
     *
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "teleportUnderBlock()V", cancellable = true, remap = false)
    public void teleportUnderBlock(CallbackInfo ci) {
        if (GameruleUtilities.getBooleanGamerule(this.level(), "disableEndermanTeleport")) {
            ci.cancel();
            return;
        }
    }
}

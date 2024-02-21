package com.glektarssza.expandedgamerules.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;

/**
 * Mixins relating to the Warden.
 */
@Mixin(Warden.class)
public abstract class WardenMixins extends Monster implements VibrationSystem {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    protected WardenMixins(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Check whether the entity can target another entity.
     *
     * @param entity The entity to check against.
     * @param ci     The callback information.
     */
    @Inject(at = @At("HEAD"), method = "canTargetEntity(Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    public void canTargetEntity(@Nullable Entity entity, CallbackInfoReturnable<Boolean> ci) {
        if (entity instanceof Player && GameruleUtilities.getBooleanGamerule(this.level(), "disableTargetingPlayers")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

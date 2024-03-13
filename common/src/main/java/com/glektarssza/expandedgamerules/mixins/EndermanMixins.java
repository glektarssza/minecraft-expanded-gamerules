package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * Mixins relating to Endermen.
 */
@Mixin(EnderMan.class)
public abstract class EndermanMixins extends Monster implements NeutralMob {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    protected EndermanMixins(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Attempt to teleport the Enderman.
     *
     * @param x  The x coordinate.
     * @param y  The y coordinate.
     * @param z  The z coordinate.
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "teleport(DDD)Z", cancellable = true)
    public void teleport(double x, double y, double z, CallbackInfoReturnable<Boolean> ci) {
        if (GameruleUtilities.getBooleanGamerule(this.level, "disableShulkerTeleport")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

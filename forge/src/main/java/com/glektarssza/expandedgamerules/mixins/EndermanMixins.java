package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

/**
 * Mixins relating to Endermen.
 */
@Mixin(EndermanEntity.class)
public abstract class EndermanMixins extends MonsterEntity {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    protected EndermanMixins(EntityType<? extends MonsterEntity> entityType, World level) {
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

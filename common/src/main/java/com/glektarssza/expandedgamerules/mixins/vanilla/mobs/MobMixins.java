package com.glektarssza.expandedgamerules.mixins.vanilla.mobs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Mixins relating to mobs.
 */
@Mixin(Mob.class)
public abstract class MobMixins extends LivingEntity implements Targeting {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level The game level.
     */
    public MobMixins(EntityType<? extends LivingEntity> entityType,
        Level level) {
        super(entityType, level);
    }

    /**
     * Set the target of this entity.
     *
     * @param entity The entity to set as the target.
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "setTarget(Lnet/minecraft/world/entity/LivingEntity;)V", cancellable = true)
    public void setTarget(LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof Player
            && GameruleUtilities.getBooleanGamerule(this.level(),
                "disableTargetingPlayers")) {
            ci.cancel();
            return;
        }
    }
}

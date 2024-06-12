package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

/**
 * Mixins relating to Withers.
 */
@Mixin(WitherEntity.class)
public abstract class WitherMixins extends MonsterEntity {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    protected WitherMixins(EntityType<? extends WitherEntity> entityType, World level) {
        super(entityType, level);
    }

    /**
     * Perform a ranged attack.
     *
     * @param headIndex The index of the head performing the attack.
     * @param x         The X coordinate of the target.
     * @param y         The Y coordinate of the target.
     * @param z         The Z coordinate of the target.
     * @param dangerous Whether to fire a blue Wither skull.
     * @param ci        The callback information.
     */
    @Inject(at = @At("HEAD"), method = "performRangedAttack(IDDDZ)V", cancellable = true)
    public void performRangedAttack(int headIndex, double x, double y, double z, boolean dangerous, CallbackInfo ci) {
        if (GameruleUtilities.getBooleanGamerule(this.level, "disableTargetingPlayers")) {
            ci.cancel();
            return;
        }
    }
}

package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * Mixins relating to Withers.
 */
@Mixin(WitherBoss.class)
public abstract class WitherMixins extends Monster {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    protected WitherMixins(EntityType<? extends WitherBoss> entityType, Level level) {
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

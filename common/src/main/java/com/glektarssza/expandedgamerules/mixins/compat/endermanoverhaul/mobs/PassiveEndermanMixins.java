package com.glektarssza.expandedgamerules.mixins.compat.endermanoverhaul.mobs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.base.PassiveEnderman;

/**
 * Mixins for the base passive Enderman class from Enderman Overhaul.
 */
@Mixin(value = PassiveEnderman.class, remap = false)
public abstract class PassiveEndermanMixins extends BaseEnderman {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level The game level.
     */
    public PassiveEndermanMixins(EntityType<? extends EnderMan> entityType,
        Level level) {
        super(entityType, level);
    }

    /**
     * Check whether the Enderman can teleport.
     *
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "canTeleport()Z", cancellable = true, remap = false)
    public void canTeleport(CallbackInfoReturnable<Boolean> ci) {
        if (GameruleUtilities.getBooleanGamerule(this.level(),
            "disableEndermanTeleport")) {
            ci.setReturnValue(false);
            return;
        }
    }
}

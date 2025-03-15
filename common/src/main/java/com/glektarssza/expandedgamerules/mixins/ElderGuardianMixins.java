package com.glektarssza.expandedgamerules.mixins;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.GameruleUtilities;

@Mixin(ElderGuardian.class)
public class ElderGuardianMixins extends Guardian {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level The game level.
     */
    public ElderGuardianMixins(EntityType<? extends ElderGuardian> entityType,
        Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "customServerAiStep()V", cancellable = true)
    public void customServerAiStep(CallbackInfo ci) {
        if (GameruleUtilities.getBooleanGamerule(this.level(),
            "disableTargetingPlayers")) {
            // -- Ensure this code still gets run before we abort the rest
            if (!this.hasRestriction()) {
                this.restrictTo(this.blockPosition(), 16);
            }
            ci.cancel();
            return;
        }
    }
}

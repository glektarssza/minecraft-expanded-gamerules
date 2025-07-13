package com.glektarssza.expandedgamerules.mixins.vanilla;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

/**
 * Mixins for the {@link Biome} class which adds custom gamerule support.
 */
@Mixin(Biome.class)
public class BiomeMixins {
    @Inject(method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z", at = @At("RETURN"), cancellable = true)
    public void injectShouldFreeze(LevelReader levelReader, BlockPos blockPos,
        boolean blocked, CallbackInfoReturnable<Boolean> cir) {
        if (levelReader instanceof Level level) {
            if (!GameruleUtilities.getBooleanGamerule(level,
                "preventColdBiomesFreezingWater")) {
                cir.setReturnValue(false);
            }
        }
    }
}

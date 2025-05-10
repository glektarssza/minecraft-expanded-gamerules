package com.glektarssza.expandedgamerules.mixins.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.Constants;
import com.glektarssza.expandedgamerules.GameruleUtilities;

@Mixin(Biome.class)
public class BiomeMixins {
    @Inject(method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z", at = @At("RETURN"), cancellable = true)
    public void injectShouldFreeze(LevelReader levelReader, BlockPos blockPos,
        boolean blocked, CallbackInfoReturnable<Boolean> cir) {
        Level level = null;
        if (levelReader instanceof Level) {
            level = (Level) levelReader;
        }
        if (Minecraft.getInstance().isSingleplayer()) {
            try {
                level = (Level) Minecraft.getInstance().getSingleplayerServer()
                    .getAllLevels().iterator().next();
            } catch (Throwable t) {
                // -- Do nothing
            }
        } else {
            Constants.LOG.warn(
                "'shouldFreeze' mixin is being triggered on the client side!");
            Constants.LOG.warn(
                "That's not supposed to happen?!");
            Constants.LOG.warn(
                "Please consider filing a bug report at https://github.com/glektarssza/minecraft-expanded-gamerules/issues");
        }
        if (level == null) {
            return;
        }
        if (!GameruleUtilities.getBooleanGamerule(level,
            "doColdBiomesFreezeWater")) {
            cir.setReturnValue(false);
        }
    }
}

package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.Level;

@Mixin(Shulker.class)
public class ShulkerMixins extends Shulker {
    /**
     * Required to make Java be quiet.
     *
     * @param entityType The entity type.
     * @param level      The level.
     */
    public ShulkerMixins(EntityType<? extends Shulker> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Teleport the Shulker.
     *
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "teleportSomewhere()Z", cancellable = true)
    public void teleportSomewhere(CallbackInfoReturnable<Boolean> ci) {
        if (GameruleUtilities.getBooleanGamerule(this.level(), "disableEndermanTeleport")) {
            ci.setReturnValue(false);
        }
    }
}

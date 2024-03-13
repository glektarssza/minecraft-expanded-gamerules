package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

/**
 * Mixins relating to Shulkers.
 */
@Mixin(ShulkerEntity.class)
public abstract class ShulkerMixins extends GolemEntity {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    protected ShulkerMixins(EntityType<? extends GolemEntity> entityType, World level) {
        super(entityType, level);
    }

    /**
     * Teleport the Shulker.
     *
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "teleportSomewhere()Z", cancellable = true)
    public void teleportSomewhere(CallbackInfoReturnable<Boolean> ci) {
        ShulkerEntity self = (ShulkerEntity) (Object) this;
        if (GameruleUtilities.getBooleanGamerule(self.level, "disableEndermanTeleport")) {
            ci.setReturnValue(false);
        }
    }
}

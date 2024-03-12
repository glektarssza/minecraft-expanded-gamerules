package com.glektarssza.expandedgamerules.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.glektarssza.expandedgamerules.GameruleUtilities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.Level;

/**
 * Mixins relating to Shulkers.
 */
@Mixin(Shulker.class)
public abstract class ShulkerMixins extends AbstractGolem implements Enemy {
    /**
     * Make Java Happy™.
     *
     * @param entityType The type of the entity being created.
     * @param level      The game level.
     */
    protected ShulkerMixins(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Teleport the Shulker.
     *
     * @param ci The callback information.
     */
    @Inject(at = @At("HEAD"), method = "teleportSomewhere()Z", cancellable = true)
    public void teleportSomewhere(CallbackInfoReturnable<Boolean> ci) {
        var self = (Shulker) (Object) this;
        if (GameruleUtilities.getBooleanGamerule(self.getLevel(), "disableEndermanTeleport")) {
            ci.setReturnValue(false);
        }
    }
}

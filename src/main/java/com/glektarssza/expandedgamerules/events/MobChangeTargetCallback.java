package com.glektarssza.expandedgamerules.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.ActionResult;

public interface MobChangeTargetCallback {
    Event<MobChangeTargetCallback> EVENT = EventFactory.createArrayBacked(MobChangeTargetCallback.class,
            (listeners) -> (entity, target) -> {
                for (MobChangeTargetCallback listener : listeners) {
                    ActionResult result = listener.changeTarget(entity, target);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult changeTarget(MobEntity entity, LivingEntity target);
}

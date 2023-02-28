package com.glektarssza.expandedgamerules;

import com.glektarssza.expandedgamerules.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CommonClass {
    public static void init() {
        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!",
                Services.PLATFORM.getPlatformName(),
                Services.PLATFORM.isDevelopmentEnvironment() ? "development" : "production");
        Constants.LOG.info("Diamond Item >> {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));
    }

    public static void onItemTooltip(ItemStack stack, TooltipFlag context, List<Component> tooltip) {
        if (!stack.isEmpty()) {
            final FoodProperties food = stack.getItem().getFoodProperties();
            if (food != null) {
                tooltip.add(Component.literal("Nutrition: " + food.getNutrition()));
                tooltip.add(Component.literal("Saturation: " + food.getSaturationModifier()));
            }
        }
    }
}

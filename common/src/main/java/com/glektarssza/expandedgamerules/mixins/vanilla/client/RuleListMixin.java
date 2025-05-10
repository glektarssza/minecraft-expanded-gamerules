package com.glektarssza.expandedgamerules.mixins.vanilla.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen.RuleEntry;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen.RuleList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.Key;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.glektarssza.expandedgamerules.api.v1.gamerule.CustomCategory;
import com.glektarssza.expandedgamerules.api.v1.gamerule.IGameruleKeyExtensions;

/**
 * A mixin into the
 * {@link net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen.RuleEntry}
 * class which augments its functionality.
 */
@Mixin(RuleList.class)
public abstract class RuleListMixin
    extends AbstractSelectionList<RuleEntry> {
    /**
     * A map of the custom categories discovered.
     */
    @Unique
    private final Map<CustomCategory, List<RuleEntry>> customCategories = new HashMap<CustomCategory, List<RuleEntry>>();

    /**
     * Create a new instance.
     *
     * @param minecraft The Minecraft instance.
     * @param width The width of the item.
     * @param height The height of the item.
     * @param top The top position of them item.
     * @param bottom The bottom position of them item.
     * @param itemHeight The height of the item.
     */
    public RuleListMixin(Minecraft minecraft, int width, int height, int top,
        int bottom, int itemHeight) {
        super(minecraft, width, height, top, bottom, itemHeight);
    }

    /**
     * Mixin to the
     * {@link net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen.RuleList}
     * constructor to inject custom categories into the list.
     *
     * @param screen The screen being initialized.
     * @param gameRules The gamerules being loaded.
     * @param ci The callback information.
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    public void initCustomCategories(EditGameRulesScreen screen,
        GameRules gameRules, CallbackInfo ci) {
        this.customCategories.forEach((category, entryList) -> {
            this.addEntry(screen.new CategoryRuleEntry(
                Component.translatable(category.getLocalizationKey())));
            for (RuleEntry entry : entryList) {
                this.addEntry(entry);
            }
        });
    }

    /**
     * Mixin to the lambda used in the
     * {@link net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen.RuleList}
     * constructor to process gamerule entries being loaded.
     *
     * Filters out gamerule entries that have custom categories so they can be
     * loaded later on by the {@link #initCustomCategories} injection.
     *
     * @param entry The entry being processed.
     * @param ci The callback information.
     */
    @Inject(method = "lambda$new$1(Ljava/util/Map$Entry;)V", at = @At("HEAD"), cancellable = true)
    public void ignoreKeysWithCustomCategories(
        Map.Entry<Key<?>, RuleEntry> entry, CallbackInfo ci) {
        Optional
            .ofNullable(
                ((IGameruleKeyExtensions) (Object) entry
                    .getKey()).getCustomCategory())
            .ifPresent(key -> {
                this.customCategories
                    .computeIfAbsent(key, c -> new ArrayList<>())
                    .add(entry.getValue());
                ci.cancel();
            });
    }
}

package com.glektarssza.expandedgamerules;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import com.glektarssza.expandedgamerules.platform.Services;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    /**
     * The package root for mixins from the mixin configuration.
     */
    private String mixinPackageRoot;

    /**
     * Create a new instance.
     */
    public MixinConfigPlugin() {
        // -- Does nothing
    }

    /**
     * Initialize the plugin.
     *
     * @param mixinPackage The package root for mixins from the mixin
     *        configuration.
     */
    @Override
    public void onLoad(String mixinPackage) {
        this.mixinPackageRoot = mixinPackage;
    }

    /**
     * Get the reference map if one was not supplied.
     *
     * @return The reference map to use or {@code null} to fall back to default
     *         behaviour.
     */
    @Override
    public String getRefMapperConfig() {
        return null;
    }

    /**
     * Check if a mixin should be applied.
     *
     * @param targetClassName The name of the class being targeted by them
     *        mixin.
     * @param mixinClassName The name of the class containing the mixin.
     *
     * @return {@code true} if the mixin should be applied; {@code false}
     *         otherwise.
     */
    @Override
    public boolean shouldApplyMixin(String targetClassName,
        String mixinClassName) {
        return true;
    }

    /**
     * Called after all mixin configurations are initialized to allow this mod
     * to decide whether to abort applying any mixins.
     *
     * @param myMixinTargets The set of mixins which are slated to be applied by
     *        this mixin configuration. Removing items from this set will abort
     *        applying them. Adding to this set does nothing.
     * @param otherMixinTargets A set of mixins which are slated to by applied
     *        by other mixin configurations. This set is read-only.
     */
    @Override
    public void acceptTargets(Set<String> myMixinTargets,
        Set<String> otherMixinTargets) {
        // -- Does nothing
    }

    /**
     * Get a list of mixins to append to the list of mixins loaded from the
     * configuration.
     *
     * @return A list of mixins to append to the existing list of mixins to be
     *         applied.
     */
    @Override
    public List<String> getMixins() {
        var additionalMixins = new ArrayList<String>();
        if (Services.PLATFORM.isModLoaded("endermanoverhaul")) {
            additionalMixins
                .add(String.format(
                    "%s.mods.endermanoverhaul.mobs.BaseEndermanMixins",
                    this.mixinPackageRoot));
            additionalMixins
                .add(String.format(
                    "%s.mods.endermanoverhaul.mobs.CaveEndermanMixins",
                    this.mixinPackageRoot));
            additionalMixins
                .add(String.format(
                    "%s.mods.endermanoverhaul.mobs.PassiveEndermanMixins",
                    this.mixinPackageRoot));

        }
        return additionalMixins;
    }

    /**
     * Called immediately before a mixin is applied.
     *
     * @param targetClassName The name of the class that will be transformed.
     * @param targetClass The class which will be transformed.
     * @param mixinClassName The class which contained the mixin which will be
     *        applied.
     * @param mixinInfo The information about the mixin which will be applied.
     */
    @Override
    public void preApply(String targetClassName, ClassNode targetClass,
        String mixinClassName, IMixinInfo mixinInfo) {

    }

    /**
     * Called immediately after a mixin is applied.
     *
     * @param targetClassName The name of the class that was transformed.
     * @param targetClass The class which was transformed.
     * @param mixinClassName The class which contained the mixin applied.
     * @param mixinInfo The information about the mixin applied.
     */
    @Override
    public void postApply(String targetClassName, ClassNode targetClass,
        String mixinClassName, IMixinInfo mixinInfo) {

    }
}

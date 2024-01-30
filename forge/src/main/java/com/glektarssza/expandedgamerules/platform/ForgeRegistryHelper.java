package com.glektarssza.expandedgamerules.platform;

import java.util.Optional;

import javax.annotation.Nonnull;

import com.glektarssza.expandedgamerules.Constants;
import com.glektarssza.expandedgamerules.api.IGamerule;
import com.glektarssza.expandedgamerules.platform.services.IRegistryHelper;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * An Forge-specific implementation of the common registry helper.
 */
public class ForgeRegistryHelper implements IRegistryHelper {
    /**
     * The gamerule registry.
     */
    private IForgeRegistry<IGamerule> registry = null;

    /**
     * Check whether the gamerule registry is initialized.
     *
     * @return Whether the gamerule registry is initialized.
     */
    public boolean isGameruleRegistryInitialized() {
        return registry != null;
    }

    /**
     * Initialize the gamerule registry.
     *
     * @param callback The callback to call when the registry has been
     *                 initialized.
     */
    public void initializeGameruleRegistry(@Nonnull IRegistryInitializedCallback callback) {
        var builder = RegistryBuilder.<IGamerule>of().setName(new ResourceLocation(Constants.MOD_ID));
        MinecraftForge.EVENT_BUS.addListener((NewRegistryEvent ev) -> {
            ev.create(builder, (registry) -> {
                this.registry = registry;
                callback.apply();
            });
        });
    }

    /**
     * Register a new gamerule.
     *
     * @param key      The identifier to register the gamerule to. This parameter
     *                 will be prefixed with the mod ID.
     * @param gamerule The gamerule to register.
     */
    public void registerGamerule(@Nonnull String key, @Nonnull IGamerule gamerule) throws IllegalStateException,
            IllegalArgumentException {
        this.registerGamerule(new ResourceLocation(Constants.MOD_ID, key), gamerule);
    }

    /**
     * Register a new gamerule.
     *
     * @param key      The identifier to register the gamerule to.
     * @param gamerule The gamerule to register.
     */
    public void registerGamerule(@Nonnull ResourceLocation key, @Nonnull IGamerule gamerule)
            throws IllegalStateException, IllegalArgumentException {
        if (this.registry == null) {
            throw new IllegalStateException("Gamerule registry is not initialized");
        }
        if (this.registry.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Gamerule \"%1$s\" already exists", key));
        }
        this.registry.register(key, gamerule);
    }

    /**
     * Get a gamerule from the registry with the given identifier.
     *
     * @param key The identifier to get a gamerule for.
     *
     * @return The gamerule that has the given identifier.
     */
    public Optional<IGamerule> getGamerule(@Nonnull ResourceLocation key) throws NullPointerException {
        if (this.registry == null) {
            throw new IllegalStateException("Gamerule registry is not initialized");
        }
        return Optional.ofNullable(this.registry.getValue(key));
    }
}

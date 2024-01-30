package com.glektarssza.expandedgamerules.platform;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.management.openmbean.KeyAlreadyExistsException;

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
    @Nullable
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
    public void registerGamerule(@Nonnull String key, @Nonnull IGamerule gamerule) throws NullPointerException,
            KeyAlreadyExistsException {
        this.registerGamerule(new ResourceLocation(Constants.MOD_ID, key), gamerule);
    }

    /**
     * Register a new gamerule.
     *
     * @param key      The identifier to register the gamerule to.
     * @param gamerule The gamerule to register.
     */
    public void registerGamerule(@Nonnull ResourceLocation key, @Nonnull IGamerule gamerule)
            throws NullPointerException, KeyAlreadyExistsException {
        if (this.registry == null) {
            throw new NullPointerException();
        }
        if (this.registry.containsKey(key)) {
            throw new KeyAlreadyExistsException();
        }
        this.registry.register(key, gamerule);
    }

    /**
     * Get a gamerule from the registry with the given identifier.
     *
     * @param key The identifier to get a gamerule for. This parameter will be
     *            prefixed with the mod ID.
     *
     * @return The gamerule that has the given identifier.
     */
    @Nullable
    public IGamerule getGamerule(@Nonnull String key) throws NullPointerException {
        return this.getGamerule(new ResourceLocation(Constants.MOD_ID, key));
    }

    /**
     * Get a gamerule from the registry with the given identifier.
     *
     * @param key The identifier to get a gamerule for.
     *
     * @return The gamerule that has the given identifier.
     */
    @Nullable
    public IGamerule getGamerule(@Nonnull ResourceLocation key) throws NullPointerException {
        if (this.registry == null) {
            throw new NullPointerException();
        }
        return this.registry.getValue(key);
    }
}

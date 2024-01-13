package com.glektarssza.expandedgamerules.api;

import net.minecraft.resources.ResourceLocation;

/**
 * An interface which represents a gamerule.
 */
public interface IGamerule<T> {
    /**
     * Get the resource location of the gamerule.
     *
     * @returns The resource location of the gamerule.
     */
    public ResourceLocation getResourceLocation();

    /**
     * Get the name of the gamerule.
     *
     * @returns The name of the gamerule.
     */
    public String getName();

    /**
     * Get the localized name of the gamerule.
     *
     * @returns The localized name of the gamerule.
     */
    public String getLocalizedName();

    /**
     * Get the description of the gamerule.
     *
     * @returns The description of the gamerule.
     */
    public String getDescription();

    /**
     * Get the localized description of the gamerule.
     *
     * @returns The localized description of the gamerule.
     */
    public String getLocalizedDescription();

    /**
     * Get the type of the gamerule.
     *
     * @returns The type of the gamerule.
     */
    public GameruleType getType();

    /**
     * Get the default value of the gamerule.
     *
     * @returns The default value of the gamerule.
     */
    public T getDefaultValue();

    /**
     * Get the current value of the gamerule.
     *
     * @returns The current value of the gamerule.
     */
    public T getValue();

    /**
     * Set the current value of the gamerule.
     *
     * @param value The new value of the gamerule.
     */
    public void setValue(T value);
}

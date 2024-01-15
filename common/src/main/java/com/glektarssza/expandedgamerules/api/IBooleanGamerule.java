package com.glektarssza.expandedgamerules.api;

/**
 * An interface for gamerules that have a boolean value.
 */
public interface IBooleanGamerule extends IGamerule {
    /**
     * Get the value of the instance.
     *
     * @return The value of the instance.
     */
    public boolean getValue();

    /**
     * Get the default value of the instance.
     *
     * @return The default value of the instance.
     */
    public default boolean getDefaultValue() {
        return false;
    }
}

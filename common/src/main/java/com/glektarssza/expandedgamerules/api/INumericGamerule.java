package com.glektarssza.expandedgamerules.api;

/**
 * An interface which represents a gamerule with a numeric value.
 */
public interface INumericGamerule<T> extends IGamerule<T> {
    /**
     * Get the minimum value of the gamerule.
     *
     * @returns The minimum value of the gamerule.
     */
    public T getMinimumValue();

    /**
     * Get the maximum value of the gamerule.
     *
     * @returns The maximum value of the gamerule.
     */
    public T getMaximumValue();
}

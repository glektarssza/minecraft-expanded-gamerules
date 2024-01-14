package com.glektarssza.expandedgamerules.api;

/**
 * An interface for gamerules that have an integer value.
 */
public interface IIntegerGamerule {
    /**
     * Get the value of the instance.
     *
     * @returns The value of the instance.
     */
    public int getValue();

    /**
     * Get the default value of the instance.
     *
     * @returns The default value of the instance.
     */
    public default int getDefaultValue() {
        return 0;
    }

    /**
     * Get the minimum value of the instance.
     *
     * @returns The minimum value of the instance.
     */
    public int getMinimumValue();

    /**
     * Get the maximum value of the instance.
     *
     * @returns The maximum value of the instance.
     */
    public int getMaximumValue();
}

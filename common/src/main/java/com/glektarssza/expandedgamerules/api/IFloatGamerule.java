package com.glektarssza.expandedgamerules.api;

/**
 * An interface for gamerules that have a floating point value.
 */
public interface IFloatGamerule {
    /**
     * Get the value of the instance.
     *
     * @returns The value of the instance.
     */
    public float getValue();

    /**
     * Get the default value of the instance.
     *
     * @returns The default value of the instance.
     */
    public default float getDefaultValue() {
        return 0;
    }

    /**
     * Get the minimum value of the instance.
     *
     * @returns The minimum value of the instance.
     */
    public float getMinimumValue();

    /**
     * Get the maximum value of the instance.
     *
     * @returns The maximum value of the instance.
     */
    public float getMaximumValue();
}

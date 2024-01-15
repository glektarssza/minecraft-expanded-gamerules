package com.glektarssza.expandedgamerules.api;

/**
 * An interface for gamerules that have a floating point value.
 */
public interface IFloatGamerule {
    /**
     * Get the value of the instance.
     *
     * @return The value of the instance.
     */
    public float getValue();

    /**
     * Get the default value of the instance.
     *
     * @return The default value of the instance.
     */
    public default float getDefaultValue() {
        return 0;
    }

    /**
     * Get the minimum value of the instance.
     *
     * @return The minimum value of the instance.
     */
    public float getMinimumValue();

    /**
     * Get the maximum value of the instance.
     *
     * @return The maximum value of the instance.
     */
    public float getMaximumValue();
}

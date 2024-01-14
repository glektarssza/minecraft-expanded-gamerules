package com.glektarssza.expandedgamerules.api;

/**
 * An interface for gamerules that have a string value.
 */
public interface IStringGamerule {
    /**
     * Get the value of the instance.
     *
     * @returns The value of the instance.
     */
    public String getValue();

    /**
     * Get the default value of the instance.
     *
     * @returns The default value of the instance.
     */
    public default String getDefaultValue() {
        return "";
    }
}

package com.glektarssza.expandedgamerules.api;

import javax.annotation.Nonnull;

/**
 * An interface for gamerules that have a string value.
 */
public interface IStringGamerule {
    /**
     * Get the value of the instance.
     *
     * @return The value of the instance.
     */
    @Nonnull
    public String getValue();

    /**
     * Get the default value of the instance.
     *
     * @return The default value of the instance.
     */
    @Nonnull
    public default String getDefaultValue() {
        return "";
    }
}

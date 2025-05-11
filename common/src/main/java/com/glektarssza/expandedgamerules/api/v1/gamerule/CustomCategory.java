package com.glektarssza.expandedgamerules.api.v1.gamerule;

import javax.annotation.Nullable;

/**
 * A class which represents a custom gamerule category.
 */
public final class CustomCategory {
    /**
     * The name which uniquely identifies this instance.
     */
    private final String name;

    /**
     * The localization key to use for this category.
     */
    private final String localizationKey;

    /**
     * Create a new instance.
     *
     * @param name The name which will identify the new instance.
     * @param localizationKey An optional localization key for this instance.
     */
    public CustomCategory(String name, @Nullable String localizationKey) {
        this.name = name;
        this.localizationKey = localizationKey;
    }

    /**
     * Get the name which uniquely identifies this instance.
     *
     * @return The name which uniquely identifies this instance.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the localization key to use for this instance.
     *
     * @return The localization key to use for this instance.
     */
    public String getLocalizationKey() {
        return this.localizationKey;
    }

    /**
     * Check if this instance equals another object.
     *
     * @param obj The object to check against.
     *
     * @return {@code true} if this instance equals the other object;
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof CustomCategory)) {
            return false;
        }
        return ((CustomCategory) obj).name.equals(this.name);
    }

    /**
     * Get the hash code of this instance.
     *
     * @return The hash code of this instance.
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}

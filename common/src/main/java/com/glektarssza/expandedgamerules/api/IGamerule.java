package com.glektarssza.expandedgamerules.api;

import javax.annotation.Nonnull;

/**
 * An interface for gamerules.
 */
public interface IGamerule {
    /**
     * Get the human-readable, unlocalized name of the instance.
     *
     * @return The human-readable, unlocalized name of the instance.
     */
    @Nonnull
    public String getName();

    /**
     * Get the human-readable name of the instance, localized to the client's
     * language.
     *
     * @return The human-readable name of the instance, localized to the
     *         client's language if possible, otherwise the unlocalized name.
     */
    @Nonnull
    public default String getLocalizedName() {
        return getName();
    }

    /**
     * Get the human-readable, unlocalized description of the instance.
     *
     * @return The human-readable, unlocalized description of the instance.
     */
    @Nonnull
    public String getDescription();

    /**
     * Get the human-readable description of the instance, localized to the
     * client's language.
     *
     * @return The human-readable description of the instance, localized to
     *         the client's language if possible, otherwise the unlocalized
     *         description.
     */
    @Nonnull
    public default String getLocalizedDescription() {
        return getDescription();
    }
}

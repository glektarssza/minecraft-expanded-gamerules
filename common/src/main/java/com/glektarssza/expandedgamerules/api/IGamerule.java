package com.glektarssza.expandedgamerules.api;

/**
 * An interface for gamerules.
 */
public interface IGamerule {
    /**
     * Get the human-readable, unlocalized name of the instance.
     *
     * @returns The human-readable, unlocalized name of the instance.
     */
    public String getName();

    /**
     * Get the human-readable name of the instance, localized to the client's
     * language.
     *
     * @returns The human-readable name of the instance, localized to the
     *          client's language if possible, otherwise the unlocalized name.
     */
    public default String getLocalizedName() {
        return getName();
    }

    /**
     * Get the human-readable, unlocalized description of the instance.
     *
     * @returns The human-readable, unlocalized description of the instance.
     */
    public String getDescription();

    /**
     * Get the human-readable description of the instance, localized to the
     * client's language.
     *
     * @returns The human-readable description of the instance, localized to
     *          the client's language if possible, otherwise the unlocalized
     *          description.
     */
    public default String getLocalizedDescription() {
        return getDescription();
    }
}

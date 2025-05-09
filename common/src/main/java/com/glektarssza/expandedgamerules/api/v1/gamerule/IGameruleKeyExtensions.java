package com.glektarssza.expandedgamerules.api.v1.gamerule;

import javax.annotation.Nullable;

public interface IGameruleKeyExtensions {
    /**
     * Get the custom category this instance is registered to.
     *
     * @return If this instance is registered to a custom category then the
     *         custom category this instance is registered to; {@code null}
     *         otherwise.
     */
    @Nullable
    CustomCategory getCustomCategory();

    /**
     * Set the custom category this instance is registered to.
     *
     * @param customCategory The custom category this instance should be
     *        registered to, if any, or {@code null} to clear the custom
     *        category registration.
     */
    void setCustomCategory(@Nullable CustomCategory customCategory);
}

package com.glektarssza.expandedgamerules.rules;

import javax.annotation.Nonnull;

import com.glektarssza.expandedgamerules.api.IBooleanGamerule;

import net.minecraft.client.resources.language.I18n;

/**
 * A gamerule to disable mobs from targeting players.
 */
public class DisableTargetingPlayersRule implements IBooleanGamerule {
    /**
     * The current value of the instance.
     */
    private boolean value = getDefaultValue();

    @Override
    @Nonnull
    public String getName() {
        return "Disable Targeting Players";
    }

    @Override
    @Nonnull
    public String getLocalizedName() {
        if (!I18n.exists("expandedgamerules.disableTargetingPlayers.name")) {
            return getName();
        }
        var ln = I18n.get("expandedgamerules.disableTargetingPlayers.name");
        if (ln == null) {
            return getName();
        }
        return ln;
    }

    @Override
    @Nonnull
    public String getDescription() {
        return "When enabled, prevent mobs from targeting players.";
    }

    @Override
    @Nonnull
    public String getLocalizedDescription() {
        if (!I18n.exists("expandedgamerules.disableTargetingPlayers.description")) {
            return getDescription();
        }
        var ld = I18n.get("expandedgamerules.disableTargetingPlayers.description");
        if (ld == null) {
            return getDescription();
        }
        return ld;
    }

    @Override
    public boolean getValue() {
        return value;
    }
}

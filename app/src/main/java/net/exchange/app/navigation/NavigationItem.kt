package net.exchange.app.navigation

import net.exchange.app.R

sealed class NavigationItem(var route: String, var titleStringRes: Int) {
    private companion object {
        const val SETTINGS_KEY = "settings"
    }

    // Settings
    object Settings : NavigationItem(SETTINGS_KEY, R.string.settings)
}
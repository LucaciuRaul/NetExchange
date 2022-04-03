package net.exchange.app.helper

import android.content.SharedPreferences
import net.exchange.app.BuildConfig
import net.exchange.app.di.SharedPreferencesModule


fun SharedPreferences.setCurrency(currency: String) {
    edit().putString(SharedPreferencesModule.CURRENCY, currency).apply()
}

fun SharedPreferences.getCurrency(): String {
    return getString(
        SharedPreferencesModule.CURRENCY,
        "EUR"
    ).toString()
}
fun SharedPreferences.getRefreshInterval(): Int {
    return getInt(
        SharedPreferencesModule.REFRESH_INTERVAL,
        3
    )
}

fun SharedPreferences.setRefreshInterval(refreshInterval: Int) {
    edit().putInt(SharedPreferencesModule.REFRESH_INTERVAL, refreshInterval).apply()
}
package net.exchange.app.application

import android.app.Application
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp
import net.exchange.app.helper.isOnline

@HiltAndroidApp
class ExchangeApplication : Application() {
    override fun onCreate() {
        try {
            if (isOnline())
                super.onCreate()
            else {
                Toast.makeText(this, "You need to have an internet connection", Toast.LENGTH_LONG)
                    .show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG)
                .show()
        }
    }
}
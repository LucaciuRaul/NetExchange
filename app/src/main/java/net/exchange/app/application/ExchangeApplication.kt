package net.exchange.app.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExchangeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
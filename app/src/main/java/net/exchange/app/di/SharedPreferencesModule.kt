package net.exchange.app.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SharedPreferencesModule {
    private const val APP_PREF = "net_exchange_preferences"
    const val CURRENCY = "currency"
    const val REFRESH_INTERVAL = "refresh_interval"

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)
    }
}

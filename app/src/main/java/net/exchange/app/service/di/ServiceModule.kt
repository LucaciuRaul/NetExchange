package net.exchange.app.service.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.exchange.app.data.model.repository.exchangerate.IExchangeRateRepository
import net.exchange.app.service.service.IExchangeRatesService
import net.exchange.app.service.service.implementation.ExchangeRatesService
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {
    @Provides
    @Singleton
    fun providesExchangeRatesService(repo: IExchangeRateRepository, sharedPreferences: SharedPreferences): IExchangeRatesService {
        return ExchangeRatesService(repo, sharedPreferences)
    }
}
package net.exchange.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.exchange.app.data.model.repository.exchangerate.IExchangeRateRepository
import net.exchange.app.data.model.repository.exchangerate.retrofit.ExchangeRateAPI
import net.exchange.app.data.model.repository.exchangerate.retrofit.RetrofitExchangeRateRepository
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun providesExchangeRateRepository(api: ExchangeRateAPI): IExchangeRateRepository {
        return RetrofitExchangeRateRepository(api)
    }

    @Provides
    @Singleton
    fun providesExchangeRateAPI(retrofitInstance: Retrofit): ExchangeRateAPI {
        return retrofitInstance.create(ExchangeRateAPI::class.java)
    }
}
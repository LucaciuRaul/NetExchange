package net.exchange.app.data.model.repository.exchangerate.retrofit

import net.exchange.app.BuildConfig
import net.exchange.app.data.model.entity.ExchangeRate
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExchangeRateAPI {
    @GET("latest")
    suspend fun getExchangeRates(
//        baseCurrency: String,
        @Query("access_key") access_key: String = BuildConfig.API_TOKEN,
        @Query("symbols") symbols: String = "USD,AUD,CAD,PLN,MXN"
    ): ExchangeRate

    @GET("{date}")
    suspend fun getCurrencyHistory(
        @Path("date") date: String,
        @Query("base") currencyName: String = "EUR",
        @Query("access_key") access_key: String = BuildConfig.API_TOKEN,
        @Query("symbols") symbols: String = "USD,AUD,CAD,PLN,MXN"
    ): ExchangeRate
}
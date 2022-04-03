package net.exchange.app.service.service

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.Flow
import net.exchange.app.data.model.entity.ExchangeRate

interface IExchangeRatesService {
    fun getExchangeRates(): Flow<ExchangeRate>

    fun startUpdatingExchangeRates(exchangeRates: MutableState<ExchangeRate?>, interval: Long)

    fun stopUpdatingExchangeRates()

    fun getCurrencyHistory(currencyName: String, callback: (List<Double>) -> Unit)
}
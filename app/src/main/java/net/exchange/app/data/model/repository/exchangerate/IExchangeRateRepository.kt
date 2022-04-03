package net.exchange.app.data.model.repository.exchangerate

import kotlinx.coroutines.flow.Flow
import net.exchange.app.data.model.entity.ExchangeRate

interface IExchangeRateRepository {
    fun getExchangeRates(currency: String): Flow<ExchangeRate>

    fun getCurrencyHistory(date: String): Flow<ExchangeRate>
}
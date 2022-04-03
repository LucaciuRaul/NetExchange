package net.exchange.app.data.model.repository.exchangerate.retrofit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.exchange.app.data.model.entity.ExchangeRate
import net.exchange.app.data.model.repository.exchangerate.IExchangeRateRepository
import javax.inject.Inject

class RetrofitExchangeRateRepository @Inject constructor(
    private val exchangeRateAPI: ExchangeRateAPI
) : IExchangeRateRepository {

    override fun getExchangeRates(currency: String): Flow<ExchangeRate> {
        return flow {
            emit(exchangeRateAPI.getExchangeRates())
        }.flowOn(Dispatchers.IO)
    }

    override fun getCurrencyHistory(date: String): Flow<ExchangeRate> {
        return flow {
            emit(exchangeRateAPI.getCurrencyHistory(date))
        }.flowOn(Dispatchers.IO)
    }
}
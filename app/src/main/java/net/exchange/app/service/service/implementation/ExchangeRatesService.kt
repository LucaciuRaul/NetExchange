package net.exchange.app.service.service.implementation

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import net.exchange.app.data.model.entity.ExchangeRate
import net.exchange.app.data.model.repository.exchangerate.IExchangeRateRepository
import net.exchange.app.helper.getCurrency
import net.exchange.app.service.service.IExchangeRatesService
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.stream.IntStream.range
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
internal class ExchangeRatesService @Inject constructor(
    private val rateRepository: IExchangeRateRepository,
    private val sharedPreferences: SharedPreferences
) : IExchangeRatesService {
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var executor: ScheduledExecutorService? = null
    override fun getExchangeRates(): Flow<ExchangeRate> {
        val baseCurrency = sharedPreferences.getCurrency()
        return rateRepository.getExchangeRates(baseCurrency)
    }

    override fun startUpdatingExchangeRates(
        exchangeRates: MutableState<ExchangeRate?>,
        interval: Long
    ) {
        executor = Executors.newSingleThreadScheduledExecutor()
        executor?.scheduleAtFixedRate({
            scope.launch {
                val baseCurrency = sharedPreferences.getCurrency()
                rateRepository.getExchangeRates(baseCurrency).collect {
                    exchangeRates.value = it
                }
            }
        }, interval, interval, TimeUnit.SECONDS)
    }

    override fun stopUpdatingExchangeRates() {
        executor?.shutdownNow()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCurrencyHistory(
        currencyName: String,
        callback: (List<Double>) -> Unit
    ) {
        val lastTenDays = ArrayList<ExchangeRate>()
        scope.launch {
            for (i in range(0, 10)) {
                val day = getDaysAgo(i)
                rateRepository.getCurrencyHistory(day).collect {
                    lastTenDays.add(it)
                }
            }
            callback(lastTenDays.map { it.rates[currencyName] ?: 0.0 })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDaysAgo(daysAgo: Int): String {
        val nowInUtc = OffsetDateTime.now(ZoneOffset.UTC)
        val someDaysAgo = nowInUtc.minusDays(daysAgo.toLong())
        return "${someDaysAgo.year}-${if (someDaysAgo.monthValue > 10) someDaysAgo.monthValue else "0" + someDaysAgo.monthValue.toString()}-${if (someDaysAgo.dayOfMonth > 10) someDaysAgo.dayOfMonth else "0" + someDaysAgo.dayOfMonth.toString()}"
    }

    private fun runAsCoroutine(transform: () -> Unit): Job {
        return scope.launch {
            transform()
        }
    }
}
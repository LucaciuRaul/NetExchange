package net.exchange.app.ui.main

import android.content.SharedPreferences
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.exchange.app.data.model.entity.ExchangeRate
import net.exchange.app.helper.getCurrency
import net.exchange.app.helper.getRefreshInterval
import net.exchange.app.service.service.IExchangeRatesService
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalMaterialApi
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val exchangeRatesService: IExchangeRatesService,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {
    var exchangeRates = mutableStateOf<ExchangeRate?>(null)
    var baseCurrency : MutableState<String> = mutableStateOf(sharedPreferences.getCurrency())

    fun updateBaseCurrency(){
        baseCurrency.value = sharedPreferences.getCurrency()
    }
    fun updateExchangeRates() {
        viewModelScope.launch(Dispatchers.IO) {
            exchangeRatesService.getExchangeRates().collect {
                exchangeRates.value = it
            }
        }
    }

    fun registerExchangeRatesScheduledUpdate() {
        val interval = sharedPreferences.getRefreshInterval()
        exchangeRatesService.startUpdatingExchangeRates(exchangeRates, interval.toLong())
    }

    fun removeExchangeRatesScheduledUpdate() {
        exchangeRatesService.stopUpdatingExchangeRates()
    }

    fun getExchangeRateHistory(currencyName: String, callback: (List<Double>) -> Unit){
        exchangeRatesService.getCurrencyHistory(currencyName, callback)
    }
}
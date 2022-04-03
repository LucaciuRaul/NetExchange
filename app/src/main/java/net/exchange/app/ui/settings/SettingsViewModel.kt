package net.exchange.app.ui.settings

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.exchange.app.data.model.entity.ExchangeRate
import net.exchange.app.helper.*
import net.exchange.app.service.service.IExchangeRatesService
import net.exchange.app.service.service.implementation.ExchangeRatesService
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val exchangeRatesService: IExchangeRatesService
) : ViewModel() {
    var baseCurrency = mutableStateOf(sharedPreferences.getCurrency())
    var dataRefreshInterval = mutableStateOf(sharedPreferences.getRefreshInterval())
    var currencies = mutableStateListOf<String>()

    fun setCurrency(currency: String) {
        baseCurrency.value = currency
        sharedPreferences.setCurrency(currency)
    }

    fun setRefreshInterval(refreshInterval: Int) {
        dataRefreshInterval.value = refreshInterval
        sharedPreferences.setRefreshInterval(refreshInterval)
    }

    fun getAllCurrencies(){
        viewModelScope.launch {
            exchangeRatesService.getExchangeRates().collect {
                currencies.addAll(it.rates.keys)
            }
        }
    }
}
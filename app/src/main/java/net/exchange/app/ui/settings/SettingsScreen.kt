package net.exchange.app.ui.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import net.exchange.app.ui.common.FullScreenLoadingIndicatorView

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.getAllCurrencies()
    }
    LazyColumn(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(text = "Hello, here you can change the settings :)", fontSize = 23.sp)

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Set the refresh interval for the data")
            Divider(
                color = White,
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        bottom = 20.dp
                    )
            )
            DataRefreshOption(viewModel, 3)
            DataRefreshOption(viewModel, 5)
            DataRefreshOption(viewModel, 15)

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Set the base currency")
            Divider(
                color = White,
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        bottom = 20.dp
                    )
            )
        }
        if (viewModel.currencies.size > 0) {
            viewModel.currencies.forEach {
                item {
                    CurrencyOption(viewModel = viewModel, currency = it)
                }
            }
        } else {
            item {
                FullScreenLoadingIndicatorView()
            }
        }
    }
}

@Composable
private fun DataRefreshOption(viewModel: SettingsViewModel, interval: Int) {
    Row {
        Text("$interval seconds: ")
        RadioButton(
            selected = viewModel.dataRefreshInterval.value == interval,
            onClick = {
                if (viewModel.dataRefreshInterval.value != interval)
                    viewModel.setRefreshInterval(interval)
            }
        )
    }
}

@Composable
private fun CurrencyOption(viewModel: SettingsViewModel, currency: String) {
    Row {
        Text(currency)
        RadioButton(
            selected = viewModel.baseCurrency.value == currency,
            onClick = {
                if (viewModel.baseCurrency.value != currency)
                    viewModel.setCurrency(currency)
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}
package net.exchange.app.ui.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation
import net.exchange.app.application.theme.BulletIDTheme
import net.exchange.app.application.theme.CommonColors.BackgroundColor
import net.exchange.app.application.theme.CommonColors.ListItemBackground
import net.exchange.app.application.theme.CommonColors.PrimaryColor
import net.exchange.app.ui.common.FullScreenLoadingIndicatorView
import net.exchange.app.ui.main.navigation.SettingsButton
import net.exchange.app.ui.main.navigation.TopBar
import java.text.SimpleDateFormat
import java.util.*


internal object MainComposeActivityValues {
    val settingsButtonPadding = 15.dp
    val settingsButtonSize = 25.dp
}

@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@ExperimentalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.O)
@InternalCoroutinesApi
@Composable
@ExperimentalMaterialApi
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                viewModel.updateBaseCurrency()
                viewModel.updateExchangeRates()
                viewModel.registerExchangeRatesScheduledUpdate()
            }
            Lifecycle.Event.ON_STOP -> {
                viewModel.removeExchangeRatesScheduledUpdate()
            }
            else -> {
                /* take a break <3 */
            }
        }
    }
    Scaffold(
        topBar = {
            TopBar(
                "Main Screen",
                actions = {
                    SettingsButton(context)
                },
                biggerTitle = true
            )
        },
    ) {
        if (viewModel.exchangeRates.value != null) {
            Row(
                modifier = Modifier.background(PrimaryColor),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text("Base currency: " + viewModel.baseCurrency.value, fontSize = 21.sp)
                    Spacer(Modifier.height(10.dp))
                    val date = Date(viewModel.exchangeRates.value?.timestamp!!)
                    val dateFormat = SimpleDateFormat("dd-MMM-yyyy, HH:mm a", Locale.ENGLISH)
                    Text("Timestamp: ${dateFormat.format(date)}", fontSize = 21.sp)
                    Spacer(Modifier.height(10.dp))
                    Text("Updated: " + dateFormat.format(viewModel.exchangeRates.value?.date!!), fontSize = 21.sp)
                    Spacer(Modifier.height(10.dp))
                    Divider(
                        color = White,
                        modifier = Modifier
                            .padding(
                                top = 2.dp,
                                bottom = 2.dp
                            )
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .background(PrimaryColor),
                        contentPadding = PaddingValues(
                        )
                    ) {
                        viewModel.exchangeRates.value?.rates?.forEach {
                            item {
                                val visible = mutableStateOf(false)
                                val rateHistory = mutableStateListOf<Double>()
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(10.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    backgroundColor = ListItemBackground,
                                    onClick = {
                                        visible.value = !visible.value
                                    }
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(text = "Currency: " + it.key)
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(text = "Current value: " + it.value.toString())
                                        AnimatedVisibility(visible = visible.value) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(150.dp)
                                                    .padding(30.dp)
                                            ) {
                                                LaunchedEffect(key1 = it.key) {
                                                    viewModel.getExchangeRateHistory(it.key) {
                                                        rateHistory.clear()
                                                        rateHistory.addAll(it)
                                                    }
                                                }
                                                AnimatedVisibility(
                                                    rateHistory.size > 0,
                                                    enter = expandHorizontally(
                                                        animationSpec = tween(
                                                            durationMillis = 1_000,
                                                            easing = LinearOutSlowInEasing
                                                        ),
                                                        expandFrom = Alignment.End
                                                    )
                                                ) {
                                                    LineChartView(rateHistory)
                                                }
                                                AnimatedVisibility(rateHistory.size == 0) {
                                                    FullScreenLoadingIndicatorView()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            LoadingIndicator()
        }
    }
}

@Composable
fun LineChartView(values: List<Double>) {
    val points = values.mapIndexed { index, value ->
        LineChartData.Point(
            value.toFloat(),
            "${index + 1}"
        )
    }
    LineChart(
        lineChartData = LineChartData(
            points = points
        ),
        // Optional properties.
        modifier = Modifier.fillMaxSize(),
        animation = simpleChartAnimation(),
        pointDrawer = FilledCircularPointDrawer(),
        lineDrawer = SolidLineDrawer(),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        horizontalOffset = 5f
    )
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}


@Composable
private fun LoadingIndicator() {
    val listHeaderTitleTextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = Color.White,
        lineHeight = 24.sp
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Loading data", style = listHeaderTitleTextStyle)
        Column(modifier = Modifier.fillMaxHeight(0.2F)) {
            FullScreenLoadingIndicatorView()
        }
    }
}

@ExperimentalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.O)
@InternalCoroutinesApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BulletIDTheme {
        MainScreen()
    }
}
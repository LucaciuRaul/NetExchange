package net.exchange.app.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.exchange.app.ui.common.IndicatorValues.padding
import net.exchange.app.ui.common.IndicatorValues.strokeWidth


private object IndicatorValues{
    val padding = 50.dp
    val strokeWidth = 5.dp
}

/**
 * Full screen circular progress indicator
 */
@Composable
fun FullScreenLoadingIndicatorView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            color = Color.White,
            strokeWidth = strokeWidth
        )
    }
}
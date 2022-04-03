package net.exchange.app.ui.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun LinearTransactionsChart(
    values: List<Double>
) {
    if (values.isEmpty()) return

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Total number of transactions.
        val totalRecords = values.size

        // Maximum distance between dots (transactions)
        val lineDistance = size.width / (totalRecords + 1)

        // Canvas height
        val cHeight = size.height

        // Add some kind of a "Padding" for the initial point where the line starts.
        var currentLineDistance = 0F + lineDistance

        val maxValue = values.maxOf { it }
        val minValue = values.minOf { it }
        val maxDifference = maxValue - minValue
        val relativePercentageOfScreen = (cHeight / maxDifference).toFloat()

        values.forEachIndexed { index, currentValue ->
            if (totalRecords >= index + 2) {
                drawLine(
                    start = Offset(
                        x = currentLineDistance,
                        y = calculateYCoordinate(
                            maxTransactionRateValue = maxValue,
                            relativePercentageOfScreen = relativePercentageOfScreen,
                            currentTransactionRate = currentValue,
                        )
                    ),
                    end = Offset(
                        x = currentLineDistance + lineDistance,
                        y = calculateYCoordinate(
                            maxTransactionRateValue = maxValue,
                            relativePercentageOfScreen = relativePercentageOfScreen,
                            currentTransactionRate = values[index + 1],
                        )
                    ),
                    color = Color(40, 193, 218),
                    strokeWidth = Stroke.DefaultMiter
                )
            }
            currentLineDistance += lineDistance
        }
    }
}

/**
 * Calculates the Y pixel coordinate for a given transaction rate.
 *
 * @param maxTransactionRateValue the highest rate value in the whole list of transactions.
 * @param currentTransactionRate the current transaction RATE while iterating the list of transactions.
 * @param relativePercentageOfScreen the canvas relative HEIGHT for drawing the linear chart.
 *
 * @return [Float] Y coordinate for a transaction rate.
 */
private fun calculateYCoordinate(
    maxTransactionRateValue: Double,
    currentTransactionRate: Double,
    relativePercentageOfScreen: Float
): Float {
    val maxAndCurrentValueDifference = (maxTransactionRateValue - currentTransactionRate)
        .toFloat()
    return maxAndCurrentValueDifference * relativePercentageOfScreen
}
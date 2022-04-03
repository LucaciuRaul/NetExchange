package net.exchange.app.application.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = CommonColors.PrimaryColor,
    primaryVariant = CommonColors.PrimaryColor,
    secondary = CommonColors.SecondaryColor,
    background = CommonColors.BackgroundColor,
)

private val LightColorPalette = lightColors(
    primary = CommonColors.PrimaryColor,
    primaryVariant = CommonColors.PrimaryColor,
    secondary = CommonColors.SecondaryColor,
    background = CommonColors.BackgroundColor,
)

@Composable
fun BulletIDTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
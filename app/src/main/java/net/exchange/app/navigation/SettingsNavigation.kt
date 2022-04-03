package net.exchange.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.exchange.app.ui.settings.SettingsScreen

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun SettingsNavigation(navController: NavHostController, innerPadding: PaddingValues, title: MutableState<Int>) {
    NavHost(
        navController,
        startDestination = NavigationItem.Settings.route,
        Modifier.padding(innerPadding)
    ) {
        composable(NavigationItem.Settings.route) {
            title.value = NavigationItem.Settings.titleStringRes
            SettingsScreen()
        }
    }
}
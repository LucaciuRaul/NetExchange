package net.exchange.app.navigation

import android.app.Activity
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import net.exchange.app.R

@Composable
fun BackNavigationButton(navController: NavController, parentActivityRouteName: String?) {
    val context = (LocalContext.current as? Activity)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    IconButton(onClick = {
        if (currentRoute == parentActivityRouteName) {
            context?.finish()
        } else {
            val hasNavigatedUp = navController.navigateUp()
            if (!hasNavigatedUp) {
                context?.finish()
            }
        }
    }) {
        Icon(Icons.Filled.ArrowBack, stringResource(R.string.back))
    }
}
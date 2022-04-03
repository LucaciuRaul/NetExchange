package net.exchange.app.ui.settings

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import net.exchange.app.application.theme.BulletIDTheme
import net.exchange.app.navigation.BackNavigationButton
import net.exchange.app.navigation.NavigationItem
import net.exchange.app.navigation.SettingsNavigation
import net.exchange.app.ui.main.navigation.TopBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BulletIDTheme {
                SettingsActivityView()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)

@Composable
fun SettingsActivityView() {
    val navController = rememberNavController()
    val title = remember { mutableStateOf(NavigationItem.Settings.titleStringRes) }
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(title.value),
                navigationIcon = {
                    BackNavigationButton(
                        navController,
                        NavigationItem.Settings.route
                    )
                }
            )
        }
    )
    { innerPadding ->
        SettingsNavigation(navController, innerPadding, title)
    }
}
package net.exchange.app.ui.main.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import net.exchange.app.R
import net.exchange.app.navigation.NavigationItem
import net.exchange.app.ui.main.MainComposeActivityValues
import net.exchange.app.ui.settings.SettingsActivity

@Composable
fun SettingsButton(context: Context) {
    Box(modifier = Modifier.padding(end = MainComposeActivityValues.settingsButtonPadding)) {
        IconButton(
            modifier = Modifier
                .size(MainComposeActivityValues.settingsButtonSize),
            onClick = {
                context.startActivity(Intent(context, SettingsActivity::class.java))
            }) {
            Icon(
                painter = painterResource(R.drawable.ic_setting_line),
                contentDescription = NavigationItem.Settings.route
            )
        }
    }
}
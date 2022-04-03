package net.exchange.app.ui.main.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    title: String,
    biggerTitle: Boolean = false,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title, fontSize = if(biggerTitle) 24.sp else 18.sp) },
        backgroundColor = MaterialTheme.colors.primary,
        actions = actions,
        navigationIcon = navigationIcon
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar("Title")
}
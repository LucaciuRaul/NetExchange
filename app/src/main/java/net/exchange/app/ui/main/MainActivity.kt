package net.exchange.app.ui.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import net.exchange.app.R
import net.exchange.app.application.theme.BulletIDTheme


@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    private val permissions = arrayOf(
        Manifest.permission.INTERNET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsResults ->
            if (permissionsResults.containsValue(false)) {
                Toast.makeText(
                    this,
                    "No permissions for: " + "${permissionsResults.filter { !it.value }.keys}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                try {
                    setContent {
                        BulletIDTheme {
                            MainScreen()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(ERROR_STRING, e.toString())
                }
            }
        }.launch(permissions)
    }

    companion object {
        private const val ERROR_STRING = "Exchange - Error"
    }
}
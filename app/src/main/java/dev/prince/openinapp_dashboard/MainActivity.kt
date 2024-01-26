package dev.prince.openinapp_dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.prince.openinapp_dashboard.ui.dashboard.DashboardScreen
import dev.prince.openinapp_dashboard.ui.theme.OpeninAppDashboardTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getData()
        setContent {
            OpeninAppDashboardTheme {
                DashboardScreen()
            }
        }
    }
}


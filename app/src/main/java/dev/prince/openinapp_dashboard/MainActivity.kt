package dev.prince.openinapp_dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint
import dev.prince.openinapp_dashboard.ui.NavGraphs
import dev.prince.openinapp_dashboard.ui.bottomnav.BottomBar
import dev.prince.openinapp_dashboard.ui.theme.OpeninAppDashboardTheme
import dev.prince.openinapp_dashboard.util.LocalSnackbar
import dev.prince.openinapp_dashboard.util.isInternetAvailable
import dev.prince.openinapp_dashboard.util.oneShotFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            if (isInternetAvailable(context)) {
                OpeninAppDashboardTheme {
                    ScaffoldDefaults.contentWindowInsets
                    val engine = rememberNavHostEngine()
                    val navController = engine.rememberNavController()

                    val snackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()

                    val onSnackbarMessageReceived = fun(message: String) {
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = {
                            SnackbarHost(snackbarHostState)
                        }
                    ) { contentPadding ->
                        CompositionLocalProvider(
                            LocalSnackbar provides onSnackbarMessageReceived
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                DestinationsNavHost(
                                    modifier = Modifier
                                        .padding(contentPadding)
                                        .weight(1f),
                                    navGraph = NavGraphs.root,
                                    navController = navController,
                                    engine = engine
                                )
                                BottomBar(navController)
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(context,"Internet not available", Toast.LENGTH_SHORT).show()
            }

        }
    }
}


package dev.prince.openinapp_dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint
import dev.prince.openinapp_dashboard.ui.NavGraphs
import dev.prince.openinapp_dashboard.ui.appCurrentDestinationAsState
import dev.prince.openinapp_dashboard.ui.bottomnav.BottomBar
import dev.prince.openinapp_dashboard.ui.startAppDestination
import dev.prince.openinapp_dashboard.ui.theme.Blue
import dev.prince.openinapp_dashboard.ui.theme.OpeninAppDashboardTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpeninAppDashboardTheme {
                ScaffoldDefaults.contentWindowInsets
                val engine = rememberNavHostEngine()
                val navController = engine.rememberNavController()
                val destination = navController.appCurrentDestinationAsState().value
                    ?: NavGraphs.root.startRoute.startAppDestination

                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                val onSnackbarMessageReceived = fun(message: String) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp), // Add padding to avoid edge clipping
                            containerColor = Color.White
                        ) {
                            BottomBar(navController)

                        }
                    },
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) { contentPadding ->
                    CompositionLocalProvider(
                        LocalSnackbar provides onSnackbarMessageReceived
                    ) {
                        DestinationsNavHost(
                            modifier = Modifier
                                .padding(contentPadding),
                            navGraph = NavGraphs.root,
                            navController = navController,
                            engine = engine
                        )
                    }
                }
            }
        }
    }
}


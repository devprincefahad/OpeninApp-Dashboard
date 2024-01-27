package dev.prince.openinapp_dashboard.ui.bottomnav

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.navigation.navigate
import dev.prince.openinapp_dashboard.R
import dev.prince.openinapp_dashboard.ui.NavGraphs
import dev.prince.openinapp_dashboard.ui.appCurrentDestinationAsState
import dev.prince.openinapp_dashboard.ui.destinations.TypedDestination
import dev.prince.openinapp_dashboard.ui.startAppDestination
import dev.prince.openinapp_dashboard.ui.theme.Blue
import dev.prince.openinapp_dashboard.ui.theme.figTreeFamily

@SuppressLint("RestrictedApi")
@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                BottomBarDestination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = currentDestination == destination.direction,
                        onClick = {
                            // remove all navigation items from the stack
                            // so only the currently selected screen remains in the stack
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            navController
                                // Restore state when reselecting a previously selected item
                                .navigate(destination.direction, fun NavOptionsBuilder.() {
                                    launchSingleTop = true
                                    val navigationRoutes =
                                        BottomBarDestination.entries.toTypedArray()

                                    val firstBottomBarDestination =
                                        navController.currentBackStack.value
                                            .firstOrNull { navBackStackEntry ->
                                                checkForDestinations(
                                                    navigationRoutes,
                                                    navBackStackEntry
                                                )
                                            }
                                            ?.destination
                                    // remove all navigation items from the stack
                                    // so only the currently selected screen remains in the stack
                                    if (firstBottomBarDestination != null) {
                                        popUpTo(firstBottomBarDestination.id) {
                                            inclusive = true
                                            saveState = true
                                        }
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                })
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(destination.icon),
                                contentDescription = stringResource(destination.label),
                                tint = if (currentDestination == destination.direction) Color.Black else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(destination.label),
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = figTreeFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (currentDestination == destination.direction) Color.Black else Color.Gray
                                )
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = Color.Unspecified,
                            indicatorColor = Color.White
                        )
                    )
                }
            }
        },
        floatingActionButton = {

            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    Toast.makeText(context, "fab click", Toast.LENGTH_SHORT).show()
                },
                containerColor = Blue,
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = "Add icon"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { it ->
        Column(modifier = Modifier.padding(it)) {}
    }

}

fun checkForDestinations(
    navigationRoutes: Array<BottomBarDestination>,
    navBackStackEntry: NavBackStackEntry
): Boolean {
    navigationRoutes.forEach {
        if (it.direction.route == navBackStackEntry.destination.route) {
            return true
        }
    }
    return false
}
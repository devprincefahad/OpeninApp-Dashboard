package dev.prince.openinapp_dashboard.ui.bottomnav

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.prince.openinapp_dashboard.ui.NavGraphs
import dev.prince.openinapp_dashboard.ui.appCurrentDestinationAsState
import dev.prince.openinapp_dashboard.ui.destinations.TypedDestination
import dev.prince.openinapp_dashboard.ui.noRippleClickable
import dev.prince.openinapp_dashboard.ui.startAppDestination
import dev.prince.openinapp_dashboard.ui.theme.Blue
import dev.prince.openinapp_dashboard.ui.theme.figTreeFamily

@SuppressLint("RestrictedApi")
@Composable
fun ColumnScope.BottomBar(
    navController: NavController
) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    val navigateToDestination = { destination: DirectionDestinationSpec ->
        navController
            // Restore state when reselecting a previously selected item
            .navigate(destination, fun NavOptionsBuilder.() {
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
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
            ,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavDestination(
                BottomBarDestination.Dashboard,
                navigateToDestination,
                currentDestination == BottomBarDestination.Dashboard.direction
            )
            BottomNavDestination(
                BottomBarDestination.Courses,
                navigateToDestination,
                currentDestination == BottomBarDestination.Courses.direction
            )
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .offset(y = (-16).dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(BottomBarDestination.AddScreen.icon),
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            BottomNavDestination(
                BottomBarDestination.Campaigns,
                navigateToDestination,
                currentDestination == BottomBarDestination.Campaigns.direction
            )
            BottomNavDestination(
                BottomBarDestination.Profile,
                navigateToDestination,
                currentDestination == BottomBarDestination.Profile.direction
            )
        }
    }
}

@Composable
private fun BottomNavDestination(
    destination: BottomBarDestination,
    navigateToDestination: (DirectionDestinationSpec) -> Unit,
    selected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.noRippleClickable {
            navigateToDestination(destination.direction)
        }
    ) {
        Icon(
            painter = painterResource(destination.icon),
            contentDescription = stringResource(destination.label),
            tint = if (selected) Color.Black else Color.Unspecified
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(destination.label),
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = figTreeFamily,
                fontWeight = FontWeight.SemiBold,
                color = if (selected) Color.Black else Color.Gray
            )
        )
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
package dev.prince.openinapp_dashboard.ui.bottomnav

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.prince.openinapp_dashboard.R
import dev.prince.openinapp_dashboard.ui.destinations.CampaignsScreenDestination
import dev.prince.openinapp_dashboard.ui.destinations.CoursesScreenDestination
import dev.prince.openinapp_dashboard.ui.destinations.DashboardScreenDestination
import dev.prince.openinapp_dashboard.ui.destinations.ProfileScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    @StringRes val label: Int
) {
    Dashboard(DashboardScreenDestination, R.drawable.icon_links, R.string.links),
    Courses(CoursesScreenDestination, R.drawable.icon_courses, R.string.courses),
    Campaigns(CampaignsScreenDestination, R.drawable.icon_campaigns, R.string.campaigns),
    Profile(ProfileScreenDestination, R.drawable.icon_profile, R.string.profile),
}
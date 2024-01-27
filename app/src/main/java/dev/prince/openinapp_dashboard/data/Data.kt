package dev.prince.openinapp_dashboard.data

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("favourite_links")
    val favouriteLinks: List<String>,
    @SerializedName("overall_url_chart")
    val overallUrlChart: OverallUrlChart,
    @SerializedName("recent_links")
    val recentLinks: List<Link>,
    @SerializedName("top_links")
    val topLinks: List<Link>
)
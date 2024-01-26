package dev.prince.openinapp_dashboard.data

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("applied_campaign")
    val appliedCampaign: Int,
    val data: Data,
    @SerializedName("extra_income")
    val extraIncome: Double,
    @SerializedName("links_created_today")
    val linksCreatedToday: Int,
    val message: String,
    @SerializedName("start_time")
    val startTime: String,
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("support_whatsapp_number")
    val supportWhatsappNumber: String,
    @SerializedName("today_clicks")
    val todayClicks: Int,
    @SerializedName("top_location")
    val topLocation: String,
    @SerializedName("top_source")
    val topSource: String,
    @SerializedName("total_clicks")
    val totalClicks: Int,
    @SerializedName("total_links")
    val totalLinks: Int
)
package dev.prince.openinapp_dashboard.data

import com.google.gson.annotations.SerializedName

data class RecentLink(
    @SerializedName("app")
    val app: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("domain_id")
    val domainId: String,
    @SerializedName("is_favourite")
    val isFavourite: Boolean,
    @SerializedName("original_image")
    val originalImage: String,
    @SerializedName("smart_link")
    val smartLink: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("times_ago")
    val timesAgo: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("total_clicks")
    val totalClicks: Int,
    @SerializedName("url_id")
    val urlId: Int,
    @SerializedName("url_prefix")
    val urlPrefix: Any,
    @SerializedName("url_suffix")
    val urlSuffix: String,
    @SerializedName("web_link")
    val webLink: String
)
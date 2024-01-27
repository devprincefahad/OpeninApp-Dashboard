package dev.prince.openinapp_dashboard.network

import dev.prince.openinapp_dashboard.data.DashboardResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {

    @GET("api/v1/dashboardNew")
    suspend fun getDashboardData(): DashboardResponse

}
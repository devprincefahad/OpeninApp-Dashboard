package dev.prince.openinapp_dashboard.network

import dev.prince.openinapp_dashboard.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import dev.prince.openinapp_dashboard.local.SharedPrefHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiInterceptor @Inject constructor(
    private val prefs: SharedPrefHelper
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest: Request = chain.request()
        val request: Request = oldRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
            .build()
        return chain.proceed(request)
    }
}
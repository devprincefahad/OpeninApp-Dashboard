package dev.prince.openinapp_dashboard

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.HiltAndroidApp
import dev.prince.openinapp_dashboard.local.SharedPrefHelper
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import javax.inject.Inject

@HiltAndroidApp
class OpeninApp : Application() {

    override fun onCreate() {
        val sharedPref = getSharedPreferences("openinapp-dashboard", Context.MODE_PRIVATE)

        sharedPref.edit {
            putString(KEY_TOKEN, ACCESS_TOKEN)
        }
        super.onCreate()

        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)

    }
}
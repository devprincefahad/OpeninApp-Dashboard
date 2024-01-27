package dev.prince.openinapp_dashboard

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.HiltAndroidApp
import dev.prince.openinapp_dashboard.util.ACCESS_TOKEN
import dev.prince.openinapp_dashboard.util.KEY_TOKEN
import logcat.AndroidLogcatLogger
import logcat.LogPriority

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
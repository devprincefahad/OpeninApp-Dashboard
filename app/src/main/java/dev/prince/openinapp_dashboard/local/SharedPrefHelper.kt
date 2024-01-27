package dev.prince.openinapp_dashboard.local

import android.content.Context
import dev.prince.openinapp_dashboard.util.KEY_TOKEN
import javax.inject.Inject

class SharedPrefHelper @Inject constructor(
    context: Context
) {

    private val sharedPref = context.getSharedPreferences("openinapp-dashboard", Context.MODE_PRIVATE)

    var token: String?
        get() = getString(KEY_TOKEN)
        set(value) = saveString(
            KEY_TOKEN,
            value ?: throw IllegalStateException("Token cannot be null while setting")
        )

    private fun saveString(key: String, value: String) {
        sharedPref.edit().putString(key, value).apply()
    }

    private fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }
}
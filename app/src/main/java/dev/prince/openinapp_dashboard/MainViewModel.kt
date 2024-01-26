package dev.prince.openinapp_dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.prince.openinapp_dashboard.data.DashboardResponse
import dev.prince.openinapp_dashboard.network.ApiService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

   fun getData() {
        viewModelScope.launch {
            try {
                // Make the API call
                val response = apiService.getDashboardData()

                // Log the data
                Log.d("MainViewModel", "Dashboard Data: $response")
            } catch (e: Exception) {
                // Handle network errors or other exceptions
                Log.e("MainViewModel", "Error: ${e.message}", e)
            }
        }
    }
}
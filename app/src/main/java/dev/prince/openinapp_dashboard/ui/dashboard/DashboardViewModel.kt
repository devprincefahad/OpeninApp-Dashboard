package dev.prince.openinapp_dashboard.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.prince.openinapp_dashboard.R
import dev.prince.openinapp_dashboard.data.DashboardItem
import dev.prince.openinapp_dashboard.data.Link
import dev.prince.openinapp_dashboard.network.ApiService
import dev.prince.openinapp_dashboard.util.formatDate
import dev.prince.openinapp_dashboard.util.oneShotFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LinkType {
    TOP_LINKS,
    RECENT_LINKS
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {

    val messages = oneShotFlow<String>()

    private val _dashboardItems = MutableStateFlow<List<DashboardItem>>(emptyList())
    val dashboardItems: StateFlow<List<DashboardItem>> get() = _dashboardItems

    private val _selectedLinkType = MutableStateFlow(LinkType.TOP_LINKS)
    val selectedLinkType: StateFlow<LinkType> = _selectedLinkType

    private val _topLinks = MutableStateFlow<List<Link>>(emptyList())
    val topLinks: StateFlow<List<Link>> = _topLinks

    private val _recentLinks = MutableStateFlow<List<Link>>(emptyList())
    val recentLinks: StateFlow<List<Link>> = _recentLinks

    private val _graphData = MutableStateFlow<List<LineData>>(listOf(LineData("Loading", 1)))
    val graphData: Flow<List<LineData>> get() = _graphData.asStateFlow()

    fun showMessage(text: String) {
        messages.tryEmit(text)
    }

    fun getDashboardItems() {
        viewModelScope.launch {
            try {
                val response = api.getDashboardData()
                val items = listOf(
                    DashboardItem(
                        R.drawable.icon_clicks,
                        "Today Clicks",
                        response.todayClicks.toString()
                    ),
                    DashboardItem(R.drawable.icon_location, "Top Location", response.topLocation),
                    DashboardItem(R.drawable.icon_source, "Top Source", response.topSource),
                )
                _dashboardItems.emit(items)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadLinks() {
        viewModelScope.launch {
            try {
                val response = api.getDashboardData()
                _topLinks.emit(response.data.topLinks)
                _recentLinks.emit(response.data.recentLinks)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectLinkType(linkType: LinkType) {
        _selectedLinkType.value = linkType
    }

    fun fetchGraphData() {
        viewModelScope.launch {
            try {
                val response = api.getDashboardData()
                val overallUrlChart = response.data.overallUrlChart
                _graphData.emit(overallUrlChart
                    .entries
                    .map {
                        LineData(formatDate(it.key), it.value)
                    }
                    .takeLast(7))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

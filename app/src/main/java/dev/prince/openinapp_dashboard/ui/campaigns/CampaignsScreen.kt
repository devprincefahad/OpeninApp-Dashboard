package dev.prince.openinapp_dashboard.ui.campaigns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import dev.prince.openinapp_dashboard.ui.theme.figTreeFamily

@Destination
@Composable
fun CampaignsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Campaigns",
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = figTreeFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )
    }
}
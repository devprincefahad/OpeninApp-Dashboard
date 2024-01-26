package dev.prince.openinapp_dashboard.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.prince.openinapp_dashboard.R
import dev.prince.openinapp_dashboard.ui.SheetSurface
import dev.prince.openinapp_dashboard.ui.theme.Blue
import dev.prince.openinapp_dashboard.ui.theme.figTreeFamily

@Composable

fun DashboardScreen() {

    Column(
        modifier = Modifier
            .background(color = Blue)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 32.dp, bottom = 22.dp,
                    start = 16.dp, end = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(16.dp))

            Text(
                text = "Dashboard",
                color = Color.White,
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = figTreeFamily,
                    fontWeight = FontWeight.SemiBold
                ),
            )

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .size(44.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_setting),
                    contentDescription = "Setting Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }

        SheetSurface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Column {
                Spacer(Modifier.width(16.dp))

                Text(
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 16.dp
                    ),
                    text = "Good Morning",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = figTreeFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF999CA0)
                    )
                )

                Row(
                    modifier = Modifier.padding(
                        top = 8.dp,
                        start = 16.dp
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(end = 6.dp),
                        text = "Ajay Manva",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = figTreeFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_hi),
                        contentDescription = "Emoji",
                        modifier = Modifier.size(24.dp) // Set the size of your emoji
                    )
                }

            }

        }
    }
}

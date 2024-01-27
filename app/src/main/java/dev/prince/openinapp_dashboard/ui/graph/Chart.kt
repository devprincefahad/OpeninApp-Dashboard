package dev.prince.openinapp_dashboard.ui.graph

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.prince.openinapp_dashboard.R
import dev.prince.openinapp_dashboard.data.OverallUrlChart
import dev.prince.openinapp_dashboard.ui.theme.Blue
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.round
import kotlin.math.roundToInt
/*
@Composable
fun Chart(
    dates: List<OverallUrlChart> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Blue
) {
    val spacing = 100f
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }
    val upperValue = remember(dates) {
        (dates.maxOfOrNull { it.close }?.plus(1))?.roundToInt() ?: 0
    }
    val lowerValue = remember(dates) {
        dates.minOfOrNull { it.close }?.toInt() ?: 0
    }
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / dates.size
        (0 until dates.size - 1 step 2).forEach { i ->
            val info = dates[i]
            val hour = info.date.hour
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + i * spacePerHour,
                    size.height - 5,
                    textPaint
                )
            }
        }
        val priceStep = (upperValue - lowerValue) / 5f
        (0..4).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + priceStep * i).toString(),
                    30f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }
        var lastX = 0f
        val strokePath = Path().apply {
            val height = size.height
            for(i in dates.indices) {
                val info = dates[i]
                val nextInfo = dates.getOrNull(i + 1) ?: dates.last()
                val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (i + 1) * spacePerHour
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if(i == 0) {
                    moveTo(x1, y1)
                }
                lastX = (x1 + x2) / 2f
                quadraticBezierTo(
                    x1, y1, lastX, (y1 + y2) / 2f
                )
            }
        }
        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )
        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}*/

@Composable
fun LineChart(chartData: Map<String, Int>) {
    val maxValue = chartData.values.maxOrNull() ?: 0

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val lineWidth = 2.dp.toPx()
        val lineColor = Color.Blue
        val transparentColor = Color(0x300000FF)

        drawLine(
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            color = lineColor,
            strokeWidth = lineWidth
        )

        val xStep = size.width / (chartData.size - 1).toFloat()

        var prevX = 0f
        var prevY = size.height

        chartData.toList().forEachIndexed { index, (date, value) ->
            val x = index * xStep
            val y = size.height - (value.toFloat() / maxValue.toFloat() * size.height)

            drawLine(
                start = Offset(prevX, prevY),
                end = Offset(x, y),
                color = lineColor,
                strokeWidth = lineWidth
            )

            drawRect(
                color = transparentColor,
                size = Size(x - prevX, size.height),
                topLeft = Offset(prevX, 0f)
            )

            prevX = x
            prevY = y
        }
    }
}
@Composable
fun MonthlyData(chartData: Map<String, Int>) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    val months = chartData.keys.map {
        dateFormat.parse(it)?.month ?: 0
    }.distinct()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        items(months) { month ->
            val monthName = DateFormatSymbols().months[month]

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .clickable { /* Handle month click */ },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_add),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = monthName.take(3),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
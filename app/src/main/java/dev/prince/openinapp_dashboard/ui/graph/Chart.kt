package dev.prince.openinapp_dashboard.ui.graph

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

/*@Composable
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
}*/
/*
@Composable
fun LineChart(chartData: Map<String, Int>) {
    val maxYValue = 100
    val gapY = 25
    val maxXValue = chartData.size
    val gapX = 1

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Draw Y-axis
        drawLine(
            start = Offset(50f, 50f),
            end = Offset(50f, size.height - 50f),
            color = Color.Black
        )

        // Draw Y-axis scale
        for (i in 0..maxYValue step gapY) {
            val yPos = size.height - 50f - (i.toFloat() / maxYValue) * (size.height - 100f)
            drawLine(
                start = Offset(50f, yPos),
                end = Offset(45f, yPos),
                color = Color.Black
            )

            drawText(
                text = i.toString(),
                color = Color.Black,
                fontSize = 12.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.offset(30.dp, yPos - 6.dp)
            )
        }

        // Draw X-axis
        drawLine(
            start = Offset(50f, size.height - 50f),
            end = Offset(size.width - 50f, size.height - 50f),
            color = Color.Black
        )

        // Draw X-axis scale and data points
        var xPos = 50f
        chartData.forEachIndexed { index, (date, clicks) ->
            val yPos = size.height - 50f - (clicks.toFloat() / maxYValue) * (size.height - 100f)

            drawLine(
                start = Offset(xPos, size.height - 50f),
                end = Offset(xPos, yPos),
                color = Color.Blue
            )

            drawText(
                text = date,
                color = Color.Black,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(x = xPos - 20.dp, y = size.height - 30.dp)
            )

            xPos += (size.width - 100f) / maxXValue
        }
    }
}*/
/*
@Composable
fun LineGraph(chartData: Map<String, Int>) {
    val maxYValue = 100f
    val yStep = 25f
    val maxXValue = 31f
    val xStep = 4f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Line Graph", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawLine(
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    color = Color.Black,
                    strokeWidth = 2.dp.toPx(),
                )

                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    color = Color.Black,
                    strokeWidth = 2.dp.toPx(),
                )

                val yStepPixels = (size.height / maxYValue) * yStep
                val xStepPixels = (size.width / maxXValue) * xStep

                for (i in 0..4) {
                    val y = i * yStepPixels
                    drawLine(
                        start = Offset(0f, size.height - y),
                        end = Offset(-8f, size.height - y),
                        color = Color.Black,
                        strokeWidth = 2.dp.toPx(),
                    )

                    drawText(
                        text = "${i * 25}",
                        offset = Offset(-32f, size.height - y),
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                for (i in 1..8) {
                    val x = i * xStepPixels
                    drawLine(
                        start = Offset(x, size.height),
                        end = Offset(x, size.height + 8f),
                        color = Color.Black,
                        strokeWidth = 2.dp.toPx(),
                    )

                    drawText(
                        text = "${i * 4}",
                        offset = Offset(x - 8f, size.height + 24f),
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                val points = chartData.map { entry ->
                    val date = entry.key
                    val value = entry.value.toFloat()

                    val dayOfMonth = SimpleDateFormat("yyyy-MM-dd").parse(date)?.day ?: 1

                    Offset(dayOfMonth * xStepPixels, size.height - (value / maxYValue) * size.height)
                }

                drawPoints(
                    points = points,
                    color = Color.Blue,
                    strokeWidth = 4.dp.toPx(),
                )

                drawPath(
                    points = points,
                    color = Color.Blue,
                    strokeWidth = 2.dp.toPx(),
                    pathEffect = null
                )
            }
        }
    }
}*/
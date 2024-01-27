package dev.prince.openinapp_dashboard.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.prince.openinapp_dashboard.R
import dev.prince.openinapp_dashboard.convertDateFormat
import dev.prince.openinapp_dashboard.data.Link
import dev.prince.openinapp_dashboard.shortenText
import dev.prince.openinapp_dashboard.ui.dashboard.DashboardViewModel
import dev.prince.openinapp_dashboard.ui.theme.Blue
import dev.prince.openinapp_dashboard.ui.theme.DarkWhite
import dev.prince.openinapp_dashboard.ui.theme.figTreeFamily

@Composable
fun SheetSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = DarkWhite,
        content = content
    )
}

@Composable
fun BoxWithImageAndText(imageResourceId: Int, key: String, value: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp)
            .height(120.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
    ) {
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(top = 12.dp, start = 12.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = key,
            modifier = Modifier.padding(top = 8.dp, start = 12.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = figTreeFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )
        Text(
            text = value,
            modifier = Modifier.padding(top = 6.dp, start = 12.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = figTreeFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF999CA0)
            )
        )
    }
}

@Composable
fun LinkTypeText(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        modifier = Modifier
            .noRippleClickable { onClick() }
            .background(
                color = if (isSelected) Blue else Color.Unspecified,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 8.dp, horizontal = 18.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = figTreeFamily,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF999CA0)
        )
    )
}

@Composable
fun LinkItemUI(
    link: Link,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = link.originalImage,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(54.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.img_placeholder)
                )

                Column {
                    Text(
                        text = shortenText(link.title),
                        maxLines = 1,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = figTreeFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = convertDateFormat(link.createdAt),
                        maxLines = 1,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = figTreeFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF999CA0)
                        )
                    )
                }

                Spacer(Modifier.weight(1f))

                Column {
                    Text(
                        text = "${link.totalClicks}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = figTreeFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Clicks",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = figTreeFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF999CA0)
                        )
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFFE8F1FF)
                )
                .fillMaxWidth()
                .dashedBorder(
                    color = Color(0xFF1369F0),
                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )

        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = shortenText(link.webLink),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = figTreeFamily,
                        fontWeight = FontWeight.Normal,
                        color = Blue
                    )
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .noRippleClickable {
                            clipboardManager.setText(
                                AnnotatedString(link.webLink)
                            )
                            viewModel.showMessage("Link copied to clipboard.")
                        },
                    painter = painterResource(R.drawable.icon_copy),
                    contentDescription = "Copy Icon",
                    tint = Color.Unspecified
                )

            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Modifier.dashedBorder(
    color: Color,
    shape: Shape,
    strokeWidth: Dp = 1.dp,
    dashWidth: Dp = 4.dp,
    gapWidth: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
) = this.drawWithContent {
    val outline = shape.createOutline(size, layoutDirection, this)

    val path = Path()
    path.addOutline(outline)

    val stroke = Stroke(
        cap = cap,
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashWidth.toPx(), gapWidth.toPx()),
            phase = 0f
        )
    )

    this.drawContent()

    drawPath(
        path = path,
        style = stroke,
        color = color
    )
}
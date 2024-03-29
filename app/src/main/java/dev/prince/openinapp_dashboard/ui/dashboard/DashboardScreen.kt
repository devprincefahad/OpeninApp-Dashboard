package dev.prince.openinapp_dashboard.ui.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaikeerthick.composable_graphs.composables.line.LineGraph
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphColors
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphVisibility
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import dev.prince.openinapp_dashboard.util.LocalSnackbar
import dev.prince.openinapp_dashboard.util.NoRippleInteractionSource
import dev.prince.openinapp_dashboard.R
import dev.prince.openinapp_dashboard.util.getGreeting
import dev.prince.openinapp_dashboard.ui.BoxWithImageAndText
import dev.prince.openinapp_dashboard.ui.ColumnProgressIndicator
import dev.prince.openinapp_dashboard.ui.LinkItemUI
import dev.prince.openinapp_dashboard.ui.LinkTypeText
import dev.prince.openinapp_dashboard.ui.SheetSurface
import dev.prince.openinapp_dashboard.ui.noRippleClickable
import dev.prince.openinapp_dashboard.ui.theme.Blue
import dev.prince.openinapp_dashboard.ui.theme.DarkWhite
import dev.prince.openinapp_dashboard.ui.theme.figTreeFamily
import kotlinx.coroutines.delay

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
@RootNavGraph(start = true)
@Destination
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
) {

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        delay(400)
        isLoading = false
    }

    // Items in Dashboard
    val dashboardItems by viewModel.dashboardItems.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getDashboardItems()
    }

    // Link Type in Dashboard
    val selectedLinkType by viewModel.selectedLinkType.collectAsState()
    val topLinks by viewModel.topLinks.collectAsState()
    val recentLinks by viewModel.recentLinks.collectAsState()

    val snackbar = LocalSnackbar.current

    LaunchedEffect(Unit) {
        viewModel.messages.collect {
            snackbar(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadLinks()
    }

    LaunchedEffect(Unit) {
        viewModel.fetchGraphData()
    }

    var showAllItems by remember { mutableStateOf(false) }

    var showButton by remember { mutableStateOf(false) }

    if (isLoading) {
        ColumnProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .background(color = Blue)
                .verticalScroll(rememberScrollState())
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
                            .noRippleClickable {
                                viewModel.showMessage("Settings")
                            }
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
                        text = getGreeting(),
                        style = TextStyle(
                            fontSize = 18.sp,
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
                            modifier = Modifier.padding(end = 8.dp),
                            text = "Ajay Manva",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontFamily = figTreeFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        )
                        Image(
                            painter = painterResource(id = R.drawable.icon_hi),
                            contentDescription = "Emoji",
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .height(280.dp)
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(color = DarkWhite),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        LineGraph(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            data = viewModel.graphData.collectAsState(
                                listOf(
                                    LineData(
                                        "Loading",
                                        1
                                    )
                                )
                            ).value,
                            onPointClick = {
                                // do something with value
                            },
                            style = LineGraphStyle(
                                colors = LineGraphColors(
                                    lineColor = Blue,
                                    pointColor = Blue,
                                    clickHighlightColor = Blue
                                ),
                                visibility = LineGraphVisibility(
                                    isGridVisible = false,
                                    isCrossHairVisible = false
                                )
                            )
                        )
                    }

                    LazyRow(
                        modifier = Modifier.padding(
                            top = 8.dp,
                            start = 8.dp
                        )
                    ) {
                        items(dashboardItems.size) { index ->
                            val item = dashboardItems[index]
                            BoxWithImageAndText(
                                imageResourceId = item.image,
                                key = item.valueText,
                                value = item.keyText
                            )
                        }
                    }

                    ButtonTransparent(
                        modifier = Modifier,
                        icon = R.drawable.icon_arrows,
                        btnText = "View Analytics",
                        onClick = {
                            viewModel.showMessage("Analytics")
                        }
                    )

                    Row(
                        modifier = Modifier.padding(
                            start = 16.dp, end = 16.dp,
                            top = 18.dp, bottom = 8.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        LinkTypeText(
                            text = "Top Links",
                            isSelected = selectedLinkType == LinkType.TOP_LINKS
                        ) {
                            viewModel.selectLinkType(LinkType.TOP_LINKS)
                        }
                        LinkTypeText(
                            text = "Recent Links",
                            isSelected = selectedLinkType == LinkType.RECENT_LINKS
                        ) {
                            viewModel.selectLinkType(LinkType.RECENT_LINKS)
                        }

                        Spacer(Modifier.weight(1f))

                        Icon(
                            modifier = Modifier
                                .noRippleClickable {
                                    viewModel.showMessage("Search")
                                }
                                .size(46.dp)
                                .padding(start = 8.dp),
                            painter = painterResource(R.drawable.icon_search),
                            contentDescription = "Search Icon",
                            tint = Color.Unspecified
                        )

                    }

                    LazyColumn(
                        modifier = Modifier
                            .height(if (showAllItems) 750.dp else 600.dp)
                            .padding(
                                top = 8.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        userScrollEnabled = false
                    ) {
                        itemsIndexed(
                            items = if (showAllItems) {
                                if (selectedLinkType == LinkType.TOP_LINKS) topLinks
                                else recentLinks
                            } else {
                                if (selectedLinkType == LinkType.TOP_LINKS) topLinks.take(4)
                                else recentLinks.take(4)
                            }
                        ) { index, link ->
                            LinkItemUI(link)

                            if (index == 3 && !showAllItems) {
                                showButton = true
                            }
                        }
                    }
                    if (showButton && !showAllItems) {
                        ButtonTransparent(
                            modifier = Modifier,
                            onClick = {
                                showAllItems = true
                            },
                            icon = R.drawable.icon_link,
                            btnText = "View all Links"
                        )
                    }

                    ColoredButton(
                        modifier = Modifier,
                        icon = R.drawable.icon_whatsapp,
                        btnText = "Talk with us",
                        onClick = {
                            viewModel.showMessage("Talk with us")
                        },
                        borderColor = Color(0x524AD15F),
                        backgroundColor = Color(0x664AD15F)
                    )

                    ColoredButton(
                        modifier = Modifier,
                        icon = R.drawable.icon_feedback,
                        btnText = "Frequently Asked Questions",
                        onClick = {
                            viewModel.showMessage("Frequently Asked Questions")
                        },
                        borderColor = Color(0x520E6FFF),
                        backgroundColor = Color(0x662196F3)
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonTransparent(modifier: Modifier, icon: Int, btnText: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp)),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = LocalContentColor.current
        ),
        interactionSource = NoRippleInteractionSource()
    ) {

        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color.Black,
            modifier = modifier
                .size(28.dp)
        )

        Spacer(modifier.width(12.dp))

        Text(
            text = btnText,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = figTreeFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )
    }
}

@Composable
fun ColoredButton(
    modifier: Modifier,
    icon: Int,
    btnText: String,
    onClick: () -> Unit,
    borderColor: Color,
    backgroundColor: Color
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, borderColor, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        interactionSource = NoRippleInteractionSource()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 12.dp),
                painter = painterResource(icon),
                tint = Color.Unspecified,
                contentDescription = null
            )

            Text(
                text = btnText,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = figTreeFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
        }
    }
}

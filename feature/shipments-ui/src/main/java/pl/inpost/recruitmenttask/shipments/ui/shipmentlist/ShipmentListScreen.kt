package pl.inpost.recruitmenttask.shipments.ui.shipmentlist

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.inpost.recruitmenttask.shipments.ui.R
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI
import pl.inpost.recruitmenttask.shipments.ui.shipmentlist.ShipmentListViewModel.UiEvent
import pl.inpost.recruitmenttask.shipments.ui.shipmentlist.ShipmentListViewModel.UiState
import pl.inpost.recruitmenttask.theme.Iron
import pl.inpost.recruitmenttask.theme.Mercury
import pl.inpost.recruitmenttask.theme.Typography
import pl.inpost.recruitmenttask.ui.extensions.formatStatusDateTime
import pl.inpost.recruitmenttask.common.translation.R as translationR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentListScreen(modifier: Modifier = Modifier) {
    val viewModel: ShipmentListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        state = pullRefreshState,
        isRefreshing = uiState.isRefreshing,
        onRefresh = { viewModel.sendUiEvent(UiEvent.RefreshShipments) },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = translationR.string.app_name),
                            style = Typography.titleMedium,
                        )
                    },
                )
            },
            content = { paddingValues ->
                ShipmentList(
                    modifier = modifier.padding(paddingValues),
                    uiState = uiState,
                )
            }
        )
    }
}

@Composable
private fun ShipmentList(
    modifier: Modifier = Modifier,
    uiState: UiState,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(
            items = uiState.shipments,
            key = { index: Int, item: ShipmentListItemUI ->
                when (item) {
                    is ShipmentListItemUI.HeaderUI -> item.status.ordinal
                    is ShipmentListItemUI.ShipmentUI -> item.number
                }
            },
        ) { index: Int, item: ShipmentListItemUI ->
            when (item) {
                is ShipmentListItemUI.HeaderUI -> {
                    HeaderItem(
                        isFirstItem = index == 0,
                        headerStringResId = item.status.nameStringResId
                    )
                }

                is ShipmentListItemUI.ShipmentUI -> {
                    ShipmentItem(item)
                }
            }
        }
    }
}

@Composable
private fun HeaderItem(
    isFirstItem: Boolean,
    @StringRes headerStringResId: Int,
    modifier: Modifier = Modifier,
) {
    val height = if (isFirstItem) 48.dp else 32.dp
    val paddingBottom = if (isFirstItem) 0.dp else 16.dp
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingBottom)
                .height(1.dp)
                .background(color = Mercury)
        )
        Text(
            text = stringResource(id = headerStringResId),
            style = Typography.headlineSmall,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingBottom
                )
        )
    }
}

@Composable
private fun ShipmentItem(
    item: ShipmentListItemUI.ShipmentUI,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
        ) {
            ParcelNumberSection(item.number)
            StatusSection(
                status = stringResource(item.status.nameStringResId),
                isStatusMessageAndDateTimeVisible = item.isStatusMessageAndDateTimeVisible,
                displayedStatusMessage = item.displayedStatusMessageStringRes
                    ?.let { stringResource(it) }
                    ?: "",
                displayedDateTimeMessage = item.displayedStatusDateTime?.formatStatusDateTime()
                    ?: "",
                modifier = Modifier.padding(top = 8.dp)
            )
            SenderSection(
                sender = item.displayedSender,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Iron, Color.Transparent),
                        endY = 15f
                    )
                )
        )
    }
}

@Composable
private fun ParcelNumberSection(
    number: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Text(
                text = stringResource(id = translationR.string.shipment_item_parcel_number_label)
                    .uppercase(Locale.current.platformLocale),
                style = Typography.labelMedium,
            )
            Text(
                text = number,
                style = Typography.headlineMedium,
            )
        }

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_locker),
            contentDescription = stringResource(id = translationR.string.shipment_item_icon_content_description),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
        )
    }
}

@Composable
private fun StatusSection(
    status: String,
    isStatusMessageAndDateTimeVisible: Boolean,
    displayedStatusMessage: String,
    displayedDateTimeMessage: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = modifier
                .wrapContentWidth()
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(id = translationR.string.shipment_item_parcel_status_label)
                    .uppercase(Locale.current.platformLocale),
                style = Typography.labelMedium,
                modifier = Modifier.wrapContentWidth(),
            )
            Text(
                text = status,
                style = Typography.titleMedium,
                modifier = Modifier.wrapContentWidth(),
            )
        }

        if (isStatusMessageAndDateTimeVisible) {
            Column(
                modifier = modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = displayedStatusMessage.uppercase(Locale.current.platformLocale),
                    style = Typography.labelMedium,
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.End),
                )
                Text(
                    text = displayedDateTimeMessage,
                    style = Typography.headlineMedium,
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.End),
                )
            }
        }
    }
}

@Composable
private fun SenderSection(
    sender: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = modifier
                .wrapContentWidth()
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(id = translationR.string.shipment_item_parcel_sender_label)
                    .uppercase(Locale.current.platformLocale),
                style = Typography.labelMedium,
                modifier = Modifier.wrapContentWidth()
            )
            Text(
                text = sender,
                style = Typography.titleMedium,
                modifier = Modifier.wrapContentWidth()
            )
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Bottom),
        ) {
            Text(
                text = stringResource(id = translationR.string.shipment_item_more_button)
                    .lowercase(Locale.current.platformLocale),
                style = Typography.titleSmall,
            )
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_rightward_arrow),
                contentDescription = stringResource(id = translationR.string.shipment_item_more_icon_content_description),
                modifier = Modifier.padding(start = 4.dp),
            )
        }
    }
}

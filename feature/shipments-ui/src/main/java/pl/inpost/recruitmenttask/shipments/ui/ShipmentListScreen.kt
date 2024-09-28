package pl.inpost.recruitmenttask.shipments.ui

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import pl.inpost.recruitmenttask.shipments.ui.ShipmentListViewModel.UiEvent
import pl.inpost.recruitmenttask.shipments.ui.ShipmentListViewModel.UiState
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentUI
import pl.inpost.recruitmenttask.theme.Concrete
import pl.inpost.recruitmenttask.theme.Iron
import pl.inpost.recruitmenttask.theme.Mercury
import pl.inpost.recruitmenttask.theme.Typography
import pl.inpost.recruitmenttask.theme.White
import pl.inpost.recruitmenttask.ui.extensions.formatStatusDateTime
import pl.inpost.recruitmenttask.common.translation.R as translationR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentListScreen(modifier: Modifier = Modifier) {
    val viewModel: ShipmentListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = translationR.string.app_name),
                        style = Typography.titleMedium,
                    )
                },
                modifier = Modifier.background(color = White)
            )
        },
        content = { paddingValues ->
            ShipmentList(
                modifier = modifier.padding(paddingValues),
                uiState = uiState,
                onSendUiEvent = { viewModel.sendUiEvent(UiEvent.RefreshShipments) }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShipmentList(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onSendUiEvent: () -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        state = pullRefreshState,
        isRefreshing = uiState.isRefreshing,
        onRefresh = onSendUiEvent,
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Concrete),
        ) {
            items(
                items = uiState.shipments,
                key = { item: ShipmentUI -> item.number },
            ) { item: ShipmentUI ->
                // TODO generate amd display headers, sort list, etc
//                HeaderItem(headerStringResId = translationR.string.header_item_ready_to_pickup)
                ShipmentItem(item)
            }
        }
    }
}

@Composable
private fun HeaderItem(
    @StringRes headerStringResId: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(1.dp)
                .background(color = Mercury)
        )
        Text(
            text = stringResource(id = headerStringResId),
            style = Typography.headlineSmall,
            modifier = Modifier
                .background(color = Concrete)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
    }
}

@Composable
private fun ShipmentItem(
    item: ShipmentUI,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(White)
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
                        colors = listOf(
                            Iron,
                            Color.Transparent
                        ),
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

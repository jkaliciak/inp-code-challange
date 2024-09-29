package pl.inpost.recruitmenttask.shipments.ui.shipmentlist.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.intl.Locale
import pl.inpost.recruitmenttask.common.translation.R
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI
import pl.inpost.recruitmenttask.theme.AppTheme
import pl.inpost.recruitmenttask.ui.extensions.formatStatusDateTime

@Composable
fun ShipmentItem(
    item: ShipmentListItemUI.ShipmentUI,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(
                    horizontal = AppTheme.dimensions.spacer20,
                    vertical = AppTheme.dimensions.spacer16
                ),
        ) {
            ParcelNumberSection(item.number)
            StatusSection(
                status = stringResource(item.status.nameStringResId),
                // TODO maybe refactor retrieving text (no logic in ui)
                isStatusMessageAndDateTimeVisible = item.isStatusMessageAndDateTimeVisible,
                displayedStatusMessage = item.displayedStatusMessageStringRes
                    ?.let { stringResource(it) }
                    ?: "",
                displayedDateTimeMessage = item.displayedStatusDateTime?.formatStatusDateTime()
                    ?: "",
                modifier = Modifier.padding(top = AppTheme.dimensions.spacer8)
            )
            SenderSection(
                sender = item.displayedSender,
                modifier = Modifier.padding(top = AppTheme.dimensions.spacer8)
            )
        }
        Divider()
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
                text = stringResource(id = R.string.shipment_item_parcel_number_label)
                    .uppercase(Locale.current.platformLocale),
                style = AppTheme.typography.subtitle,
            )
            Text(
                text = number,
                style = AppTheme.typography.h6,
            )
        }

        Image(
            imageVector = ImageVector.vectorResource(id = pl.inpost.recruitmenttask.shipments.ui.R.drawable.ic_locker),
            contentDescription = stringResource(id = R.string.shipment_item_icon_content_description),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = AppTheme.dimensions.spacer8)
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
                text = stringResource(id = R.string.shipment_item_parcel_status_label)
                    .uppercase(Locale.current.platformLocale),
                style = AppTheme.typography.subtitle,
                modifier = Modifier.wrapContentWidth(),
            )
            Text(
                text = status,
                style = AppTheme.typography.status,
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
                    style = AppTheme.typography.subtitle,
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.End),
                )
                Text(
                    text = displayedDateTimeMessage,
                    style = AppTheme.typography.date,
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
                text = stringResource(id = R.string.shipment_item_parcel_sender_label)
                    .uppercase(Locale.current.platformLocale),
                style = AppTheme.typography.subtitle,
                modifier = Modifier.wrapContentWidth()
            )
            Text(
                text = sender,
                style = AppTheme.typography.status,
                modifier = Modifier.wrapContentWidth()
            )
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Bottom),
        ) {
            Text(
                text = stringResource(id = R.string.shipment_item_more_button)
                    .lowercase(Locale.current.platformLocale),
                style = AppTheme.typography.h9,
            )
            Image(
                imageVector = ImageVector.vectorResource(id = pl.inpost.recruitmenttask.shipments.ui.R.drawable.ic_rightward_arrow),
                contentDescription = stringResource(id = R.string.shipment_item_more_icon_content_description),
                modifier = Modifier.padding(start = AppTheme.dimensions.spacer4),
            )
        }
    }
}
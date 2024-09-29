package pl.inpost.recruitmenttask.shipments.ui.shipmentlist.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI
import pl.inpost.recruitmenttask.shipments.ui.shipmentlist.ShipmentListViewModel

@Composable
fun ShipmentList(
    modifier: Modifier = Modifier,
    uiState: ShipmentListViewModel.UiState,
    onArchiveShipment: (ShipmentListItemUI.ShipmentUI) -> Unit,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(
            items = uiState.shipments,
            key = { _: Int, item: ShipmentListItemUI ->
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
                    SwipeToDismissContainer<ShipmentListItemUI.ShipmentUI>(
                        isDismissEnabled = item.operations.manualArchive,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        onDismiss = { onArchiveShipment(item) }
                    ) {
                        ShipmentItem(item)
                    }
                }
            }
        }
    }
}
package pl.inpost.recruitmenttask.shipments.ui.model

import pl.inpost.recruitmenttask.common.translation.R
import pl.inpost.recruitmenttask.shipments.domain.api.model.Shipment
import java.time.ZonedDateTime

sealed class ShipmentListItemUI {

    data class HeaderUI(
        val status: ShipmentStatusUI
    ) : ShipmentListItemUI()

    data class ShipmentUI(
        val number: String,
        val shipmentType: ShipmentTypeUI,
        val status: ShipmentStatusUI,
        val openCode: String?,
        val expiryDate: ZonedDateTime?,
        val storedDate: ZonedDateTime?,
        val pickUpDate: ZonedDateTime?,
        val receiver: CustomerUI?,
        val sender: CustomerUI?,
        val operations: OperationsUI,
        val eventLog: List<EventLogUI>
    ) : ShipmentListItemUI() {

        val isStatusMessageAndDateTimeVisible =
            status == ShipmentStatusUI.READY_TO_PICKUP || status == ShipmentStatusUI.DELIVERED

        val displayedStatusMessageStringRes: Int? = when (status) {
            ShipmentStatusUI.READY_TO_PICKUP -> R.string.shipment_item_date_time_status_awaiting_collection
            ShipmentStatusUI.DELIVERED -> R.string.status_delivered
            else -> null
        }

        val displayedStatusDateTime: ZonedDateTime? = when (status) {
            ShipmentStatusUI.READY_TO_PICKUP -> expiryDate
            ShipmentStatusUI.DELIVERED -> pickUpDate
            else -> null
        }

        val displayedSender: String = sender?.name
            ?: sender?.email
            ?: sender?.phoneNumber
            ?: ""
    }
}

fun Shipment.toUI() = ShipmentListItemUI.ShipmentUI(
    number = number,
    shipmentType = shipmentType.toUI(),
    status = status.toUI(),
    openCode = openCode,
    expiryDate = expiryDate,
    storedDate = storedDate,
    pickUpDate = pickUpDate,
    receiver = receiver?.toUI(),
    sender = sender?.toUI(),
    operations = operations.toUI(),
    eventLog = eventLog.toUI(),
)

fun List<Shipment>.toUI() = map { it.toUI() }
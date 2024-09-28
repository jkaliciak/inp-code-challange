package pl.inpost.recruitmenttask.shipments.ui.model

import pl.inpost.recruitmenttask.shipments.domain.api.model.Shipment
import java.time.ZonedDateTime

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
)

fun Shipment.toUI() = ShipmentUI(
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
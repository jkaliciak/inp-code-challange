package pl.inpost.recruitmenttask.shipments.domain.api.model

import java.time.ZonedDateTime

data class Shipment(
    val number: String,
    val shipmentType: ShipmentType,
    val status: ShipmentStatus,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: Customer?,
    val sender: Customer?,
    val operations: Operations,
    val eventLog: List<EventLog>,
) {
    val isValid: Boolean = number.isNotBlank() &&
            status != ShipmentStatus.UNKNOWN &&
            shipmentType != ShipmentType.UNKNOWN &&
            sender != null &&
            (!sender.name.isNullOrBlank() || !sender.email.isNullOrBlank() ||
                    !sender.phoneNumber.isNullOrBlank())
}

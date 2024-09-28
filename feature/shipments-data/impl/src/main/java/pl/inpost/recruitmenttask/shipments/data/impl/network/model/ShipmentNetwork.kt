package pl.inpost.recruitmenttask.shipments.data.impl.network.model

import pl.inpost.recruitmenttask.shipments.data.api.database.model.ShipmentEntity
import java.time.ZonedDateTime

data class ShipmentNetwork(
    val number: String,
    val shipmentType: String,
    val status: String,
    val eventLog: List<EventLogNetwork>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: CustomerNetwork?,
    val sender: CustomerNetwork?,
    val operations: OperationsNetwork,
)

fun ShipmentNetwork.toEntity() = ShipmentEntity(
    number = number,
    shipmentType = shipmentType,
    status = status,
    openCode = openCode,
    expiryDate = expiryDate,
    storedDate = storedDate,
    pickUpDate = pickUpDate,
    receiver = receiver?.toEntity(),
    sender = sender?.toEntity(),
    operations = operations.toEntity(),
)

fun List<ShipmentNetwork>.toEntity() = map { it.toEntity() }
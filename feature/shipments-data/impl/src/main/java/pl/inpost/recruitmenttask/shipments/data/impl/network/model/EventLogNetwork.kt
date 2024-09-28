package pl.inpost.recruitmenttask.shipments.data.impl.network.model

import pl.inpost.recruitmenttask.shipments.data.api.database.model.EventLogEntity
import java.time.ZonedDateTime

data class EventLogNetwork(
    val name: String,
    val date: ZonedDateTime
)

fun EventLogNetwork.toEntity(shipmentNumber: String) = EventLogEntity(
    shipmentNumber = shipmentNumber,
    name = name,
    date = date
)

fun List<EventLogNetwork>.toEntity(shipmentNumber: String) = map { it.toEntity(shipmentNumber) }
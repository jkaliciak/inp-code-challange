package pl.inpost.recruitmenttask.shipments.domain.api.model

import pl.inpost.recruitmenttask.shipments.data.api.database.model.EventLogEntity
import java.time.ZonedDateTime

data class EventLog(
    val name: String,
    val date: ZonedDateTime
)

fun EventLogEntity.toDomain() = EventLog(
    name = name,
    date = date
)

fun List<EventLogEntity>.toDomain() = map { it.toDomain() }
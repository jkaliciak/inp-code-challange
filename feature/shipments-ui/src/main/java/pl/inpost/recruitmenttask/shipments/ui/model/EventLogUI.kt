package pl.inpost.recruitmenttask.shipments.ui.model

import pl.inpost.recruitmenttask.shipments.domain.api.model.EventLog
import java.time.ZonedDateTime

data class EventLogUI(
    val name: String,
    val date: ZonedDateTime
)

fun EventLog.toUI() = EventLogUI(
    name = name,
    date = date,
)

fun List<EventLog>.toUI() = map { it.toUI() }
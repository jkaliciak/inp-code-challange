package pl.inpost.recruitmenttask.shipments.data.api.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "event_log")
data class EventLogEntity(
    @PrimaryKey val shipmentNumber: String,
    val name: String,
    val date: ZonedDateTime
)
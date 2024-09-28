package pl.inpost.recruitmenttask.shipments.data.api.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "shipment")
data class ShipmentEntity(
    @PrimaryKey val number: String,
    val shipmentType: String,
    val status: String,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    @Embedded("receiver") val receiver: CustomerEntity?,
    @Embedded("sender") val sender: CustomerEntity?,
    @Embedded val operations: OperationsEntity,
)

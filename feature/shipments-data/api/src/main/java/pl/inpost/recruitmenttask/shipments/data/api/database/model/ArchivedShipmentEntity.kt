package pl.inpost.recruitmenttask.shipments.data.api.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "archived_shipment")
data class ArchivedShipmentEntity(
    @PrimaryKey val shipmentNumber: String,
)
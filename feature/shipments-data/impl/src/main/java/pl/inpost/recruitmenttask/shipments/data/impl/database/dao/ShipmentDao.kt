package pl.inpost.recruitmenttask.shipments.data.impl.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.shipments.data.api.database.model.EventLogEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.ShipmentEntity

@Dao
interface ShipmentDao {

    @Query("SELECT * FROM shipment LEFT JOIN event_log ON shipment.number = event_log.shipmentNumber")
    fun getShipmentsAndEventLogs(): Flow<Map<ShipmentEntity, List<EventLogEntity>>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShipments(vararg shipments: ShipmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEventLogs(vararg eventLogs: EventLogEntity)
}
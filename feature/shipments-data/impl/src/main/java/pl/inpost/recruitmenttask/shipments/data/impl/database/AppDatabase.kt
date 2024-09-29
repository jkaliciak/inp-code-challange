package pl.inpost.recruitmenttask.shipments.data.impl.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.inpost.recruitmenttask.shipments.data.api.database.model.ArchivedShipmentEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.EventLogEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.ShipmentEntity
import pl.inpost.recruitmenttask.shipments.data.impl.database.converters.ZonedDateTimeConverter
import pl.inpost.recruitmenttask.shipments.data.impl.database.dao.ShipmentDao

@Database(
    entities = [
        ShipmentEntity::class,
        EventLogEntity::class,
        ArchivedShipmentEntity::class,
    ],
    version = 3
)
@TypeConverters(ZonedDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shipmentDao(): ShipmentDao
}
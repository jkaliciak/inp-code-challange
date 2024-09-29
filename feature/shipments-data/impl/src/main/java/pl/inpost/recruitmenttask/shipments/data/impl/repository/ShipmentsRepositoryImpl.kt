package pl.inpost.recruitmenttask.shipments.data.impl.repository

import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.data.api.database.model.ArchivedShipmentEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.EventLogEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.ShipmentEntity
import pl.inpost.recruitmenttask.shipments.data.api.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.shipments.data.impl.database.AppDatabase
import pl.inpost.recruitmenttask.shipments.data.impl.network.api.ShipmentApi
import pl.inpost.recruitmenttask.shipments.data.impl.network.model.toEntity

class ShipmentsRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val shipmentApi: ShipmentApi,
) : ShipmentsRepository {

    override fun observeShipments(): Flow<AppResult<Map<ShipmentEntity, List<EventLogEntity>>>> =
        appDatabase.shipmentDao().getShipmentsAndEventLogs()
            .flowOn(Dispatchers.IO)
            .map { AppResult.Success(it) }

    override suspend fun updateShipments() = withContext(Dispatchers.IO) {
        appDatabase.withTransaction {
            val response = shipmentApi.getShipments()

            val shipments = response.toEntity()
            appDatabase.shipmentDao().insertShipments(*shipments.toTypedArray())

            val eventLog = response.map { it.eventLog.toEntity(it.number) }
                .flatten()
            appDatabase.shipmentDao().insertEventLogs(*eventLog.toTypedArray())
        }
    }

    override suspend fun archiveShipment(number: String) = withContext(Dispatchers.IO) {
        val archivedShipmentEntity = ArchivedShipmentEntity(number)
        appDatabase.shipmentDao().insertArchivedShipments(archivedShipmentEntity)
    }
}



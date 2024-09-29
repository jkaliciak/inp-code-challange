package pl.inpost.recruitmenttask.shipments.data.api.repository

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.data.api.database.model.EventLogEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.ShipmentEntity

interface ShipmentsRepository {

    fun observeShipments(): Flow<AppResult<Map<ShipmentEntity, List<EventLogEntity>>>>

    suspend fun updateShipments()

    suspend fun archiveShipment(number: String)
}
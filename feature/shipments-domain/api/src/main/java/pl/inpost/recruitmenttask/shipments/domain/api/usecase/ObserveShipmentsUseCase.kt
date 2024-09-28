package pl.inpost.recruitmenttask.shipments.domain.api.usecase

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.domain.api.model.Shipment

interface ObserveShipmentsUseCase {
    fun observeShipments(): Flow<AppResult<List<Shipment>>>
}
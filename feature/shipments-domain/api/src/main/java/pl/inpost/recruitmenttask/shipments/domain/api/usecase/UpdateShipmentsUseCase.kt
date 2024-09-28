package pl.inpost.recruitmenttask.shipments.domain.api.usecase

import pl.inpost.recruitmenttask.data.model.AppResult

interface UpdateShipmentsUseCase {
    suspend fun updateShipments(): AppResult<Unit>
}
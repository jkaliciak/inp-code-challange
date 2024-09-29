package pl.inpost.recruitmenttask.shipments.domain.api.usecase

import pl.inpost.recruitmenttask.data.model.AppResult

interface ArchiveShipmentUseCase {
    suspend fun archiveShipment(number: String): AppResult<Unit>
}
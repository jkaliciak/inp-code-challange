package pl.inpost.recruitmenttask.shipments.domain.impl.usecase

import kotlinx.coroutines.CoroutineDispatcher
import pl.inpost.recruitmenttask.common.domain.SuspendUseCase
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.data.api.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.shipments.domain.impl.usecase.ArchiveShipmentUseCaseImpl.ArchiveShipmentParams

class ArchiveShipmentUseCaseImpl(
    private val shipmentsRepository: ShipmentsRepository,
    coroutineDispatcher: CoroutineDispatcher
) : ArchiveShipmentUseCase, SuspendUseCase<ArchiveShipmentParams, Unit>(coroutineDispatcher) {

    override suspend fun archiveShipment(number: String): AppResult<Unit> =
        invoke(ArchiveShipmentParams(number))

    override suspend fun execute(parameters: ArchiveShipmentParams): Unit =
        shipmentsRepository.archiveShipment(number = parameters.number)

    data class ArchiveShipmentParams(val number: String)
}
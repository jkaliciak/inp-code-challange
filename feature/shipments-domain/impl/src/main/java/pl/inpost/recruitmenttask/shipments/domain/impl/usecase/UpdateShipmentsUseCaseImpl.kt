package pl.inpost.recruitmenttask.shipments.domain.impl.usecase

import kotlinx.coroutines.CoroutineDispatcher
import pl.inpost.recruitmenttask.common.domain.SuspendUseCase
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.data.api.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.UpdateShipmentsUseCase

class UpdateShipmentsUseCaseImpl(
    private val shipmentsRepository: ShipmentsRepository,
    coroutineDispatcher: CoroutineDispatcher
) : UpdateShipmentsUseCase, SuspendUseCase<Unit, Unit>(coroutineDispatcher) {

    override suspend fun updateShipments(): AppResult<Unit> = invoke(Unit)

    override suspend fun execute(parameters: Unit): Unit = shipmentsRepository.updateShipments()
}
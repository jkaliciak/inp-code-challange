package pl.inpost.recruitmenttask.shipments.domain.impl.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.inpost.recruitmenttask.common.domain.FlowUseCase
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.data.api.database.model.EventLogEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.ShipmentEntity
import pl.inpost.recruitmenttask.shipments.data.api.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.shipments.domain.api.model.Shipment
import pl.inpost.recruitmenttask.shipments.domain.api.model.ShipmentStatus.Companion.toShipmentStatus
import pl.inpost.recruitmenttask.shipments.domain.api.model.ShipmentType.Companion.toShipmentType
import pl.inpost.recruitmenttask.shipments.domain.api.model.toDomain
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ObserveShipmentsUseCase

class ObserveShipmentsUseCaseImpl(
    private val shipmentsRepository: ShipmentsRepository,
    coroutineDispatcher: CoroutineDispatcher
) : ObserveShipmentsUseCase, FlowUseCase<Unit, List<Shipment>>(coroutineDispatcher) {

    override fun observeShipments(): Flow<AppResult<List<Shipment>>> = invoke()

    override fun execute(parameters: Unit?): Flow<AppResult<List<Shipment>>> =
        shipmentsRepository.observeShipments().map {
            when (it) {
                is AppResult.Loading -> AppResult.Loading(it.isLoading)

                is AppResult.Success -> {
                    val shipments = it.data.toDomain()
                        .filter { shipment -> shipment.isValid }
                    AppResult.Success(shipments)
                }

                is AppResult.Error -> AppResult.Error(it.throwable)
            }
        }

    private fun Map<ShipmentEntity, List<EventLogEntity>>.toDomain(): List<Shipment> =
        map { entry ->
            val eventLog = entry.value.toDomain()
            Shipment(
                number = entry.key.number,
                shipmentType = entry.key.shipmentType.toShipmentType(),
                status = entry.key.status.toShipmentStatus(),
                openCode = entry.key.openCode,
                expiryDate = entry.key.expiryDate,
                storedDate = entry.key.storedDate,
                pickUpDate = entry.key.pickUpDate,
                receiver = entry.key.receiver?.toDomain(),
                sender = entry.key.sender?.toDomain(),
                operations = entry.key.operations.toDomain(),
                eventLog = eventLog
            )
        }
}

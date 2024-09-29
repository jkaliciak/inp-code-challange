package pl.inpost.recruitmenttask.shipments.domain.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import pl.inpost.recruitmenttask.shipments.data.api.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ObserveShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.UpdateShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.impl.usecase.ArchiveShipmentUseCaseImpl
import pl.inpost.recruitmenttask.shipments.domain.impl.usecase.ObserveShipmentsUseCaseImpl
import pl.inpost.recruitmenttask.shipments.domain.impl.usecase.UpdateShipmentsUseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ShipmentsDomainModule {

    @Singleton
    @Provides
    fun observeShipmentsUseCase(
        shipmentsRepository: ShipmentsRepository
    ): ObserveShipmentsUseCase = ObserveShipmentsUseCaseImpl(
        shipmentsRepository = shipmentsRepository,
        coroutineDispatcher = Dispatchers.IO
    )

    @Singleton
    @Provides
    fun updateShipmentsUseCase(
        shipmentsRepository: ShipmentsRepository
    ): UpdateShipmentsUseCase = UpdateShipmentsUseCaseImpl(
        shipmentsRepository = shipmentsRepository,
        coroutineDispatcher = Dispatchers.IO
    )

    @Singleton
    @Provides
    fun archiveShipmentUseCase(
        shipmentsRepository: ShipmentsRepository
    ): ArchiveShipmentUseCase = ArchiveShipmentUseCaseImpl(
        shipmentsRepository = shipmentsRepository,
        coroutineDispatcher = Dispatchers.IO
    )
}

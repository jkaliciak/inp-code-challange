package pl.inpost.recruitmenttask.shipments.domain.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import pl.inpost.recruitmenttask.shipments.data.api.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ObserveShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.UpdateShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.impl.usecase.ObserveShipmentsUseCaseImpl
import pl.inpost.recruitmenttask.shipments.domain.impl.usecase.UpdateShipmentsUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
class ShipmentsDomainModule {

    @Provides
    fun observeShipmentsUseCase(
        shipmentsRepository: ShipmentsRepository
    ): ObserveShipmentsUseCase = ObserveShipmentsUseCaseImpl(
        shipmentsRepository = shipmentsRepository,
        coroutineDispatcher = Dispatchers.IO
    )

    @Provides
    fun updateShipmentsUseCase(
        shipmentsRepository: ShipmentsRepository
    ): UpdateShipmentsUseCase = UpdateShipmentsUseCaseImpl(
        shipmentsRepository = shipmentsRepository,
        coroutineDispatcher = Dispatchers.IO
    )
}

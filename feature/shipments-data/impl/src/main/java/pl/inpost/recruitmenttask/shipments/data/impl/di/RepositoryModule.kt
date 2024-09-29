package pl.inpost.recruitmenttask.shipments.data.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.shipments.data.api.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.shipments.data.impl.database.AppDatabase
import pl.inpost.recruitmenttask.shipments.data.impl.network.api.ShipmentApi
import pl.inpost.recruitmenttask.shipments.data.impl.repository.ShipmentsRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun shipmentsRepository(
        appDatabase: AppDatabase,
        shipmentApi: ShipmentApi,
    ): ShipmentsRepository = ShipmentsRepositoryImpl(
        appDatabase = appDatabase,
        shipmentApi = shipmentApi,
    )
}

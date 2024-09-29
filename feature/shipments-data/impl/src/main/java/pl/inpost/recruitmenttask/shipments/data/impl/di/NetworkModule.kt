package pl.inpost.recruitmenttask.shipments.data.impl.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.shipments.data.impl.network.ApiTypeAdapter
import pl.inpost.recruitmenttask.shipments.data.impl.network.api.MockShipmentApi
import pl.inpost.recruitmenttask.shipments.data.impl.network.api.ShipmentApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun shipmentApi(
        @ApplicationContext context: Context,
        apiTypeAdapter: ApiTypeAdapter
    ): ShipmentApi = MockShipmentApi(
        context = context,
        apiTypeAdapter = apiTypeAdapter
    )
}

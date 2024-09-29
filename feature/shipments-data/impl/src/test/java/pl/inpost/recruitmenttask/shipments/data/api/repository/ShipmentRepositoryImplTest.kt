package pl.inpost.recruitmenttask.shipments.data.api.repository

import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import pl.inpost.recruitmenttask.shipments.data.impl.database.AppDatabase
import pl.inpost.recruitmenttask.shipments.data.impl.network.api.ShipmentApi
import pl.inpost.recruitmenttask.shipments.data.impl.repository.ShipmentsRepositoryImpl
import kotlinx.coroutines.test.StandardTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class ShipmentRepositoryImplTest {
    val testDispatcher: TestDispatcher = StandardTestDispatcher()

    private val appDatabase: AppDatabase = mockk()
    private val shipmentApi: ShipmentApi = mockk()
    private lateinit var underTest: ShipmentsRepositoryImpl

    @Before
    fun before() {
        underTest = ShipmentsRepositoryImpl(appDatabase, shipmentApi)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun should() = testDispatcher.runTest {
        underTest.updateShipments()
    }
}
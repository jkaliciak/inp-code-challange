package pl.inpost.recruitmenttask.shipments.domain.impl.usecase

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.data.api.database.model.CustomerEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.EventLogEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.OperationsEntity
import pl.inpost.recruitmenttask.shipments.data.api.database.model.ShipmentEntity
import pl.inpost.recruitmenttask.shipments.data.api.repository.ShipmentsRepository
import java.time.ZonedDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ObserveShipmentsUseCaseImplTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val shipmentsRepository: ShipmentsRepository = mockk()
    private lateinit var underTest: ObserveShipmentsUseCaseImpl

    @Before
    fun before() {
        underTest = ObserveShipmentsUseCaseImpl(shipmentsRepository, coroutineRule.dispatcher)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `should return success when use case executed and repository was empty`() = runTest {
        coEvery { shipmentsRepository.observeShipments() } returns MutableStateFlow(
            AppResult.Success(emptyMap())
        )

        underTest.observeShipments().test {
            assertTrue(awaitItem() is AppResult.Success)
        }
    }

    @Test
    fun `should return success when use case executed and repository was non-empty but all shipments are invalid`() =
        runTest {
            val map = mapOf(createInvalidShipment() to createEventLog())
            coEvery { shipmentsRepository.observeShipments() } returns MutableStateFlow(
                AppResult.Success(map)
            )

            underTest.observeShipments().test {
                val awaitItem = awaitItem()
                assertTrue(awaitItem is AppResult.Success)
                assertEquals(expected = 0, actual = awaitItem.data.size)
            }
        }

    @Test
    fun `should return success when use case executed and repository was non-empty but all shipments are valid`() =
        runTest {
            val map = mapOf(createValidShipment() to createEventLog())
            coEvery { shipmentsRepository.observeShipments() } returns MutableStateFlow(
                AppResult.Success(map)
            )

            underTest.observeShipments().test {
                val awaitItem = awaitItem()
                assertTrue(awaitItem is AppResult.Success)
                assertEquals(expected = map.keys.size, actual = awaitItem.data.size)
            }
        }

    @Test
    fun `should return error when use case executed and failed`() = runTest {
        val throwable = Exception()
        coEvery { shipmentsRepository.observeShipments() } returns MutableStateFlow(
            AppResult.Error(throwable)
        )

        underTest.observeShipments().test {
            assertTrue { awaitItem() is AppResult.Error }
        }
    }

    @Test
    fun `should trigger ShipmentsRepository updateShipments when use case executed`() = runTest {
        val map = mapOf(createValidShipment() to createEventLog())
        coEvery { shipmentsRepository.observeShipments() } returns MutableStateFlow(
            AppResult.Success(map)
        )

        underTest.observeShipments()

        coVerify(exactly = 1) { shipmentsRepository.observeShipments() }
        confirmVerified(shipmentsRepository)
    }

    private fun createInvalidShipment() = ShipmentEntity(
        number = "gravida",
        shipmentType = "tellus",
        status = "comprehensam",
        openCode = null,
        expiryDate = null,
        storedDate = null,
        pickUpDate = null,
        receiver = null,
        sender = null,
        operations = OperationsEntity(
            manualArchive = false,
            delete = false,
            collect = false,
            highlight = false,
            expandAvizo = false,
            endOfWeekCollection = false
        )
    )

    private fun createValidShipment() = ShipmentEntity(
        number = "gravida",
        shipmentType = "PARCEL_LOCKER",
        status = "CREATED",
        openCode = null,
        expiryDate = null,
        storedDate = null,
        pickUpDate = null,
        receiver = null,
        sender = CustomerEntity(
            email = "test@inpost.pl",
            phoneNumber = "1234567890",
            name = "Darth Vader"
        ),
        operations = OperationsEntity(
            manualArchive = false,
            delete = false,
            collect = false,
            highlight = false,
            expandAvizo = false,
            endOfWeekCollection = false
        )
    )

    private fun createEventLog() = listOf(
        EventLogEntity(
            shipmentNumber = "potenti",
            name = "Raul Emerson",
            date = ZonedDateTime.now()
        )
    )
}

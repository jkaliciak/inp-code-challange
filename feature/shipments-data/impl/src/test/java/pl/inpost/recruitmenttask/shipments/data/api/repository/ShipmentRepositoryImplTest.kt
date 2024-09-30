package pl.inpost.recruitmenttask.shipments.data.api.repository

import androidx.room.withTransaction
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.recruitmenttask.shipments.data.impl.database.AppDatabase
import pl.inpost.recruitmenttask.shipments.data.impl.database.dao.ShipmentDao
import pl.inpost.recruitmenttask.shipments.data.impl.network.api.ShipmentApi
import pl.inpost.recruitmenttask.shipments.data.impl.network.model.OperationsNetwork
import pl.inpost.recruitmenttask.shipments.data.impl.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.shipments.data.impl.repository.ShipmentsRepositoryImpl

class ShipmentRepositoryImplTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val appDatabase: AppDatabase = mockk(relaxed = true)
    private val shipmentDao: ShipmentDao = mockk(relaxed = true)
    private val shipmentApi: ShipmentApi = mockk(relaxed = true)
    private lateinit var underTest: ShipmentsRepositoryImpl

    @Before
    fun before() {
        underTest = ShipmentsRepositoryImpl(appDatabase, shipmentApi, coroutineRule.dispatcher)
        coEvery { appDatabase.shipmentDao() } returns shipmentDao
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `should fetch shipments from API and add shipments and event log to database when shipments update triggered`() =
        runTest {
            mockkStatic("androidx.room.RoomDatabaseKt")
            val transactionLambda = slot<suspend () -> Unit>()
            coEvery { appDatabase.withTransaction(capture(transactionLambda)) } coAnswers {
                transactionLambda.captured.invoke()
            }
            coEvery { shipmentApi.getShipments() } returns createShipments()
            every { shipmentDao.insertShipments(any()) } just Runs
            every { shipmentDao.insertEventLogs(any()) } just Runs

            underTest.updateShipments()

            coVerify(exactly = 1) { shipmentApi.getShipments() }
            coVerify(exactly = 2) { appDatabase.shipmentDao() }
            coVerify(exactly = 1) { shipmentDao.insertShipments(*anyVararg()) }
            coVerify(exactly = 1) { shipmentDao.insertShipments(*anyVararg()) }
            coVerify(exactly = 1) { shipmentDao.insertEventLogs(*anyVararg()) }
            confirmVerified(shipmentApi)
            confirmVerified(appDatabase)
        }

    @Test(expected = Exception::class)
    fun `should throw exception when API throws exception`() =
        runTest {
            mockkStatic("androidx.room.RoomDatabaseKt")
            val transactionLambda = slot<suspend () -> Unit>()
            coEvery { appDatabase.withTransaction(capture(transactionLambda)) } coAnswers {
                transactionLambda.captured.invoke()
            }
            coEvery { shipmentApi.getShipments() } throws Exception()
            every { shipmentDao.insertShipments(any()) } just Runs
            every { shipmentDao.insertEventLogs(any()) } just Runs

            underTest.updateShipments()
    }

    @Test(expected = Exception::class)
    fun `should throw exception when shipments database insert throws exception`() =
        runTest {
            mockkStatic("androidx.room.RoomDatabaseKt")
            val transactionLambda = slot<suspend () -> Unit>()
            coEvery { appDatabase.withTransaction(capture(transactionLambda)) } coAnswers {
                transactionLambda.captured.invoke()
            }
            coEvery { shipmentApi.getShipments() } returns createShipments()
            every { shipmentDao.insertShipments(any()) } throws Exception()
            every { shipmentDao.insertEventLogs(any()) } just Runs

            underTest.updateShipments()
        }

    private fun createShipments() = listOf(
        ShipmentNetwork(
            number = "hendrerit",
            shipmentType = "possit",
            status = "sit",
            eventLog = listOf(),
            openCode = null,
            expiryDate = null,
            storedDate = null,
            pickUpDate = null,
            receiver = null,
            sender = null,
            operations = OperationsNetwork(
                manualArchive = false,
                delete = false,
                collect = false,
                highlight = false,
                expandAvizo = false,
                endOfWeekCollection = false
            )
        )
    )
}